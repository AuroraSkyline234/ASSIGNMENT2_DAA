public class DynamicArray {
    private int[] data;
    private int size;

    public long steps = 0;
    public long moves = 0;
    public long comparisons = 0;

    public DynamicArray() {
        data = new int[2];
        size = 0;
    }

    public void resetCounters() {
        steps = 0;
        moves = 0;
        comparisons = 0;
    }

    private void grow() {
        int[] newData = new int[data.length * 2];
        for (int i = 0; i < size; i++) {
            steps++
            newData[i] = data[i];
            moves++;
        }
        data = newData;
    }

    public void add(int x) {
        if (size == data.length) {
            grow();
        }
        data[size] = x;
        size++;
        moves++;
    }

    public void add(int index, int x) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        if (size == data.length) {
            grow();
        }
        for (int i = size; i > index; i--) {
            steps++;
            data[i] = data[i - 1];
            moves++;
        }
        data[index] = x;
        moves++;
        size++;
    }

    public int get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        steps++; // одно чтение ячейки
        return data[index];
    }

    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
            moves++;
        }
        size--;
    }

    public boolean contains(int x) {
        for (int i = 0; i < size; i++) {
            steps++;
            comparisons++;
            if (data[i] == x) {
                return true;
            }
        }
        return false;
    }

    public int size() {
        return size;
    }
}