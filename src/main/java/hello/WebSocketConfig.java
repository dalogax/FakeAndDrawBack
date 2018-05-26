package hello;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.HandshakeInterceptor;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/qwer").setAllowedOrigins("*").addInterceptors(new HttpHandshakeInterceptor());
    }
    
    public class HttpHandshakeInterceptor implements HandshakeInterceptor {

    	@Override
    	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
    			Map<String, Object> sessionAttributes) throws Exception {
    		if (request instanceof ServletServerHttpRequest) {
    			ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
    			HttpSession session = servletRequest.getServletRequest().getSession();
    			sessionAttributes.put("sessionId", session.getId());
    		}
    		return true;
    	}

    	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
    			Exception ex) {
    	}
    }

}