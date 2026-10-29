# Lab 1: Production Systems & State Space Search

**Course:** IC-709D | Artificial Intelligence Lab  
**Student Name:** Vinisha Wagh  
**Roll No:** IC-2K23-99  
**Programming Language:** Java  

---

## 1. Water Jug Problem (`WaterJugProblem.java`)

### Aim
Measure a desired target amount of water using two jugs of specified capacities with no measuring markings.

### User Inputs
- Capacity of Jug 1 (e.g., `4`)
- Capacity of Jug 2 (e.g., `3`)
- Target amount to measure (e.g., `2`)

### Core Logic (Beginner Explanation)
- **State:** `(x, y)` where `x` is water in Jug 1 and `y` is water in Jug 2.
- **Start State:** `(0, 0)` (both jugs empty).
- **Goal Condition:** Reaching the target amount in either jug.
- **Allowed Actions:**
  1. Fill Jug 1 completely
  2. Fill Jug 2 completely
  3. Empty Jug 1 completely
  4. Empty Jug 2 completely
  5. Pour Jug 1 into Jug 2: $\text{pour} = \min(x, \text{jug2Cap} - y)$
  6. Pour Jug 2 into Jug 1: $\text{pour} = \min(y, \text{jug1Cap} - x)$
- **Search Strategy:** Breadth-First Search (BFS) using a Queue to find the shortest sequence of steps.

### Run Command
```bash
javac Lab1/WaterJugProblem.java
java -cp Lab1 WaterJugProblem
```

---

## 2. Missionaries and Cannibals Problem (`MissionariesCannibals.java`)

### Aim
Safely transport $N$ Missionaries and $N$ Cannibals across a river using a boat with a given capacity.

### User Inputs
- Number of Missionaries / Cannibals (e.g., `3`)
- Boat capacity (e.g., `2`)

### Core Logic (Beginner Explanation)
- **Rules:**
  - Boat can carry between 1 and `boatCap` people.
  - Cannibals must never outnumber missionaries on either bank when missionaries are present.
- **State:** `(m, c, boat)` representing counts on the **left bank** and boat position (`1` for left, `0` for right).
- **Safety Condition:**
  - Left bank: `m == 0 || m >= c`
  - Right bank: `(total - m) == 0 || (total - m) >= (total - c)`
- **Search Strategy:** Breadth-First Search (BFS) exploring all valid boat transfers until reaching `(0, 0, 0)`.

### Run Command
```bash
javac Lab1/MissionariesCannibals.java
java -cp Lab1 MissionariesCannibals
```

---

## 3. Basic Sudoku Solver (`SudokuSolver.java`)

### Aim
Solve any 9×9 Sudoku grid using numbers 1 to 9 such that no digit repeats in any row, column, or 3×3 subgrid.

### User Inputs
- **Option 1:** Load standard default sample puzzle
- **Option 2:** Enter a custom 9×9 puzzle row by row (using `0` for empty cells)

### Core Logic (Beginner Explanation)
- **Backtracking Algorithm:**
  1. Scan for the first empty cell (`0`).
  2. Try placing digits `1` through `9`.
  3. Check if digit is valid in current row, column, and 3×3 subgrid.
  4. If valid, recursively call `solve()`.
  5. If the recursive call succeeds, the puzzle is solved.
  6. If no digit leads to a solution, reset cell to `0` (**backtrack**) and try the next number.

### Run Command
```bash
javac Lab1/SudokuSolver.java
java -cp Lab1 SudokuSolver
```
