package nonko.quetify.entities

import android.content.Context
import android.media.AudioManager
import android.media.ToneGenerator
import android.util.Log
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Exclude
import com.spotify.android.appremote.api.PlayerApi
import kotlinx.coroutines.experimental.DefaultDispatcher
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext
import nonko.quetify.Utils
import nonko.quetify.messaging.MessagingService
import nonko.quetify.repositories.PartyRepository
import java.util.*

class Party(name: String?, secret: String?) {
    private constructor() : this(null, null)

    var secret = secret @Exclude get
    var documentReference: DocumentReference? = null @Exclude get
    var created: Date
    var updated: Date
    var player: PlayerApi? = null @Exclude get

    var name: String? = name
        set(value) {
            field = value
            updated = Date()
            documentReference?.set(this)
        }

    var messagingToken: String? = null
        set(value) {
            field = value
            updated = Date()
            documentReference?.set(this)
        }

    fun updateSecret(context: Context, newSecret: String){
        secret = newSecret
        val preferences = context.getSharedPreferences(Party::class.java.name, Context.MODE_PRIVATE)
        val preferencesEditor = preferences.edit()
        preferencesEditor.putString(SecretPreferencesKey, secret)
        preferencesEditor.apply()
    }

    init {
        messagingToken = MessagingService.MessagingToken
        created = Date()
        updated = Date()
    }

    companion object {
        private const val IdPreferencesKey = "Id"
        private const val SecretPreferencesKey = "Secret"

        private var lastHostedParty: Party? = null
        var lastRemotelyControlledParty: Party? = null

        suspend fun getLastHostedParty(context: Context): Party? {
            return if (lastHostedParty != null) {
                lastHostedParty
            } else {
                val preferences = context.getSharedPreferences(Party::class.java.name, Context.MODE_PRIVATE)
                val id = preferences.getString(IdPreferencesKey, null)
                val secret = preferences.getString(SecretPreferencesKey, null)

                if (id != null && secret != null) {
                    lastHostedParty = recreateParty(id, secret)
                    lastHostedParty
                } else {
                    null
                }
            }
        }

        private suspend fun recreateParty(id: String, secret: String?): Party? {
            val partySnapshot = PartyRepository.get(id)
            val party = partySnapshot?.toObject(Party::class.java)

            if (party != null) {
                party.documentReference = partySnapshot.reference
                party.secret = secret

                MessagingService.MessagingToken

                if(MessagingService.MessagingToken == null){
                    MessagingService.initializeFirebaseToken()
                }

                party.messagingToken = MessagingService.MessagingToken
            }

            return party
        }

        suspend fun createNewParty(name: String, secret: String, context: Context): Party {
            val party = Party(name, secret)
            withContext(DefaultDispatcher) { party.documentReference = PartyRepository.add(party) }
            lastHostedParty = party

            val preferences = context.getSharedPreferences(Party::class.java.name, Context.MODE_PRIVATE)
            val preferencesEditor = preferences.edit()
            preferencesEditor.putString(SecretPreferencesKey, secret)
            preferencesEditor.putString(IdPreferencesKey, party.documentReference?.id)
            preferencesEditor.apply()

            return party
        }

        fun processQueueRequest(request: QueueRequest, context: Context) {
            launch(UI) {
                if (lastHostedParty == null) {
                    lastHostedParty = getLastHostedParty(context)
                }

                if (request.secret == lastHostedParty?.secret) {
                    try {
                        launch(UI) {
                            val songRequest = request.songLink.replace("https://open.spotify.com/", "spotify:").replace("/", ":").split("?")[0]

                            if (lastHostedParty != null && lastHostedParty?.player == null) {
                                Utils.initializeSpotifyRemote(context, null)
                            }

                            lastHostedParty?.player?.playerState?.setResultCallback { playerState ->
                                if (playerState.track == null) {
                                    lastHostedParty?.player?.play(songRequest)
                                } else {
                                    lastHostedParty?.player?.queue(songRequest)
                                }
                                lastHostedParty?.player?.resume()

                                val toneGen1 = ToneGenerator(AudioManager.STREAM_MUSIC, 100)
                                toneGen1.startTone(ToneGenerator.TONE_PROP_BEEP2, 150)
                            }
                        }
                    } catch (exception: Throwable) {
                        //TODO(HANDLE ERROR AND SEND RESPONSE)
                        Log.v("spotify queue error", exception.message)
                    }
                }
            }
        }

        fun removePartyFromPreferences(context: Context) {
            val preferences = context.getSharedPreferences(Party::class.java.name, Context.MODE_PRIVATE)
            val prefEdit = preferences.edit()

            prefEdit.remove(IdPreferencesKey)
            prefEdit.remove(SecretPreferencesKey)

            prefEdit.apply()

            lastRemotelyControlledParty = null
        }
    }
}