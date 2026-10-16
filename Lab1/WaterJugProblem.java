import java.util.*;

/**
 * AI Lab - Experiment 1
 * Problem Statement: Water Jug Problem using Production Rule System
 * 
 * Student Name : Vinisha Wagh
 * Roll No.     : IC-2K23-99
 * Course       : IC-709D / IC-703 C (Artificial Intelligence Lab)
 */
public class WaterJugProblem {

    // Represents a state in the state space tree
    static class State {
        int x; // water in Jug 1
        int y; // water in Jug 2
        State parent;
        String action;

        public State(int x, int y, State parent, String action) {
            this.x = x;
            this.y = y;
            this.parent = parent;
            this.action = action;
        }

        @Override
        public String toString() {
            return "(" + x + ", " + y + ")";
        }
    }

    // Function to calculate Greatest Common Divisor (GCD)
    public static int gcd(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }

    // Check if the given target is achievable using number theory
    public static boolean isSolvable(int jug1Cap, int jug2Cap, int target) {
        if (target > Math.max(jug1Cap, jug2Cap)) {
            return false;
        }
        return (target % gcd(jug1Cap, jug2Cap) == 0);
    }

    // Display the 8 formal AI production rules
    public static void displayProductionRules(int jug1Cap, int jug2Cap) {
        System.out.println("\n----------------------------------------------------------------------------------");
        System.out.println("                           PRODUCTION RULES FOR WATER JUG                         ");
        System.out.println("----------------------------------------------------------------------------------");
        System.out.println("Rule 1: (x, y) if x < " + jug1Cap + "             --> (" + jug1Cap + ", y)        [Fill Jug 1]");
        System.out.println("Rule 2: (x, y) if y < " + jug2Cap + "             --> (x, " + jug2Cap + ")        [Fill Jug 2]");
        System.out.println("Rule 3: (x, y) if x > 0             --> (0, y)        [Empty Jug 1]");
        System.out.println("Rule 4: (x, y) if y > 0             --> (x, 0)        [Empty Jug 2]");
        System.out.println("Rule 5: (x, y) if x + y >= " + jug1Cap + " and y > 0 --> (" + jug1Cap + ", y-(" + jug1Cap + "-x)) [Pour Jug 2 to Jug 1 until full]");
        System.out.println("Rule 6: (x, y) if x + y >= " + jug2Cap + " and x > 0 --> (x-(" + jug2Cap + "-y), " + jug2Cap + ") [Pour Jug 1 to Jug 2 until full]");
        System.out.println("Rule 7: (x, y) if x + y <= " + jug1Cap + " and y > 0 --> (x + y, 0)    [Pour all of Jug 2 into Jug 1]");
        System.out.println("Rule 8: (x, y) if x + y <= " + jug2Cap + " and x > 0 --> (0, x + y)    [Pour all of Jug 1 into Jug 2]");
        System.out.println("----------------------------------------------------------------------------------\n");
    }

