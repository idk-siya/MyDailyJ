package com.example.poefn.ui.login

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.poefn.R
import com.example.poefn.model.JournalEntry

class JournalAdapter(
    private var entries: List<JournalEntry>,
    private val onEntryClick: (JournalEntry) -> Unit
) : RecyclerView.Adapter<JournalAdapter.EntryViewHolder>() {

    class EntryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleText: TextView = view.findViewById(R.id.entryTitleText)
        val dateText: TextView = view.findViewById(R.id.entryDateText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_journal_entry, parent, false)
        return EntryViewHolder(view)
    }

    override fun onBindViewHolder(holder: EntryViewHolder, position: Int) {
        val entry = entries[position]
        holder.titleText.text = entry.title
        holder.dateText.text = entry.createdAt
        holder.itemView.setOnClickListener { onEntryClick(entry) }
    }

    override fun getItemCount(): Int = entries.size

    fun updateEntries(newEntries: List<JournalEntry>) {
        entries = newEntries
        notifyDataSetChanged()
    }
}
