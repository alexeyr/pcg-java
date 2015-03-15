/*
 * PCG Random Number Generation for Java
 *
 * Copyright 2014 Melissa O'Neill <oneill@pcg-random.org>, 2015 Alexey Romanov <alexey.v.romanov@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * For additional information about the PCG random number generation scheme,
 * including its license and other licensing options, visit
 *
 * http://www.pcg-random.org
 */
package com.github.alexeyr.pcg;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.*;

/**
 * Tests for {@link com.github.alexeyr.pcg.Pcg32}.
 */
// adapted from https://github.com/non/spire/blob/8b7a78db7d262364421c4f96cfa000cc6f7a35bf/tests/src/test/scala/spire/random/GeneratorTest.scala
@RunWith(Parameterized.class)
public class Pcg32HistogramTest {
    // TODO threshold should depend on base
    private int size = 10000000;
    private double threshold = 0.0038;

    @Parameterized.Parameters(name = "checkDistributionByMod: base = {0}")
    public static Object[] bases() {
        return new Object[] { 3, 5, 7, 11/*, 13*/ };
    }

    private int base;

    public Pcg32HistogramTest(int base) {
        this.base = base;
    }

    @Test
    public void checkDistributionByMod() {
        int[] histogram = new int[base];
        Pcg32 rnd = new Pcg32();
        for (int i = 0; i < size; i++) {
            int n = rnd.nextInt(base);
            histogram[n]++;
        }
        double ratio = 1.0 * size / base;
        for (int n = 0; n < base; n++) {
            double deviation = Math.abs(1.0 - (histogram[n] / ratio));
            assertTrue("Deviation must be less than threshold", deviation < threshold);
        }
    }
}