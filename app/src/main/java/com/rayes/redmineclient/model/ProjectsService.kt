package com.rayes.redmineclient.model

typealias ProjectsListener = (projects: MutableList<Project>) -> Unit

class ProjectsService : AbstractService<Project, ProjectsListener>() {

    override val listeners = mutableSetOf<ProjectsListener>()

    override fun addListener(listener: ProjectsListener) {
        listeners.add(listener)
        listener.invoke(getElements())
    }

    override fun removeListener(listener: ProjectsListener) {
        listeners.remove(listener)
    }

    override fun notifyChanges() {
        listeners.forEach { it.invoke(getElements()) }
    }
}