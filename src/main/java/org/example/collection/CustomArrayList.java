package org.example.collection;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class CustomArrayList<T> implements Iterable<T> {
    private static final int DEFAULT_CAPACITY = 10;
    private T[] elements;
    private int size;

    public CustomArrayList() {
        this(DEFAULT_CAPACITY);
    }

    public CustomArrayList(int initialCapacity) {
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Initial capacity cannot be negative");
        this.elements = (T[]) new Object[initialCapacity];
    }

    // Добавление элемента в конец списка
    public void add(T element) {
        ensureCapacity(size + 1); // Убедимся, что массив достаточно большой
        elements[size++] = element;
    }

    // Получение элемента по индексу
    @SuppressWarnings("unchecked")
    public T get(int index) {
        checkIndex(index); // Проверка корректности индекса
        return (T) elements[index];
    }


    // Установка значения элемента по индексу
    @SuppressWarnings("unchecked")
    public void set(int index, T element) {
        checkIndex(index); // Проверка корректности индекса
        elements[index] = element; // Устанавливаем новое значение
    }

    // Удаление элемента по индексу
    @SuppressWarnings("unchecked")
    public T remove(int index) {
        checkIndex(index); // Проверка корректности индекса
        T removedElement = (T) elements[index];

        // Сдвигаем элементы влево, чтобы заполнить пустоту
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(elements, index + 1, elements, index, numMoved);
        }

        elements[--size] = null; // Очищаем последний элемент и уменьшаем размер
        return removedElement;
    }

    // Получение текущего размера списка
    public int size() {
        return size;
    }

    // Проверка, пуст ли список
    public boolean isEmpty() {
        return size == 0;
    }

    // Увеличение емкости массива, если необходимо
    private void ensureCapacity(int minCapacity) {
        if (minCapacity > elements.length) {
            int newCapacity = elements.length * 2; // Увеличиваем емкость в 2 раза
            if (newCapacity < minCapacity) {
                newCapacity = minCapacity;
            }
            elements = Arrays.copyOf(elements, newCapacity);
        }
    }

    // Проверка корректности индекса
    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    // Переопределение toString для удобного вывода
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(elements[i]);
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    // Переопределение equals и hashCode для корректного сравнения списков
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomArrayList<?> that = (CustomArrayList<?>) o;
        return size == that.size && Arrays.equals(elements, that.elements);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(size);
        result = 31 * result + Arrays.hashCode(elements);
        return result;
    }

    @Override
    public Iterator<T> iterator() {
        return new CustomIterator();
    }

    private class CustomIterator implements Iterator<T> {
        private int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex < size;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements in the collection");
            }
            return elements[currentIndex++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove operation is not supported"); // Optional
        }
    }
}