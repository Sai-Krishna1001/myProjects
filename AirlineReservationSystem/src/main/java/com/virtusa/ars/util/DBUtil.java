package com.virtusa.ars.util;

import java.io.IOException;

import java.io.InputStream;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBUtil {
    private static final Logger LOGGER = Logger.getLogger(DBUtil.class.getName());
    private static final Properties PROPERTIES = new Properties();
    
    private DBUtil() {
    	
    }
    static {
        try {
            InputStream inputStream = DBUtil.class.getClassLoader().getResourceAsStream("db.properties");
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public static Connection getConnection() {
        Connection conn = null;
        try {
            String url = PROPERTIES.getProperty("db.url");
            String username = PROPERTIES.getProperty("db.username");
            String password = PROPERTIES.getProperty("db.password");
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
        return conn;
    }
}


