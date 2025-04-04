package com.hi.kotlin

import User
import org.springframework.data.repository.CrudRepository

interface Repository:CrudRepository<User, Long> {
    fun findByUserId(userId:String):User
}