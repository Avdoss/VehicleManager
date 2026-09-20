package org.team1.app;

import java.util.Comparator;
import java.util.List;

public interface Sorter
{
    <T> List<T> sort(List<T> items, Comparator<? super T> comparator);
}
