package com.example.interactivebible.userauth

import android.content.ContentValues.TAG
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.interactivebible.R
import com.example.interactivebible.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!


    fun onCreate(){
        (activity as AppCompatActivity).supportActionBar?.title=""
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val text = "Already have an account? Log in"
        val spannableString = SpannableString(text)
        val clickableSpan1: ClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                findNavController().navigate(R.id.action_registerFragment_to_SecondFragment)
            }
        }


        spannableString.setSpan(clickableSpan1,25,31, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.accountLogin.setText(spannableString,TextView.BufferType.SPANNABLE)
        binding.accountLogin.movementMethod = LinkMovementMethod.getInstance()


        lateinit var auth: FirebaseAuth
        auth = Firebase.auth


        binding.signUp.setOnClickListener{
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()


            auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.currentUser
                    }
                    else {
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    }
                }
        }
    }
}