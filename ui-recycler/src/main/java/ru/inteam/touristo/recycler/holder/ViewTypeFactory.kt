package ru.inteam.touristo.recycler.holder

interface ViewTypeFactory {

    fun createViewType(viewType: Int): ViewType?
}
