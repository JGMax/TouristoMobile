package ru.inteam.touristo.common_data

import app.cash.turbine.test
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import ru.inteam.touristo.common_data.remote.storage.CoroutineDataStorage
import ru.inteam.touristo.common_data.state.LoadingState
import ru.inteam.touristo.common_data.util.value

internal class CoroutineDataStorageTest : BehaviorSpec({
    Given("CoroutineDataStorage") {
        CoroutineDataStorageTestData().apply {

            When("Source has no parameters") {
                val storage = CoroutineDataStorage(source = { source() })

                Then("Result is 1, 0") {
                    storage.data().test {
                        awaitItem() shouldBe LoadingState.Loaded(expectedResultWithoutParameters)
                        cancelAndConsumeRemainingEvents()
                    }
                }
            }

            When("Source has parameters 0, 0") {
                val aParamName = "A"
                val bParamName = "B"

                val storage = CoroutineDataStorage(
                    source = { source(aParamName.value(), bParamName.value()) }
                )

                Then("Result is 0, 0") {
                    storage.data(aParamName to 0, bParamName to 0).test {
                        awaitItem() shouldBe LoadingState.Loaded(Result(0, 0))
                        cancelAndConsumeRemainingEvents()
                    }
                }
            }
        }
    }
})
