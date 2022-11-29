package java.edu.utexas.cs.happinessjar

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import java.edu.utexas.cs.happinessjar.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val viewModel: UserModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    private val signInLauncher =
        registerForActivityResult(FirebaseAuthUIActivityResultContract()) {
            viewModel.updateUser()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        viewModel.updateUser()
        viewModel.observeDisplayName().observe(this, Observer { binding.userName.text = "Hi! $it!" })

        binding.bottomNav.setOnItemSelectedListener {
            Log.d("Main", it.title.toString())
            when (it.title) {
                "Settings" -> {
                    val intent = Intent(this, SettingsActivity::class.java)
                    startActivity(intent)
                }
                "New" -> {
                    val intent = Intent(this, LetterActivity::class.java)
                    startActivity(intent)
                }
                "Logout" -> {
                    viewModel.signOut()
                    val intent = Intent(this, AuthActivity::class.java)
                    startActivity(intent)
                }
            }
            true
        }

        AuthInit(viewModel, signInLauncher)
    }

}