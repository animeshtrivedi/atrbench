package com.github.animeshtrivedi;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.nio.ByteBuffer;
import java.util.Random;

/**
 * Created by atr on 29.11.17.
 */
@State(Scope.Thread)
public class MakeDouble {
    private byte[] src;
    private Random random;
    private int index;
    private int max = 1024 *1024 * 1;
    private ByteBuffer bb;
    private long _sum;

    public MakeDouble(){
        src = new byte[max];
        random = new Random(System.nanoTime());
        index = 0;
        random.nextBytes(src);
        bb = ByteBuffer.wrap(src);
    }

    @Setup(Level.Iteration)
    public void doSetupIteration() {
        index = 0;
        random.nextBytes(src);
        bb = ByteBuffer.wrap(src);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void makeInts(Blackhole blackhole, MakeInt state) {
        double val = Platform.getDouble(src, Platform.BYTE_ARRAY_OFFSET + index);
        //int val = bb.getInt(index);
        //this._sum+=val;

        index+=Double.BYTES;
        if((index + Double.BYTES) >= max)
            index = 0;

        blackhole.consume(val);
    }
}
