package org.example;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class Metrics {
    private final AtomicLong comparisons = new AtomicLong(0);
    private final AtomicLong moves       = new AtomicLong(0);   // proxy for writes/swaps/copies
    private final AtomicInteger depth    = new AtomicInteger(0);
    private final AtomicInteger maxDepth = new AtomicInteger(0);

    private long startNs;
    private long elapsedNs;

    public void startTimer() { startNs = System.nanoTime(); }
    public void stopTimer()  { elapsedNs = System.nanoTime() - startNs; }
    public long elapsedNanos() { return elapsedNs; }

    public void addCompare() { comparisons.incrementAndGet(); }
    public void addCompares(long c) { comparisons.addAndGet(c); }
    public void addMove() { moves.incrementAndGet(); }
    public void addMoves(long m) { moves.addAndGet(m); }

    public long getComparisons() { return comparisons.get(); }
    public long getMoves()       { return moves.get(); }

    public void enter() {
        int d = depth.incrementAndGet();
        maxDepth.updateAndGet(cur -> Math.max(cur, d));
    }
    public void exit() {
        depth.decrementAndGet();
    }
    public int currentDepth() { return depth.get(); }
    public int maxDepth()     { return maxDepth.get(); }

    public boolean leq(int a, int b) { addCompare(); return a <= b; }
    public boolean lt (int a, int b) { addCompare(); return a  < b; }

    public void swap(int[] arr, int i, int j) {
        if (i == j) return;
        int t = arr[i]; moves.incrementAndGet();
        arr[i] = arr[j]; moves.incrementAndGet();
        arr[j] = t;      moves.incrementAndGet();
    }

    public void write(int[] arr, int idx, int val) {
        arr[idx] = val; moves.incrementAndGet();
    }
}