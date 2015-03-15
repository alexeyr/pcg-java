package com.github.alexeyr.pcg;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.*;

// Note: currently doesn't support Windows
@RunWith(Parameterized.class)
public class Pcg32CComparisonTest {
    private static File cCodeDir = new File("core/src/test/c").getAbsoluteFile();
    private int countForEachTest = 100;
    private long initState;
    private long initSeq;

    public Pcg32CComparisonTest(long initState, long initSeq) {
        this.initState = initState;
        this.initSeq = initSeq;
    }

    @BeforeClass
    public static void compileC() throws InterruptedException {
        try {
            Process process = new ProcessBuilder("make").directory(cCodeDir).redirectErrorStream(true).start();
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    String line;
                    StringBuilder sb = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        sb.append(line).append("\n");
                    }

                    throw new IllegalStateException(String.format("Failed to compile C code: %s", sb.toString()));
                }
            }
        } catch (IOException e) {
            Assume.assumeTrue("make is not available", false);
        }
    }

    @Parameterized.Parameters(name = "initState = {0}, initSeq = {1}")
    public static Object[][] seeds() {
        int range = 10;
        long[] testCases = new long[range * 2 + 3];
        for (int i = 0; i < range * 2 + 1; i++) {
            // fill testCases[0..range*2] with [-range..range]
            testCases[i] = i - range;
        }
        testCases[range * 2 + 1] = Long.MIN_VALUE;
        testCases[range * 2 + 2] = Long.MAX_VALUE;
        Object[][] result = new Object[testCases.length * testCases.length][2];
        for (int i = 0; i < testCases.length; i++) {
            for (int j = 0; j < testCases.length; j++) {
                result[i * testCases.length + j][0] = testCases[i];
                result[i * testCases.length + j][1] = testCases[j];
            }
        }
        return result;
    }

    @Test
    public void compareWithCBasicDemo() throws InterruptedException, IOException {
        Pcg32 rng = new Pcg32(initState, initSeq);

        Process process = new ProcessBuilder("./pcg32-global", Integer.toString(countForEachTest),
                Long.toString(initState), Long.toString(initSeq)).directory(cCodeDir).start();

        Assert.assertEquals(0, process.waitFor());

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            for (int i = 0; i < countForEachTest; i++) {
                String line = reader.readLine();
                int fromC = Integer.parseInt(line);
                int fromJava = rng.nextInt();
                Assert.assertEquals(
                        String.format("With initState=%d, initSeq=%d, got %d from C but %d from Java on step %d",
                                initState, initSeq, fromC, fromJava, i),
                        fromC, fromJava);
            }
        }
    }
}
