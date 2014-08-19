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
/**
 * The PubSub class consists of 
 * 
 *  - A main method, which publishes several messages to a topic
 *    and creates a subscriber and a temporary topic on which
 *    to receive replies
 *  - A TextListener class that receives the replies
 * 
 * Run this program in conjunction with ReplyBeanApp.  
 * Specify a topic name on the command line when you run the
 * program.  By default, the program sends one message.  
 * Specify a number after the topic name to send that number 
 * of messages.
 *
 * To end the program, enter Q or q on the command line.
 */
import javax.jms.*;
import javax.naming.*;
import java.io.*;

public class PubSub {

	/**
	 * Main method.
	 * 
	 * @param args
	 *            the topic used by the example and, optionally, the number of
	 *            messages to send
	 */
	public static void main(String[] args) {
		String topicName = null;
		Context jndiContext = null;
		TopicConnectionFactory topicConnectionFactory = null;
		TopicConnection topicConnection = null;
		TopicSession topicSession = null;
		Topic topic = null;
		Topic replyTopic = null;
		TopicPublisher topicPublisher = null;
		TopicSubscriber topicSubscriber = null;
		TextMessage message = null;
		InputStreamReader inputStreamReader = null;
		char answer = '\0';
		final int NUM_MSGS;

		if ((args.length < 1) || (args.length > 2)) {
			System.out.println("Usage: java " + "PubSub <topic-name> " + "[<number-of-messages>]");
			System.exit(1);
		}
		topicName = new String(args[0]);
		System.out.println("Topic name is " + topicName);
		if (args.length == 2) {
			NUM_MSGS = (new Integer(args[1])).intValue();
		} else {
			NUM_MSGS = 1;
		}

		/*
		 * Create a JNDI API InitialContext object if none exists yet.
		 */
		try {
			jndiContext = new InitialContext();
		} catch (NamingException e) {
			System.out.println("Could not create JNDI API " + "context: " + e.toString());
			e.printStackTrace();
			System.exit(1);
		}

		/*
		 * Look up connection factory and topic. If either does not exist, exit.
		 */
		try {
			topicConnectionFactory = (TopicConnectionFactory) jndiContext.lookup("jms/TopicConnectionFactory");
			topic = (Topic) jndiContext.lookup(topicName);
		} catch (NamingException e) {
			System.out.println("JNDI API lookup failed: " + e.toString());
			e.printStackTrace();
			System.exit(1);
		}

		/*
		 * Create connection. Create session from connection; false means
		 * session is not transacted. Create publisher, temporary topic, and
		 * text message, setting JMSReplyTo field to temporary topic and setting
		 * an id property. Send messages, varying text slightly. Create
		 * subscriber and set message listener to receive replies. When all
		 * messages have been received, enter Q to quit. Finally, close
		 * connection.
		 */
		try {
			topicConnection = topicConnectionFactory.createTopicConnection();
			topicSession = topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
			topicPublisher = topicSession.createPublisher(topic);
			replyTopic = topicSession.createTemporaryTopic();
			message = topicSession.createTextMessage();
			message.setJMSReplyTo(replyTopic);
			int id = 1;
			for (int i = 0; i < NUM_MSGS; i++) {
				message.setText("This is message " + id);
				message.setIntProperty("id", id);
				System.out.println("Publishing message: " + message.getText());
				topicPublisher.publish(message);
				id++;
			}

			topicSubscriber = topicSession.createSubscriber(replyTopic);
			topicSubscriber.setMessageListener(new TextListener());
			topicConnection.start();
			System.out.println("To end program, enter Q or q, " + "then <return>");
			inputStreamReader = new InputStreamReader(System.in);
			while (!((answer == 'q') || (answer == 'Q'))) {
				try {
					answer = (char) inputStreamReader.read();
				} catch (IOException e) {
					System.out.println("I/O exception: " + e.toString());
				}
			}
		} catch (JMSException e) {
			System.out.println("Exception occurred: " + e.toString());
		} finally {
			if (topicConnection != null) {
				try {
					topicConnection.close();
				} catch (JMSException e) {
				}
			}
		}
	}

	/**
	 * The TextListener class implements the MessageListener interface by
	 * defining an onMessage method that displays the contents and id property
	 * of a TextMessage.
	 * 
	 * This class acts as the listener for the PubSub class.
	 */
	static class TextListener implements MessageListener {

		/**
		 * Casts the message to a TextMessage and displays its text.
		 * 
		 * @param message
		 *            the incoming message
		 */
		public void onMessage(Message message) {
			TextMessage msg = null;
			String txt = null;
			int id = 0;

			try {
				if (message instanceof TextMessage) {
					msg = (TextMessage) message;
					id = msg.getIntProperty("id");
					txt = msg.getText();
					System.out.println("Reading message: id=" + id + ", text=" + txt);
				} else {
					System.out.println("Message of wrong type: " + message.getClass().getName());
				}
			} catch (JMSException e) {
				System.out.println("JMSException in onMessage():" + e.toString());
			} catch (Throwable t) {
				System.out.println("Exception in onMessage():" + t.getMessage());
			}
		}
	}
}
