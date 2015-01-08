package me.sicongtang.module.columnparser;

import java.io.File;
import java.util.List;

/**
 * 
 * @author BobbyTang
 *
 */
public interface ISingleColumnParser {
	int parseFileCount(File file);
	List<String> parseFile(File file, int startIndex, int limit);
}
