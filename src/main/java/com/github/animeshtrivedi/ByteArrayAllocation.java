package com.github.animeshtrivedi;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

/**
 * Created by atr on 26.07.17.
 */
@State(Scope.Thread)
public class ByteArrayAllocation {
    @Param({"1024", "4096", "65536", "524288", "1048576"})
    int size;

    public ByteArrayAllocation(){
    }

    @Setup(Level.Trial)
    public void doSetupTrail() {
    }

    @TearDown(Level.Trial)
    public void doTearDownTrail() {
    }

    @Setup(Level.Iteration)
    public void doSetupIteration() {
    }

    @TearDown(Level.Iteration)
    public void doTearDownIteration() {
    }

    @Setup(Level.Invocation)
    public void doSetupInvocation() {
    }

    @TearDown(Level.Invocation)
    public void doTearDownInvocation() {
    }
    @Benchmark   @BenchmarkMode(Mode.Throughput)
    public void testByteArrayAllocation(Blackhole blackhole, ByteArrayAllocation state) {
        blackhole.consume(new byte[state.size]);
    }
}
