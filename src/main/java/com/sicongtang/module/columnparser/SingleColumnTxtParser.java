package com.sicongtang.module.columnparser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;


/**
 * 
 * @author BobbyTang
 *
 */
public class SingleColumnTxtParser implements ISingleColumnParser{
	private static Logger logger = Logger.getLogger(SingleColumnTxtParser.class);

	public int parseFileCount(File vcodeFile) {
		int count = 0;
		FileReader reader = null;
		LineNumberReader buffer = null;
		try {
			reader = new FileReader(vcodeFile);
			buffer = new LineNumberReader(reader);

			buffer.skip(Long.MAX_VALUE);

			count = buffer.getLineNumber();
			
			logger.info("The total line number of this file is " + count);

		} catch (Exception e) {
			logger.error("Encounter error while reading file", e);
		} finally {
			try {
				if (buffer != null) {
					buffer.close();
				}
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				logger.error("Encounter error while closing file buffer", e);
			}
		}

		return count;
	}

	public List<String> parseFile(File vcodeFile, int startIndex, int limit) {
		List<String> stringList = new ArrayList<String>();
		
		FileReader reader = null;
		LineNumberReader buffer = null;
		try {
			reader = new FileReader(vcodeFile);
			buffer = new LineNumberReader(reader);
			String line = null;
			
			//skip line
			for(int i=0; i< startIndex; i++) {
				buffer.readLine();
			}
			
			//start read
			for(int i=0; i< limit; i++) {
				line = buffer.readLine();
				if(line != null) {
					line = line.trim();
					if (!"".equals(line)) {
						stringList.add(line);
					}
				}
			}

		} catch (Exception e) {
			logger.error("Encounter error while reading file", e);
		} finally {
			try {
				if (buffer != null) {
					buffer.close();
				}
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				logger.error("Encounter error while closing file buffer", e);
			}
		}

		return stringList;
	}

}
