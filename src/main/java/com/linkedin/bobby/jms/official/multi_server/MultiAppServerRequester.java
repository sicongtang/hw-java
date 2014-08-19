package com.linkedin.bobby.jms.official.multi_server;

/*
 *
 * Copyright 2002 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * Redistribution and use in source and binary forms, with or
 * without modification, are permitted provided that the following
 * conditions are met:
 * 
 * - Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 * 
 * - Redistribution in binary form must reproduce the above
 *   copyright notice, this list of conditions and the following
 *   disclaimer in the documentation and/or other materials
 *   provided with the distribution.
 * 
 * Neither the name of Sun Microsystems, Inc. or the names of
 * contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.
 * 
 * This software is provided "AS IS," without a warranty of any
 * kind. ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND
 * WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY
 * EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY
 * DAMAGES OR LIABILITIES SUFFERED BY LICENSEE AS A RESULT OF OR
 * RELATING TO USE, MODIFICATION OR DISTRIBUTION OF THIS SOFTWARE OR
 * ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE
 * FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT,
 * SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF
 * THE USE OF OR INABILITY TO USE THIS SOFTWARE, EVEN IF SUN HAS
 * BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 * 
 * You acknowledge that this software is not designed, licensed or
 * intended for use in the design, construction, operation or
 * maintenance of any nuclear facility.
 * 
 */
import javax.jms.*;
import javax.naming.*;
import java.util.*;

/**
 * The MultiAppServerRequester class is the client program for this J2EE
 * application. It publishes a message to two different JMS providers and waits
 * for a reply.
 */
public class MultiAppServerRequester {
	static Object waitUntilDone = new Object();
	static SortedSet outstandingRequests1 = Collections.synchronizedSortedSet(new TreeSet());
	static SortedSet outstandingRequests2 = Collections.synchronizedSortedSet(new TreeSet());

