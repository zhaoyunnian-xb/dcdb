package com.bm.dcdb.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;

public class DBCPUtils {

	private static DBCPUtils dbcpUtils;
	private DataSource dataSource;
	
	public static DBCPUtils getInstance(){
		if(dbcpUtils == null){
			dbcpUtils = new DBCPUtils();
		}
		return dbcpUtils;
	}
	
	public Connection init(){
		
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(new File("src/test/resources/dbcp.properties")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			dataSource = BasicDataSourceFactory.createDataSource(prop);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
