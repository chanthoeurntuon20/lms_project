package com.amk.lms.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("home/v1/")
public class HomeController {

    @RequestMapping("hello")
    public String getHome(){
        return  "Hello Home controller";
    }
}
