# Assignment 2 (Data Structures) Report

## 1. Complexity Table

| Structure | Method | Best | Avg | Worst | Space | Justification |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **DynamicArray** | `get(i)` | O(1) | O(1) | O(1) | O(n) | Direct access to the array index in memory. |
| | `add(x)` | O(1) | O(1) | O(n) | O(n) | Usually just adds to the end, but copies the whole array when resizing (growing). |
| | `add(i, x)` / `remove(i)`| O(1) | O(n) | O(n) | O(n) | We have to shift all elements after index `i`. |
| | `contains(x)` | O(1) | O(n) | O(n) | O(n) | Simple linear search with a loop. |
| **MyLinkedList**| `get(i)` | O(1) | O(n) | O(n) | O(n) | We have to follow pointers from the head to the needed index. |
| | `add(x)` | O(1) | O(1) | O(1) | O(n) | We have a `tail` pointer, so we just attach it to the end. |
| | `add(i, x)` / `remove(i)`| O(1) | O(n) | O(n) | O(n) | The insertion itself is O(1), but finding the index `i` takes O(n) time. |
| | `contains(x)` | O(1) | O(n) | O(n) | O(n) | We go through all nodes one by one. |
| **MinHeap** | `peekMin()` | O(1) | O(1) | O(1) | O(n) | The minimum element is always at the root (index 0). |
| | `insert(x)` | O(1) | O(log n)| O(log n)| O(n) | We add to the end of the array and do `bubbleUp` up to the tree height. |
| | `extractMin()`| O(1) | O(log n)| O(log n)| O(n) | We put the last element in the root and do `bubbleDown`. |

## 2. Loop Invariant Proofs

### Operation 1: `contains(x)` in DynamicArray
* **Invariant:** Before iteration `i` starts, the element `x` is definitely not in the subarray from `0` to `i-1`.
* **Initialization:** Before the first iteration (`i = 0`), the subarray is empty. Since it is empty, `x` is not there. The invariant holds.
* **Maintenance:** At step `i`, we check `data[i]`. If it is not `x`, we continue to the next step. This means before step `i+1`, we know for sure that `x` is not in the subarray from `0` to `i`. The invariant is maintained.
* **Termination:** The loop stops if we find `x` (then it returns `true`), or if `i` reaches the end of the array (meaning `x` is nowhere to be found, so it returns `false`). The method works correctly.

### Operation 2: `bubbleUp(index)` in MinHeap
* **Invariant:** The heap property (parent <= child) is correct everywhere in the tree, except possibly between the current `index` and its parent.
* **Initialization:** Before the loop, we added a new element to the very end. The rest of the tree was already a correct heap, so the only potential problem is between the new element and its parent.
* **Maintenance:** Inside the loop, if the current element is smaller than its parent, we swap them. Now they are in the correct order. But this element moved up, and it might be smaller than its new parent. So, the possible violation just moved one level up with the `index`.
* **Termination:** The loop stops when we reach the root (`index == 0`) or when the current element is greater than or equal to its parent. At this point, there are no more violations, and the heap is fully correct again.

## 3. Discussion

**Why DynamicArray is faster:** An array stores data in one continuous block of memory. When the CPU reads one element, it automatically loads the neighboring elements into its fast cache (spatial locality). That is why iterating or doing `get(i)` is almost instant.

**Why MyLinkedList is slow:** The nodes of the list are scattered randomly in the memory. To go to the next node, the CPU has to jump by references (pointer chasing). Because of this, the CPU constantly misses the cache and waits for data from the slow RAM. Also, node objects take more space because of the pointers and create extra work for the Garbage Collector.

**When to use what:**
* `DynamicArray` is the best choice almost all the time for standard data storage because it is very CPU-cache friendly.
* `MyLinkedList` makes sense only when we constantly insert and remove elements strictly at the very beginning or the end (head / tail).
* `MinHeap` is perfect for priority queues (priority-based scheduling) because it finds the minimum in O(1) and doesn't require sorting the whole array.