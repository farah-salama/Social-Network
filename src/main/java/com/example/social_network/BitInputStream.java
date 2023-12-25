package com.example.social_network;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.NoSuchElementException;

public class BitInputStream{

    private static final int  END_OF_INPUT = -1;          //the end of the input stream.

    //used to read data from the standard input stream.
    private static BufferedInputStream inputStream;      // buffered inputPath stream to avoid multiple calling for sys in
    // a single character buffer used to read data efficiently.
    private static int buf;                  // one char buffer

    private static int bitsRemaining;                       // number of bits remained in the buffer

    private static boolean Initialized;       // StdIn already called
       // Reads a new character into the buffer and resets bitsRemaining.
    private static void fillBuffer()
    {
        try
        {
            buf =inputStream.read();
            bitsRemaining = 8;   // n represents the number of bits read
        }
        catch (IOException e)
        {
            System.out.println("end of file");
            buf = END_OF_INPUT;
            bitsRemaining = -1;
        }
    }
    //Initializes the inputStream and buf if not already done
    private static void initialize()
    {
        inputStream = new BufferedInputStream(System.in);
        buf = 0;
        bitsRemaining = 0;
        fillBuffer();
        Initialized= true;
    }


   // Closes the underlying input stream if initialized.
    public static void close()
    {
        if (!Initialized)
            initialize();
        try
        {
            inputStream.close();
            Initialized = false;
        }
        catch (IOException ioe)
        {
            throw new IllegalStateException("could not close the inputPath stream", ioe);
        }
    }

    //Checks if the input stream is empty
    private static boolean isEmpty()
    {
        if (!Initialized) initialize();
        return buf ==  END_OF_INPUT;
    }
   // Reads a single bit and returns true if it's 1, otherwise false.
    public static boolean readBoolean()
    {
        if (isEmpty()) throw new NoSuchElementException("The inputPath stream is empty");
        bitsRemaining--;
        boolean bit = ((buf >> bitsRemaining) & 1) == 1;
        if (bitsRemaining == 0) fillBuffer();
        return bit;
    }
  // Reads one character (8 bits) from the stream
    public static char readChar()
    {
        if (isEmpty()) throw new NoSuchElementException("Reading from empty inputPath stream");
        if (bitsRemaining == 8)
        {
            int x = buf;
            fillBuffer();
            return (char) (x & 0xff);
        }

        int x = (buf <<= (8 - bitsRemaining));
        int preN = bitsRemaining;
        fillBuffer();
        if (isEmpty()) throw new NoSuchElementException("Reading from empty inputPath stream");
        bitsRemaining = preN;
        x |= (buf >>> bitsRemaining);
        return (char) (x & 0xff);
    }
  // Reads characters continuously until the stream is empty, building a string.
    public static String readString()
    {
        if (isEmpty()) throw new NoSuchElementException("Reading from empty inputPath stream");

        StringBuilder strBuilder = new StringBuilder();
        while (!isEmpty())
        {
            char c = readChar();
            strBuilder.append(c);
        }
        return strBuilder.toString();
    }
    //Reads four characters (32 bits) and combines them into an integer
    public static int readInt()
    {
        if (isEmpty()) throw new NoSuchElementException("Reading from empty inputPath stream");

        int x = 0;
        for (int i = 0; i < 4; i++)
        {
            x <<= 8;
            x |= readChar();
        }
        return x;
    }
}