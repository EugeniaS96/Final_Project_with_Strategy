package org.example.strategy;

import org.example.collection.CustomArrayList;

import java.util.function.Supplier;

public class RandomFillingStrategy<T> implements DataFillingStrategy<T> {
    private final Supplier<T> generator;

    public RandomFillingStrategy(Supplier<T> generator) {
        this.generator = generator;
    }

    @Override
    public CustomArrayList<T> fillData(int size) {
        CustomArrayList<T> result = new CustomArrayList<>();
        for (int i = 0; i < size; i++) {
            result.add(generator.get());
        }
        return result;
    }
}