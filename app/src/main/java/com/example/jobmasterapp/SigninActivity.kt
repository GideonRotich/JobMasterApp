package com.example.jobmasterapp

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_signin.*
import kotlinx.android.synthetic.main.activity_signup.*

class SigninActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        val window: Window = this@SigninActivity.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = ContextCompat.getColor(this@SigninActivity,R.color.giddy)


        signup_four.setOnClickListener {
            startActivity(Intent(this,SignupActivity::class.java))
        }

        btnlogin.setOnClickListener {
            loginUser()
        }

        forgot_two_yes.setOnClickListener {
            startActivity(Intent(this,ForgotPasswordActivity::class.java))
        }
        wajuakwamba.setOnClickListener {
            startActivity(Intent(this,MainActivity9::class.java))
        }

    }

    private fun loginUser() {
        val email = email_text_two.text.toString()
        val password = password_text_two.text.toString()


        when {
            TextUtils.isEmpty(email) -> Toast.makeText(this,"Email is required", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(password) -> Toast.makeText(this,"Password is required", Toast.LENGTH_LONG).show()


            else -> {
                val progressDialog = ProgressDialog(this@SigninActivity)
                progressDialog.setTitle("Sign In")
                progressDialog.setMessage("Please wait, this might take a while...")
                val mAuth:FirebaseAuth = FirebaseAuth.getInstance()
                progressDialog.setCanceledOnTouchOutside(false)
                progressDialog.show()

                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener {task ->
                    if (task.isSuccessful)
                    {
                        progressDialog.dismiss()
                        val intent = Intent(this@SigninActivity,MainActivity6::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        finish()
                    }
                    else
                    {
                        val message = task.exception!!.toString()
                        Toast.makeText(this, "Error: $message",Toast.LENGTH_LONG).show()
                        FirebaseAuth.getInstance().signOut()
                        progressDialog.dismiss()
                    }
                }

            }
        }
    }

    override fun onStart() {
        super.onStart()

        if (FirebaseAuth.getInstance().currentUser != null)
        {
            val intent = Intent(this@SigninActivity,MainActivity6::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }
}