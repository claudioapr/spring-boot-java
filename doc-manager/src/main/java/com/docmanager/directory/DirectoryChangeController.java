package com.docmanager.directory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class DirectoryChangeController {



    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public String greeting(String message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return "";
    }


}