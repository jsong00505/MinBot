package com.minbot.db.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataSourceConfig {

  private static Connection CONN;
  private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceConfig.class);

  public static Connection getConnection() {
    if (CONN == null) {
      loadProperties();
    }
    return CONN;
  }

  private static void loadProperties() {
    String applicationResource = "jdbc.properties";

    ClassLoader loader = Thread.currentThread().getContextClassLoader();
    try (InputStream input = loader.getResourceAsStream(applicationResource)) {
      Properties properties = new Properties();
      // load a properties file
      properties.load(input);

      String userName = properties.getProperty("username");
      String password = properties.getProperty("password");
      String driver = properties.getProperty("driver");
      String url = properties.getProperty("url");

      Class.forName(driver);
      CONN = DriverManager.getConnection(url, userName, password);
    } catch (IOException ex) {
      LOGGER.error("Failed to load token properties!");
      ex.printStackTrace();
    } catch (Exception e) {
      LOGGER.error("Failed to connect!");
      e.printStackTrace();
    }
  }

  private static void disconnect() {
    try {
      CONN.close();
    } catch (SQLException e) {
      LOGGER.error("Failed to connect!");
      e.printStackTrace();
    }
  }

}
