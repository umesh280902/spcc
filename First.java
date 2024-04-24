import java.util.*;
import java.util.function.*;

public class First {
    static Map<String, List<List<String>>> productions;
    static Set<String> nonTerminals;
    static Set<String> terminals;
    static Map<String, Set<String>> firsts;

    public static void main(String args[]) {
        productions = new HashMap<>();
        productions.put("E", Arrays.asList(Arrays.asList("T", "R")));
        productions.put("R", Arrays.asList(Arrays.asList("+", "T", "R"), Arrays.asList("#")));
        productions.put("T", Arrays.asList(Arrays.asList("F", "Y")));
        productions.put("Y", Arrays.asList(Arrays.asList("*", "F", "Y"), Arrays.asList("#")));
        productions.put("F", Arrays.asList(Arrays.asList("(", "E", ")"), Arrays.asList("i")));

        nonTerminals = new HashSet<>(Arrays.asList("E", "R", "T", "Y", "F"));
        terminals = new HashSet<>(Arrays.asList("+", "*", "(", ")", "i"));
        firsts = new HashMap<>();

        for (String key : productions.keySet()) {
            firsts.put(key, new HashSet<>());
        }

        productions.forEach((nonTerm, prods) -> {
            Set<String> visited = new HashSet<>();
            computeFirstSet(nonTerm, visited);
        });

        System.out.println("\nCalculated firsts: ");
        firsts.forEach((nonTerm, firstSet) -> {
            System.out.println("first(" + nonTerm + ") => " + firstSet);
        });
    }

    static void computeFirstSet(String nonTerm, Set<String> visited) {
        if (visited.contains(nonTerm)) {
            return;
        }

        visited.add(nonTerm);

        for (List<String> production : productions.get(nonTerm)) {
            String firstSymbol = production.get(0);
            if (!nonTerminals.contains(firstSymbol)) {
                firsts.get(nonTerm).add(firstSymbol);
            } else {
                computeFirstSet(firstSymbol, visited);
                firsts.get(nonTerm).addAll(firsts.get(firstSymbol));

                if (firsts.get(firstSymbol).contains("#")) {
                    firsts.get(nonTerm).add("#");
                } else {
                    firsts.get(nonTerm).remove("#");
                }
            }
        }
    }
}
