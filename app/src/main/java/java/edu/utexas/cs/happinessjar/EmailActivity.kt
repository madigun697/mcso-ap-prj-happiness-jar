package java.edu.utexas.cs.happinessjar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.edu.utexas.cs.happinessjar.databinding.ActivityEmailBinding

class EmailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEmailBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmailBinding.inflate(layoutInflater)
        auth = Firebase.auth
        setContentView(binding.root)
        supportActionBar?.hide()

        when (intent.getStringExtra("type")) {
            "signup" -> {
                binding.regArea.visibility = View.VISIBLE
                binding.loginArea.visibility = View.GONE
            }
            "signin" -> {
                binding.regArea.visibility = View.GONE
                binding.loginArea.visibility = View.VISIBLE
            }
        }

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        binding.createAccountBtn.setOnClickListener {
            val id = binding.email.text.toString()
            val password = binding.pwd.text.toString()
            val userName = binding.userName.text.toString()
            val confirmPassword = binding.confirmPwd.text.toString()

            if (id.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || userName.isEmpty()) {
                Toast.makeText(baseContext, "Please enter the every fields.", Toast.LENGTH_SHORT).show()
            } else if (password != confirmPassword) {
                Toast.makeText(baseContext, "Please enter correct Password.", Toast.LENGTH_SHORT).show()
            } else {
                auth.createUserWithEmailAndPassword(id, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val user = FirebaseAuth.getInstance().currentUser
                            if (user != null && userName.trim() != "") {
                                Log.d("Auth", userName)
                                val userProfileChangeRequest =
                                    UserProfileChangeRequest.Builder().setDisplayName(userName).build()
                                user.updateProfile(userProfileChangeRequest).addOnCompleteListener {
                                    if (it.isSuccessful) {
                                        val intent = Intent(this, MainActivity::class.java)
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent)
                                    }
                                }
                            }
                        } else {
                            Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                        }
                    }
            }

        }

        binding.loginBtn.setOnClickListener {
            val id = binding.emailIn.text.toString()
            val password = binding.pwdIn.text.toString()

            if (id.isEmpty() || password.isEmpty()) {
                Toast.makeText(baseContext, "Please enter correct ID/PWD.", Toast.LENGTH_SHORT).show()
            } else {
                auth.signInWithEmailAndPassword(id, password).addOnCompleteListener(this) {
                    if (it.isSuccessful) {
                        val intent = Intent(this, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent)
                    } else {
                        Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }
}