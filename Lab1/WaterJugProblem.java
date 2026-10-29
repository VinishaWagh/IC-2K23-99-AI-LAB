import java.util.*;

/**
 * Water Jug Problem using Breadth-First Search (BFS)
 * Takes user inputs for Jug 1 capacity, Jug 2 capacity, and Target amount.
 */
public class WaterJugProblem {

    static class State {
        int x, y; // x = water in Jug 1, y = water in Jug 2
        State parent;

        State(int x, int y, State parent) {
            this.x = x;
            this.y = y;
            this.parent = parent;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter capacity of Jug 1: ");
        int jug1Cap = sc.nextInt();

        System.out.print("Enter capacity of Jug 2: ");
        int jug2Cap = sc.nextInt();

        System.out.print("Enter target amount: ");
        int target = sc.nextInt();

        solve(jug1Cap, jug2Cap, target);
        sc.close();
    }

    public static void solve(int jug1Cap, int jug2Cap, int target) {
        if (target > Math.max(jug1Cap, jug2Cap)) {
            System.out.println("Target cannot be larger than the largest jug.");
            return;
        }

        Queue<State> queue = new LinkedList<>();
        boolean[][] visited = new boolean[jug1Cap + 1][jug2Cap + 1];

        // Start with both jugs empty
        queue.add(new State(0, 0, null));
        visited[0][0] = true;

        State goalState = null;

        while (!queue.isEmpty()) {
            State curr = queue.poll();

            // Goal check: either jug has the target amount
            if (curr.x == target || curr.y == target) {
                goalState = curr;
                break;
            }

            List<State> nextStates = new ArrayList<>();

            // 1. Fill Jug 1
            nextStates.add(new State(jug1Cap, curr.y, curr));
            // 2. Fill Jug 2
            nextStates.add(new State(curr.x, jug2Cap, curr));
            // 3. Empty Jug 1
            nextStates.add(new State(0, curr.y, curr));
            // 4. Empty Jug 2
            nextStates.add(new State(curr.x, 0, curr));

            // 5. Pour Jug 1 -> Jug 2
            int p1 = Math.min(curr.x, jug2Cap - curr.y);
            nextStates.add(new State(curr.x - p1, curr.y + p1, curr));

            // 6. Pour Jug 2 -> Jug 1
            int p2 = Math.min(curr.y, jug1Cap - curr.x);
            nextStates.add(new State(curr.x + p2, curr.y - p2, curr));

            // Explore unvisited states
            for (State next : nextStates) {
                if (!visited[next.x][next.y]) {
                    visited[next.x][next.y] = true;
                    queue.add(next);
                }
            }
        }

        // Print step-by-step path
        if (goalState != null) {
            List<State> path = new ArrayList<>();
            for (State s = goalState; s != null; s = s.parent) {
                path.add(s);
            }
            Collections.reverse(path);

            System.out.println("\nSolution Path:");
            int step = 0;
            for (State s : path) {
                System.out.println("Step " + step + ": Jug 1 = " + s.x + "L, Jug 2 = " + s.y + "L");
                step++;
            }
        } else {
            System.out.println("\nNo solution found.");
        }
    }
}
