package com.github.animeshtrivedi;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

/**
 * Created by atr on 29.11.17.
 */
@State(Scope.Thread)
public class SystemArrayCopyTest {

    public SystemArrayCopyTest(){
    }

    @Param({"1048576"})
    private int srcBufferSize;

    @Param({"16"})
    private int dstBufferSize;

    private byte[] srcBuffer, dstBuffer;
    private int currentSrcIndex, currentDstIndex;

    void srcInit(){
        srcBuffer = new byte[srcBufferSize];
        currentSrcIndex = 0;
    }

    void dstInit(){
        dstBuffer = new byte[dstBufferSize];
        currentDstIndex = 0;
    }

    @Setup(Level.Trial)
    public void doSetupTrail() {
        //System.out.println("[TRIAL-setup] " + this.toString());
    }

    @TearDown(Level.Trial)
    public void doTearDownTrail() {
        //System.out.println("[TRIAL-teardown] ");
    }

    @Setup(Level.Iteration)
    public void doSetupIteration() {
        srcInit();
        dstInit();
        //System.out.println("\t[ITERATION-setup] " + this.toString());
    }

    @TearDown(Level.Iteration)
    public void doTearDownIteration() {
        //System.out.println("\t[ITERATION-teardown] ");
    }

    @Setup(Level.Invocation)
    public void doSetupInvocation() {
        currentSrcIndex+=dstBufferSize;
        if((currentSrcIndex + dstBufferSize) > srcBufferSize )
            currentSrcIndex = 0;
        //System.out.println("\t\t [INVOCATION-setup] " + this.toString());
    }

    @TearDown(Level.Invocation)
    public void dotearmDownInvocation() {
        //System.out.println("\t\t [INVOCATION-teardown] ");
    }

    public String toString(){
        return " srcSize: " + srcBufferSize + " dstSize: " + dstBufferSize + " srcIndex: " + currentSrcIndex +  " dstIndex: " + currentDstIndex;
    }

    @Benchmark   @BenchmarkMode(Mode.Throughput)
    public void testByteArrayAllocation(Blackhole blackhole, SystemArrayCopyTest state) {
        //System.out.println(this.toString());
        System.arraycopy(srcBuffer, currentSrcIndex, dstBuffer, currentDstIndex, dstBufferSize);
        blackhole.consume(srcBuffer);
    }
}
