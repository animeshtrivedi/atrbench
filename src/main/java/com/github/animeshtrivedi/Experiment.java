package com.github.animeshtrivedi;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.IOException;
import java.util.Random;

/**
 * Created by atr on 01.12.17.
 */
@State(Scope.Thread)
public class Experiment {
    private byte[] src;
    private byte[] src1;
    private byte[] src2;
    public Experiment(){
        src = new byte[8];
        src1 = new byte[8];
        src2 = new byte[8];
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void makeLongs(Blackhole blackhole, Experiment state) {
        /* we want to set bit 2 one */
        int index = 16;
        assert index >= 0 : "index (" + index + ") should >= 0";
        final long mask = 1L << (index & 0x3f);  // mod 64 and shift
        final long wordOffset = Platform.BYTE_ARRAY_OFFSET + (index >> 6) * 8;
        final long word = Platform.getLong(src, wordOffset);
        Platform.putLong(src, wordOffset, word | mask);

        StringBuilder sb1 = new StringBuilder();
        sb1.append(String.format(" mask = %02X ", mask));
        System.out.println(sb1.toString());

        //Platform.putInt(src, Platform.BYTE_ARRAY_OFFSET, 0xDEADBEEF);
        StringBuilder sb = new StringBuilder();
        for (byte b : src) {
            sb.append(String.format("%02X ", b));
        }
        System.out.println(sb.toString());
        System.out.println("----------------------- ");
        src1[0] = 1;
        src2[0] = 0;
        Platform.copyMemory(src1, Platform.BYTE_ARRAY_OFFSET, src2, Platform.BYTE_ARRAY_OFFSET, 1);

        sb1 = new StringBuilder();
        for (byte b : src2) {
            sb1.append(String.format("%02X ", b));
        }
        System.out.println(sb1.toString());


        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
