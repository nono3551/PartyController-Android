package nonko.quetify.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import nonko.quetify.R
import nonko.quetify.activities.partiesListActivity.PartiesListActivity
import nonko.quetify.entities.Party
import nonko.quetify.messaging.MessagingService

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        connect_to_someone_button.setOnClickListener {
            PartiesListActivity.startActivity(this@MainActivity)
        }

        start_streaming_session_button.setOnClickListener {
            HostingActivity.startActivity(this@MainActivity)
        }

        close_hosting_session_button.setOnClickListener {
            MessagingService.unregister()
            Party.removePartyFromPreferences(applicationContext)
        }
    }
}