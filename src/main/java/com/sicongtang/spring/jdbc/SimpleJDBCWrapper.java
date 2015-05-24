package com.sicongtang.spring.jdbc;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

public class SimpleJDBCWrapper extends SimpleJdbcTemplate {

	public SimpleJDBCWrapper(DataSource datasource) {
		super(datasource);
	}

	public void batchUpdateInChunk(String sql, List<Object[]> batchArgs, int batchSize) {
		if (batchArgs == null || batchArgs.size() == 0) {
			throw new IllegalArgumentException("Parameter batchArgs is null or zero size.");
		}
		if (batchSize <= 0) {
			throw new IllegalArgumentException("Parameter batchSize must exceed zero.");
		}

		int batchArgsSize = batchArgs.size();

		int startIdx = 0;

		while (startIdx < batchArgsSize) {
			int endIdx = startIdx + batchSize;
			if (endIdx > batchArgsSize) {
				endIdx = batchArgsSize;
			}

			List<Object[]> subBatchArgs = batchArgs.subList(startIdx, endIdx);
			
			startIdx += batchSize;
			this.batchUpdate(sql, subBatchArgs);
		}

	}
}
