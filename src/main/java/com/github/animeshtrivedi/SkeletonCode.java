package com.github.animeshtrivedi;

/**
 * Created by atr on 25.07.17.
 */
import org.openjdk.jmh.annotations.*;

public class SkeletonCode {

    @State(Scope.Benchmark)
    public static class SkeletonState {
        @Param({"1", "10000"})
        public int index;

        @Param({"5", "50000"})
        public int index2;

        public SkeletonState(){
        }

        @Setup(Level.Trial)
        public void doSetupTrail() {
            System.out.println("Do per trail setup, index is " + index);
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
    public void TestSkeletonCode(SkeletonState state) {
        System.out.println("\t\t\tBenchmark starts " + state);
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

//https://stackoverflow.com/questions/29472797/why-is-returning-a-java-object-reference-so-much-slower-than-returning-a-primiti
//@BenchmarkMode(Mode.AverageTime)
//@OutputTimeUnit(TimeUnit.NANOSECONDS)
//@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
//@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
//@Fork(5)
//public class PrimVsRef {
//
//    @Benchmark
//    public void prim() {
//        doPrim();
//    }
//
//    @Benchmark
//    public void ref() {
//        doRef();
//    }
//
//    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
//    private int doPrim() {
//        return 42;
//    }
//
//    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
//    private Object doRef() {
//        return this;
//    }
//
//}
