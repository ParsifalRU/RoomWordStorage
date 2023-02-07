package com.example.roomwordstorage.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.roomwordstorage.data.UserDao
import com.example.roomwordstorage.model.User


class UserRepository(private val userDao: UserDao){

   val readAllData: LiveData<List<User>> = userDao.readAllData()

    suspend fun addUser(user: User){
        userDao.addUser(user)
        Log.d("LOGTAG", "$user")
    }

    suspend fun updateUser(user: User){
        userDao.updateUser(user)
    }

    suspend fun deleteUser(user: User){
        userDao.deleteUser(user)
    }

    suspend fun deleteAllUsers(){
        userDao.deleteAll()
    }
}
