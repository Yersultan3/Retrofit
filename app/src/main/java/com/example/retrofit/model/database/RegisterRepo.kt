package com.example.retrofit.model.database

class RegisterRepo(private val dao: UserDatabaseDao) {

    val users = dao.getAllUsers()

    suspend fun insert(user: User) {
        return dao.insert(user)
    }

    suspend fun getUserName(userName: String):User? {
        return dao.getUsername(userName)
    }
}