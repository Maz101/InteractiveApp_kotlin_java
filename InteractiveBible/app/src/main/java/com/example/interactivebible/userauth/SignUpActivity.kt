package com.example.interactivebible.userauth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.interactivebible.R
import com.example.interactivebible.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class SignUpActivity : AppCompatActivity() {


    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

      //  FacebookSdk.sdkInitialize(getApplicationContext())

    //    AppEventsLogger.activateApp(application)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)


        auth = Firebase.auth


        val navController = findNavController(R.id.nav_host_fragment_content_sign_up)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_sign_up)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

//
//    override fun onStart() {
//        super.onStart()
//        val currentUser = auth.currentUser
//        if (currentUser != null) {
//            LoginFragment().updateUiWithUser(LoggedInUserView(displayName = currentUser.toString()))
//        }
//    }
}

