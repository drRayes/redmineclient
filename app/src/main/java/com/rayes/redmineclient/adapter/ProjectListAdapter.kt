package com.rayes.redmineclient.adapter

import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.rayes.redmineclient.R
import com.rayes.redmineclient.databinding.ProjectItemBinding
import com.rayes.redmineclient.model.Project
import java.util.*

interface ProjectActionListener {

    fun onMouseMove(project: Project)

    fun onProjectDetails(project: Project)

    fun onProjectMove(project: Project, moveBy: Int)

    fun onProjectDelete(project: Project)
}

class ProjectListAdapter(private val projectActionListener: ProjectActionListener) :
    RecyclerView.Adapter<ProjectListAdapter.ProjectViewHolder>(),
    View.OnClickListener {

    class ProjectViewHolder(val binding: ProjectItemBinding) : RecyclerView.ViewHolder(binding.root)

    var projects: MutableList<Project> = LinkedList<Project>()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    override fun onClick(view: View) {
        val project: Project = view.tag as Project
        when (view.id) {
            R.id.moreImageViewButton -> {
                showPopUpMenu(view)
            }
            else -> {
                projectActionListener.onMouseMove(project)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        // Create a new view, which defines the UI of the list item
        val inflater = LayoutInflater.from(parent.context)
        val binding = ProjectItemBinding.inflate(inflater, parent, false)

        binding.root.setOnClickListener(this)
        binding.moreImageViewButton.setOnClickListener(this)

        return ProjectViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ProjectViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val project = projects[position]
        with(viewHolder.binding) {
            viewHolder.itemView.tag = project
            moreImageViewButton.tag = project

            projectItem.text = project.name
        }
    }

    fun showPopUpMenu(view: View) {
        val popUpMenu = PopupMenu(view.context, view)
        val context = view.context
        val project = view.tag as Project
        val position = projects.indexOfFirst { it.id == project.id }

        popUpMenu.menu.add(
            0,
            ID_MORE_INFORMATION,
            Menu.NONE,
            context.getString(R.string.more_information)
        )
        popUpMenu.menu.add(0, ID_MOVE_UP, Menu.NONE, context.getString(R.string.move_up)).apply {
            isEnabled = position > 0
        }
        popUpMenu.menu.add(0, ID_MOVE_DOWN, Menu.NONE, context.getString(R.string.move_down)).apply {
            isEnabled = position < projects.size - 1
        }
        popUpMenu.menu.add(0, ID_DELETE, Menu.NONE, context.getString(R.string.delete))


        popUpMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                ID_MORE_INFORMATION -> {
                    projectActionListener.onProjectDetails(project)
                }
                ID_MOVE_UP -> {
                    projectActionListener.onProjectMove(project, -1)
                }
                ID_MOVE_DOWN -> {
                    projectActionListener.onProjectMove(project, 1)
                }
                ID_DELETE -> {
                    projectActionListener.onProjectDelete(project)
                }
            }
            return@setOnMenuItemClickListener true
        }
        popUpMenu.show()
    }

    override fun getItemCount() = projects.size

    companion object {
        private const val ID_MORE_INFORMATION = 1
        private const val ID_MOVE_UP = 2
        private const val ID_MOVE_DOWN = 3
        private const val ID_DELETE = 4
    }
}