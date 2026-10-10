# Experiment 1: Water Jug Problem using Production Rule System

**Course:** IC-709D | Artificial Intelligence Lab  
**Student Name:** Vinisha Wagh  
**Roll No:** IC-2K23-99  
**Semester:** MCA 
**Programming Language:** Java  

---

## 1. Aim
To solve the Water Jug Problem using a Production Rule System in Java and find the solution path using Breadth First Search (BFS) and Depth First Search (DFS).

---

## 2. Problem Statement
Given two jugs:
- **Jug 1** of capacity $X = 4$ liters
- **Jug 2** of capacity $Y = 3$ liters
- Both jugs initially empty: $(0, 0)$
- Target: Measure exactly $Z = 2$ liters in Jug 1.

Neither jug has any measuring markings. The only allowed operations are:
1. Filling a jug completely from an infinite water source.
2. Emptying a jug completely onto the ground.
3. Pouring water from one jug into another until either the source jug is empty or the receiving jug is full.

### Solvability Condition (Bézout's Lemma)
A target amount $Z$ can be measured using jugs of capacities $X$ and $Y$ if:
1. $Z \le \max(X, Y)$
2. $Z$ is a multiple of $\gcd(X, Y)$, i.e., $Z \pmod{\gcd(X, Y)} = 0$.

Here, $\gcd(4, 3) = 1$, and $2 \pmod{1} = 0$, so the problem is guaranteed to have a solution.

---

## 3. Production Rules

In Artificial Intelligence, a **Production Rule System** consists of:
- **State Space:** Set of all possible states $(x, y)$ where $0 \le x \le 4$ and $0 \le y \le 3$.
- **Initial State:** $(0, 0)$
- **Goal State:** $(2, y)$ where Jug 1 has 2 liters.
- **Production Rules:** A set of condition-action pairs:

| Rule No. | Condition (Precondition) | Action (Postcondition) | Description |
|:---:|:---|:---|:---|
| **Rule 1** | $x < 4$ | $(4, y)$ | Fill Jug 1 completely |
| **Rule 2** | $y < 3$ | $(x, 3)$ | Fill Jug 2 completely |
| **Rule 3** | $x > 0$ | $(0, y)$ | Empty Jug 1 |
| **Rule 4** | $y > 0$ | $(x, 0)$ | Empty Jug 2 |
| **Rule 5** | $x + y \ge 4 \text{ and } y > 0$ | $(4, y - (4 - x))$ | Pour Jug 2 into Jug 1 until Jug 1 is full |
| **Rule 6** | $x + y \ge 3 \text{ and } x > 0$ | $(x - (3 - y), 3)$ | Pour Jug 1 into Jug 2 until Jug 2 is full |
| **Rule 7** | $x + y \le 4 \text{ and } y > 0$ | $(x + y, 0)$ | Pour all water from Jug 2 into Jug 1 |
| **Rule 8** | $x + y \le 3 \text{ and } x > 0$ | $(0, x + y)$ | Pour all water from Jug 1 into Jug 2 |

---

## 4. Algorithm

### 4.1 Breadth First Search (BFS)
1. Initialize a `Queue` and enqueue the initial state $(0, 0)$.
2. Maintain a 2D boolean array `visited[5][4]` to track already explored states and prevent loops. Mark $(0, 0)$ as visited.
3. While the queue is not empty:
   - Dequeue the front state `current`.
   - Check if `current.x == 2`. If yes, backtrack using `parent` pointers to reconstruct and print the path, then exit.
   - Otherwise, apply each of the 8 production rules to generate valid next states.
   - For every valid next state `(next_x, next_y)` that has not been visited:
     - Mark `visited[next_x][next_y] = true`.
     - Set its parent to `current`.
     - Enqueue it into the queue.
4. If the queue becomes empty without reaching the goal, report that no solution exists.

### 4.2 Depth First Search (DFS)
1. Initialize a `Stack` and push the initial state $(0, 0)$.
2. Use a visited array to track visited states.
3. While the stack is not empty:
   - Pop the top state `current`.
   - If already visited, continue; otherwise mark as visited.
   - If `current.x == 2`, backtrack and print the path.
   - Generate all successor states by applying the 8 rules and push unvisited successors onto the stack.

---

## 5. State Space Search Tree (BFS Path)

```
       (0, 0)
         │  Rule 1: Fill Jug 1
         ▼
       (4, 0)
         │  Rule 6: Pour Jug 1 into Jug 2 until full
         ▼
       (1, 3)
         │  Rule 4: Empty Jug 2
         ▼
       (1, 0)
         │  Rule 8: Pour all of Jug 1 into Jug 2
         ▼
       (0, 1)
         │  Rule 1: Fill Jug 1
         ▼
       (4, 1)
         │  Rule 6: Pour Jug 1 into Jug 2 until full
         ▼
       (2, 3)  <-- Goal Reached: Jug 1 has 2 liters!
```

