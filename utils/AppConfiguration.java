package com.example.springsocial.utils;

import io.dropwizard.Configuration;

public class AppConfiguration extends Configuration {

  private String apiKey;

  private String secretKey;

  public String getApiKey() {
    return apiKey;
  }

  public String getSecretKey() {
    return secretKey;
  }

}
