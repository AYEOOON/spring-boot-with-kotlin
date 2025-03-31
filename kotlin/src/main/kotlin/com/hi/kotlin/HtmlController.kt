package com.hi.kotlin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HtmlController {

    @GetMapping("/")
    fun index(model:Model):String{
        return "index"
    }

    @GetMapping("/sign")
    fun signForm(model: Model) : String{
        return "sign"
    }

    @GetMapping("/login")
    fun loginForm(model: Model) : String{
        return "login"
    }

}
