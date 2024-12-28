package com.sintia.crud_berita_user.screen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.sintia.crud_berita_user.MainActivity
import com.sintia.crud_berita_user.R
import com.sintia.crud_berita_user.model.LoginResponse
import com.sintia.crud_berita_user.service.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login_screen)

        //deklarasi widget
        val etUsername: EditText = findViewById(R.id.etUsername)
        val etPassword: EditText = findViewById(R.id.etPassword)
        val btnLogin: Button = findViewById(R.id.btnLogin)
        val txtToRegister: TextView = findViewById(R.id.txtToRegister)

        //ketik teks belum punya akun di klik, maka akan ke page register
        txtToRegister.setOnClickListener(){
            val toRegister = Intent(this@LoginScreenActivity,
                RegisterScreenActivity::class.java)
            startActivity(toRegister)
        }

        btnLogin.setOnClickListener(){
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()

            //validasi input kosong
            if(username.isEmpty() || password.isEmpty()){
                Toast.makeText(
                    this@LoginScreenActivity,"Username atau password tidak boleh kosong",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                ApiClient.retrofit.login(username, password).enqueue(
                    object : Callback<LoginResponse>{

                        override fun onResponse(
                            call: Call<LoginResponse>,
                            response: Response<LoginResponse>
                        ) {
                            if (response.isSuccessful) {
                                val loginResponse = response.body()
                                if (loginResponse != null && response.isSuccessful) {
                                    //login berhasil
                                    Toast.makeText(
                                        this@LoginScreenActivity, "Login Success",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    // mau pindah ke page login
                                    val toMain = Intent(
                                        this@LoginScreenActivity,
                                        MainActivity::class.java
                                    )
                                    startActivity(toMain)

                                } else {
                                    //login gagal
                                    Toast.makeText(
                                        this@LoginScreenActivity, "Login gagal",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            } else {
                                val errorMessage = response.errorBody()?.string() ?: "Unknown Error"
                                Log.e("Login error", errorMessage)
                                Toast.makeText(
                                    this@LoginScreenActivity,
                                    "Login Failure",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                            Toast.makeText(
                                this@LoginScreenActivity, t.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                )
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