	public static void main(String[] args) {
		InitialContext ic = null;
		TopicConnectionFactory tcf1 = null; // App Server 1
		TopicConnectionFactory tcf2 = null; // App Server 2
		TopicConnection tc1 = null;
		TopicConnection tc2 = null;
		TopicSession pubSession1 = null;
		TopicSession pubSession2 = null;
		TopicPublisher topicPublisher1 = null;
		TopicPublisher topicPublisher2 = null;
		Topic pTopic = null;
		TemporaryTopic replyTopic1 = null;
		TemporaryTopic replyTopic2 = null;
		TopicSession subSession1 = null;
		TopicSession subSession2 = null;
		TopicSubscriber topicSubscriber1 = null;
		TopicSubscriber topicSubscriber2 = null;
		TextMessage message = null;

		/*
		 * Create a JNDI API InitialContext object.
		 */
		try {
			ic = new InitialContext();
		} catch (NamingException e) {
			System.err.println("Could not create JNDI API " + "context: " + e.toString());
			e.printStackTrace();
			System.exit(1);
		}

		/*
		 * Look up connection factories and topic. If any do not exist, exit.
		 */
		try {
			tcf1 = (TopicConnectionFactory) ic.lookup("java:comp/env/jms/TopicConnectionFactory1");
			tcf2 = (TopicConnectionFactory) ic.lookup("java:comp/env/jms/TopicConnectionFactory2");
			pTopic = (Topic) ic.lookup("java:comp/env/jms/PTopic");
		} catch (NamingException e) {
			System.err.println("JNDI API lookup failed: " + e.toString());
			e.printStackTrace();
			System.exit(1);
		}

		try {
			// Create two TopicConnections.
			tc1 = tcf1.createTopicConnection();
			tc2 = tcf2.createTopicConnection();

			// Create TopicSessions for publishers.
			pubSession1 = tc1.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
			pubSession2 = tc2.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

			// Create temporary topics for replies.
			replyTopic1 = pubSession1.createTemporaryTopic();
			replyTopic2 = pubSession2.createTemporaryTopic();

			// Create TopicSessions for subscribers.
			subSession1 = tc1.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
			subSession2 = tc2.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

			/*
			 * Create subscribers, set message listeners, and start connections.
			 */
			topicSubscriber1 = subSession1.createSubscriber(replyTopic1);
			topicSubscriber2 = subSession2.createSubscriber(replyTopic2);
			topicSubscriber1.setMessageListener(new ReplyListener(outstandingRequests1));
			topicSubscriber2.setMessageListener(new ReplyListener(outstandingRequests2));
			tc1.start();
			tc2.start();

			// Create publishers.
			topicPublisher1 = pubSession1.createPublisher(pTopic);
			topicPublisher2 = pubSession2.createPublisher(pTopic);

			/*
			 * Create and send two sets of messages, one set to each app server,
			 * at 1.5-second intervals. For each message, set the JMSReplyTo
			 * message header to a reply topic, and set an id property. Add the
			 * message ID to the list of outstanding requests for the message
			 * listener.
			 */
			message = pubSession1.createTextMessage();
			int id = 1;
			for (int i = 0; i < 5; i++) {
				message.setJMSReplyTo(replyTopic1);
				message.setIntProperty("id", id);
				message.setText("text: id=" + id + " to local app server");
				topicPublisher1.publish(message);
				System.out.println("Published message: " + message.getText());
				outstandingRequests1.add(message.getJMSMessageID());
				id++;
				Thread.sleep(1500);
				message.setJMSReplyTo(replyTopic2);
				message.setIntProperty("id", id);
				message.setText("text: id=" + id + " to remote app server");
				try {
					topicPublisher2.publish(message);
					System.out.println("Published message: " + message.getText());
					outstandingRequests2.add(message.getJMSMessageID());
				} catch (Exception e) {
					System.err.println("Exception: Caught " + "failed publish to " + "topicConnectionFactory2");
					e.printStackTrace();
				}
				id++;
				Thread.sleep(1500);
			}

			/*
			 * Wait for replies.
			 */
			System.out.println("Waiting for " + outstandingRequests1.size() + " message(s) " + "from local app server");
			System.out
					.println("Waiting for " + outstandingRequests2.size() + " message(s) " + "from remote app server");
			while (outstandingRequests1.size() > 0 || outstandingRequests2.size() > 0) {
				synchronized (waitUntilDone) {
					waitUntilDone.wait();
				}
			}
			System.out.println("Finished");

		} catch (Exception e) {
			System.err.println("Exception occurred: " + e.toString());
			e.printStackTrace();
		} finally {
			System.out.println("Closing connection 1");
			if (tc1 != null) {
				try {
					tc1.close();
				} catch (Exception e) {
					System.err.println("Error closing " + "connection 1: " + e.toString());
				}
			}
			System.out.println("Closing connection 2");
			if (tc2 != null) {
				try {
					tc2.close();
				} catch (Exception e) {
					System.err.println("Error closing " + "connection 2: " + e.toString());
				}
			}
			System.exit(0);
		}
	}

	/**
	 * The ReplyListener class is instantiated with a set of outstanding
	 * requests.
	 */
	static class ReplyListener implements MessageListener {
		SortedSet outstandingRequests = null;

		/**
		 * Constructor for ReplyListener class.
		 * 
		 * @param outstandingRequests
		 *            set of outstanding requests
		 */
		ReplyListener(SortedSet outstandingRequests) {
			this.outstandingRequests = outstandingRequests;
		}

		/**
		 * onMessage method, which displays the contents of the id property and
		 * text and uses the JMSCorrelationID to remove from the list of
		 * outstanding requests the message to which this message is a reply. If
		 * this is the last message, it notifies the client.
		 * 
		 * @param message
		 *            the incoming message
		 */
		public void onMessage(Message message) {
			TextMessage tmsg = (TextMessage) message;
			String txt = null;
			int id = 0;
			String correlationID = null;

			try {
				id = tmsg.getIntProperty("id");
				txt = tmsg.getText();
				correlationID = tmsg.getJMSCorrelationID();
			} catch (JMSException e) {
				System.err.println("ReplyListener.onMessage: " + "JMSException: " + e.toString());
			}
			System.out.println("ReplyListener: Received " + "message: id=" + id + ", text=" + txt);
			outstandingRequests.remove(correlationID);

			if (outstandingRequests.size() == 0) {
				synchronized (waitUntilDone) {
					waitUntilDone.notify();
				}
			} else {
				System.out.println("ReplyListener: Waiting " + "for " + outstandingRequests.size() + " message(s)");
			}
		}
	}
}
