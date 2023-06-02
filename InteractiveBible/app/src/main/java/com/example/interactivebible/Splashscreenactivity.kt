package com.example.interactivebible

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.interactivebible.userauth.SignUpActivity
import io.realm.Realm


class Splashscreenactivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val time : Long  = 2000

        Realm.init(this)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        },time)



        }
    }
