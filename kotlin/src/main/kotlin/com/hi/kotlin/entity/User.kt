package com.hi.kotlin.entity
import jakarta.persistence.*

@Entity
@Table(name = "users")
class User(
        var userId: String,
        var password: String,

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null
)