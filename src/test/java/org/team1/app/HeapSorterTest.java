package org.team1.app;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HeapSorterTest {

    private final HeapSorter sorter = new HeapSorter();

    @Test
    void shouldSortListInAscendingOrder() {
        List<Integer> input = List.of(5, 1, 8, 3, 2);

        List<Integer> result = sorter.sort(
                input,
                Comparator.naturalOrder()
        );

        assertEquals(List.of(1, 2, 3, 5, 8), result);
    }

    @Test
    void shouldSortListInDescendingOrderUsingComparator() {
        List<Integer> input = List.of(1, 5, 3, 2, 4);

        List<Integer> result = sorter.sort(
                input,
                Comparator.reverseOrder()
        );

        assertEquals(List.of(5, 4, 3, 2, 1), result);
    }

    @Test
    void shouldReturnEmptyListWhenInputIsEmpty() {
        List<Integer> input = List.of();

        List<Integer> result = sorter.sort(
                input,
                Comparator.naturalOrder()
        );

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldSortListWithDuplicates() {
        List<Integer> input = List.of(5, 2, 5, 1, 2);

        List<Integer> result = sorter.sort(
                input,
                Comparator.naturalOrder()
        );

        assertEquals(List.of(1, 2, 2, 5, 5), result);
    }

    @Test
    void shouldNotModifyOriginalList() {
        List<Integer> input = new ArrayList<>(List.of(5, 1, 3));
        List<Integer> original = new ArrayList<>(input);

        List<Integer> result = sorter.sort(
                input,
                Comparator.naturalOrder()
        );

        assertEquals(original, input);
        assertEquals(List.of(1, 3, 5), result);
        assertNotSame(input, result);
    }
}