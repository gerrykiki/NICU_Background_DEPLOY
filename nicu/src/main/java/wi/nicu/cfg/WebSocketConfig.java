package wi.nicu.cfg;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker("/topic");
		// Client side destinations prefix
		// "/topic" implies publish-subscribe(one-to-many)
		// "/queue" is one-to-one
		// message broker
		config.setApplicationDestinationPrefixes("/app");
		// Prefix
		// routed to message-handling methods(Controller) to do application work
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/gs-guide-websocket").withSockJS();// Register endpoint, SockJS client connect to "/gs-guide-websocket"
	}
}
//https://spring.io/guides/gs/messaging-stomp-websocket/
//https://docs.spring.io/spring-framework/docs/5.0.0.BUILD-SNAPSHOT/spring-framework-reference/html/websocket.html