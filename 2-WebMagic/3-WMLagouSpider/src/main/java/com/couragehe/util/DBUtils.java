package com.couragehe.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;
/**
 * 数据库连接工具类
 * @author CourageHe
 *
 */
public class DBUtils {
	private static String username;
	private static String password;
	private static String driver;
	private static String url;
	private static int maxActive;
	private static int initialSize;
	private static BasicDataSource dataSource;
	
	static {
		Properties config = new Properties();
		InputStream in =  DBUtils.class.getClassLoader().getResourceAsStream("db.properties");
		try {
			config.load(in);
			//初始化 连接参数
			driver = config.getProperty("jdbc.driver");
			url = config.getProperty("jdbc.url");
			username = config.getProperty("jdbc.username");
			password = config.getProperty("jdbc.password");
			maxActive = Integer.parseInt(config.getProperty("maxActive"));
			initialSize = Integer.parseInt(config.getProperty("initialSize"));
			dataSource = new BasicDataSource();
			dataSource.setUsername(username);
			dataSource.setPassword(password);
			dataSource.setUrl(url);
			dataSource.setDriverClassName(driver);
			dataSource.setMaxActive(maxActive);
			dataSource.setInitialSize(initialSize);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static Connection getConnection() {
		Connection connection = null;
		try {
	
			connection = dataSource.getConnection();
			return connection;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return connection;
	}
	public static void closeConnection(Connection connection) {
		if(connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}	
		}
	}
	
}
