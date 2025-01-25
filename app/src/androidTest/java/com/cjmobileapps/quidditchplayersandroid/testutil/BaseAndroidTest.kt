package com.cjmobileapps.quidditchplayersandroid.testutil

import io.mockk.MockKAnnotations
import org.junit.Before

abstract class BaseAndroidTest {
    @Before
    open fun setup() {
        MockKAnnotations.init(this)
    }
}
