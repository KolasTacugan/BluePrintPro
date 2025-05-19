package com.example.myapplication.adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.Architect

class ArchitectAdapter(
    private val context: Context,
    private var architectList: List<Architect>
) : RecyclerView.Adapter<ArchitectAdapter.ArchitectViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArchitectViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_architect, parent, false)
        return ArchitectViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArchitectViewHolder, position: Int) {
        val architect = architectList[position]

        // Show full name
        holder.nameTextView.text = "${architect.firstname} ${architect.lastname}"

        // Show specializations joined as a single string
        holder.specialtyTextView.text = architect.specializations.joinToString(", ")

        holder.matchButton.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle("Match Found!")
                .setMessage("You matched with ${architect.firstname} ${architect.lastname}!")
                .setPositiveButton("Close") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }

    override fun getItemCount(): Int = architectList.size

    fun updateList(newList: List<Architect>) {
        architectList = newList
        notifyDataSetChanged()
    }

    inner class ArchitectViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.architectName)
        val specialtyTextView: TextView = view.findViewById(R.id.architectSpecialty)
        val matchButton: Button = view.findViewById(R.id.matchButton)
    }
}
