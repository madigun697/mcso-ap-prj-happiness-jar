package java.edu.utexas.cs.happinessjar

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import java.edu.utexas.cs.happinessjar.databinding.ActivityMainBinding
import java.edu.utexas.cs.happinessjar.models.UserModel

class MainActivity : AppCompatActivity() {
    private val viewModel: UserModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    private var letterCnt = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        fetchUserData()

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

    }

    private fun fetchUserData() {
        viewModel.updateUser()
        viewModel.observeDisplayName().observe(this, Observer { binding.userName.text = "Hi! $it!" })
        viewModel.observeLetterCnt().observe(this, Observer {
            letterCnt = it
            binding.notesCnt.text = "${letterCnt.toString()} happiness"
        })

        setJarImage()
    }

    override fun onResume() {
        super.onResume()
        fetchUserData()
    }

    private fun setJarImage() {
        if (letterCnt == 0) {
            binding.imageArea.setImageResource(R.drawable.jar_00_empty)
        } else if (letterCnt >= 1) {
            binding.imageArea.setImageResource(R.drawable.jar_01_start)
        } else if (letterCnt >= 100) {
            binding.imageArea.setImageResource(R.drawable.jar_02_half)
        } else if (letterCnt >= 200) {
            binding.imageArea.setImageResource(R.drawable.jar_03_almost_full)
        } else if (letterCnt >= 365) {
            binding.imageArea.setImageResource(R.drawable.jar_04_full)
        }
    }

}