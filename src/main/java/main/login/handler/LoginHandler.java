package main.login.handler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginHandler {
    @RequestMapping("/index")
    public String login(){
        return "index" ;
    }
}
