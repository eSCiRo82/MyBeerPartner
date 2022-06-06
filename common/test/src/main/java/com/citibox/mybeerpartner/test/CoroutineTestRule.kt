package com.citibox.mybeerpartner.test

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

/**
 * Enable to launch tests in the test coroutin scope.
 */
@ExperimentalCoroutinesApi
class CoroutineTestRule: TestRule
{
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    override fun apply(base: Statement, description: Description): Statement =
        object : Statement() {
            override fun evaluate() {
                Dispatchers.setMain(testDispatcher)
                base.evaluate()
                Dispatchers.resetMain()
            }
        }

    /**
     * Run the test in a coroutin using the test scope
     */
    fun runTest(delay: Long = 0, test: suspend TestScope.() -> Unit) = testScope.runTest(delay, test)
}