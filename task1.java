// Online Java Compiler
// Use this editor to write, compile and run your Java code online
import java.util.*;
/***
A   B   C
D   E   F
G   H   I
 
***/

class Main {
    private static ArrayList<String> res;
    private static ArrayList<String> finalres;
    private static Set<Integer> visited;
    private static Map<Integer, Integer> posPoint;
    private static Map<Integer, Set<Integer>> graph;
    
    public static void printGraph(Map<Integer, Set<Integer>> graph) {
        for (int i = 0; i < 9; i++) {
            System.out.println("Node: " + i);
            for (Integer n : graph.get(i)) {
                System.out.print(n + " ");
            }
            System.out.println();
        }
    }

    public static boolean canPassOver(int summ) {
        return ((summ % 2 == 0) && visited.contains(summ / 2));
    }
    
    public static void dfs(int cur, int pos, StringBuilder path) {
        if (cur == posPoint.get(3)) {  // already reached the end point 
            res.add(path.toString());
            return;
        }

        // get the neighbor
        Set<Integer> neighbors = graph.get(cur);

        // go through every single point
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                int cood = x + y * 3;
                if (cood == cur) continue;
                if (!(visited.contains(cood)) && (neighbors.contains(cood) || (!(neighbors.contains(cood)) && canPassOver(cur + cood)))) {
                    // unvisited neighbor or (visited neighbor)'s neighbor - can dfs
                    if (pos == 1 && cood == posPoint.get(3)) { // dont want to skip the 2nd point
                        continue;
                    } 
                    
                    path.append(Integer.toString(cood));
                    visited.add(cood);

                    if (cood == posPoint.get(pos + 1)) {
                        dfs(cood, pos+1, path);
                    } else {
                        dfs(cood, pos, path);
                    }
                    
                    path.deleteCharAt(path.length()-1);
                    visited.remove(cood);
                } 
            }
        }
    }
    
    public static ArrayList<String> listPatterns(String a, String b, String c) {
        int n = 3;

        // keep track of used points
        visited = new HashSet<>();
        res = new ArrayList<String>();
        finalres = new ArrayList<String>();
        posPoint = new HashMap<>() {{
            put(1, (int) a.charAt(0) - 65 );
            put(2, (int) b.charAt(0) - 65 );
            put(3, (int) c.charAt(0) - 65 );
        }};
        
        // build the graph
        graph = new HashMap<>();
        for (int i = 0; i < 9; i++) {
            int _x = i % 3;
            int _y = i / 3;
            
            // find all possible neighbors 
           Set<Integer> neis = new HashSet<>();
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    if ((_x != j && _y != k) || (_x == j && Math.abs(_y - k) <= 1) || ( (_y == k && Math.abs(_x - j) <= 1))) {
                        if (!(Math.abs(_x - j) >= 2 && Math.abs(_y - k) >= 2) && i != (j + k * 3))
                            neis.add(j + k * 3);
                    }
                    
                }
            }
            
            graph.put(i, neis);
        }
        
        StringBuilder path = new StringBuilder();
        path.append("0");
        visited.add(0);
        dfs(posPoint.get(1), 1, path);

        // change each string back to original
        for (String numbers : res) {
            StringBuilder code = new StringBuilder();
            for (char digit : numbers.toCharArray()) {
                // Convert digit to integer
                int num = Character.getNumericValue(digit);
                // Map to corresponding letter
                char letter = (char) ('A' + num);
                code.append(letter);
            }
            finalres.add(code.toString());
        }
        
        return finalres;
    }
    
    public static void main(String []args)
    {
        ArrayList<String> ok = listPatterns("A", "B", "C");
        
        //for (String r : ok) System.out.println(r);
        
    }
}