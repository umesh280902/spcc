import java.util.*;
import java.util.function.*;

public class Follow {
    static Map<String, List<List<String>>> productions;
    static Set<String> nonTerminals;
    static Set<String> terminals;
    static Map<String, Set<String>> firsts;
    static Map<String, Set<String>> follow;

    public static void main(String args[]){
        productions = new HashMap<>();
        productions.put("E", Arrays.asList(Arrays.asList("T", "R")));
        productions.put("R", Arrays.asList(Arrays.asList("+", "T", "R"), Arrays.asList("#")));
        productions.put("T", Arrays.asList(Arrays.asList("F", "Y")));
        productions.put("Y", Arrays.asList(Arrays.asList("*", "F", "Y"), Arrays.asList("#")));
        productions.put("F", Arrays.asList(Arrays.asList("(", "E", ")"), Arrays.asList("i")));

        nonTerminals = new HashSet<>(Arrays.asList("E", "R", "T", "Y", "F"));
        terminals = new HashSet<>(Arrays.asList("+", "*", "(", ")", "i"));

        // Initialize firsts map with predefined values
        firsts = new HashMap<>();
        firsts.put("E", new HashSet<>(Arrays.asList("(", "i")));
        firsts.put("R", new HashSet<>(Arrays.asList("#", "+")));
        firsts.put("T", new HashSet<>(Arrays.asList("(", "i")));
        firsts.put("Y", new HashSet<>(Arrays.asList("#", "*")));
        firsts.put("F", new HashSet<>(Arrays.asList("(", "i")));

        // Initialize follow map with empty sets
        follow = new HashMap<>();
        for (String nonTerminal : nonTerminals) {
            follow.put(nonTerminal, new HashSet<>());
        }
        follow.get("E").add("$");
        // Compute follow sets for each non-terminal
        for (String key : productions.keySet()) {
            Set<String> visited = new HashSet<>();
            computeFollowSet(key, visited);
        }

        for (Set<String> followSet : follow.values()) {
            followSet.remove("#");
        }
        // Print computed follow sets
        System.out.println("Computed Follow Sets:");
        for (String nonTerminal : nonTerminals) {
            System.out.println("follow(" + nonTerminal + ") => " + follow.get(nonTerminal));
        }
    }

    // Method to compute follow set for a non-terminal symbol
    static void computeFollowSet(String nonTerminal, Set<String> visited) {
        if (visited.contains(nonTerminal)) {
            return;
        }
        visited.add(nonTerminal);

        for (Map.Entry<String, List<List<String>>> entry : productions.entrySet()) {
            String key = entry.getKey();
            List<List<String>> prods = entry.getValue();
            for (List<String> prod : prods) {
                int index = prod.indexOf(nonTerminal);
                if (index != -1) {
                    if (index == prod.size() - 1) { // Case 2
                        computeFollowSet(key, visited);
                        follow.get(nonTerminal).addAll(follow.get(key));
                    } else {
                        String nextSymbol = prod.get(index + 1);
                        if (terminals.contains(nextSymbol)) {
                            follow.get(nonTerminal).add(nextSymbol);
                        } else {
                            Set<String> firstSet = firsts.get(nextSymbol);
                            follow.get(nonTerminal).addAll(firstSet);
                            if (firstSet.contains("#")) { // Case 3
                                computeFollowSet(key, visited);
                                follow.get(nonTerminal).addAll(follow.get(key));
                            }
                        }
                    }
                }
            }
        }
    }
}
