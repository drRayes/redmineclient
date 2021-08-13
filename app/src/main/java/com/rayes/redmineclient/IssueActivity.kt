package com.rayes.redmineclient

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.gson.Gson
import com.rayes.redmineclient.databinding.ActivityIssueBinding
import com.rayes.redmineclient.http.Dispatcher
import com.rayes.redmineclient.model.IdNameTemplate
import com.rayes.redmineclient.model.Issue
import com.rayes.redmineclient.model.Project
import io.ktor.client.*
import org.json.JSONObject

class IssueActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIssueBinding

    override fun onCreate(savedInstanceState: Bundle?)  {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_issue)

        val button = findViewById<Button>(R.id.issue_update_button)

        val arguments = intent.extras
        val issue: Issue = Gson().fromJson(arguments?.get("issue").toString(), Issue::class.java)

        binding.issue = issue

        button.setOnClickListener {
            Dispatcher(this).createIssue(
                "https://rayesredminetest.planio.com/issues.json",
                issue
            )
            Dispatcher(this).updateIssue(
                "https://rayesredminetest.planio.com/issues/${issue.id}.json",
                issue
            )
        }

    }

}