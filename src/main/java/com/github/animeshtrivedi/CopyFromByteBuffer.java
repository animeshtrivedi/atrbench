package com.github.animeshtrivedi;

/**
 * Created by atr on 26.07.17.
 */
import org.openjdk.jmh.annotations.*;

import java.nio.ByteBuffer;

@State(Scope.Thread)
public class CopyFromByteBuffer {
    /* declare all the variables here
    *
    * We need to make sure atleast more than the cache size memory footprint
    * say 1GB = 1024 x 1 MB buffers
    */
    int footprint = 1 << 30; // we need to ensure a footprint of 1 GB to avoid caching effect
    /* size is a variable */
    @Param({"1024", "65536", "524288", "1048576", "8388608"})
    int size;
    @Param({"heap", "direct"})
    String mode;
    /* count we will calculate */
    int count;
    int index;

    ByteBuffer[] bbArrary;
    byte[][] byteArray;

    public CopyFromByteBuffer(){
    }

    private void init(){
        count = footprint / size;
        index = 0;

        bbArrary = new ByteBuffer[count];
        byteArray = new byte[count][];

        for (int i =0; i < count; i++){
            if(mode.compareTo("direct") == 0)
                bbArrary[i] = ByteBuffer.allocateDirect(size);
            else
                bbArrary[i] = ByteBuffer.allocate(size);
            byteArray[i] = new byte[size];
        }
        System.out.println("\n size is " + size + " and count is " + count + " and mode is " + mode);
    }

    private void deInit(){
        for (int i =0; i < count; i++){
            bbArrary[i] = null;
            byteArray[i] = null;
        }

        bbArrary = null;
        byteArray = null;

        size = -1;
        count = -1;
        index = -1;
    }

    @Setup(Level.Trial)
    public void doSetupTrail() {
        init();
    }

    @TearDown(Level.Trial)
    public void doTearDownTrail() {
        deInit();
    }

    @Setup(Level.Iteration)
    public void doSetupIteration() {
        // we reset the index
        index = 0;
    }

    @TearDown(Level.Iteration)
    public void doTearDownIteration() {
    }

    @Setup(Level.Invocation)
    public void doSetupInvocation() {
        index++;
        if(index == count)
            index = 0;
        // and we clear the buffer for that index
        bbArrary[index].clear();
    }

    @TearDown(Level.Invocation)
    public void dotearDownInvocation() {
    }

    @Benchmark   @BenchmarkMode(Mode.AverageTime)
    public void testCopyFromByteBuffer(CopyFromByteBuffer state) {
        /* we pick the current index and just operate on the index */
        state.bbArrary[state.index].get(state.byteArray[state.index]);
    }
}
