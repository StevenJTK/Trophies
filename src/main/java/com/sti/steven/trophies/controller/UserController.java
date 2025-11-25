package com.sti.steven.trophies.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    @RequestMapping("/hello")
    @ResponseBody
    public String helloUser() {
        return "Hello User.";
    }


    @RequestMapping("/trophies")
    @ResponseBody
    public String displayTrophies() {
        return "Your trophies should display here";
    }


    @RequestMapping("admin")
    @ResponseBody
    public String displayAdminPage() {
        return "This is actually a super secret admin page";
    }

}
