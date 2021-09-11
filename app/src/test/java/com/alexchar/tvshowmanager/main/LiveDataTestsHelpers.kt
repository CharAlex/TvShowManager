package com.alexchar.tvshowmanager.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit


/**
 * Waits for given time period if the live data emits any value.
 *
 * NOTE: This method waits for the given time span, increasing the test duration.
 *
 * @return false -> value was emitted (printed to log)
 * @return true -> no value was emitted in given time period
 */
fun <T> LiveData<T>.neverEmits(
    time: Long = 100,
    timeUnit: TimeUnit = TimeUnit.MILLISECONDS
): Boolean {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(t: T) {
            data = t
            latch.countDown()
            this@neverEmits.removeObserver(this)
        }
    }

    this.observeForever(observer)

    if (!latch.await(time, timeUnit)) {
        return true
    }
    println("LiveData emitted value: $data")
    return false
}

/**
 * Provides a Matcher for LiveData sources that asserts whether the source emits the given value
 * eventually. Which means: a bit of delay (as defined by the timeout and timeUnit parameters) is
 * allowed, as well as the emission of additional other values, as long as the expected value is
 * emitted at some point before the timeout.
 */
fun <T> emitsNullableValueEventually(
    expectedValue: T?,
    timeout: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS
): Matcher<LiveData<T?>> {

    return object : TypeSafeMatcher<LiveData<T?>>() {

        val emittedValues: MutableCollection<T?> = mutableListOf()

        override fun describeTo(description: Description?) {
            description
                ?.appendText("LiveData that emits value ")
                ?.appendValue(expectedValue)
                ?.appendText(" after at most $timeout $timeUnit")
        }

        override fun describeMismatchSafely(
            item: LiveData<T?>?,
            mismatchDescription: Description?
        ) {
            if (emittedValues.isEmpty()) {
                mismatchDescription?.appendText("no values were emitted")
            } else {
                mismatchDescription
                    ?.appendText("the emitted values were: ")
                    ?.appendValue(emittedValues)
            }
        }

        override fun matchesSafely(item: LiveData<T?>?): Boolean {
            val latch = CountDownLatch(1)
            val observer = object : Observer<T?> {
                override fun onChanged(nextValue: T?) {
                    emittedValues.add(nextValue)
                    if (nextValue == expectedValue) {
                        latch.countDown()
                        item?.removeObserver(this)
                    }
                }
            }
            item?.observeForever(observer)
            return latch.await(timeout, timeUnit)
        }
    }
}

/**
 * Provides a Matcher for LiveData of non-nullable types.
 *
 * See also [emitsNullableValueEventually].
 */
@Suppress("UNCHECKED_CAST")
fun <T> emitsValueEventually(
    expectedValue: T,
    timeout: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS
): Matcher<LiveData<T>> {
    return emitsNullableValueEventually(expectedValue, timeout, timeUnit) as Matcher<LiveData<T>>
}

