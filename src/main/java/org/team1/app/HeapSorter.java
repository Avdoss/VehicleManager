package org.team1.app;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class HeapSorter implements Sorter {

    @Override
    public <T> List<T> sort(List<T> items, Comparator<? super T> comparator) {
        Objects.requireNonNull(items, "Items list must not be null");
        Objects.requireNonNull(comparator, "Comparator must not be null");

        List<T> result = new ArrayList<>(items);

        for (int i = result.size() / 2 - 1; i >= 0; i--) {
            heapify(result, result.size(), i, comparator);
        }

        for (int end = result.size() - 1; end > 0; end--) {
            swap(result, 0, end);
            heapify(result, end, 0, comparator);
        }

        return result;
    }

    private <T> void heapify(
            List<T> items,
            int heapSize,
            int rootIndex,
            Comparator<? super T> comparator
    ) {
        int largestIndex = rootIndex;
        int leftChildIndex = 2 * rootIndex + 1;
        int rightChildIndex = 2 * rootIndex + 2;

        if (leftChildIndex < heapSize
                && comparator.compare(items.get(leftChildIndex), items.get(largestIndex)) > 0) {
            largestIndex = leftChildIndex;
        }

        if (rightChildIndex < heapSize
                && comparator.compare(items.get(rightChildIndex), items.get(largestIndex)) > 0) {
            largestIndex = rightChildIndex;
        }

        if (largestIndex != rootIndex) {
            swap(items, rootIndex, largestIndex);
            heapify(items, heapSize, largestIndex, comparator);
        }
    }

    private <T> void swap(List<T> items, int firstIndex, int secondIndex) {
        T temporary = items.get(firstIndex);
        items.set(firstIndex, items.get(secondIndex));
        items.set(secondIndex, temporary);
    }
}