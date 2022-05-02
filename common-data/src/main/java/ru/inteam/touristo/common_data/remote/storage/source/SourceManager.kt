package ru.inteam.touristo.common_data.remote.storage.source

internal class SourceManager<T>(
    private val source: suspend (Map<String, Any>) -> T
) {
    private var params: Map<String, Any> = mapOf()
    private var staticParams: Map<String, Any> = mapOf()

    fun setStaticParams(vararg params: Pair<String, Any>) {
        if (staticParams.isNotEmpty()) error("Static params has already set")

        staticParams = mapOf(*params)
    }

    fun setParams(vararg params: Pair<String, Any>) {
        val errorKey = params.find { it.first in staticParams.keys }
        if (errorKey != null)
            error("Pair $errorKey has already set in static params")
        this.params = mapOf(*params)
    }

    suspend fun invoke(): T = source(params + staticParams)
}
