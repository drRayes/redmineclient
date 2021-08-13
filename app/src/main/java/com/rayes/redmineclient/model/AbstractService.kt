package com.rayes.redmineclient.model

import java.util.*

abstract class AbstractService <T, E> {
    private var elements = mutableListOf<T>()

    protected abstract val listeners: MutableSet<E>

    fun getElements() : MutableList<T> {
        return elements
    }

    fun addElement(element: T) {
        elements.add(element)
        notifyChanges()
    }

    fun removeElement(element: T) {
        val indexToDelete: Int = elements.indexOfFirst { it == element }
        if (indexToDelete != -1) {
            elements.removeAt(indexToDelete)
            notifyChanges()
        }
    }
    fun clear() {
        elements.clear()
    }

    fun moveElement(element: T, moveBy: Int) {
        val oldIndex = elements.indexOfFirst { it == element }
        if (oldIndex == -1) return
        val newIndex = oldIndex + moveBy
        if (newIndex < 0 || newIndex >= elements.size) return
        Collections.swap(elements, oldIndex, newIndex)
        notifyChanges()
    }

    abstract fun addListener(listener: E)

    abstract fun removeListener(listener: E)

    protected abstract fun notifyChanges()
}