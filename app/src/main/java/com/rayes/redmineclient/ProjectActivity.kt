package com.rayes.redmineclient

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.rayes.redmineclient.adapter.IssueEventListener
import com.rayes.redmineclient.adapter.IssuesListAdapter
import com.rayes.redmineclient.databinding.ActivityProjectBinding
import com.rayes.redmineclient.model.Issue
import com.rayes.redmineclient.model.IssuesListener
import com.rayes.redmineclient.model.IssuesService
import com.rayes.redmineclient.model.Project

class ProjectActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProjectBinding
    private lateinit var adapter: IssuesListAdapter

    private val issuesService: IssuesService
        get() = (applicationContext as App).issueService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProjectBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_project)

        val recyclerView = findViewById<RecyclerView>(R.id.issue_list)

        val argements = intent.extras

//        val project: Project = Gson().fromJson()

        adapter = IssuesListAdapter(object: IssueEventListener{
            override fun onMouseMove(issue: Issue) {
                val intent = Intent(this@ProjectActivity, IssueActivity::class.java)
                intent.putExtra("issue", Gson().toJson(issue))
                intent.putExtra("project", argements?.get("project")?.toString())
                startActivity(intent)
            }
        })

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        issuesService.addListener(issueListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        issuesService.removeListener(issueListener)
    }

    private val issueListener: IssuesListener = {
        adapter.issues = it
    }
}