    // Generates valid successor states by applying applicable production rules
    public static List<State> getSuccessors(State current, int jug1Cap, int jug2Cap) {
        List<State> successors = new ArrayList<>();
        int x = current.x;
        int y = current.y;

        // Rule 1: Fill Jug 1
        if (x < jug1Cap) {
            successors.add(new State(jug1Cap, y, current, "Rule 1: Fill Jug 1 completely"));
        }

        // Rule 2: Fill Jug 2
        if (y < jug2Cap) {
            successors.add(new State(x, jug2Cap, current, "Rule 2: Fill Jug 2 completely"));
        }

        // Rule 3: Empty Jug 1
        if (x > 0) {
            successors.add(new State(0, y, current, "Rule 3: Empty Jug 1"));
        }

        // Rule 4: Empty Jug 2
        if (y > 0) {
            successors.add(new State(x, 0, current, "Rule 4: Empty Jug 2"));
        }

        // Rule 5: Pour Jug 2 into Jug 1 until Jug 1 is full
        if (y > 0 && (x + y >= jug1Cap) && x < jug1Cap) {
            int poured = jug1Cap - x;
            successors.add(new State(jug1Cap, y - poured, current, "Rule 5: Pour Jug 2 into Jug 1 until full"));
        }

        // Rule 6: Pour Jug 1 into Jug 2 until Jug 2 is full
        if (x > 0 && (x + y >= jug2Cap) && y < jug2Cap) {
            int poured = jug2Cap - y;
            successors.add(new State(x - poured, jug2Cap, current, "Rule 6: Pour Jug 1 into Jug 2 until full"));
        }

        // Rule 7: Pour all water from Jug 2 into Jug 1
        if (y > 0 && (x + y <= jug1Cap)) {
            successors.add(new State(x + y, 0, current, "Rule 7: Pour all water from Jug 2 to Jug 1"));
        }

        // Rule 8: Pour all water from Jug 1 into Jug 2
        if (x > 0 && (x + y <= jug2Cap)) {
            successors.add(new State(0, x + y, current, "Rule 8: Pour all water from Jug 1 to Jug 2"));
        }

        return successors;
    }

    // Prints the solution path from initial state to goal state
    public static void printSolutionPath(State goal) {
        List<State> path = new ArrayList<>();
        State curr = goal;
        while (curr != null) {
            path.add(curr);
            curr = curr.parent;
        }
        Collections.reverse(path);

        System.out.println("Step-by-step Solution Path:");
        System.out.println(String.format("%-6s | %-12s | %-40s | %-12s", "Step", "Current (x,y)", "Production Rule Applied", "Next (x,y)"));
        System.out.println("----------------------------------------------------------------------------------");

        for (int i = 0; i < path.size() - 1; i++) {
            State from = path.get(i);
            State to = path.get(i + 1);
            System.out.println(String.format("%-6d | %-12s | %-40s | %-12s", (i + 1), from, to.action, to));
        }
        System.out.println("----------------------------------------------------------------------------------");
        System.out.println("Total Steps Required: " + (path.size() - 1));
        System.out.println("Goal State Reached   : " + goal + "\n");
    }

    // BFS Search - Guarantees shortest path (optimal)
    public static void solveBFS(int jug1Cap, int jug2Cap, int target) {
        System.out.println("==================================================================================");
        System.out.println("               SEARCH STRATEGY: BREADTH FIRST SEARCH (BFS)                        ");
        System.out.println("==================================================================================");

        long startTime = System.currentTimeMillis();

        if (!isSolvable(jug1Cap, jug2Cap, target)) {
            System.out.println("Problem is UNSOLVABLE based on GCD theorem (target is not a multiple of gcd).");
            return;
        }

        Queue<State> queue = new LinkedList<>();
        boolean[][] visited = new boolean[jug1Cap + 1][jug2Cap + 1];

        State start = new State(0, 0, null, "Initial State");
        queue.add(start);
        visited[0][0] = true;

        State goalNode = null;
        int exploredCount = 0;

        while (!queue.isEmpty()) {
            State current = queue.poll();
            exploredCount++;

            // Goal condition: Jug 1 contains target amount (or Jug 2 if target exceeds Jug 1)
            boolean isGoal = (target <= jug1Cap) ? (current.x == target) : (current.y == target);
            if (isGoal) {
                goalNode = current;
                break;
            }

            for (State next : getSuccessors(current, jug1Cap, jug2Cap)) {
                if (!visited[next.x][next.y]) {
                    visited[next.x][next.y] = true;
                    queue.add(next);
                }
            }
        }

        long endTime = System.currentTimeMillis();

        if (goalNode != null) {
            printSolutionPath(goalNode);
            System.out.println("States Explored : " + exploredCount);
            System.out.println("Execution Time  : " + (endTime - startTime) + " ms");
        } else {
            System.out.println("No solution found.");
        }
        System.out.println();
    }

