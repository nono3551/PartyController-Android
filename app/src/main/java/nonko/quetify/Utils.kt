package nonko.quetify

import android.content.Context
import android.os.Looper
import android.util.Log
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote
import kotlinx.coroutines.experimental.launch
import nonko.quetify.entities.Party
import kotlin.coroutines.experimental.suspendCoroutine

class Utils {
    companion object {
        private const val spotifyClientId = "6d039b752ff04093862366db1b031ac2"
        private const val redirectUrl = "https://accounts.spotify.com/authorize"

        suspend fun initializeSpotifyRemote(context: Context, onInitializationFailure: (suspend () -> Unit)?) {
            if (Looper.myLooper() == null) {
                Looper.prepare()
            }

            val connectionParams = ConnectionParams.Builder(spotifyClientId)
                    .setRedirectUri(redirectUrl)
                    .showAuthView(true)
                    .build()

            return suspendCoroutine { continuation ->
                SpotifyAppRemote.CONNECTOR.connect(context, connectionParams, object : Connector.ConnectionListener {
                    override fun onFailure(p0: Throwable?) {
                        launch { onInitializationFailure?.invoke() }
                    }

                    override fun onConnected(appRemote: SpotifyAppRemote?) {
                        launch {
                            val party = Party.getLastHostedParty(context)

                            if(party != null){
                                party.player = appRemote?.playerApi
                            }
                            continuation.resume(Unit)
                        }
                    }
                })
            }
        }
    }
}