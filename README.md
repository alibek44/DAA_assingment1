1. Architecture Notes

All algorithms were implemented in Java using divide-and-conquer recursion.
	•	Recursion depth is controlled by always recursing into the smaller subproblem (QuickSort and Select), or by explicit depth tracking via the Metrics class.
	•	Allocations are minimized by reusing buffers where possible. For example, MergeSort uses a single reusable auxiliary array instead of allocating at each merge step.
	•	Cutoff to insertion sort was applied in MergeSort for small subproblems (n ≤ 32), which reduces overhead and improves cache locality.
	•	Metrics counters were integrated to record comparisons, moves, and recursion depth, while a lightweight CSV writer outputs results for later analysis.

⸻

2. Recurrence Analysis

MergeSort

The recurrence is
T(n) = 2T(n/2) + Θ(n)
from splitting into two halves and merging in linear time.
By the Master Theorem (Case 2), this gives
T(n) = Θ(n \log n).
The cutoff to insertion sort does not change the asymptotics but improves constants due to fewer recursive calls and better cache usage.

⸻

QuickSort

The recurrence depends on pivot choice.
	•	Best/Average case:
T(n) = T(n/2) + T(n/2) + Θ(n) = Θ(n \log n).
	•	Worst case:
T(n) = T(n-1) + Θ(n) = Θ(n^2).
Randomized pivots ensure expected balance, so with high probability runtime is Θ(n \log n). Recursing on the smaller partition first keeps recursion depth bounded by O(\log n).

⸻

Deterministic Select (Median of Medians)

The recurrence is
T(n) = T(n/5) + T(7n/10) + Θ(n),
since the pivot guarantees at least a 30/70 split.
By the Akra–Bazzi Theorem, this solves to
T(n) = Θ(n).
The constant factors are larger than randomized quickselect, but performance is stable.

⸻

Closest Pair of Points (2D)

The recurrence is
T(n) = 2T(n/2) + Θ(n),
from dividing by x-coordinate and checking the cross-strip in linear time.
By the Master Theorem (Case 2),
T(n) = Θ(n \log n).
The hidden constant is modest because the strip check requires at most 7–8 distance comparisons per point.

⸻

3. Plots and Discussion
	•	Time vs. n:
Both MergeSort and QuickSort scale as Θ(n \log n), but MergeSort’s curve is smoother. QuickSort’s runtime is slightly lower for large n, due to fewer memory writes and better cache behavior.
Select grows linearly, but with higher constants than expected, so only for very large n does it beat QuickSort. Closest Pair follows the n \log n curve.
	•	Depth vs. n:
MergeSort shows depth exactly \log_2(n). QuickSort stays around \log(n) due to randomized pivots and recursion strategy. Select also tracks logarithmic depth.
	•	Constant-factor effects:
	•	Cache performance favors QuickSort over MergeSort on large arrays.
	•	MergeSort’s allocation strategy (reused buffer) reduces GC pressure compared to naïve implementations.
	•	Select’s overhead comes from grouping into fives and copying subarrays.
	•	Closest Pair’s constants remain small because the strip scan checks only a constant number of neighbors.

<img width="524" height="168" alt="Screenshot 2025-09-28 at 20 16 30" src="https://github.com/user-attachments/assets/b66b88a4-38a0-4ebb-a53f-02171448f671" />

⸻

4. Summary

The experimental results align closely with the theoretical asymptotics:
	•	MergeSort and Closest Pair both confirmed Θ(n \log n).
	•	QuickSort showed expected average Θ(n \log n) scaling, with rare deviations when pivots were unbalanced.
	•	Deterministic Select was clearly linear, though slower for moderate input sizes due to large constants.
