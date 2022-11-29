package java.edu.utexas.cs.happinessjar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.google.firebase.auth.FirebaseAuth
import java.edu.utexas.cs.happinessjar.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {
    private val viewModel: UserModel by viewModels()
    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            viewModel.updateUser()
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent)
        }

        binding.signupBtn.setOnClickListener {
            val intent = Intent(this, EmailActivity::class.java)
            intent.putExtra("type", "signup")
            startActivity(intent)
        }

        binding.signInEmailBtn.setOnClickListener {
            val intent = Intent(this, EmailActivity::class.java)
            intent.putExtra("type", "signin")
            startActivity(intent)
        }
    }
}