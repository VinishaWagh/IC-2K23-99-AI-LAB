import java.util.*;

/**
 * Missionaries and Cannibals Problem using Breadth-First Search (BFS)
 * Takes user inputs for number of pairs and boat capacity.
 */
public class MissionariesCannibals {

    static class State {
        int m, c, boat; // m, c on left bank; boat: 1 = left, 0 = right
        State parent;

        State(int m, int c, int boat, State parent) {
            this.m = m;
            this.c = c;
            this.boat = boat;
            this.parent = parent;
        }
    }

    // Check if the current counts on both banks are safe
    static boolean isValid(int m, int c, int total) {
        if (m < 0 || m > total || c < 0 || c > total) return false;
        if (m > 0 && c > m) return false;                               // Left bank check
        if ((total - m) > 0 && (total - c) > (total - m)) return false; // Right bank check
        return true;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of Missionaries / Cannibals (e.g. 3): ");
        int total = sc.nextInt();

        System.out.print("Enter boat capacity (e.g. 2): ");
        int boatCap = sc.nextInt();

        solve(total, boatCap);
        sc.close();
    }

    public static void solve(int total, int boatCap) {
        Queue<State> queue = new LinkedList<>();
        boolean[][][] visited = new boolean[total + 1][total + 1][2];

        // Generate possible boat combinations: 1 to boatCap people
        List<int[]> moves = new ArrayList<>();
        for (int m = 0; m <= boatCap; m++) {
            for (int c = 0; c <= boatCap; c++) {
                if (m + c >= 1 && m + c <= boatCap && (m == 0 || m >= c)) {
                    moves.add(new int[]{m, c});
                }
            }
        }

        // Initial state: everyone on the left bank
        queue.add(new State(total, total, 1, null));
        visited[total][total][1] = true;

        State goalState = null;

        while (!queue.isEmpty()) {
            State curr = queue.poll();

            // Goal check: everyone is on the right bank (0 on left)
            if (curr.m == 0 && curr.c == 0 && curr.boat == 0) {
                goalState = curr;
                break;
            }

            for (int[] move : moves) {
                int nextM = (curr.boat == 1) ? curr.m - move[0] : curr.m + move[0];
                int nextC = (curr.boat == 1) ? curr.c - move[1] : curr.c + move[1];
                int nextBoat = 1 - curr.boat;

                if (isValid(nextM, nextC, total) && !visited[nextM][nextC][nextBoat]) {
                    visited[nextM][nextC][nextBoat] = true;
                    queue.add(new State(nextM, nextC, nextBoat, curr));
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
                String b = (s.boat == 1) ? "Left" : "Right";
                System.out.println("Step " + step + ": Left Bank [" + s.m + "M, " + s.c + "C] | Boat: " 
                                   + b + " | Right Bank [" + (total - s.m) + "M, " + (total - s.c) + "C]");
                step++;
            }
        } else {
            System.out.println("\nNo solution found.");
        }
    }
}
