package davi.xavier.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import davi.xavier.todolist.databinding.ActivityLoginBinding
import kotlinx.android.synthetic.main.activity_login.view.*

const val EMAIL_KEY = "email"

class LoginActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding: ActivityLoginBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = Firebase.auth
        
        binding.loginButton.setOnClickListener { onLogin() }
    }

    override fun onStart() {
        super.onStart()
        
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null && currentUser.email != null) {
            gotoHome(currentUser.email!!)
        }
    }
    
    fun onLogin() {
        val email: String = binding.emailField.text.toString()
        val pass: String = binding.passwordField.passwordField.text.toString()
        
        if (email.isNotBlank() && pass.isNotBlank())
        {
            if (binding.signUpCheckbox.isChecked) {
                firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnSuccessListener {
                    firebaseAuth.signInWithEmailAndPassword(email, pass).addOnSuccessListener {
                        gotoHome(email)
                    }.addOnFailureListener {
                        Toast.makeText(this, "Credenciais inválidas.", Toast.LENGTH_SHORT).show()
                    }
                }.addOnFailureListener {
                    Toast.makeText(this, "Falha no cadastro: " + it.message, Toast.LENGTH_LONG).show()
                }
            }
            
        }
    }
    
    fun gotoHome(email: String) {
        val intent = Intent(this, MainActivity::class.java).apply { 
            putExtra(EMAIL_KEY, email)
        }
        startActivity(intent)
    }
}
