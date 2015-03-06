package com.github.alexeyr.pcg.benchmarks;

import java.util.Random;
import com.github.alexeyr.pcg.Pcg32;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class Pcg32Benchmarks {
    @State(Scope.Thread)
    public static class JUR {
        volatile Random gen = new Random();
    }

    @State(Scope.Thread)
    public static class PCG {
        volatile Pcg32 gen = new Pcg32();
    }

    @Benchmark
    public Random jurCreation() {
        return new Random();
    }

    @Benchmark
    public Pcg32 pcgCreation() {
        return new Pcg32();
    }

    @Benchmark
    public int jurInt(JUR jur) {
        return jur.gen.nextInt();
    }

    @Benchmark
    public int pcgInt(PCG pcg) {
        return pcg.gen.nextInt();
    }

    public static void main(String... args) throws Exception {
        Options opts = new OptionsBuilder()
                .include(".*")
                .warmupIterations(10)
                .measurementIterations(10)
                .forks(3)
                .jvmArgs("-server")
                .resultFormat(ResultFormatType.TEXT)
                .build();

        new Runner(opts).run();
    }
}
