package com.jacob.reservationapp.Splash

import android.content.Intent
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.jacob.reservationapp.Auth.User
import com.jacob.reservationapp.AuthActivity
import com.jacob.reservationapp.MainActivity

import com.jacob.reservationapp.Utils.Constants.USER

class SplashActivity : AppCompatActivity() {
    internal lateinit var splashViewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSplashViewModel()
        checkIfUserIsAuthenticated()
    }

    private fun initSplashViewModel() {
        splashViewModel = ViewModelProvider(this).get(SplashViewModel::class.java)
    }

    private fun checkIfUserIsAuthenticated() {
        splashViewModel.checkIfUserIsAuthenticated()
        splashViewModel.isUserAuthenticatedLiveData.observe(this, Observer { user ->
            if (!user.isAuthenticated) {
                goToAuthInActivity()
                finish()
            } else {
                getUserFromDatabase(user.uid)
            }
        })
    }

    private fun goToAuthInActivity() {
        val intent = Intent(this@SplashActivity, AuthActivity::class.java)
        startActivity(intent)
    }

    private fun getUserFromDatabase(uid: String) {
        splashViewModel.setUid(uid)
        splashViewModel.userLiveData.observe(this, Observer{ user ->
            goToMainActivity(user)
            finish()
        })
    }

    private fun goToMainActivity(user: User) {
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        intent.putExtra(USER, user)
        startActivity(intent)
    }
}
