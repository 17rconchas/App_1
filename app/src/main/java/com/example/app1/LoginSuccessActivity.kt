package com.example.app1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class LoginSuccessActivity : AppCompatActivity() {
    private var strFirstName: String? = null
    private var strMiddleName: String? = null
    private var strLastName: String? = null
    private var tvFirstName: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_success)
        strFirstName = intent.getStringExtra("firstName")
        strMiddleName = intent.getStringExtra("middleName")
        strLastName = intent.getStringExtra("lastName")


        //tvFirstName = findViewById<View>(R.id.tv_nameFromMain) as TextView
        val tv1: TextView = findViewById(R.id.tv_nameFromMain)
        tv1.text = strFirstName + " " + strMiddleName + " " + strLastName + " is logged in!"

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //outState.putString("FirstName", strFirstName)
        outState.putString("MiddleName", strMiddleName)
        outState.putString("LastName", strLastName)

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        strFirstName = savedInstanceState.getString("FirstName")
        strMiddleName = savedInstanceState.getString("MiddleName")
        strLastName = savedInstanceState.getString("LastName")

    }
}