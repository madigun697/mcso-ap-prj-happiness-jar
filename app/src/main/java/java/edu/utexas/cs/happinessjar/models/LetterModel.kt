package java.edu.utexas.cs.happinessjar.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import java.edu.utexas.cs.happinessjar.utils.DBHelper

class LetterModel: ViewModel() {
    private var letters = MutableLiveData<List<Letter>>()
    private lateinit var dbHelper: DBHelper

    init {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (uid != null) {
            dbHelper = DBHelper(uid)
        }
    }

    fun observeLetters(): LiveData<List<Letter>> {
        return letters
    }

    fun fetchLetters() {
        dbHelper.fetchLetters(letters)
    }

    fun getLetter(position: Int): Letter {
        val letter = letters.value?.get(position)
        return letter!!
    }
}