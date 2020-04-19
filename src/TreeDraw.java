/**
*
* D:\Java\avinal\FrameUi\TreeDraw.java
*
* @author Avinal Kumar
* @since  April 19, 2020
*/
package src;

import java.io.*;
import java.util.*;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * A simple program to create and draw Binary Search Tree of numbers. Please
 * place StdDraw.java in the same directory as this file. Uses: Provide a text
 * file "tree.txt" conataining sequence of numbers. Please keep the first
 * element as the pivot element or root element for best result.
 */
public class TreeDraw {

    public static void main(String[] args) {
        BinarySearchTree t = new BinarySearchTree();
        BufferedReader diskInput;
        String word;

        try {
            diskInput = new BufferedReader(new InputStreamReader(new FileInputStream(openFile())));
            Scanner input = new Scanner(diskInput);
            while (input.hasNext()) {
                word = input.next();
                t.root = t.insert(t.root, word);
            }
            input.close();
        } catch (IOException e) {
            System.out.println("I/O Exception");
        }
        t.computeNodePositions();
        t.maxHeight = t.treeHeight(t.root);
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(-10, 810);
        StdDraw.setYscale(-790, 10);
        StdDraw.point(0, 0);
        Display dt = new Display(t);
        dt.drawTree(t.root);

    }

    public static File openFile() {
        JFileChooser open = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text file containing numbers", "txt");
        open.setFileFilter(filter);
        int status = open.showOpenDialog(null);
        if (status == JFileChooser.APPROVE_OPTION) {
            return open.getSelectedFile();
        }
        else {
            return new File("tree.txt");
        }
    }

}

/**
 * This class contains routines related to displaying the tree.
 */
class Display {
    BinarySearchTree tree;

    /**
     * Constructor of the BinarySearchTree Class.
     * 
     * @param t Tree to display
     */
    public Display(BinarySearchTree t) {
        this.tree = t;
    }

    /**
     * Recursive Tree Drawing Routine. Follows inorder traversal.
     * 
     * @param root starting point for the tree.
     */
    public void drawTree(Node root) {
        int x1, y1, x2, y2;
        int SCREEN_WIDTH = 800;
        int SCREEN_HEIGHT = 700;
        int XSCALE, YSCALE;
        XSCALE = SCREEN_WIDTH / tree.nodeNumber;
        YSCALE = (SCREEN_HEIGHT * -1) / (tree.maxHeight + 1);

        if (root != null) {
            drawTree(root.left);
            x1 = root.xpos * XSCALE;
            y1 = root.ypos * YSCALE;
            String s = root.data;
            StdDraw.textLeft(x1, y1, s);
            StdDraw.circle(x1, y1, 2);
            if (root.left != null) {
                x2 = root.left.xpos * XSCALE;
                y2 = root.left.ypos * YSCALE;
                StdDraw.line(x1, y1, x2, y2);
            }
            if (root.right != null) {
                x2 = root.right.xpos * XSCALE;
                y2 = root.right.ypos * YSCALE;
                StdDraw.line(x1, y1, x2, y2);
            }
            drawTree(root.right);
        }
        StdDraw.show();
        StdDraw.pause(100);
    }
}

/**
 * Class constructs a binary search tree from individual Nodes.
 */
class BinarySearchTree {
    Node root;
    int maxHeight = 0;
    int nodeNumber = 0;

    BinarySearchTree() {
        root = null;
    }

    /**
     * To find the maximum height/depth of the tree.
     * 
     * @param t starting node
     * @return The height of the tree.
     */
    public int treeHeight(Node t) {
        if (t == null) {
            return -1;
        } else {
            return 1 + Math.max(treeHeight(t.left), treeHeight(t.right));
        }
    }

    /**
     * Indicative position of every nodes. Actual position to place nodes will be
     * calculated by multiplying proper scaling.
     */
    public void computeNodePositions() {
        int depth = 1;
        inorder_traversal(root, depth);
    }

    /**
     * Numbers every node with respect to its inorder position and depth of the
     * node.
     * 
     * @param t     starting node
     * @param depth previous depth of the last node
     */
    public void inorder_traversal(Node t, int depth) {
        if (t != null) {
            inorder_traversal(t.left, depth + 1);
            // X coordinate given inorder numbering
            t.xpos = nodeNumber++;
            // Y coordinate given depth of the node
            t.ypos = depth;
            inorder_traversal(t.right, depth + 1);
        }
    }

    /**
     * Recursive routinr to Insert a new node into the tree.
     * 
     * @param root the root node of the tree
     * @param s    string to be inserted
     * @return root of the resulting tree
     */
    public Node insert(Node root, String s) {
        if (root == null) {
            root = new Node(s, null, null);
            return root;
        } else {
            if (Integer.parseInt(s) == Integer.parseInt(root.data)) {
                return root;
            } else if (Integer.parseInt(s) < Integer.parseInt(root.data)) {
                root.left = insert(root.left, s);
            } else if (Integer.parseInt(s) > Integer.parseInt(root.data)) {
                root.right = insert(root.right, s);
            }
            return root;
        }
    }

}

/**
 * A container class to contain Node Information.
 * 
 */
class Node {
    String data;
    int xpos;
    int ypos;
    Node left;
    Node right;

    /**
     * Default constructor of the Node Class.
     * 
     * @param x Data conatained by the Node
     * @param l Left Node
     * @param r Right Node
     */
    Node(String x, Node l, Node r) {
        left = l;
        right = r;
        data = x;
    }
}