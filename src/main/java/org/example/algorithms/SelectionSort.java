package org.example.algorithms;

import org.example.collection.CustomArrayList;

public class SelectionSort {
    // Сортировка выбором
    public static <T extends Comparable<T>> void selectionSort(CustomArrayList<T> list) {
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (list.get(j).compareTo(list.get(minIndex)) < 0) {
                    minIndex = j;
                }
            }
            // Меняем элементы местами
            T temp = list.get(minIndex);
            list.set(minIndex, list.get(i));
            list.set(i, temp);
        }
    }
}

