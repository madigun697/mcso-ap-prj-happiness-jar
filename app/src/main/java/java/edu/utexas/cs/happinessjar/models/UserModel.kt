package java.edu.utexas.cs.happinessjar.models

import android.os.Handler
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.AggregateSource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.coroutineContext

class UserModel: ViewModel() {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    private var displayName = MutableLiveData("Uninitialized")
    private var email = MutableLiveData("Uninitialized")
    private var uid = MutableLiveData("Uninitialized")
    private var letterCnt = MutableLiveData(-1)

    init {
        updateUser()
    }

    private fun userLogout() {
        displayName.postValue("No user")
        email.postValue("No email, no active user")
        uid.postValue("No uid, no active user")
    }

    fun updateUser() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            displayName.postValue(currentUser.displayName)
            email.postValue(currentUser.email)
            uid.postValue(currentUser.uid)
            getLetterCnt()
            viewModelScope.launch(viewModelScope.coroutineContext + Dispatchers.IO) {
                getLetterCnt()
            }
        }
        Log.d("uid", uid.value.toString())
    }

    fun observeDisplayName() : LiveData<String> {
        return displayName
    }

    fun observeUid() : LiveData<String> {
        return uid
    }

    fun observeLetterCnt(): LiveData<Int> {
        return letterCnt
    }

    fun signOut() {
        FirebaseAuth.getInstance().signOut()
        userLogout()
    }
    private fun getLetterCnt() {
        db.collection(uid.value!!).addSnapshotListener { snapshot, e ->
            if (snapshot != null) {
                letterCnt.postValue(snapshot.count())
            }
        }
        Log.d("model", letterCnt.value.toString())
    }
}