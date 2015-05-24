package com.sicongtang.spring.jdbc;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

public class SimpleJdbcDAO {
	private SimpleJdbcTemplate template;

	public SimpleJdbcDAO() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost/jilv");
		dataSource.setUsername("root");
		dataSource.setPassword("");
		template = new SimpleJdbcTemplate(dataSource);
	}

	public void batchUpdate() {
		List<Object[]> list = new ArrayList<Object[]>();
		//list.add(new Object[] { 33.3333, "controller", "action", 123123, new java.sql.Date(new java.util.Date().getTime()) });
		//list.add(new Object[] { 44, "controller", "action", 123123, null });
		list.add(new Object[] { 44, "controller", "action", 123123, new java.util.Date() });
		template.batchUpdate(
				"insert into admin_logs(user_id, controller, action, params_id, created_at) values(?,?,?,?,?)", list);
	}

	public static void main(String[] args) {
		SimpleJdbcDAO dao = new SimpleJdbcDAO();
		dao.batchUpdate();
	}
}
