package com.github.alexeyr.pcg;

import org.junit.Assert;
import org.junit.Test;

public class Pcg32CDemoComparison {
    @Test
    public void compareWithCBasicDemo() {
        Pcg32 rng = new Pcg32(42, 54);

        // pcg32-global-demo output:
        //        Round 1:
        //        32bit: 0xa15c02b7 0x7b47f409 0xba1d3330 0x83d2f293 0xbfa4784b 0xcbed606e
        //        Coins: HHTTTHTHHHTHTTTHHHHHTTTHHHTHTHTHTTHTTTHHHHHHTTTTHHTTTTTHTTTTTTTHT
        //        Rolls: 3 4 1 1 2 2 3 2 4 3 2 4 3 3 5 2 3 1 3 1 5 1 4 1 5 6 4 6 6 2 6 3 3
        //        Cards: Qd Ks 6d 3s 3d 4c 3h Td Kc 5c Jh Kd Jd As 4s 4h Ad Th Ac Jc 7s Qs
        //        2s 7h Kh 2d 6c Ah 4d Qh 9h 6s 5s 2c 9c Ts 8d 9s 3c 8c Js 5d 2h 6h
        //        7d 8s 9d 5h 8h Qc 7c Tc
        // we only compare 32bit, since the algorithm for nextInt(n) is currently different

        int[] expectedInts = new int[] { 0xa15c02b7, 0x7b47f409, 0xba1d3330, 0x83d2f293, 0xbfa4784b, 0xcbed606e };

        int[] actualInts = new int[expectedInts.length];

        for (int i = 0; i < expectedInts.length; i++) {
            actualInts[i] = rng.nextInt();
        }

        Assert.assertArrayEquals(expectedInts, actualInts);
    }
}
