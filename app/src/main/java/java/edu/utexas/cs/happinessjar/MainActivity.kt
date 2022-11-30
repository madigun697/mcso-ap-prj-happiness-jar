package java.edu.utexas.cs.happinessjar

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import java.edu.utexas.cs.happinessjar.databinding.ActivityMainBinding
import java.edu.utexas.cs.happinessjar.models.UserModel
import java.edu.utexas.cs.happinessjar.utils.AuthInit

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

        binding.bottomNav.setOnItemSelectedListener { it ->
            Log.d("Main", it.title.toString())
            when (it.title) {
                "Settings" -> {
                    val intent = Intent(this, SettingsActivity::class.java)
                    startActivity(intent)
                }
                "New" -> {
                    val intent = Intent(this, LetterActivity::class.java)
                    viewModel.observeUid().observe(this, Observer { intent.putExtra("uid", it) })
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

    private fun fetchUserData() {
        viewModel.updateUser()
        viewModel.observeDisplayName().observe(this, Observer { binding.userName.text = "Hi! $it!" })
        viewModel.observeLetterCnt().observe(this, Observer { binding.notesCnt.text = "${it.toString()} happiness" })
    }

    override fun onResume() {
        super.onResume()
        fetchUserData()
    }

}