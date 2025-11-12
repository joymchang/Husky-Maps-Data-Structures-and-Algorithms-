package autocomplete;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

/**
 * Ternary search tree (TST) implementation of the {@link Autocomplete} interface.
 *
 * @see Autocomplete
 */
public class TernarySearchTreeAutocomplete implements Autocomplete {
    /**
     * The overall root of the tree: the first character of the first autocompletion term added to this tree.
     */
    private Node overallRoot;

    /**
     * Constructs an empty instance.
     */
    public TernarySearchTreeAutocomplete() {
        overallRoot = null;
    }

    @Override
    public void addAll(Collection<? extends CharSequence> terms) {

        for(CharSequence term : terms) {
            if(term != null && (term.length() > 0)) {
                overallRoot = insert(overallRoot, term, 0);
            }
        }

        //throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public List<CharSequence> allMatches(CharSequence prefix) {

        List<CharSequence> result = new ArrayList<>();
        if(prefix == null) {
            return result;
        }

        Node node = getNode(overallRoot, prefix, 0);
        if(node == null) {
            return result;
        }

        if(node.isTerm) {
            result.add(prefix.toString());
        }

        collect(node.mid, new StringBuilder(prefix), result);

        return result;

        //throw new UnsupportedOperationException("Not implemented yet");
    }

    // helper method that inserts the term sorted into the TST
    // returns new node for this subtree is applicablex 
    private Node insert(Node node, CharSequence term, int index) {
        char c = term.charAt(index);
        if(node == null) {
            node = new Node(c);
        }

        if(c < node.data) {
            node.left = insert(node.left, term, index);
        } else if(c > node.data) {
            node.right = insert(node.right, term, index);
        } else { // c == node.data
            if((index + 1) == term.length()) {
                node.isTerm = true;
            } else {
                node.mid = insert(node.mid, term, index + 1);
            }
        }

        return node;
    }

    private Node getNode(Node node, CharSequence prefix, int index) {
        char c = prefix.charAt(index);
        
        if(c < node.data) {
            return getNode(node.left, prefix, index);
        } else if(node.data < c) {
            return getNode(node.right, prefix, index);
        } else {
            if(index == prefix.length() - 1) {
                return node;
            } else {
                return getNode(node.mid, prefix, (index + 1));
            }
        }
    }

    // 
    private void collect(Node node, StringBuilder prefix, List<CharSequence> result) {
        if(node == null) {
            return;
        }
        collect(node.left, prefix, result);
        if(node.isTerm) {
            result.add(prefix.toString() + node.data);
        }

        collect(node.mid, prefix.append(node.data), result);
        prefix.deleteCharAt(prefix.length() - 1);
        
        collect(node.right, prefix, result);
    }

    /**
     * A search tree node representing a single character in an autocompletion term.
     */
    private static class Node {
        private final char data;
        private boolean isTerm;
        private Node left;
        private Node mid;
        private Node right;

        public Node(char data) {
            this.data = data;
            this.isTerm = false;
            this.left = null;
            this.mid = null;
            this.right = null;
        }
    }
}
