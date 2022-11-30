package java.edu.utexas.cs.happinessjar.utils

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.edu.utexas.cs.happinessjar.models.Letter

class DBHelper(private val uid: String) {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun uploadNewLetter(letter: Letter, callback: Unit) {
        letter.firestoreID = db.collection(uid).document().id
        db.collection(uid)
            .document(letter.firestoreID)
            .set(letter)
            .addOnSuccessListener {
                callback
            }
    }

    fun fetchLetters(letters: MutableLiveData<List<Letter>>) {
        val query = db.collection(uid)
        query.orderBy("timeStamp", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { it ->
                letters.postValue(it.documents.mapNotNull {
                    it.toObject(Letter::class.java)
                })
            }
    }
}