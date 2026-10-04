import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class Benchmark {
    private static final int[] SIZES = {100, 1000, 10000, 100000};
    private static final int RUNS = 5;
    private static final int WARMUP_RUNS = 2;
    private static final Random rand = new Random(42);
    private static FileWriter csvWriter;

    public static void main(String[] args) throws IOException {
        File resultsDir = new File("results");
        if (!resultsDir.exists()) resultsDir.mkdirs();

        csvWriter = new FileWriter("results/results.csv");
        csvWriter.append("workload,variant,structure,n,time_ms,steps,moves,comparisons\n");

        System.out.println("Starting Benchmark...");

        for (int n : SIZES) {
            System.out.println("Running for N = " + n);
            runW1(n);
            runW2(n);
            runW3(n, "head");
            runW3(n, "middle");
            runW4(n);
        }

        csvWriter.flush();
        csvWriter.close();
        System.out.println("Done! results.csv generated.");
    }

    private static void runW1(int n) throws IOException {
        for (String structure : new String[]{"DynamicArray", "MyLinkedList"}) {
            long[] times = new long[RUNS];
            long steps = 0, moves = 0, comps = 0;

            for (int r = -WARMUP_RUNS; r < RUNS; r++) {
                DynamicArray arr = new DynamicArray();
                MyLinkedList list = new MyLinkedList();
                for (int i = 0; i < n; i++) {
                    if (structure.equals("DynamicArray")) arr.add(i);
                    else list.add(i);
                }

                if (structure.equals("DynamicArray")) arr.resetCounters();
                else list.resetCounters();

                long start = System.currentTimeMillis();
                for (int i = 0; i < 10000; i++) {
                    int idx = rand.nextInt(n);
                    if (structure.equals("DynamicArray")) arr.get(idx);
                    else list.get(idx);
                }
                long end = System.currentTimeMillis();

                if (r >= 0) {
                    times[r] = end - start;
                    steps = structure.equals("DynamicArray") ? arr.steps : list.steps;
                    moves = structure.equals("DynamicArray") ? arr.moves : list.moves;
                    comps = structure.equals("DynamicArray") ? arr.comparisons : list.comparisons;
                }
            }
            writeResult("W1", "-", structure, n, getMedian(times), steps, moves, comps);
        }
    }

    private static void runW2(int n) throws IOException {
        for (String structure : new String[]{"DynamicArray", "MyLinkedList"}) {
            long[] times = new long[RUNS];
            long steps = 0, moves = 0, comps = 0;

            for (int r = -WARMUP_RUNS; r < RUNS; r++) {
                DynamicArray arr = new DynamicArray();
                MyLinkedList list = new MyLinkedList();
                for (int i = 0; i < n; i++) {
                    if (structure.equals("DynamicArray")) arr.add(i);
                    else list.add(i);
                }

                if (structure.equals("DynamicArray")) arr.resetCounters();
                else list.resetCounters();

                long start = System.currentTimeMillis();
                for (int i = 0; i < 1000; i++) {
                    int target = (i % 2 == 0) ? rand.nextInt(n) : n + rand.nextInt(n);
                    if (structure.equals("DynamicArray")) arr.contains(target);
                    else list.contains(target);
                }
                long end = System.currentTimeMillis();

                if (r >= 0) {
                    times[r] = end - start;
                    steps = structure.equals("DynamicArray") ? arr.steps : list.steps;
                    moves = structure.equals("DynamicArray") ? arr.moves : list.moves;
                    comps = structure.equals("DynamicArray") ? arr.comparisons : list.comparisons;
                }
            }
            writeResult("W2", "-", structure, n, getMedian(times), steps, moves, comps);
        }
    }

    private static void runW3(int n, String variant) throws IOException {
        for (String structure : new String[]{"DynamicArray", "MyLinkedList"}) {
            long[] times = new long[RUNS];
            long steps = 0, moves = 0, comps = 0;

            for (int r = -WARMUP_RUNS; r < RUNS; r++) {
                DynamicArray arr = new DynamicArray();
                MyLinkedList list = new MyLinkedList();
                for (int i = 0; i < n; i++) {
                    if (structure.equals("DynamicArray")) arr.add(i);
                    else list.add(i);
                }

                int index = variant.equals("head") ? 0 : n / 2;

                if (structure.equals("DynamicArray")) arr.resetCounters();
                else list.resetCounters();

                long start = System.currentTimeMillis();
                for (int i = 0; i < 1000; i++) {
                    if (structure.equals("DynamicArray")) arr.add(index, 999);
                    else list.add(index, 999);
                }
                for (int i = 0; i < 1000; i++) {
                    if (structure.equals("DynamicArray")) arr.remove(index);
                    else list.remove(index);
                }
                long end = System.currentTimeMillis();

                if (r >= 0) {
                    times[r] = end - start;
                    steps = structure.equals("DynamicArray") ? arr.steps : list.steps;
                    moves = structure.equals("DynamicArray") ? arr.moves : list.moves;
                    comps = structure.equals("DynamicArray") ? arr.comparisons : list.comparisons;
                }
            }
            writeResult("W3", variant, structure, n, getMedian(times), steps, moves, comps);
        }
    }

    private static void runW4(int n) throws IOException {
        long[] times = new long[RUNS];
        long steps = 0, moves = 0, comps = 0;

        for (int r = -WARMUP_RUNS; r < RUNS; r++) {
            MinHeap heap = new MinHeap();
            for (int i = 0; i < n; i++) {
                heap.insert(rand.nextInt(n * 10));
            }

            heap.resetCounters();

            long start = System.currentTimeMillis();
            for (int i = 0; i < n; i++) {
                heap.extractMin();
            }
            long end = System.currentTimeMillis();

            if (r >= 0) {
                times[r] = end - start;
                steps = heap.steps;
                moves = heap.moves;
                comps = heap.comparisons;
            }
        }
        writeResult("W4", "-", "MinHeap", n, getMedian(times), steps, moves, comps);
    }

    private static long getMedian(long[] times) {
        Arrays.sort(times);
        return times[times.length / 2];
    }

    private static void writeResult(String w, String v, String s, int n, long t, long steps, long moves, long comps) throws IOException {
        csvWriter.append(String.format("%s,%s,%s,%d,%d,%d,%d,%d\n", w, v, s, n, t, steps, moves, comps));
    }
}