package com.example.loginregister.database

import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {
    var firstName: String? = null
    var lastName: String? = null
    var email: String? = null
    var password: String? = null
}