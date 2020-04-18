package com.steventimothy.scryfall.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@EnableAutoConfiguration
@ComponentScan
@Configuration
public class ApplicationConfig {

  /**
   * The rest template used to make rest calls.
   * @param restTemplateBuilder The builder used to make the rest template.
   * @return The Rest template singleton.
   */
  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
    RestTemplate restTemplate = restTemplateBuilder
          .requestFactory(() -> new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()))
          .build();

    restTemplate.setMessageConverters(Arrays.asList(
          new MappingJackson2HttpMessageConverter(),
          new FormHttpMessageConverter(),
          new ByteArrayHttpMessageConverter()
    ));

    return restTemplate;
  }
}
