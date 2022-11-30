package java.edu.utexas.cs.happinessjar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.commit
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import java.edu.utexas.cs.happinessjar.databinding.ActivityLetterListBinding
import java.edu.utexas.cs.happinessjar.models.LetterModel
import java.edu.utexas.cs.happinessjar.utils.LetterAdapter
import java.edu.utexas.cs.happinessjar.views.LetterListFragment

class LetterListActivity : AppCompatActivity() {
    private val viewModel: LetterModel by viewModels()
    private lateinit var binding: ActivityLetterListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLetterListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar!!.setDisplayShowCustomEnabled(true)
        actionBar.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        actionBar.setCustomView(R.layout.appbar_letter_list)
        actionBar.setHomeAsUpIndicator(com.google.android.material.R.drawable.material_ic_keyboard_arrow_left_black_24dp)
        actionBar.setDisplayHomeAsUpEnabled(true)

        supportFragmentManager.commit {
            replace(R.id.letterFragment, LetterListFragment.newInstance())
        }
        viewModel.fetchLetters()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}