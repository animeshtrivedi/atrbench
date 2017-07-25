package com.github.animeshtrivedi;

/**
 * Created by atr on 25.07.17.
 */
import org.openjdk.jmh.annotations.*;

import java.nio.ByteBuffer;


public class ArrayCopy {

    @State(Scope.Benchmark)
    public static class ArrayCopyState {
        int size = 1024 * 1024;
        int count = 1024;
        private ByteBuffer[] bbArray;
        private byte[][] byteArray;
        private int index;

        public ArrayCopyState(){
            index = 0;
        }

        @Setup(Level.Trial)
        public void doSetupTrail() {
            System.out.println("Do per trail setup");
        }

        @TearDown(Level.Trial)
        public void doTearDownTrail() {
            System.out.println("Do per trail TearDown");
        }

        @Setup(Level.Iteration)
        public void doSetupIteration() {
            System.out.println("\tDo per iteration setup");
        }

        @TearDown(Level.Iteration)
        public void doTearDownIteration() {
            System.out.println("\tDo per iteration TearDown");
        }

        @Setup(Level.Invocation)
        public void doSetupInvocation() {
           System.out.println("\t\t Do per invokation setup, index " + index++);
        }

        @TearDown(Level.Invocation)
        public void dotearmDownInvocation() {
           System.out.println("\t\t Do per invokation teardown ");
        }
        public String toString(){
            return " index " + index;
        }
    }

    @Benchmark   @BenchmarkMode(Mode.AverageTime)
    public void copyMethod(ArrayCopyState state) {
        // This is a demo/sample template for building your JMH benchmarks. Edit as needed.
        // Put your benchmark code here.
        System.out.println("\t\t\tBenchmark starts " + state);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
