package java.edu.utexas.cs.happinessjar

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import java.edu.utexas.cs.happinessjar.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

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
            }
            true
        }
    }

}