package com.example.loginregister.database

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

class UserViewModel : ViewModel() {
    val user = MutableLiveData<User>()

    fun setUser(newUser: User) {
        user.value = newUser
    }
}
