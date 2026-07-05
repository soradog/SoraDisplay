package org.sorakun.soradisplay

import org.junit.Test
import java.text.SimpleDateFormat

class BenchmarkTest {
    @Test
    fun benchmarkDateFormatting() {
        val testData = List(10000) { "2023-08-16" }

        // Baseline
        val startBaseline = System.nanoTime()
        for (date in testData) {
            val printer = SimpleDateFormat("dd (EE)")
            val parser = SimpleDateFormat("yyyy-MM-dd")
            val parsed = parser.parse(date)
            if (parsed != null) printer.format(parsed)
        }
        val baselineTime = System.nanoTime() - startBaseline

        // Optimized
        val printer = SimpleDateFormat("dd (EE)")
        val parser = SimpleDateFormat("yyyy-MM-dd")
        val startOptimized = System.nanoTime()
        for (date in testData) {
            val parsed = parser.parse(date)
            if (parsed != null) printer.format(parsed)
        }
        val optimizedTime = System.nanoTime() - startOptimized

        println("Baseline: ${baselineTime / 1_000_000} ms")
        println("Optimized: ${optimizedTime / 1_000_000} ms")
    }
}
