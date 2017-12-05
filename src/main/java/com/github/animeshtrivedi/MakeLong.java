package com.github.animeshtrivedi;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.nio.ByteBuffer;
import java.util.Random;

/**
 * Created by atr on 29.11.17.
 */
@State(Scope.Thread)
public class MakeLong {
    private byte[] src;
    private Random random;
    private int index;
    private int max = 1024 *1024 * 510;
    private ByteBuffer bb;
    private long _sum;
    private long _validLongs;

    public MakeLong(){
        src = new byte[max];
        random = new Random(System.nanoTime());
        index = 0;
        random.nextBytes(src);
        bb = ByteBuffer.wrap(src);
        this._sum = 0;
        this._validLongs = 0;
    }

    @Setup(Level.Iteration)
    public void doSetupIteration() {
        index = 1;
        random.nextBytes(src);
        bb = ByteBuffer.wrap(src);
    }

    @TearDown(Level.Iteration)
    public void doTearDownTrail() {
        System.out.println("[Iteration-teardown] sum is " + this._sum + " longs " + this._validLongs);
        this._sum = this._validLongs = 0;
    }


    public void makeLongsAlign(Blackhole blackhole, MakeLong state) {
        long val = Platform.getLong(state.src, Platform.BYTE_ARRAY_OFFSET + state.index);
        //long val = bb.getInt(index);

        this._sum+=val;
        this._validLongs+=1;
        index+=Long.BYTES;
        if((index + Long.BYTES) >= max)
            index = 0;

        //blackhole.consume(val);
    }

    @Benchmark @BenchmarkMode(Mode.Throughput)
    public void makeLongsUnalign(Blackhole blackhole, MakeLong state) {
        long val = Platform.getLong(state.src, Platform.BYTE_ARRAY_OFFSET + state.index);
        //long val = bb.getInt(index);

        this._sum+=val;
        this._validLongs+=1;
        index+=Long.BYTES;
        if((index + Long.BYTES) >= max)
            index = 1;

        //blackhole.consume(val);
    }
}
