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
    // we need to ensure a footprint of 1 GB to avoid caching effect
    int footprintLarge = 1 << 30;
    /* size is a variable */
    @Param({"1024", "65536", "524288", "1048576", "8388608"})
    int size;
    @Param({"heap", "direct"})
    String mode;

    @Param({"small", "large"})
    String footPrintSrc;

    @Param({"small", "large"})
    String footPrintSink;

    /* count we will calculate */
    int countSrc, countSink;
    int indexSrc, indexSink;

    ByteBuffer[] bbArrary;
    byte[][] byteArray;

    public CopyFromByteBuffer(){
    }

    private void initSrc(){
        if(footPrintSrc.compareTo("small") == 0){
            /* we just use one buffer that there is, to maximize the caching effect */
            countSrc = 1;
        } else {
            /* otherwise 1GB size, divided by the current size */
            countSrc = footprintLarge / size;
        }
        indexSrc = 0;
        bbArrary = new ByteBuffer[countSrc];
        for (int i =0; i < countSrc; i++){
            if(mode.compareTo("direct") == 0)
                bbArrary[i] = ByteBuffer.allocateDirect(size);
            else
                bbArrary[i] = ByteBuffer.allocate(size);
        }
    }

    private void initSink(){
        if(footPrintSink.compareTo("small") == 0){
            /* we just use one buffer that there is, to maximize the caching effect */
            countSink = 1;
        } else {
            /* otherwise 1GB size, divided by the current size */
            countSink = footprintLarge / size;
        }
        indexSink = 0;
        byteArray = new byte[countSink][];
        for (int i =0; i < countSink; i++){
            byteArray[i] = new byte[size];
        }
    }

    private void deInit(){
        for (int i =0; i < countSrc; i++)
            bbArrary[i] = null;
        for (int i =0; i < countSink; i++)
            byteArray[i] = null;

        bbArrary = null;
        byteArray = null;

        size = -1;
        countSrc = countSink = -1;
        indexSrc = indexSink = -1;
    }

    @Setup(Level.Trial)
    public void doSetupTrail() {
        initSrc();
        initSink();
        System.out.println("\n size is " + size +
                " and count (src,sink) is (" + countSrc + "," + countSink +
                ") and mode is " + mode +
                " and footprint (src, sink) is (" + footPrintSrc + "," +footPrintSink+")");
    }

    @TearDown(Level.Trial)
    public void doTearDownTrail() {
        deInit();
    }

    @Setup(Level.Iteration)
    public void doSetupIteration() {
        // we reset the index
        indexSrc = 0;
        indexSink = 0;
    }

    @TearDown(Level.Iteration)
    public void doTearDownIteration() {
    }

    @Setup(Level.Invocation)
    public void doSetupInvocation() {
        indexSrc++;
        if(indexSrc == countSrc)
            indexSrc = 0;
        // and we clear the buffer for that index
        bbArrary[indexSrc].clear();

        indexSink++;
        if(indexSink == countSink)
            indexSink = 0;
    }

    @TearDown(Level.Invocation)
    public void dotearDownInvocation() {
    }

    @Benchmark   @BenchmarkMode(Mode.AverageTime)
    public void testCopyFromByteBuffer(CopyFromByteBuffer state) {
        /* we pick the current index and just operate on the index */
        state.bbArrary[state.indexSrc].get(state.byteArray[state.indexSink]);
    }
}
