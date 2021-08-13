package com.rayes.redmineclient

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rayes.redmineclient.adapter.ProjectActionListener
import com.rayes.redmineclient.adapter.ProjectListAdapter
import com.rayes.redmineclient.databinding.ActivityMainBinding
import com.rayes.redmineclient.http.Dispatcher
import java.time.LocalTime
import java.util.*
import android.content.Intent
import com.google.gson.Gson
import com.rayes.redmineclient.adapter.IssueEventListener
import com.rayes.redmineclient.model.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ProjectListAdapter

    private val projectsService: ProjectsService
        get() = (applicationContext as App).projectService

    private val issueService: IssuesService
        get() = (applicationContext as App).issueService

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)
        val buttonLogin = findViewById<Button>(R.id.to_login_page)
        val projectList = findViewById<RecyclerView>(R.id.projectList)

        buttonLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        val dispatcher = Dispatcher(this)

        adapter = ProjectListAdapter(object: ProjectActionListener {
            override fun onMouseMove(project: Project) {
                val intent = Intent(this@MainActivity, ProjectActivity::class.java)
                intent.putExtra("project", Gson().toJson(project))
                startActivity(intent)

                val url =
                    "https://rayesredminetest.planio.com/projects/${project.identifier}/issues.json"
                println(url)
                dispatcher.requestIssuesByProject(
                    url,
                    issueService
                )
            }

            override fun onProjectDetails(project: Project) {
                Toast.makeText(
                    this@MainActivity,
                    project.toString(),
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onProjectMove(project: Project, moveBy: Int) {
                projectsService.moveElement(project, moveBy)
            }

            override fun onProjectDelete(project: Project) {
                projectsService.removeElement(project)
            }
        })

        projectList.layoutManager = LinearLayoutManager(this)
        projectList.adapter = adapter

        button.setOnClickListener {
//            result.text = "another result ${LocalTime.now()}"
            val url = "https://rayesredminetest.planio.com/projects.json"
            dispatcher.requestProjects(url, projectsService)
        }
        projectsService.addListener(projectsListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        projectsService.removeListener(projectsListener)
    }

    private val projectsListener: ProjectsListener = {
        adapter.projects = it
    }



}