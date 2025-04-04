package com.hi.kotlin.repository

import com.hi.kotlin.entity.User
import org.springframework.data.repository.CrudRepository

interface Repository:CrudRepository<User, Long> {
    fun findByUserId(userId:String): User
}