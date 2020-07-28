package nonko.quetify.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.android.synthetic.main.activity_queing.*
import kotlinx.coroutines.experimental.DefaultDispatcher
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext
import nonko.quetify.R
import nonko.quetify.entities.Party
import nonko.quetify.entities.QueueRequest
import nonko.quetify.messaging.MessagingService
import nonko.quetify.repositories.PartyRepository

class QueuingActivity : AppCompatActivity() {

    private var remotePartySnapshotListener: ListenerRegistration? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_queing)

        val partyId = intent.getStringExtra(partySessionIdExtras)

        if(partyId != null) {
            launch(UI) {
                withContext(DefaultDispatcher) {
                    remotePartySnapshotListener =
                            PartyRepository.get(partyId)?.reference?.addSnapshotListener { documentSnapshot, _ ->
                                Party.lastRemotelyControlledParty = documentSnapshot?.toObject(Party::class.java)
                                Party.lastRemotelyControlledParty?.documentReference = documentSnapshot?.reference
                            }
                }
            }

            session_secret_edit_text.setText(Party.lastRemotelyControlledParty?.secret)
            session_secret_edit_text.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun afterTextChanged(editable: Editable?) {
                    Party.lastRemotelyControlledParty?.secret = session_secret_edit_text.text.toString()
                }
            })

            add_to_queue_button.setOnClickListener {
                Party.lastRemotelyControlledParty?.secret = session_secret_edit_text!!.text.toString()
                MessagingService.send(
                        QueueRequest(
                                Party.lastRemotelyControlledParty?.secret!!,
                                song_url_edit_text!!.text.toString(),
                                Party.lastRemotelyControlledParty?.messagingToken
                        ),
                        this@QueuingActivity
                )
            }
        }
        else {
            val builder: AlertDialog.Builder = AlertDialog.Builder(this@QueuingActivity, android.R.style.Theme_Material_Dialog_Alert)
            builder.setTitle("Error")
                    .setMessage("You need to connect to remote session from Quetify first.")
                    .setPositiveButton("Ok") { _, _ -> this@QueuingActivity.finish() }
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setCancelable(false)
                    .show()
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        intent?.getStringExtra("android.intent.extra.TEXT").let { stringExtras -> song_url_edit_text.setText(stringExtras) }
    }

    override fun onDestroy() {
        super.onDestroy()
        remotePartySnapshotListener?.remove()
    }

    companion object {
        private const val partySessionIdExtras: String = "partySessionIdExtras"

        fun startActivity(context: Context, partySessionId: String?) {
            val intent = Intent(context, QueuingActivity::class.java)
            intent.putExtra(partySessionIdExtras, partySessionId)
            context.startActivity(intent)
        }
    }
}