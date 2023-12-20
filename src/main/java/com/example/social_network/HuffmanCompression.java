

import java.io.*;
import java.util.PriorityQueue;

public class HuffmanCompression {


    private static class HuffmanNode implements Comparable<HuffmanNode> {

        private final char data;  //  Stores the character associated with the node (leaf nodes only).
        private final int frequency;   //Holds the frequency of the character in the input data.
        private final HuffmanNode left, right; //References to the left and right child nodes, forming the tree structure.

        HuffmanNode(char ch, int freq, HuffmanNode left, HuffmanNode right) {
            this.data = ch; //Represents the character associated with the node.
            this.frequency = freq; //
            this.left = left;
            this.right = right;
        }

        @Override
        public int compareTo(HuffmanNode that) {
            return Integer.compare(this.frequency, that.frequency);
        }

        private boolean isLeaf() {
            return ((this.left == null) && (this.right == null));
        }
    }
    private static void huffmanEncode(String input)
    {     //calculates the frequency of each character in the input string and stores it in the freq array.
        char[] inChars = input.toCharArray();
         // assuming 256 possible characters
        int[] freq = new int[256];
        for (char inChar : inChars)
            freq[inChar]++;

        // build Huffman tree  (binary tree)
        HuffmanNode root = buildHuffmanTree(freq);

        // build code table
        String[] symbolTable = new String[256];
        buildCodeTable(symbolTable, root, "");

        //Writing Huffman Tree to File
        writeHuffmanTree(root);

        // Writing Input Stream Length
        BitOutputStream.write(inChars.length);

        // use huffman compression to encode the outputPath
        for (char ch : inChars) {
            String code = symbolTable[ch];
            for (int j = 0; j < code.length(); j++) {
                if (code.charAt(j) == '1')
                    BitOutputStream.write(true);
                else if (code.charAt(j) == '0')
                    BitOutputStream.write(false);
                else
                    throw new IllegalStateException("either 0 or 1, illegal state");
            }
        }
        BitOutputStream.close();
    }

    public static void compress(){
        // Reading Input String
        String s = BitInputStream.readString();
        //The input stream is closed after reading the string
        BitInputStream.close();

        huffmanEncode(s);
    }

    public static void compress(File input, File output)
    {
        InputStream InStream = System.in;
        PrintStream OutStream = System.out;

        try {
            System.setIn(new FileInputStream(input));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            System.setOut(new PrintStream(output));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        compress();

        System.setIn(InStream);
        System.setOut(OutStream);
    }

    private static HuffmanNode buildHuffmanTree(int[] freq) {
        // using priority queue
        PriorityQueue<HuffmanNode> priorityQueue = new PriorityQueue<>();

        for (char c = 0; c < 256; c++)
            if (freq[c] > 0)
                priorityQueue.add(new HuffmanNode(c, freq[c], null, null));

        while (priorityQueue.size() > 1) {
            HuffmanNode left = priorityQueue.poll();
            HuffmanNode right = priorityQueue.poll();
            HuffmanNode parent = new HuffmanNode('\0', left.frequency + right.frequency, left, right);
            priorityQueue.add(parent);
        }
        //Returning Root of Huffman Tree
        return priorityQueue.poll();
    }

    private static void writeHuffmanTree(HuffmanNode node) {
        if (node.isLeaf()) {
            BitOutputStream.write(true);
            BitOutputStream.write(node.data);
            return;
        }
        BitOutputStream.write(false);
        writeHuffmanTree(node.left);
        writeHuffmanTree(node.right);
    }

    private static void buildCodeTable(String[] symbolTable,HuffmanNode node, String code) {
        if (!node.isLeaf()) {
            buildCodeTable(symbolTable, node.left, code + '0');
            buildCodeTable(symbolTable, node.right, code + '1');
        }
        else
            symbolTable[node.data] = code;

    }

    public static void decompress() {

        // read the Huffman trie first from the inputPath
        HuffmanNode root =readHuffmanTree();

        // read the length of the stream
        int length = BitInputStream.readInt();

        for (int i = 0; i < length; i++) {
            HuffmanNode temp = root;
            while (!temp.isLeaf()) {
                // read bit
                boolean b = BitInputStream.readBoolean();
                if (b)
                    temp = temp.right;
                else
                    temp = temp.left;
            }
            BitOutputStream.write(temp.data);
        }
        BitInputStream.close();
        BitOutputStream.close();
    }

    public static void decompress(File input, File output) {
        InputStream InStream = System.in;
        PrintStream OutStream = System.out;

        try {
            System.setIn(new FileInputStream(input));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            System.setOut(new PrintStream(output));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        decompress();

        System.setIn(InStream);
        System.setOut(OutStream);
    }

    private static HuffmanNode readHuffmanTree()
    {
        boolean isLeafNode = BitInputStream.readBoolean();
        if (isLeafNode)
            return new HuffmanNode(BitInputStream.readChar(), -1, null, null);
        else
            return new HuffmanNode('\0', -1,readHuffmanTree(), readHuffmanTree());

    }



    public static void main(String[] args) {

        HuffmanCompression.compress(new File("E:\\Marioma\\senior1 term1\\Data Structure\\project\\project\\sample.xml"),
                new File("E:\\Marioma\\senior1 term1\\Data Structure\\project\\project\\Compression.TXT"));
        System.out.println("Compression complete.");
        HuffmanCompression.decompress(new File("E:\\Marioma\\senior1 term1\\Data Structure\\project\\project\\compression.TXT"),
                new File("E:\\Marioma\\senior1 term1\\Data Structure\\project\\project\\Decompression.TXT"));
        System.out.println("Decompression complete.");
    }
}

