package com.example.interactivebible.userauth


import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.findNavController
import com.example.interactivebible.R
import com.example.interactivebible.databinding.FragmentSigninBinding
import com.example.interactivebible.ui.login.LoggedInUserView
import com.example.interactivebible.ui.login.LoginFragment
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.identity.GetSignInIntentRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class SignInFragment : Fragment() {

    private var _binding: FragmentSigninBinding? = null
    private lateinit var auth: FirebaseAuth
    val REQUEST_CODE_GOOGLE_SIGN_IN = 1


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    fun onCreate(){
        val callbackManager = CallbackManager.Factory.create()
        auth = Firebase.auth

        (activity as AppCompatActivity).supportActionBar?.title=""

        binding.googleSignInButton.setOnClickListener {signIn()
        }


        binding.fbLoginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                Log.d(TAG, "facebook:onSuccess:$loginResult")
                handleFacebookAccessToken(loginResult.accessToken)
            }

            override fun onCancel() {
                Log.d(TAG, "facebook:onCancel")
            }

            override fun onError(error: FacebookException) {
                Log.d(TAG, "facebook:onError", error)
            }
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSigninBinding.inflate(inflater, container, false)

        return binding.root
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d(TAG, "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(FragmentActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    LoginFragment().updateUiWithUser(LoggedInUserView(user?.uid.toString()))
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
//                    Toast.makeText(TAG, "Authentication failed.",
//                        Toast.LENGTH_SHORT).show()
//                    LoginFragment().updateUiWithUser(LoggedInUserView(nul)
                }
            }
    }


    private fun signIn() {
        val request = GetSignInIntentRequest.builder()
            .setServerClientId("1059481664357-jacn8eno9c0ilm45oiinu2cs6gctp0mm.apps.googleusercontent.com")
            .build()
        Identity.getSignInClient(FragmentActivity())
            .getSignInIntent(request)
            .addOnSuccessListener { result ->
                try {
                    startIntentSenderForResult(
                        result.intentSender,
                        REQUEST_CODE_GOOGLE_SIGN_IN,  /* fillInIntent= */
                        null,  /* flagsMask= */
                        0,  /* flagsValue= */
                        0,  /* extraFlags= */
                        0,  /* options= */
                        null
                    )
                } catch (e: IntentSender.SendIntentException) {
                    Log.e(TAG, "Google Sign-in failed")
                }
            }
            .addOnFailureListener { e -> Log.e(TAG, "Google Sign-in failed", e) }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(FragmentActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    LoginFragment().updateUiWithUser(LoggedInUserView(user?.uid.toString()))
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.textView3.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_readingActivity)
        }

        binding.emailPwd.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_registerFragment)
        }
    }



//    override fun onResume() {
//        super.onResume()
//
////        val actionBar = (activity as AppCompatActivity).supportActionBar;
////        actionBar?.hide();
//
//    }

    companion object {
        private const val TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
    }

//    override fun onStart() {
//        super.onStart()
//        auth = Firebase.auth
//        // Check if user is signed in (non-null) and update UI accordingly.
//        val currentUser = auth.currentUser
//        if (currentUser != null) {
//            LoginFragment().updateUiWithUser(LoggedInUserView(currentUser?.uid.toString()))
//        }
//    }

  override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}