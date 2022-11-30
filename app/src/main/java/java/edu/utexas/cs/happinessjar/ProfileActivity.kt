package java.edu.utexas.cs.happinessjar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import java.edu.utexas.cs.happinessjar.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar!!.setDisplayShowCustomEnabled(true)
        actionBar.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        actionBar.setCustomView(R.layout.appbar_profile)
        actionBar.setHomeAsUpIndicator(com.google.android.material.R.drawable.material_ic_keyboard_arrow_left_black_24dp)
        actionBar.setDisplayHomeAsUpEnabled(true)

        val user = FirebaseAuth.getInstance().currentUser
        binding.userName.setText(user!!.displayName, TextView.BufferType.EDITABLE)
        binding.userName.isFocusable = true

        binding.userName.requestFocus()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.profile_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save_change -> {
                val userName = binding.userName.text.toString()
                val user = FirebaseAuth.getInstance().currentUser

                if (user != null && userName.trim() != "" && userName != user.displayName) {
                    val userProfileChangeRequest =
                        UserProfileChangeRequest.Builder().setDisplayName(userName).build()
                    user.updateProfile(userProfileChangeRequest).addOnCompleteListener {
                        if (it.isSuccessful) {
                            onBackPressed()
                        }
                    }
                } else {
                    onBackPressed()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}