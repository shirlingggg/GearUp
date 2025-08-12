package com.shirleen.gearup.repository

import com.shirleen.gearup.data.UserDao
import com.shirleen.gearup.model.User


class UserRepository(private val userDao: UserDao) {
    suspend fun registerUser(user: User) {
        userDao.registerUser(user)
    }

    suspend fun loginUser(email: String, password: String): User? {
        return userDao.loginUser(email, password)
    }
}