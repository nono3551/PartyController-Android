package nonko.quetify.activities.partiesListActivity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_parties_list.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext
import nonko.quetify.R
import nonko.quetify.entities.Party
import nonko.quetify.repositories.PartyRepository

class PartiesListActivity : AppCompatActivity() {
    lateinit var layoutManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parties_list)

        parties_list_recycler_view.setHasFixedSize(true)

        layoutManager = LinearLayoutManager(this)

        parties_list_recycler_view.layoutManager = layoutManager

        launch(UI) {
            withContext(UI) {
                val parties: List<Party>? = PartyRepository.get(10, null)
                parties?.let {
                    parties_list_recycler_view.adapter = PartyRecyclerAdapter(it)
                }
            }
        }
    }

    companion object {
        fun startActivity(context: Context){
            context.startActivity(Intent(context, PartiesListActivity::class.java))
        }
    }
}