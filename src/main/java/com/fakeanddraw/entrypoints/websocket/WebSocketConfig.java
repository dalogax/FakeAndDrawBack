package com.fakeanddraw.entrypoints.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  public static final int MAX_TEXT_MESSAGE_SIZE = 2048000; // 2 Megabytes.
  public static final int BUFFER_SIZE = MAX_TEXT_MESSAGE_SIZE * 5;

  // @Bean
  // public ServletServerContainerFactoryBean createServletServerContainerFactoryBean() {
  // ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
  // container.setMaxTextMessageBufferSize(MAX_TEXT_MESSAGE_SIZE);
  // container.setMaxBinaryMessageBufferSize(BUFFER_SIZE);
  // return container;
  // }

  @Override
  public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
    registration.setMessageSizeLimit(500 * 1024);
    registration.setSendBufferSizeLimit(1024 * 1024);
    registration.setSendTimeLimit(20000);
  }

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/fakeanddraw").setAllowedOrigins("*").withSockJS();
  }
}
