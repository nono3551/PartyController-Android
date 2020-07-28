package nonko.quetify.activities.partiesListActivity

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import nonko.quetify.R
import nonko.quetify.activities.QueuingActivity
import nonko.quetify.entities.Party

class PartyRecyclerAdapter(private val items: List<Party>) : RecyclerView.Adapter<PartyRowHolder>() {

    override fun onBindViewHolder(holder: PartyRowHolder, possition: Int) {
        val party: Party = items[holder.adapterPosition]

        holder.textView.text = party.name

        holder.textView.setOnClickListener{
            QueuingActivity.startActivity(holder.textView.context, party.documentReference?.id)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, possition: Int): PartyRowHolder {
        val textView: TextView = LayoutInflater.from(parent.context).inflate(R.layout.party_session_row_layout, parent, false) as TextView
        return PartyRowHolder(textView)
    }

}