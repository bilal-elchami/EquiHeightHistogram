package com.live.bilalchami.equiHeightHistogram.packUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectToDB {
	private static Connection conn = null;
	private static String databaseName = "DVD_Rental";
	private static String username = "bilal";
	private static String password = "bilal";
	
	private static String ip = "localhost";
	private static String port = "5433";
	
	//Create a database connection and return a Statement
	public static Statement getStatement(){
		try {
			Class.forName("org.postgresql.Driver");
			
			String url = "jdbc:postgresql://" + ip + ":" + port + "/" + databaseName;

			conn = DriverManager.getConnection(url, username, password);
			Statement st = conn.createStatement();
			return st;
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	//This function closes the connection
	public static void closeConnection(){
		try {
			if(conn!=null){
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
