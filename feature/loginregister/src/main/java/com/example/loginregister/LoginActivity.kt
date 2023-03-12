package com.example.loginregister

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.core.database.AppDatabase
import com.example.home.HomePage1
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)



        val loginButton = findViewById<Button>(R.id.loginbtn)
        loginButton.setOnClickListener {
            loginUser()
        }

    }

    private fun loginUser() {

        val emailEditText = findViewById<EditText>(R.id.email)
        val passwordEditText = findViewById<EditText>(R.id.password)
        val email = emailEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()

        if (email.isEmpty()) {
            emailEditText.error = "Введите вашу почту!"
            emailEditText.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.error = "Вы ввели почту неправильного формата!"
            emailEditText.requestFocus()
            return
        }

        if (password.isEmpty()) {
            passwordEditText.error = "Вы не ввели пароль!"
            passwordEditText.requestFocus()
            return
        }

        // get the UserDao instance
        val userDao = AppDatabase.getInstance(applicationContext).userDao()

        // launch a coroutine to retrieve the user from the database
        lifecycleScope.launch {
            val user = userDao.getUserByEmail(email)

            if (user != null && user.email == email && user.password == password ) {
                // login successful, do something
                Toast.makeText(applicationContext, "Вы успешно вошли в систему", Toast.LENGTH_LONG).show()
                // login successful, pass the user object as an extra in the intent
                val intent = Intent(this@LoginActivity, HomePage1::class.java).apply {
                    putExtra("user", user)
                }
                startActivity(intent)
                finish()


            } else {
                // login failed, show an error message
                Toast.makeText(applicationContext, "Такого пользователя не существует, либо введённые данные неверные", Toast.LENGTH_LONG).show()
            }
        }
    }
}