public class MyLinkedList {
    private Node head;
    private Node tail;
    private int size;

    public long steps = 0;
    public long moves = 0;
    public long comparisons = 0;

    private static class Node {
        int data;
        Node next;
        Node(int data) { this.data = data; }
    }

    public void resetCounters() {
        steps = 0;
        moves = 0;
        comparisons = 0;
    }

    public void add(int x) {
        Node newNode = new Node(x);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        moves++;
        size++;
    }

    public void add(int index, int x) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();
        Node newNode = new Node(x);
        if (index == 0) {
            newNode.next = head;
            head = newNode;
            if (tail == null) tail = newNode;
            moves++;
        } else if (index == size) {
            add(x);
            return;
        } else {
            Node current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
                steps++;
            }
            newNode.next = current.next;
            current.next = newNode;
            moves += 2;
        }
        size++;
    }

    public int get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
            steps++;
        }
        return current.data;
    }

    public void remove(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        if (index == 0) {
            head = head.next;
            if (head == null) tail = null;
            moves++;
        } else {
            Node current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
                steps++;
            }
            current.next = current.next.next;
            if (current.next == null) tail = current;
            moves++;
        }
        size--;
    }

    public boolean contains(int x) {
        Node current = head;
        while (current != null) {
            comparisons++;
            if (current.data == x) return true;
            current = current.next;
            steps++;
        }
        return false;
    }

    public int size() {
        return size;
    }
}