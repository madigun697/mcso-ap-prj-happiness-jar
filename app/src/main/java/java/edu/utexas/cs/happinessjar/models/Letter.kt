package java.edu.utexas.cs.happinessjar.models

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp

data class Letter(
    var title: String = "",
    var body: String = "",
    @ServerTimestamp val timeStamp: Timestamp? = null,
    @DocumentId var firestoreID: String = ""
)