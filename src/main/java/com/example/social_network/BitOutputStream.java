package com.example.social_network;
import java.io.BufferedOutputStream;
import java.io.IOException;

public class BitOutputStream {
    private static BufferedOutputStream outputStream;    // outputPath stream
    private static int buf;                  // 8-bit buffer
    private static int bitsRemaining;                       // number of bits remaining in buffer
    private static boolean isInitialized;       // the class is already initialized

       //Sets up the output stream and buffer if not already done.
    private static void initialize() {
        outputStream = new BufferedOutputStream(System.out);
        buf = 0;
        bitsRemaining = 0;
        isInitialized = true;
    }
      // Writes a single bit to the buffer (1 or 0)
    private static void writeBoolean(boolean x) {
        if (!isInitialized) initialize();

        buf <<= 1;
        if (x) buf |= 1;

        bitsRemaining ++;
        if (bitsRemaining == 8) clearBuffer();
    }
   //Writes a byte to the output stream, either directly or by packing bits into the buffer.
    private static void writeByte(int x) {
        if (!isInitialized) initialize();

        assert x >= 0 && x < 256;

        if (bitsRemaining == 0) {
            try {
                outputStream.write(x);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        for (int i = 0; i < 8; i++) {
            boolean b = (x >>> (8 - 1 - i) & 1) == 1;
            writeBoolean(b);
        }
    }
      //Flush the remaining bits in the buffer to the output stream.
    private static void clearBuffer() {
        if (!isInitialized) initialize();

        if (bitsRemaining== 0) return;
        if (bitsRemaining > 0)
            buf <<= (8 - bitsRemaining);
        try {
            outputStream.write(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }

        buf = 0;
        bitsRemaining = 0;
    }
// Force all buffered data to be written to the output stream
    private static void flush() {
        clearBuffer();
        try {
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
  //Flush and close the underlying output stream.
    public static void close() {
        flush();
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        isInitialized = false;
    }

    // Overloaded methods to write various data types using the appropriate base functions.


    public static void write(boolean x) {
        writeBoolean(x);
    }

    public static void write(byte x) {
        writeByte(x & 0xff);
    }

    public static void write(int x) {
        writeByte((x >>> 24) & 0xff);
        writeByte((x >>> 16) & 0xff);
        writeByte((x >>> 8) & 0xff);
        writeByte((x >>> 0) & 0xff);
    }

    public static void write(char c) {
        writeByte((int) c);
    }

    public static void write(String s) {
        for (int i = 0; i < s.length(); i++)
            write(s.charAt(i));
    }

}