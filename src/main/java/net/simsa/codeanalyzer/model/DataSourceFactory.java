package net.simsa.codeanalyzer.model;

import java.sql.Connection;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

// TODO - replace DataSourceFactory with a more legit db access approach
public class DataSourceFactory {

    static String connectURI = "jdbc:mysql://localhost:3306/codeanalyzer";
    static DataSource ds;

    public static DataSource getDataSource() {
	if (ds == null) {
	    ds = setupDataSource();
	}
	return ds;
    }

    public static DataSource setupDataSource() {
	BasicDataSource ds = new BasicDataSource();
	ds.setDriverClassName("com.mysql.jdbc.Driver");
	ds.setUsername("analytics");
	ds.setPassword("codeanalytics");
	ds.setUrl(connectURI);
	return ds;
    }

    public static void main(String[] args) throws Exception {
	DataSource ds = setupDataSource();
	Connection conn = null;
	try {
	    conn = ds.getConnection();
	    if (conn != null) {
		System.out.println("Got connection. Closing.");
	    }
	} finally {
	    if (conn != null) {
		conn.close();
	    }
	}

    }

}
