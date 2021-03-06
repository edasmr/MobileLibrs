package com.example.mobilelibrs

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.StringBuilder
import kotlin.math.log

class Activity_Login : AppCompatActivity() {

    // Firebase
    private lateinit var mAuthListener: FirebaseAuth.AuthStateListener
    var flag = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_login)

        setupFirebase()
        var btnEmail_sign_in_button = findViewById<Button>(R.id.btn_add_library)
        var etEmail = findViewById<EditText>(R.id.etEmail)
        var etPassword = findViewById<EditText>(R.id.etPassword)
        var btnEmail_sign_up_button = findViewById<TextView>(R.id.btn_sign_up)
        var tvresend_verification_email = findViewById<TextView>(R.id.tvresend_verification_email)



        btnEmail_sign_up_button.setOnClickListener {
            reDirectSignUpPage()
        }

        // login btn click listener
        btnEmail_sign_in_button.setOnClickListener {

            // this email for login admin menu page
            if (etEmail.text.toString().equals("a@gmail.com"))
                flag = 1
            // this email for login lm menu page
            else if (!etEmail.text.toString().equals("a@gmail.com") && !etEmail.text.toString().equals("lb@gmail.com")  )
                flag = 2
            // this email for librarian admin menu page
            else if (etEmail.text.toString().equals("lb@gmail.com"))
                flag = 3
            else
                flag = 4

            if (etEmail.text.isNotEmpty() && etPassword.text.isNotEmpty()) {
                showProgressbar()
                if (flag == 1) {
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(
                        etEmail.text.toString(),
                        etPassword.text.toString()
                    )
                        .addOnCompleteListener(object : OnCompleteListener<AuthResult> {
                            override fun onComplete(p0: Task<AuthResult>) {
                                // if user is admin
                                // reDirect_Admin_MenuPage()
                                closeProgressBar()
                                Toast.makeText(
                                    this@Activity_Login,
                                    "Admin Login Successful: " + p0.isSuccessful,
                                    Toast.LENGTH_LONG
                                ).show()

                            }

                        })
                        .addOnFailureListener(object : OnFailureListener {
                            override fun onFailure(p0: Exception) {
                                Toast.makeText(
                                    this@Activity_Login,
                                    "Login Failed: " + p0.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                                closeProgressBar()
                            }
                        })



                }
                if (flag == 2) {
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(
                        etEmail.text.toString(),
                        etPassword.text.toString()
                    )
                        .addOnCompleteListener(object : OnCompleteListener<AuthResult> {
                            override fun onComplete(p0: Task<AuthResult>) {
                                // if user is LM
                                closeProgressBar()
                                Toast.makeText(
                                    this@Activity_Login,
                                    "Library Member Login Successful: " + p0.isSuccessful,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        })
                        .addOnFailureListener(object : OnFailureListener {
                            override fun onFailure(p0: Exception) {
                                Toast.makeText(
                                    this@Activity_Login,
                                    "Login Failed: " + p0.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                                closeProgressBar()
                            }
                        })
                }

                if (flag == 3) {
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(
                        etEmail.text.toString(),
                        etPassword.text.toString()
                    )
                        .addOnCompleteListener(object : OnCompleteListener<AuthResult> {
                            override fun onComplete(p0: Task<AuthResult>) {
                                // if user is librarian
                                closeProgressBar()
                                Toast.makeText(
                                    this@Activity_Login,
                                    "Librarian Login Successful: " + p0.isSuccessful,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        })
                        .addOnFailureListener(object : OnFailureListener {
                            override fun onFailure(p0: Exception) {
                                Toast.makeText(
                                    this@Activity_Login,
                                    "Login Failed: " + p0.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                                closeProgressBar()
                            }
                        })
                }

            } else {
                Toast.makeText(this, "Please fill in the blank fields.", Toast.LENGTH_LONG).show()
            }
        }
/*
        // if user is lib mem
        else if(flag == 2)
        {
            reDirect_LM_MenuPage()
            closeProgressBar()
            Toast.makeText(this@Activity_Login, "Library Member Login Successful: " + p0.isSuccessful, Toast.LENGTH_SHORT).show()
        }

        // if user is librarian
        else if(flag == 3)
        {
            reDirect_Librarian_MenuPage()
            closeProgressBar()
            Toast.makeText(this@Activity_Login, "Librarian Login Successful: " + p0.isSuccessful, Toast.LENGTH_SHORT).show()

        }
*/

        tvresend_verification_email.setOnClickListener {
            val dialog = Activity_Resend_Verification()
            dialog.show(supportFragmentManager, "dialog_resend_email_verification")
        }
    }

    //FIREBASE SETUP
    private fun setupFirebase() {

        mAuthListener = object : FirebaseAuth.AuthStateListener {

            override fun onAuthStateChanged(p0: FirebaseAuth) {
                var user = p0.currentUser

                if (user != null && flag==1) {


                    Toast.makeText(
                        this@Activity_Login,
                        "Please confirm your e-mail. : " + user.uid,
                        Toast.LENGTH_LONG
                    ).show()

                    //user.uid de??erini al databaseden
                    val userId = user.uid
                    val newIntent = Intent(this@Activity_Login, Activity_Admin_Menu::class.java)
                    newIntent.putExtra("userId", userId)
                    startActivity(newIntent)
                    finish()
                    FirebaseAuth.getInstance().signOut()

                }
                if (user != null && flag==2) {

                    Toast.makeText(
                        this@Activity_Login,
                        "Please confirm your e-mail. : " + user.uid,
                        Toast.LENGTH_LONG
                    ).show()

                    //user.uid de??erini al databaseden
                    val userId = user.uid
                    val newIntent = Intent(this@Activity_Login, Activity_LM_Menu::class.java)
                    newIntent.putExtra("userId", userId)
                    startActivity(newIntent)
                    finish()
                    FirebaseAuth.getInstance().signOut()

                }
                if (user != null && flag==3) {

                    Toast.makeText(
                        this@Activity_Login,
                        "Please confirm your e-mail. : " + user.uid,
                        Toast.LENGTH_LONG
                    ).show()

                    //user.uid de??erini al databaseden
                    val userId = user.uid
                    val newIntent = Intent(this@Activity_Login, Activity_Librarian_Menu::class.java)
                    newIntent.putExtra("userId", userId)
                    startActivity(newIntent)
                    finish()
                    FirebaseAuth.getInstance().signOut()

                }else {
                    Toast.makeText(this@Activity_Login, "EXIT WAS DONE. : ", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        if (mAuthListener != null) {
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthListener)
        }
    }

    override fun onStart() {
        super.onStart()
        FirebaseAuth.getInstance().addAuthStateListener(mAuthListener)
    }

    public fun showProgressbar() {
        var progressBar = findViewById<ProgressBar>(R.id.progressBar)
        progressBar.visibility = View.VISIBLE


    }

    public fun closeProgressBar() {
        var progressBar = findViewById<ProgressBar>(R.id.progressBar)
        progressBar.visibility = View.INVISIBLE
    }

    fun reDirectSignUpPage() {
        var intent = Intent(this@Activity_Login, Activity_Register::class.java)
        startActivity(intent)
        finish()
    }


    // The user is directed from the login page to the home(main) library member page.
    fun reDirect_LM_MenuPage() {
        val intent = Intent(this@Activity_Login, Activity_LM_Menu::class.java)
        startActivity(intent)
        finish()
    }

    // The user is directed from the login page to the home(main) admin page.
    fun reDirect_Admin_MenuPage() {
        val intent = Intent(this@Activity_Login, Activity_Admin_Menu::class.java)
        startActivity(intent)
        finish()
    }

    // The user is directed from the login page to the home(main) librarian page.
    fun reDirect_Librarian_MenuPage() {
        val intent = Intent(this@Activity_Login, Activity_Librarian_Menu::class.java)
        startActivity(intent)
        finish()
    }

}