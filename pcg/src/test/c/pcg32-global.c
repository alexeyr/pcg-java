/*
 * PCG Random Number Generation for C.
 *
 * Copyright 2014 Melissa O'Neill <oneill@pcg-random.org>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
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
 *     http://www.pcg-random.org
 */

/*
 * This file was modified from pcg32-global-demo.c in basic C library
 */

#include <stdio.h>
#include <stddef.h>
#include <stdlib.h>
#include <stdint.h>
#include <stdbool.h>
#include <time.h>
#include <string.h>

#include "pcg_basic.h"

int main(int argc, char** argv)
{
    // Read command-line options

    int count = atoi(argv[1]);
    // use int64_t for Java convenience
    int64_t initState = strtoll(argv[2], NULL, 10);
    int64_t initSeq = strtoll(argv[3], NULL, 10);
    int i;

    // In this version of the code, we'll use the global rng, rather than a
    // local one.

    // You should *always* seed the RNG.  The usual time to do it is the
    // point in time when you create RNG (typically at the beginning of the
    // program).
    //
    // pcg32_srandom_r takes two 64-bit constants (the initial state, and the
    // rng sequence selector; rngs with different sequence selectors will
    // *never* have random sequences that coincide, at all) - the code below
    // shows three possible ways to do so.

    pcg32_srandom((uint64_t) initState, (uint64_t) initSeq);

    for (i = 0; i < count; ++i) {
        printf("%d\n", (int32_t) pcg32_random());
    }

    return 0;
}
