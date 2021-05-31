package com.parkit.parkingsystem.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.parkit.parkingsystem.util.LoginReader;

import java.sql.*;

public class DataBaseConfig {

    private static final Logger logger = LogManager.getLogger("DataBaseConfig");

    public Connection getConnection() throws ClassNotFoundException, SQLException {
 
    	logger.info("Create DB connection");
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/prod",LoginReader.readUserName(),LoginReader.readPassword()); // changement accès DB
    }
   
    public static void close(ResultSet rs, Statement ps, Connection conn) {
        if (rs!=null)
        {
            try {
                rs.close();
            }
            catch(SQLException e) {
                logger.error("The result set cannot be closed.", e);
            }
        }
        if (ps != null) {
            try {
                ps.close();
            }catch (SQLException e) {
                logger.error("The statement cannot be closed.", e);
            }
        }
        if (conn != null) {
            try {
                conn.close();
            }catch (SQLException e) {
                logger.error("The data source connection cannot be closed.", e);
            }
        }
    }
}