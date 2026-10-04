public class MinHeap {
    private int[] data;
    private int size;

    public long steps = 0;
    public long moves = 0;
    public long comparisons = 0;

    public MinHeap() {
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
            steps++;
            newData[i] = data[i];
            moves++;
        }
        data = newData;
    }

    public void insert(int x) {
        if (size == data.length) {
            grow();
        }
        data[size] = x;
        moves++;
        size++;
        bubbleUp(size - 1);
    }

    private void bubbleUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            steps += 2;
            comparisons++;
            if (data[index] < data[parent]) {
                swap(index, parent);
                index = parent;
            } else {
                break;
            }
        }
    }

    public int peekMin() {
        if (size == 0) throw new IllegalStateException("Heap is empty");
        steps++;
        return data[0];
    }

    public int extractMin() {
        if (size == 0) throw new IllegalStateException("Heap is empty");
        steps++;
        int min = data[0];

        steps++;
        data[0] = data[size - 1];
        moves++;
        size--;

        if (size > 0) {
            bubbleDown(0);
        }
        return min;
    }

    private void bubbleDown(int index) {
        while (true) {
            int leftChild = 2 * index + 1;
            int rightChild = 2 * index + 2;
            int smallest = index;

            if (leftChild < size) {
                steps += 2;
                comparisons++;
                if (data[leftChild] < data[smallest]) {
                    smallest = leftChild;
                }
            }

            if (rightChild < size) {
                steps += 2;
                comparisons++;
                if (data[rightChild] < data[smallest]) {
                    smallest = rightChild;
                }
            }

            if (smallest != index) {
                swap(index, smallest);
                index = smallest;
            } else {
                break;
            }
        }
    }

    private void swap(int i, int j) {
        steps += 2;
        int temp = data[i];
        data[i] = data[j];
        data[j] = temp;
        moves += 2;
    }

    public int size() {
        return size;
    }
}