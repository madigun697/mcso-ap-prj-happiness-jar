package java.edu.utexas.cs.happinessjar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import java.edu.utexas.cs.happinessjar.databinding.ActivityLetterBinding
import java.edu.utexas.cs.happinessjar.models.Letter
import java.edu.utexas.cs.happinessjar.utils.DBHelper

class LetterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLetterBinding
    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLetterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar!!.setDisplayShowCustomEnabled(true)
        actionBar.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        actionBar.setCustomView(R.layout.appbar_letter)
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_close_24)
        actionBar.setDisplayHomeAsUpEnabled(true)

        val uid = intent.getStringExtra("uid")
        dbHelper = DBHelper(uid!!)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.letter_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_letter -> {
                Log.d("Letter", "Submit")
                if (binding.letterTitle.text.isEmpty() || binding.letterBody.text.isEmpty()) {
                    Toast.makeText(baseContext, "Please type the title and body.", Toast.LENGTH_SHORT).show()
                } else {
                    val letter = Letter(binding.letterTitle.text.toString(), binding.letterBody.text.toString())
                    dbHelper.uploadNewLetter(letter, callback())
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun callback() {
        onBackPressed()
    }
}