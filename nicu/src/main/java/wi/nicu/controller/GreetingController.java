package wi.nicu.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import wi.nicu.model.Greeting;
import wi.nicu.model.HelloMessage;

//https://spring.io/guides/gs/rest-service/
@Controller
public class GreetingController {

	@MessageMapping("/hello") // STOMP server side destination mapping
	@SendTo("/topic/greetings") // Subscriber
	public Greeting greeting(HelloMessage message) throws Exception {
		Thread.sleep(1000); // simulated delay
		return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
	}
}
