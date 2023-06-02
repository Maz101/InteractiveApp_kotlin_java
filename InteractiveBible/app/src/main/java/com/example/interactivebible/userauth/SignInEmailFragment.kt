package com.example.interactivebible.userauth

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.interactivebible.databinding.FragmentSigninEmailBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

import com.example.interactivebible.R


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SignInEmailFragment : Fragment() {

    private var _binding: FragmentSigninEmailBinding? = null
    private lateinit var auth: FirebaseAuth


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSigninEmailBinding.inflate(inflater, container, false)
        return binding.root


    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title=getString(R.string.back_button)


        binding.login.setOnClickListener {
            email_login()
        }

        val edittext = binding.passwordText
        edittext.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                // If the event is a key-down event on the "enter" button
                if (event.action === KeyEvent.ACTION_DOWN &&
                    keyCode == KeyEvent.KEYCODE_ENTER
                ) {
                    // Perform action on key press
//                    Toast.makeText(activity, edittext.text, Toast.LENGTH_SHORT).show()
                    email_login()
                    return true
                }
                return false
            }
        })
    }


    fun email_login(){


        val progressbar = binding.progressBar
        progressbar.visibility = View.VISIBLE


        val password = binding.password.editText?.text.toString().trim()
        val email = binding.username.editText?.text.toString().trim()

        println(email)
        println(binding.passwordText)


//        auth.signInWithEmailAndPassword(email, password)
//            .addOnCompleteListener(FragmentActivity()) { task ->
//                if (task.isSuccessful) {
//                    // Sign in success, update UI with the signed-in user's information
//                    findNavController().navigate(R.id.action_SecondFragment_to_readingActivity)
//                } else {
//                    progressbar.visibility = View.INVISIBLE
//                    // If sign in fails, display a message to the user.
//                    Log.w(TAG, "signInWithEmail:failure", task.exception)
//
//                    val fragmentManager = (context as? AppCompatActivity)?.supportFragmentManager
//                    fragmentManager?.let {
//                        val currentFragment = fragmentManager.findFragmentById(R.id.container)
//                        currentFragment?.let {
//                            val fragmentTransaction = fragmentManager.beginTransaction()
//                            fragmentTransaction.detach(it)
//                            fragmentTransaction.attach(it)
//                            fragmentTransaction.commit()
//                        }
//                    }
//                }
//            }

    }


    override fun onResume(){
        super.onResume()
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.show()
    }

//    public override fun onStart() {
//        super.onStart()
//        // Check if user is signed in (non-null) and update UI accordingly.
//        val currentUser = auth.currentUser
////        if(currentUser != null){
////            reload();
////        }
//    }

    private fun validateEmail():Boolean{
        val emailInput = binding.username.editText.toString().trim()
        val textInputEmail = binding.username

        if (emailInput.isEmpty()) {
            textInputEmail.error = "Field is empty"
            return false
        } else {
            textInputEmail.error = null
            return true
        }
    }
//
//
    private fun validatePassword():Boolean{
        val passwordInput = binding.password.editText.toString().trim()
        val textInputPassword = binding.password

        if (passwordInput.isEmpty()) {
            textInputPassword.error = "Field is empty"
            return false
        } else {
            textInputPassword.error = null
            return true
        }
    }
//
//    fun confirmInput(view: View) :Boolean{
//        if (!validateEmail() || !validatePassword()){
//            return false
//        }
//        if (validateEmail() and validatePassword()) {
//            return true
//        }
//    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
