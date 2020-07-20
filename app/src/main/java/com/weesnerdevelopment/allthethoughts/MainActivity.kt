package com.weesnerdevelopment.allthethoughts

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.firebase.ui.auth.IdpResponse
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var auth: Auth
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        navController = findNavController(R.id.nav_host_fragment)
        setupActionBarWithNavController(navController)

        auth = FireAuth(this).also {
            if (it.current == null) it.login()
            else Toast.makeText(this, "Welcome ${it.current?.name}", Toast.LENGTH_LONG).show()
        }

        fab.setOnClickListener {
            navController.navigate(NavGraphDirections.actionGlobalAddThoughtFragment())
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            fab.isVisible = destination.id != R.id.AddThoughtFragment
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val response = IdpResponse.fromResultIntent(data)

        Snackbar.make(
            fab,
            if (resultCode != Activity.RESULT_OK) "Failed to login with code ${response?.error?.errorCode}"
            else "Welcome ${auth.current?.name}",
            Snackbar.LENGTH_LONG
        ).show()

        recreate()
    }
}
