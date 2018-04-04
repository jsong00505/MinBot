package com.minbot.util;

import com.minbot.model.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TokenConfig {

  private static final Logger LOGGER = LoggerFactory.getLogger( TokenConfig.class );
  private static Token TOKEN;

  public static Token loadProperties() {
    if(TOKEN == null) {
      TOKEN = new Token();

      String applicationResource = "application.properties";
      ClassLoader loader = Thread.currentThread().getContextClassLoader();
      try (InputStream input = loader.getResourceAsStream(applicationResource)) {
        Properties properties = new Properties();
        // load a properties file
        properties.load(input);

        // get the property value and print it out
        TOKEN.setBotToken(properties.getProperty("minbot.token"));
        TOKEN.setBotName(properties.getProperty("minbot.name"));
      } catch (IOException ex) {
        LOGGER.error("Failed to load token properties!");
        ex.printStackTrace();
      }
    }
    return TOKEN;
  }
}
