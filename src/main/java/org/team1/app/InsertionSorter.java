package org.team1.app;

import java.util.Comparator;
import java.util.List;

public class InsertionSorter implements Sorter{
    @Override
    public <T> List<T> sort(List<T> items, Comparator<? super T> comparator) {
        //Создаем копию списка чтобы не модифицировать исходный
        List<T> sortedItems  = new java.util.ArrayList<>(items);
        //Проходим по каждому элементу, начиная со второго
        for (int i = 1; i < sortedItems.size(); i++){
            T key = sortedItems.get(i);
            int j = i-1;
            //Сдвигаем элементы, которые больше key, на одну позицию вправо
            while (j>=0 && comparator.compare(sortedItems.get(j), key)>0){
                sortedItems.set(j+1, sortedItems.get(j));
                j--;
            }
            // Вставляем key на правильное место
            sortedItems.set(j+1, key);
        }
        return sortedItems;}
}
