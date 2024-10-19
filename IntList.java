/*************************************************************
 * Copyright (C) 2023
 *    Daniil Smirnov a.k.a. DS2
 *************************************************************/

/*************************************************************
 * File name   : IntList.java
 * Purpose     : List of ints.
 * Programmer  : DS2.
 * Created     : 20.09.2023.
 * Last update : 20.09.2023.
 *************************************************************/

class IntList {
    private int[] data;
    private int capacity;
    private int size;

    public IntList() {
        capacity = 2;
        size = 0;
        data = new int[capacity];
    }

    public IntList(int newCapacity) {
        capacity = Math.max(2, newCapacity);
        size = 0;
        data = new int[capacity];
    }

    private void realloc(int newCapacity) {
        if (newCapacity != capacity) {
            int[] oldData = data;
            data = new int[newCapacity];
            if (size >= 0) {
                System.arraycopy(oldData, 0, data, 0, Math.min(size, newCapacity));
            }
        }
        capacity = newCapacity;
    }
    public void add(int value) {
        if (size == capacity) {
            realloc((int) (capacity * 1.5));
        }
        data[size++] = value;
    }

    public void resize(int newSize) {
        if (newSize > capacity) {
            realloc(newSize);
        }
        size = newSize;
    }

    public int get(int index) {
        return data[index];
    }

    public void set(int index, int value) {
        data[index] = value;
    }

    public void shrinkToFit() {
        realloc(size);
    }

    public int size() {
        return size;
    }
}

/* END OF 'IntList.java' FILE. */
