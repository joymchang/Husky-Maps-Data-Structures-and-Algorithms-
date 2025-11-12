package minpq;

import java.util.*;

/**
 * Optimized binary heap implementation of the {@link MinPQ} interface.
 *
 * @param <E> the type of elements in this priority queue.
 * @see MinPQ
 */
public class OptimizedHeapMinPQ<E> implements MinPQ<E> {
    /**
     * {@link List} of {@link PriorityNode} objects representing the heap of element-priority pairs.
     */
    private final List<PriorityNode<E>> elements;
    /**
     * {@link Map} of each element to its associated index in the {@code elements} heap.
     */
    private final Map<E, Integer> elementsToIndex;

    /**
     * Constructs an empty instance.
     */
    public OptimizedHeapMinPQ() {
        elements = new ArrayList<>();
        elementsToIndex = new HashMap<>();
        elements.add(null);
    }

    /**
     * Constructs an instance containing all the given elements and their priority values.
     *
     * @param elementsAndPriorities each element and its corresponding priority.
     */
    public OptimizedHeapMinPQ(Map<E, Double> elementsAndPriorities) {

        this();
        for(Map.Entry<E, Double> entry : elementsAndPriorities.entrySet()){
            add(entry.getKey(), entry.getValue());
        }

        //throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void add(E element, double priority) {
        if (contains(element)) {
            throw new IllegalArgumentException("Already contains " + element);
        }

        PriorityNode<E> newNode = new PriorityNode<E>(element, priority);
        elements.add(newNode);
        int index = elements.size() - 1;
        elementsToIndex.put(element, index);
        swim(index);

        //throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public boolean contains(E element) {

        return elementsToIndex.containsKey(element);

        //throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public double getPriority(E element) {

        for(int i = 1; i < elements.size(); i++) {
            PriorityNode<E> node = elements.get(i);

            if(node.getElement().equals(element)){
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

        return elements.get(1).getElement();

        //throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public E removeMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }

        E minElement = peekMin();
        int lastIndex = elements.size() - 1;

        swap(1, lastIndex);
        elements.remove(lastIndex);
        elementsToIndex.remove(minElement);

        if(elements.size() > 1){
            sink(1);
        }
        return minElement;

        //throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void changePriority(E element, double priority) {
        if (!contains(element)) {
            throw new NoSuchElementException("PQ does not contain " + element);
        }

        int index = elementsToIndex.get(element);
        double oldPriority = elements.get(index).getPriority();
        elements.set(index, new PriorityNode<E>(element, priority));

        if(priority < oldPriority){
            swim(index);
        } else if(priority > oldPriority){
            sink(index);
        }

        //throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int size() {

        return elements.size() - 1;

        //throw new UnsupportedOperationException("Not implemented yet");
    }

    // HELPER METHODS

    private void swim(int index){
        boolean done = false;
        while(index > 1 && !done){
            int indexOfParent = index / 2;
            if((elements.get(index).getPriority()) < (elements.get(indexOfParent).getPriority())){
                swap(index, indexOfParent);
                index = indexOfParent;
            } else {
                done = true;
            }
        }
    }

    private void swap(int index1, int index2){
        PriorityNode<E> temp = elements.get(index1);
        elements.set(index1, elements.get(index2));
        elements.set(index2, temp);

        elementsToIndex.put(elements.get(index1).getElement(), index1);
        elementsToIndex.put(elements.get(index2).getElement(), index2);
    }

    private void sink(int index) {
        boolean done = false;
        while(!done) {
            int left = index * 2;
            int right = 2 * index + 1;
            int smallest = index;

            if((left < elements.size()) && (elements.get(left).getPriority() < 
                elements.get(smallest).getPriority())){
                smallest = left;
            }

            if((right < elements.size()) && (elements.get(right).getPriority() < 
                elements.get(smallest).getPriority())){
                smallest = right;
            }

            if(smallest != index) {
                swap(index, smallest);
                index = smallest;
            } else {
                done = true;
            }
        }
    }
}
