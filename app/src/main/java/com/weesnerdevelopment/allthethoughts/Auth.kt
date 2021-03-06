package com.weesnerdevelopment.allthethoughts

import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth

data class User(
    val id: String,
    val name: String
)

interface Auth {
    var current: User?
    fun login()
    fun logout(callback: () -> Unit)
}

data class FireAuth(
    private val activity: AppCompatActivity
) : Auth {
    private val RC_SIGN_IN = 0XFACE
    private var providers = arrayListOf(
        AuthUI.IdpConfig.GoogleBuilder().build()
    )

    override var current: User? = null
        get() = FirebaseAuth.getInstance().currentUser?.let {
            User(
                it.uid,
                it.displayName ?: ""
            )
        }

    override fun login() {
        activity.startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setTheme(R.style.AppTheme)
                .build(),
            RC_SIGN_IN
        )
    }

    override fun logout(callback: () -> Unit) {
        AuthUI.getInstance()
            .signOut(activity)
            .addOnCompleteListener {
                callback()
            }
    }
}
