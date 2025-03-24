package org.example.strategy;

import org.example.collection.CustomArrayList;

public interface DataFillingStrategy<T> {
    CustomArrayList<T> fillData(int size);
}
