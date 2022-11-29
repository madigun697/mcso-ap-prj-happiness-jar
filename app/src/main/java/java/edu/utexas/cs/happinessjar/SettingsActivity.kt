package java.edu.utexas.cs.happinessjar

import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.edu.utexas.cs.happinessjar.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding

    private val settingItems = mutableListOf("Profile settings", "Letters")
    private val settingIcons =
        mutableListOf(R.drawable.ic_baseline_person_24, R.drawable.ic_baseline_library_books_24)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar!!.setDisplayShowCustomEnabled(true)
        actionBar.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        actionBar.setCustomView(R.layout.appbar_settings)
        actionBar.setHomeAsUpIndicator(com.google.android.material.R.drawable.material_ic_keyboard_arrow_left_black_24dp)
        actionBar.setDisplayHomeAsUpEnabled(true)

        val settingAdapter = SettingAdapter(settingItems, settingIcons)

        binding.settingList.adapter = settingAdapter
        binding.settingList.setOnItemClickListener { parent, view, position, id ->
            Log.d("Setting", settingItems[position])
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}