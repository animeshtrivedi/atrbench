package com.github.animeshtrivedi;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.nio.ByteBuffer;
import java.util.Random;

/**
 * Created by atr on 05.12.17.
 */
@State(Scope.Thread)
public class ScalaCallMethod {
    private int max = 1024 *1024 * 512;
    private byte[] src;
    private Random random;
    private CallMethod method;
    private ByteBuffer bb;
    private long _sum;

    public ScalaCallMethod(){
        this.src = new byte[max];
        this.random = new Random(System.nanoTime());
        this.method = new CallMethod();
        this.random.nextBytes(src);
        bb = ByteBuffer.wrap(src);
    }

    @Setup(Level.Iteration)
    public void doSetupIteration() {
        random.nextBytes(src);
        bb = ByteBuffer.wrap(src);
        this.method = new CallMethod();
    }

    @TearDown(Level.Iteration)
    public void doTearDownTrail() {
        System.out.println("[Iteration-teardown] sum is " + this._sum);
        this._sum = 0;
    }


    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void experiment(Blackhole blackhole, ScalaCallMethod state) {
        if(bb.remaining() < Integer.BYTES)
            bb.clear();
        this._sum+=state.method.multiplyMethod(bb.getInt());
    }
}

