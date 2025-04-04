package com.hi.kotlin.controller

import com.hi.kotlin.entity.User
import com.hi.kotlin.repository.Repository
import com.sun.net.httpserver.HttpServer
import jakarta.servlet.http.HttpSession
import java.security.MessageDigest
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
    lateinit var repository: Repository

    @GetMapping("/")
    fun index(model: Model): String {
        model.addAttribute("title", "Home")
        return "index"  // index.html을 찾음
    }

    fun crypto(ss: String): String {
        val sha = MessageDigest.getInstance("SHA-256")
        val hexa = sha.digest(ss.toByteArray())
        val cryptoStr = hexa.fold("") { str, it -> str + "%02x".format(it) }
        return cryptoStr
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
            val cryptoPass = crypto(password)

            val user = repository.save(User(userId, cryptoPass))
        }catch(e:Exception){
            e.printStackTrace()
        }
        model.addAttribute("title", "sign success")
        return "index"
    }

    @PostMapping("/login")
    fun postLogin(
            model: Model,
            session: HttpSession,
            @RequestParam("id") userId: String,
            @RequestParam("password") password: String
    ): String {
        return try {
            val cryptoPass = crypto(password)
            val dbUser = repository.findByUserId(userId)

            if (dbUser != null) {
                val dbPass = dbUser.password

                if (cryptoPass == dbPass) {
                    session.setAttribute("userId", dbUser.userId)
                    model.addAttribute("title", "welcome")
                    model.addAttribute("userId", userId)
                    "welcome"
                } else {
                    model.addAttribute("title", "login")
                    "login"  // 비밀번호 불일치
                }
            } else {
                model.addAttribute("title", "login")
                "login"  // 사용자 없음
            }
        } catch (e: Exception) {
            e.printStackTrace()
            model.addAttribute("title", "login")
            "login"  // 예외 발생 시도 로그인 페이지로
        }
    }
}
