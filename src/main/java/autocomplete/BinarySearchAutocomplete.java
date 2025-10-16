package autocomplete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Binary search implementation of the {@link Autocomplete} interface.
 *
 * @see Autocomplete
 */
public class BinarySearchAutocomplete implements Autocomplete {
    /**
     * {@link List} of added autocompletion terms.
     */
    private final List<CharSequence> elements;

    /**
     * Constructs an empty instance.
     */
    public BinarySearchAutocomplete() {
        elements = new ArrayList<>();
    }

    @Override
    public void addAll(Collection<? extends CharSequence> terms) {
        // Add the new terms to the end of the list and then sort the whole list

        elements.addAll(terms);
        Collections.sort(elements, null);

        //throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public List<CharSequence> allMatches(CharSequence prefix) {

        List<CharSequence> result = new ArrayList<>();
        int index = Collections.binarySearch(elements, prefix, null);
        if(index < 0) {
            index = -1 * (index + 1);
        }

        while(index < elements.size() && Autocomplete.isPrefixOf(prefix, elements.get(index))) {
            result.add(elements.get(index));
            index++;
        }

        return result;
    }
}
