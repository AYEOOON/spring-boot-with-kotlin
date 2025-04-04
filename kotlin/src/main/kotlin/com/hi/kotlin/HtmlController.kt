package com.hi.kotlin

import User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class HtmlController {

    @Autowired
    lateinit var repository:Repository

    @GetMapping("/")
    fun index(model: Model): String {
        model.addAttribute("title", "Home")
        return "index"  // index.html을 찾음
    }

    @GetMapping("/{formType}")
    fun signForm(model: Model, @PathVariable formType: String): String {
        val response = when (formType) {
            "sign" -> "sign"
            "login" -> "login"
            else -> "index"  // 예외 처리 (기본 페이지 반환)
        }

        model.addAttribute("title", response)
        return response
    }

    @PostMapping("/sign")
    fun postSign(model: Model,
                 @RequestParam(value="id") userId:String,
                 @RequestParam(value="password") password:String):String{
        try{
            val user = repository.save(User(userId, password))
            println(user.toString())
        }catch(e:Exception){
            e.printStackTrace()
        }
        return "index"
    }
}
