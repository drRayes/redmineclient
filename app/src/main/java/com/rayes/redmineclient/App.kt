package com.rayes.redmineclient

import android.app.Application
import com.rayes.redmineclient.http.Dispatcher
import com.rayes.redmineclient.model.Issue
import com.rayes.redmineclient.model.IssuesService
import com.rayes.redmineclient.model.ProjectsService

class App : Application() {
    val projectService = ProjectsService()
    val issueService = IssuesService()
}