    // DFS Search - Demonstrates alternative rule traversal
    public static void solveDFS(int jug1Cap, int jug2Cap, int target) {
        System.out.println("==================================================================================");
        System.out.println("                SEARCH STRATEGY: DEPTH FIRST SEARCH (DFS)                         ");
        System.out.println("==================================================================================");

        long startTime = System.currentTimeMillis();

        if (!isSolvable(jug1Cap, jug2Cap, target)) {
            System.out.println("Problem is UNSOLVABLE based on GCD theorem.");
            return;
        }

        Stack<State> stack = new Stack<>();
        boolean[][] visited = new boolean[jug1Cap + 1][jug2Cap + 1];

        State start = new State(0, 0, null, "Initial State");
        stack.push(start);

        State goalNode = null;
        int exploredCount = 0;

        while (!stack.isEmpty()) {
            State current = stack.pop();

            if (visited[current.x][current.y]) {
                continue;
            }
            visited[current.x][current.y] = true;
            exploredCount++;

            // Goal condition: Jug 1 contains target amount (or Jug 2 if target exceeds Jug 1)
            boolean isGoal = (target <= jug1Cap) ? (current.x == target) : (current.y == target);
            if (isGoal) {
                goalNode = current;
                break;
            }

            for (State next : getSuccessors(current, jug1Cap, jug2Cap)) {
                if (!visited[next.x][next.y]) {
                    stack.push(next);
                }
            }
        }

        long endTime = System.currentTimeMillis();

        if (goalNode != null) {
            printSolutionPath(goalNode);
            System.out.println("States Explored : " + exploredCount);
            System.out.println("Execution Time  : " + (endTime - startTime) + " ms");
        } else {
            System.out.println("No solution found.");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        System.out.println("==================================================================================");
        System.out.println("          IC-709D | ARTIFICIAL INTELLIGENCE LAB - EXPERIMENT 1                    ");
        System.out.println("                     WATER JUG PROBLEM (JAVA)                                     ");
        System.out.println("       Name: Vinisha Wagh   |   Roll No: IC-2K23-99                               ");
        System.out.println("==================================================================================");

        Scanner scanner = new Scanner(System.in);

        // Simple user input
        System.out.print("Enter capacity of Jug 1 (liters)       : ");
        int jug1Cap = scanner.nextInt();

        System.out.print("Enter capacity of Jug 2 (liters)       : ");
        int jug2Cap = scanner.nextInt();

        System.out.print("Enter target amount to measure (liters): ");
        int target = scanner.nextInt();
        System.out.println();

        System.out.println("Capacity of Jug 1 : " + jug1Cap + " liters");
        System.out.println("Capacity of Jug 2 : " + jug2Cap + " liters");
        System.out.println("Target Amount     : " + target + " liters");

        // Display production rules
        displayProductionRules(jug1Cap, jug2Cap);

        // Check solvability
        int g = gcd(jug1Cap, jug2Cap);
        System.out.println("Solvability Check:");
        System.out.println("  GCD(" + jug1Cap + ", " + jug2Cap + ") = " + g);
        System.out.println("  Target (" + target + ") % GCD (" + g + ") = " + (target % g));

        if (!isSolvable(jug1Cap, jug2Cap, target)) {
            System.out.println("  Result: Problem is NOT SOLVABLE.\n");
            scanner.close();
            return;
        }
        System.out.println("  Result: Problem is SOLVABLE.\n");

        // Run BFS
        solveBFS(jug1Cap, jug2Cap, target);

        // Run DFS
        solveDFS(jug1Cap, jug2Cap, target);

        // Short comparison note
        System.out.println("==================================================================================");
        System.out.println("                              COMPARISON SUMMARY                                  ");
        System.out.println("==================================================================================");
        System.out.println("1. BFS guarantees the shortest sequence of steps to reach the goal state.");
        System.out.println("2. DFS explores one branch deeply, which may yield a longer, sub-optimal path.");
        System.out.println("3. Both algorithms use a visited array to avoid infinite loops and cycles.");
        System.out.println("==================================================================================");
        scanner.close();
    }
}
