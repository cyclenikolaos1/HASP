package com.hasp.jmvp;
/*
 *  Copyright (C) 2008-2023 Ioannis Torounoglou <johntor@ionio.gr>
 *        _       _           _
 *       | | ___ | |__  _ __ | |_ ___  _ __
 *    _  | |/ _ \| '_ \| '_ \| __/ _ \| '__|
 *   | |_| | (_) | | | | | | | || (_) | |
 *    \___/ \___/|_| |_|_| |_|\__\___/|_|
 *
 *  Project files can not be copied and/or distributed without the
 *  written permission of Ioannis Torounoglou
 *
 */


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 *
 * @author airhacks.com
 */
public class Configurator {

  public final static String CONFIGURATION_FILE = "configuration.properties";

  private final Properties systemProperties;
  private Function<Object, Object> customConfigurator;

  private Consumer<String> LOG;

  public Configurator() {
    this.systemProperties = System.getProperties();
    this.LOG = l -> {
    };
  }

  public Configurator set(Function<Object, Object> custom) {
    this.customConfigurator = custom;
    return this;
  }

  public Configurator setLogger(Consumer<String> log) {
    this.LOG = log;
    return this;
  }

  Properties getProperties(Class<?> clazz) {
    Properties configuration = new Properties();
    try (InputStream stream = clazz.getResourceAsStream(CONFIGURATION_FILE)) {
      if (stream == null) {
        return null;
      }
      configuration.load(stream);
    } catch (IOException ex) {
      //a property file does not have to exist...
    }
    return configuration;
  }

  /**
   *
   * @param clazz is used to locate the properties @see
   * java.lang.Class#getResourceAsStream
   * @param key to be resolved
   *
   * @return the resolved value.
   */
  public Object getProperty(Class<?> clazz, Object key) {
    Object value = this.systemProperties.get(key);
    if (value != null) {
      return value;
    }
    if (customConfigurator != null) {
      value = customConfigurator.apply(key);
      if (value != null) {
        return value;
      }
    }
    Properties clazzProperties = this.getProperties(clazz);
    if (clazzProperties != null) {
      value = clazzProperties.get(key);
    }
    return value;
  }

  public void forgetAll() {
    this.customConfigurator = null;
  }

}
