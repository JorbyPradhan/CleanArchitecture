package com.example.cleanarchitecturemovieapi.splash

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.cleanarchitecturemovieapi.MainActivity
import com.example.cleanarchitecturemovieapi.R
import kotlinx.android.synthetic.main.activity_splash.*

@Suppress("DEPRECATION")
class SplashActivity : AppCompatActivity() {

    private val sharedPrefFile = "kotlinsharedpreference"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile,
            Context.MODE_PRIVATE)

        /*  val sharedIdValue = sharedPreferences.getInt("id_key",0)
            val sharedNameValue = sharedPreferences.getString("name_key","defaultname")  */
        btn_male.setOnClickListener {
            val editor:SharedPreferences.Editor =  sharedPreferences.edit()
            editor.putString("gender_key","male")
            editor.apply()
        }
        btn_female.setOnClickListener {
            val editor:SharedPreferences.Editor =  sharedPreferences.edit()
            editor.putString("gender_key","female")
            editor.apply()
        }
        Handler().postDelayed({
            if (this == this@SplashActivity)
             handler(sharedPreferences)
        }, 2500)

    }

    private fun handler(sharedPreferences: SharedPreferences) {
        val sharedNameValue = sharedPreferences.getString("gender_key","female")
        if (sharedNameValue.equals("female")){
            Intent(this@SplashActivity,MainActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                it.putExtra("gender","female")
                startActivity(it)
            }
        }else{
            Intent(this@SplashActivity,MainActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                it.putExtra("gender","male")
                startActivity(it)
            }
        }
    }
}