package com.hi.kotlin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable

@Controller
public class HtmlController {

    @GetMapping("/")
    fun index(model:Model):String{
        return "index"
    }

    @GetMapping("/{formType}")
    fun signForm(model: Model, @PathVariable formType:String) : String{
        var response : String = ""

        if(formType.equals("sign")){
            response="sign"
        }
        else if(formType.equals("login")){
            response="login"
        }
        return response
    }
}
