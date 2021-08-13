package com.rayes.redmineclient.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.rayes.redmineclient.databinding.IssueItemBinding
import com.rayes.redmineclient.model.Issue
import java.util.*

interface IssueEventListener {
    fun onMouseMove(issue: Issue)
}

class IssuesListAdapter(private val issueEventListener: IssueEventListener) :
    RecyclerView.Adapter<IssuesListAdapter.IssueViewHolder>(),
    View.OnClickListener {

    class IssueViewHolder(val binding: IssueItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    var issues: MutableList<Issue> = LinkedList<Issue>()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueViewHolder {
        // Create a new view, which defines the UI of the list item
        val inflater = LayoutInflater.from(parent.context)
        val binding = IssueItemBinding.inflate(inflater, parent, false)

        binding.root.setOnClickListener(this)
        binding.moreImageViewButton.setOnClickListener(this)

        return IssueViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IssueViewHolder, position: Int) {
        val issue = issues[position]
        with(holder.binding) {
            holder.itemView.tag = issue

            issueItem.text = issue.author.name + issue.id
        }
    }

    override fun getItemCount(): Int {
        return issues.size
    }

    override fun onClick(view: View) {
        val issue = view.tag as Issue
        issueEventListener.onMouseMove(issue)
    }

}