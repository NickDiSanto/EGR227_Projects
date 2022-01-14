/**
 * The HuffmanCode class initializes a new PriorityQueue of ASCII values for each of the characters in a text
 * file. They are arranged by frequency, and are assigned a binary value (Huffman Code) based on how often they
 * appear in the text file in order to minimize the total binary digits. Finally, it provides the means to compress
 * or decompress the file, as written in the HuffmanCompressor class.
 *
 * This class also contains a private inner HuffmanNode class that initializes and creates each specific node.
 *
 * Nick DiSanto
 * EGR227-A
 * Professor Knisley
 * 28 April 2021
 *
 * Consulted with Gavin Harding on this project
 **/

import java.io.PrintStream;
import java.util.*;

public class HuffmanCode {

    public int[] frequencies; // creates public values to be used throughout the class
    public PriorityQueue<HuffmanNode> queue = new PriorityQueue<>();
    public HuffmanNode root = new HuffmanNode(-1);

    // private inner class creates individual nodes
    private static class HuffmanNode implements Comparable<HuffmanNode> {
        int code; // creates values to be used by each individual node
        int frequency;
        HuffmanNode leftNode; // potential children of current node
        HuffmanNode rightNode;

        // constructor initializes frequency
        HuffmanNode(int frequency) {
            this.code = -1;
            this.frequency = frequency;
        }

        // constructor initializes both frequency and code (when it's provided)
        HuffmanNode(int code, int frequency) {
            this.code = code;
            this.frequency = frequency;
        }

        // returns difference in frequency
        @Override
        public int compareTo(HuffmanNode node) {
            return this.frequency - node.frequency;
        }
    }

    // initializes array of char frequencies from the text file
    public HuffmanCode(int[] frequencies) {
        this.frequencies = frequencies;
    }

    // runs through the file, scans, and utilizes the text
    public HuffmanCode(Scanner input) {
        while (input.hasNext()) { // iterates through the file
            HuffmanNode currentNode = root;
            int digit = Integer.parseInt(input.nextLine()); // reads each individual integer
            String code = input.nextLine();
            build(currentNode, code, digit); // calls function to build with new node
        }
    }

    // creates new nodes for each char with a frequency, adding their child nodes to the PriorityQueue
    public void save(PrintStream output) {
        for (int i = 0; i < frequencies.length; i++) { // iterates through the frequencies
            if (frequencies[i] != 0) { // creates and adds new node if there is a frequency >0
                HuffmanNode newNode = new HuffmanNode(i, frequencies[i]);
                queue.add(newNode);
            }
        }
        while (queue.size() > 1) { // iterates through PriorityQueue
            HuffmanNode left = queue.remove(); // initializes new nodes
            HuffmanNode right = queue.remove();
            HuffmanNode parent = new HuffmanNode(left.frequency + right.frequency); // creates new parent node
            parent.leftNode = left;
            parent.rightNode = right;
            queue.add(parent); // adds new node to queue
        }
        makeTree(queue.remove(), output, ""); // creates new tree with updated information
    }

    // runs through file and assigns each bit to the left or right node, depending on if it's 0 or 1
    public void translate(BitInputStream input, PrintStream output) {
        HuffmanNode currentNode = root;
        while (input.hasNextBit()) { // iterates through file
            if (input.nextBit() == 0) // if the bit is 0, it adds to the left
                currentNode = currentNode.leftNode;
            else // if the bit is 1, it adds to the right
                currentNode = currentNode.rightNode;
            if (currentNode.code != -1) { // as long as the code is valid, it is printed to the output
                output.print((char)currentNode.code);
                currentNode = root;
            }
        }
    }

    // helper method recursively creates and updates tree with new information
    public static void makeTree(HuffmanNode node, PrintStream output, String runningCode) {
        if (node.code == -1) { // recursive case: recursively calls method to update with new code
            makeTree(node.leftNode, output, runningCode + "0");
            makeTree(node.rightNode, output, runningCode + "1");
        } else // base case: if the code is valid, it is printed to the output
            output.println(node.code + "\n" + runningCode);
    }

    // helper method builds new nodes based on the given chars in the code
    public static void build(HuffmanNode node, String code, int digit) {
        if (code.charAt(0) == '0') { // if the first char is 0, the left creates a new node
            if (node.leftNode == null)
                node.leftNode = new HuffmanNode(-1);
            if (code.length() == 1) // base case: node's left value is initialized as a leaf
                node.leftNode.code = digit;
            else // recursive case: recursively calls build with new code
                build(node.leftNode, code.substring(1), digit);
        } else { // if the first char is 1, the right creates a new node
            if (node.rightNode == null)
                node.rightNode = new HuffmanNode(-1);
            if (code.length() == 1) // base case: node's right value is initialized as a leaf
                node.rightNode.code = digit;
            else // recursive case: recursively calls build with new code
                build(node.rightNode, code.substring(1), digit);
        }
    }
}
