package com.ruoyi.framework.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 *       使用spring boot时，使用的是spring-boot的内置容器，
 *      如果要使用WebSocket，需要注入ServerEndpointExporter
 *
 * @author ruoyi
 */
@Configuration
public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
