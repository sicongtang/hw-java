package me.sicongtang.spring.jdbc;

import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.List;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

public class SimpleJDBCWrapperTest {

	private SimpleJDBCWrapper wrapper;

	@Before
	public void setUp() throws Exception {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost/jilv");
		dataSource.setUsername("root");
		dataSource.setPassword("");
		wrapper = new SimpleJDBCWrapper(dataSource);
	}

	@Test
	public void testBatchUpdateInChunk() {
		String sql = "select 1 from dual";

		List<Object[]> batchArgs = mock(List.class);
		SimpleJDBCWrapper spyWrapper = spy(wrapper);

		// Impossible: real method throws exception
		// when(spyWrapper.batchUpdate(anyString(), anyListOf(Object[].class))).thenReturn(new int[] { 0 });
		// If using spy, please use doXXX style
		doReturn(new int[] { 0 }).when(spyWrapper).batchUpdate(eq(sql), anyListOf(Object[].class));

		InOrder inOrder = inOrder(batchArgs);

		when(batchArgs.size()).thenReturn(15);
		spyWrapper.batchUpdateInChunk(sql, batchArgs, 10);
		inOrder.verify(batchArgs).subList(0, 10);
		inOrder.verify(batchArgs).subList(10, 15);

		when(batchArgs.size()).thenReturn(10);
		spyWrapper.batchUpdateInChunk(sql, batchArgs, 10);
		// if not using inOrder, please use "verify(batchArgs, times(2))" instead
		inOrder.verify(batchArgs).subList(0, 10);

		when(batchArgs.size()).thenReturn(6);
		spyWrapper.batchUpdateInChunk(sql, batchArgs, 10);
		inOrder.verify(batchArgs).subList(0, 6);

	}

}
