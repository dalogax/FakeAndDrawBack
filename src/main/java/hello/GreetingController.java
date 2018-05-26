package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {


    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }
    
    @Autowired
    private SimpMessagingTemplate template;

    @Scheduled(fixedRate = 5000)
    public void greeting() throws Exception {
        Thread.sleep(1000); // simulated delay
        System.out.println("scheduled");
        this.template.convertAndSend("/topic/greetings", "{\"body\":\"Hello\"}");
    }    
}
