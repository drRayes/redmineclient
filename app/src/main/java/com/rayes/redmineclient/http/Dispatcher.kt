package com.rayes.redmineclient.http

import android.content.Context
import android.widget.Toast
import com.google.gson.Gson
import com.rayes.redmineclient.model.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.http.cio.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import java.util.*
import java.util.logging.Logger

private val LOG = Logger.getLogger(Dispatcher::class.java.toString())
class Dispatcher(val context: Context) {

    val httpClient: HttpClient = HttpClient()

    fun requestProjects(url: String, projectsService: ProjectsService)  {
        runBlocking {
            try {
                val response = httpClient.get<HttpResponse>(url)
                projectsService.clear()
                val array = JSONObject(response.receive<String>()).getJSONArray("projects")
                val projects = Gson().fromJson(array.toString(), Array<Project>::class.java)

                projects.forEach {
                    projectsService.addElement(it)
                }
            } catch (e: Exception) {
                Toast.makeText(
                    context,
                    "ERROR: $e",
                    Toast.LENGTH_LONG
                ).show()
                e.printStackTrace()
            }
        }
    }

    fun requestIssuesByProject(url: String, issuesService: IssuesService) {
        runBlocking {
            try {
                val response = httpClient.get<HttpResponse>(url)
                issuesService.clear()
                val array = JSONObject(response.receive<String>()).getJSONArray("issues")
                val issues =
                    Gson().fromJson(array.toString(), Array<Issue>::class.java)

                issues.forEach {
                    issuesService.addElement(it)
                }
            } catch (e: Exception) {
                Toast.makeText(
                    context,
                    "ERROR: $e",
                    Toast.LENGTH_LONG
                ).show()
                e.printStackTrace()
            }
        }
    }

    fun updateIssue(url: String, issue: Issue) {
        val jsonObject = JSONObject().apply {
            put("project_id", issue.project.id)
            put("tracker_id", 3)
            put("subject", issue.subject)
            put("description", issue.description)
            put("status_id", 5)
            put("priority_id", 12)
//            fixed_version_id - ID of the Target Versions (previously called 'Fixed Version' and still referred to as such in the API)
        }
        runBlocking {
            try {
                val response = httpClient.put<HttpResponse>(url) {
                    body = jsonObject.toString()
                }
                println(response.status)
            } catch (e: Exception) {
                Toast.makeText(
                    context,
                    "ERROR: $e",
                    Toast.LENGTH_LONG
                ).show()
                e.printStackTrace()
            }
        }
    }

    fun createIssue(url: String, issue: Issue) {
        val jsonObject = JSONObject().apply {
            put("project_id", issue.project.id)
            put("tracker_id", 3)
            put("subject", issue.subject)
            put("description", issue.description)
            put("status_id", 5)
            put("priority_id", 12)
        }
        println(Gson().toJson(issue))
        println(jsonObject)
        runBlocking {
            try {
                val response = httpClient.post<HttpResponse>(
//                    "$url?key=0b820ed94b981f7bb2fa056f8828ee021820eeca"
                    "$url"
                ) {
                    headers {
                        append(HttpHeaders.ContentType, "application/json")
                    }
                    body = jsonObject.toString()
                }
                println(response.status)
                println(response.receive<String>())
            } catch (e: Exception) {
                Toast.makeText(
                    context,
                    "ERROR: $e",
                    Toast.LENGTH_LONG
                ).show()
                e.printStackTrace()
            }
        }
    }

    fun ktorGetToken(user: User) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response: HttpResponse =
                    httpClient.request("https://rayesredminetest.planio.com/login") {
                        method = HttpMethod.Get
                    }
                val string: String = response.receive()
                val array = string.split("<")
                val tokenElement =  array.find {
                    it.contains("authenticity_token") && it.contains("value")
                }
                val csrfTokenElement = array.find {
                    it.contains("csrf-token")
                }
                val csrfToken = csrfTokenElement?.trim()?.substring(
                    32,
                    (csrfTokenElement?.trim()?.length - 4)
                )
                println(csrfTokenElement)
                println(csrfToken)
                println(tokenElement)
                val authToken = tokenElement?.trim()?.substring(53, (tokenElement?.trim()?.length - 4))
                println(authToken)
                user.authenticity_token = authToken ?: ""
                user.csrf_token = csrfToken ?: ""
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun ktorLogin(user: User) {
        println(Gson().toJson(user))
        runBlocking {
            try {
                val response: HttpResponse =
                    httpClient.post(
                        "https://rayesredminetest.planio.com/login"
                    ) {
                        contentType(ContentType.Application.Json)
                        body = Gson().toJson(user)
                    }
                val body = response.receive<String>()
                println(body)
            } catch (e: Exception) {
                Toast.makeText(
                    context,
                    "ERROR: $e",
                    Toast.LENGTH_LONG
                ).show()
                e.printStackTrace()
            }
        }
    }
}