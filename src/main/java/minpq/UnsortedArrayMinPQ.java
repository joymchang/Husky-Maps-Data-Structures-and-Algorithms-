package minpq;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Unsorted array (or {@link ArrayList}) implementation of the {@link MinPQ} interface.
 *
 * @param <E> the type of elements in this priority queue.
 * @see MinPQ
 */
public class UnsortedArrayMinPQ<E> implements MinPQ<E> {
    /**
     * {@link List} of {@link PriorityNode} objects representing the element-priority pairs in no specific order.
     */
    private final List<PriorityNode<E>> elements;

    /**
     * Constructs an empty instance.
     */
    public UnsortedArrayMinPQ() {
        elements = new ArrayList<>();
    }

    /**
     * Constructs an instance containing all the given elements and their priority values.
     *
     * @param elementsAndPriorities each element and its corresponding priority.
     */
    public UnsortedArrayMinPQ(Map<E, Double> elementsAndPriorities) {
        elements = new ArrayList<>(elementsAndPriorities.size());
        for (Map.Entry<E, Double> entry : elementsAndPriorities.entrySet()) {
            add(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void add(E element, double priority) {
        if (contains(element)) {
            throw new IllegalArgumentException("Already contains " + element);
        }
        // TODO: Replace with your code

        elements.add(new PriorityNode<>(element, priority));

        //throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public boolean contains(E element) {
        // TODO: Replace with your code

        for(PriorityNode<E> node : elements){
            if(Objects.equals(node.getElement(), element)) {
                return true;
            }
        }
        return false;

        //throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public double getPriority(E element) {
        // TODO: Replace with your code

        for(PriorityNode<E> node : elements){
            if(Objects.equals(node.getElement(), element)){
                return node.getPriority();
            }
        }
        return -1;

        //throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public E peekMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        // TODO: Replace with your code

        int indexOfMin = 0;
        double minPriority = elements.get(0).getPriority();

        for(int i = 0; i < elements.size(); i++) {
            double priority = elements.get(i).getPriority();
            if(priority < minPriority){
                indexOfMin = i;
                minPriority = priority;
            }
        }

        return elements.get(indexOfMin).getElement();

        //throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public E removeMin() {

        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        // TODO: Replace with your code

        int indexOfMin = 0;
        double minPriority = elements.get(0).getPriority();

        for(int i = 0; i < elements.size(); i++) {
            double priority = elements.get(i).getPriority();
            if(priority < minPriority){
                indexOfMin = i;
                minPriority = priority;
            }
        }

        PriorityNode<E> removed = elements.remove(indexOfMin);
        return removed.getElement();

        //throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void changePriority(E element, double priority) {
        if (!contains(element)) {
            throw new NoSuchElementException("PQ does not contain " + element);
        }
        // TODO: Replace with your code

        for(int i = 0; i < elements.size(); i++) {
            PriorityNode<E> node = elements.get(i);
            if(Objects.equals(node.getElement(), element)){
                elements.set(i, new PriorityNode<E>(element, priority));
            }
        }

        //throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int size() {
        // TODO: Replace with your code

        return elements.size();

        //throw new UnsupportedOperationException("Not implemented yet");
    }
}
