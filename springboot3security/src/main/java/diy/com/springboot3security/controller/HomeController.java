package diy.com.springboot3security.controller;


import diy.com.springboot3security.config.exp0007CustomMyUserAndUserDetailServiceMariaDB.MyUser;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "<h1>Welcome home!</h1>";
    }

    @GetMapping("/user")
    public String user(Authentication authentication) {
        return "<h1>Welcome User!</h1><h2>" + authentication.getName() + "</h2>";
    }

    @GetMapping("/admin")
    public String admin(Authentication authentication) {
        return "<h1>Welcome Admin!</h1><h2>" + authentication.getName() + " " + authentication.getAuthorities() + "</h2>";
    }

    @GetMapping("/admin2")
    public String admin2(Authentication authentication) {
        MyUser myUser = (MyUser) authentication.getPrincipal();
        return "<h1>Welcome Admin!</h1><h2>" + authentication.getName() + "</h2><p>MyUser:<br>User name: " + myUser.getUsername()
                + "<br>Full name: " + myUser.getFullname() + "<br>E-Mail: " + myUser.getEmailaddress() + "<br>Birthday: " + myUser.getBirthdate()
                + "<br>Authorities: " + myUser.getAuthorities();
    }

}
