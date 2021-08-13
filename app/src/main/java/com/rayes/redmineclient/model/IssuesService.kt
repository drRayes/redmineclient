package com.rayes.redmineclient.model

typealias IssuesListener = (issues: MutableList<Issue>) -> Unit

class IssuesService : AbstractService<Issue, IssuesListener>() {

    override val listeners =  mutableSetOf<IssuesListener>()

    override fun addListener(listener: IssuesListener) {
        listeners.add(listener)
        listener.invoke(getElements())
    }

    override fun removeListener(listener: IssuesListener) {
        listeners.remove(listener)

    }

    override fun notifyChanges() {
        listeners.forEach {
            it.invoke(getElements())
        }
    }
}