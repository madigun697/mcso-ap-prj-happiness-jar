package java.edu.utexas.cs.happinessjar

import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest


// https://firebase.google.com/docs/auth/android/firebaseui
class AuthInit(viewModel: UserModel, signInLauncher: ActivityResultLauncher<Intent>) {
    companion object {
        private const val TAG = "AuthInit"
        fun setDisplayName(displayName : String, viewModel: UserModel) {
            Log.d(TAG, "XXX profile change request")
            // XXX Write me. User is attempting to update display name. Get the profile updates (see android doc)
            val user = FirebaseAuth.getInstance().currentUser
            if (user != null && displayName.trim() != "") {
                Log.d(TAG, displayName)
                val userProfileChangeRequest =
                    UserProfileChangeRequest.Builder().setDisplayName(displayName).build()
                user.updateProfile(userProfileChangeRequest).addOnCompleteListener {
                    if (it.isSuccessful) {
                        viewModel.updateUser()
                    }
                }
            }
        }
    }

    init {
        val user = FirebaseAuth.getInstance().currentUser
        if (user == null) {
            Log.d(TAG, "XXX user null")
            // Choose authentication providers
            val providers = arrayListOf(
                AuthUI.IdpConfig.EmailBuilder().build()
            )

            // Create and launch sign-in intent
            // XXX Write me. Set authentication providers and start sign-in for user
            val signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build()
            signInLauncher.launch(signInIntent)
            viewModel.updateUser()
        } else {
            Log.d(TAG, "XXX user ${user.displayName} email ${user.email}")
            viewModel.updateUser()
        }
    }
}
