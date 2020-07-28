package nonko.quetify.repositories

import com.google.firebase.firestore.*
import nonko.quetify.entities.Party
import kotlin.coroutines.experimental.coroutineContext
import kotlin.coroutines.experimental.suspendCoroutine

class PartyRepository {
    companion object {
        private const val collectionName = "PartySession"

        suspend fun add(value: Any) : DocumentReference? {
            return suspendCoroutine { continuation ->
                FirebaseFirestore.getInstance().collection(collectionName).add(value).addOnSuccessListener { documentReference ->
                    continuation.resume(documentReference)
                }
            }
        }

        suspend fun get(id: String): DocumentSnapshot? {
            var snapshotListener: ListenerRegistration? = null

            return suspendCoroutine { continuation ->
                snapshotListener = FirebaseFirestore.getInstance().collection(collectionName).document(id).addSnapshotListener{ documentSnapshot, _ ->
                    snapshotListener?.remove()
                    continuation.resume(documentSnapshot)
                }
            }
        }

        suspend fun get(limit: Long, startAfter: Party?): List<Party>? {
            val query = FirebaseFirestore.getInstance().collection(collectionName).orderBy("created", Query.Direction.DESCENDING).limit(limit)

            if(startAfter != null){
                query.startAfter(startAfter.documentReference)
            }

            return suspendCoroutine { continuation ->
                query.get().addOnSuccessListener { querySnapshot ->
                    val parties = ArrayList<Party>()

                    for (documentSnapshot: DocumentSnapshot in querySnapshot.documents) {
                        val party = documentSnapshot.toObject(Party::class.java)
                        party?.documentReference = documentSnapshot.reference
                        party?.let { parties.add(it) }
                    }

                    continuation.resume(parties)
                }
            }
        }
    }
}