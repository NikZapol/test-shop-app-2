package com.example.loginregister

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.loginregister.databinding.ActivitySigninBinding
import kotlinx.coroutines.launch

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySigninBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registerbtn.setOnClickListener {
            registerUser()
        }

        binding.login.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.signinapplelayout.setOnClickListener {
            lifecycleScope.launch {
                val userDao = com.example.core.database.AppDatabase.getInstance(applicationContext).userDao()
                userDao.deleteAllUsers()
                // check all users
                val allUsers = userDao.getAllUsers()
                for (user in allUsers) {
                    Log.d("my-tag", "User: $user")
                }
            }
        }

        binding.signingooglelayout.setOnClickListener {
            lifecycleScope.launch {
                val userDao = com.example.core.database.AppDatabase.getInstance(applicationContext).userDao()
                // check all users
                val allUsers = userDao.getAllUsers()
                for (user in allUsers) {
                    Log.d("my-tag", "User: $user")
                }
            }
        }


    }
    private fun registerUser() {
        val email = binding.email.text.toString().trim()
        val firstname = binding.firstname.text.toString().trim()
        val lastname = binding.lastname.text.toString().trim()
        val password = binding.password.text.toString().trim()


        if (firstname.isEmpty()) {
            binding.firstname.error = "Введите ваше имя!"
            binding.firstname.requestFocus()
            return
        } else

        if (lastname.isEmpty()) {
            binding.lastname.error = "Введите вашу фамилию!"
            binding.lastname.requestFocus()
            return
        }else
        if (email.isEmpty()) {
            binding.email.error = "Введите вашу почту!"
            binding.email.requestFocus()
            return
        }else

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.email.error = "Вы ввели почту неправильного формата!"
            binding.email.requestFocus()
            return
        }else

        if (password.isEmpty()) {
            binding.password.error = "Вы не ввели пароль!"
            binding.password.requestFocus()

        }else {
            val newUser =
                com.example.core.database.User(
                    firstname = firstname,
                    lastname = lastname,
                    email = email,
                    password = password,
                    profileImage = null
                )


            lifecycleScope.launch {
                try {
                    // get the UserDao instance
                    val userDao = com.example.core.database.AppDatabase.getInstance(applicationContext).userDao()

                    // check if the user already exists
                    val existingUser = userDao.getUserByEmail(email)
                    if (existingUser != null) {
                        // user already exists, show an error message
                        binding.email.error = "This email is already registered"
                        binding.email.requestFocus()
                    } else {
                        // insert the new user into the database
                        userDao.insertUser(newUser)

                        // registration successful, log a message
                        Log.d("my-tag", "Registration successful: $newUser")

                        // check all users
                        val allUsers = userDao.getAllUsers()
                        for (user in allUsers) {
                            Log.d("my-tag", "User: $user")
                        }

                        // registration successful, do something
                        Toast.makeText(applicationContext, "Вы успешно зарегистрированы", Toast.LENGTH_LONG).show()
                    }
                } catch (e: Exception) {
                    // registration failed, handle the exception
                    Log.e("my-tag", "Registration failed: ${e.message}")
                }
            }
        }
    }
}