---

## 6. Output / Execution Results

```text
==================================================================================
          IC-709D | ARTIFICIAL INTELLIGENCE LAB - EXPERIMENT 1                    
                     WATER JUG PROBLEM (JAVA)                                     
       Name: Vinisha Wagh   |   Roll No: IC-2K23-99                               
==================================================================================
Running with standard textbook parameters:
Capacity of Jug 1 : 4 liters
Capacity of Jug 2 : 3 liters
Target Amount     : 2 liters

Solvability Check:
  GCD(4, 3) = 1
  Target (2) % GCD (1) = 0
  Result: Problem is SOLVABLE.

==================================================================================
               SEARCH STRATEGY: BREADTH FIRST SEARCH (BFS)                        
==================================================================================
Step-by-step Solution Path:
Step   | Current (x,y) | Production Rule Applied                  | Next (x,y)  
----------------------------------------------------------------------------------
1      | (0, 0)       | Rule 1: Fill Jug 1 completely            | (4, 0)      
2      | (4, 0)       | Rule 6: Pour Jug 1 into Jug 2 until full | (1, 3)      
3      | (1, 3)       | Rule 4: Empty Jug 2                      | (1, 0)      
4      | (1, 0)       | Rule 8: Pour all water from Jug 1 to Jug 2 | (0, 1)      
5      | (0, 1)       | Rule 1: Fill Jug 1 completely            | (4, 1)      
6      | (4, 1)       | Rule 6: Pour Jug 1 into Jug 2 until full | (2, 3)      
----------------------------------------------------------------------------------
Total Steps Required: 6
Goal State Reached   : (2, 3)

States Explored : 13
Execution Time  : 0 ms

==================================================================================
                SEARCH STRATEGY: DEPTH FIRST SEARCH (DFS)                         
==================================================================================
Step-by-step Solution Path:
Step   | Current (x,y) | Production Rule Applied                  | Next (x,y)  
----------------------------------------------------------------------------------
1      | (0, 0)       | Rule 2: Fill Jug 2 completely            | (0, 3)      
2      | (0, 3)       | Rule 7: Pour all water from Jug 2 to Jug 1 | (3, 0)      
3      | (3, 0)       | Rule 2: Fill Jug 2 completely            | (3, 3)      
4      | (3, 3)       | Rule 5: Pour Jug 2 into Jug 1 until full | (4, 2)      
5      | (4, 2)       | Rule 4: Empty Jug 2                      | (4, 0)      
6      | (4, 0)       | Rule 6: Pour Jug 1 into Jug 2 until full | (1, 3)      
7      | (1, 3)       | Rule 4: Empty Jug 2                      | (1, 0)      
8      | (1, 0)       | Rule 8: Pour all water from Jug 1 to Jug 2 | (0, 1)      
9      | (0, 1)       | Rule 1: Fill Jug 1 completely            | (4, 1)      
10     | (4, 1)       | Rule 6: Pour Jug 1 into Jug 2 until full | (2, 3)      
----------------------------------------------------------------------------------
Total Steps Required: 10
Goal State Reached   : (2, 3)

States Explored : 11
Execution Time  : 1 ms
```

---

## 7. Performance Analysis (BFS vs DFS)

| Parameter | Breadth First Search (BFS) | Depth First Search (DFS) | Observation / Reason |
|:---|:---:|:---:|:---|
| **Solution Length** | **6 steps** | 10 steps | BFS always finds the optimal (shortest) path |
| **States Explored** | 13 | 11 | BFS explores level by level across all branches |
| **Optimality** | **Optimal** | Non-Optimal | DFS follows deep paths first, reaching sub-optimal solutions |
| **Memory Data Structure**| Queue (FIFO) | Stack (LIFO) | Standard search traversal structures |
| **Cycle Handling** | `visited[5][4]` | `visited[5][4]` | Visited matrix prevents infinite loops in both |

---

## 8. How to Run the Program

```bash
# Compile
javac Lab1/WaterJugProblem.java

# Run standard experiment
java -cp Lab1 WaterJugProblem

# Run with custom parameters (Jug1 Jug2 Target)
java -cp Lab1 WaterJugProblem 5 3 4
```

---

## 9. Learning Outcomes
1. Understood how an AI problem can be formulated using a **State Space Representation** $(x, y)$.
2. Implemented a formal **Production Rule System** with conditions and actions.
3. Verified the mathematical solvability of the problem using the **GCD condition**.
4. Observed the difference between **BFS** and **DFS** in state space search, confirming that BFS guarantees an optimal solution.
