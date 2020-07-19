package com.weesnerdevelopment.allthethoughts

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.IdpResponse
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var auth: Auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        auth = FireAuth(this).also {
            println("current ${it.current}")
            if (it.current == null) it.login()
            else Toast.makeText(this, "Welcome ${it.current?.name}", Toast.LENGTH_LONG).show()
        }

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val response = IdpResponse.fromResultIntent(data)

        Snackbar.make(
            fab,
            if (resultCode != Activity.RESULT_OK) "Failed to login with code ${response?.error?.errorCode}"
            else "Welcome ${auth.current?.name}"
            ,
            Snackbar.LENGTH_LONG
        ).show()
    }
}
