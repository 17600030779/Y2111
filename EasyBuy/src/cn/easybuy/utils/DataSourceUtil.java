package cn.easybuy.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.Properties;

public class DataSourceUtil {
	private static DataSource dataSource;
	
	private static String driver;
	private static String url;
	private static String user;
	private static String password;
	
	static {
		init();
	}
	
	public static void init(){
		Properties params=new Properties();
		String configFile = "database.properties";
		InputStream is=DataSourceUtil.class.getClassLoader().getResourceAsStream(configFile);
		try {
			params.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		driver=params.getProperty("jdbc.driver");
		url=params.getProperty("jdbc.url");
		user=params.getProperty("jdbc.username");
		password=params.getProperty("jdbc.password");
	}   

	//获取连接
	public static Connection openConnection() throws SQLException {
		Connection connection = null;
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return connection;
	}
	//关闭连接
	public static void closeConnection(Connection connection) {
		try {
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
