import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DataStructuresTest {

    @Test
    void testDynamicArray() {
        DynamicArray arr = new DynamicArray();
        arr.add(10);
        arr.add(20);
        arr.add(1, 15);

        assertEquals(3, arr.size());
        assertEquals(15, arr.get(1));
        assertTrue(arr.contains(20));
        assertFalse(arr.contains(99));

        arr.remove(0);
        assertEquals(15, arr.get(0));
        assertEquals(2, arr.size());

        assertThrows(IndexOutOfBoundsException.class, () -> arr.get(5));
        assertThrows(IndexOutOfBoundsException.class, () -> arr.remove(-1));
    }

    @Test
    void testMyLinkedList() {
        MyLinkedList list = new MyLinkedList();
        list.add(10);
        list.add(20);
        list.add(1, 15);

        assertEquals(3, list.size());
        assertEquals(15, list.get(1));
        assertTrue(list.contains(20));
        assertFalse(list.contains(99));

        list.remove(0);
        assertEquals(15, list.get(0));
        assertEquals(2, list.size());

        assertThrows(IndexOutOfBoundsException.class, () -> list.get(5));
    }

    @Test
    void testMinHeap() {
        MinHeap heap = new MinHeap();

        assertThrows(IllegalStateException.class, heap::peekMin);
        assertThrows(IllegalStateException.class, heap::extractMin);

        heap.insert(50);
        heap.insert(20);
        heap.insert(30);
        heap.insert(10);
        heap.insert(40);

        assertEquals(10, heap.peekMin());

        assertEquals(10, heap.extractMin());
        assertEquals(20, heap.extractMin());
        assertEquals(30, heap.extractMin());
        assertEquals(40, heap.extractMin());
        assertEquals(50, heap.extractMin());

        assertEquals(0, heap.size());
    }
}