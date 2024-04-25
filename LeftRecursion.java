import java.util.*;
import java.util.function.*;

public class LeftRecursion {
    static Consumer<String> out = (String str) -> {
        System.out.println(str);
    };
    static HashMap<String, List<String>> grammar = new HashMap<>();
    static List<String> nonTerminals;
    static List<List<String>> productions;
    static List<String> terminals;

    public static void main(String args[]) {
        grammar.put("E", Arrays.asList("E + T", "T"));
        grammar.put("T", Arrays.asList("T * F", "F"));
        grammar.put("F", Arrays.asList("( E )", "id"));
        out.accept("Original Grammar:" + grammar.toString());

        nonTerminals = new ArrayList<>(grammar.keySet());
        productions = new ArrayList<>(grammar.values());
        terminals = extractTerminals.get();
        
        out.accept("Non-terminals: " + nonTerminals);
        out.accept("Productions: " + productions);
        out.accept("Terminals: " + terminals);

        HashMap<String, List<String>> newGrammar = deleteRecursion.get();
        out.accept("Grammar without left recursion: " + newGrammar.toString());
    }

    static Supplier<List<String>> extractTerminals = () -> {
        List<String> terminal = new ArrayList<>();
        for (List<String> list : productions) {
            for (String l : list) {
                if (l.matches("[a-z]+")) {
                    terminal.add(l);
                }
            }
        }
        return terminal;
    };

    static Supplier<HashMap<String, List<String>>> deleteRecursion = () -> {
        HashMap<String, List<String>> newGrammar = new HashMap<>();
        for (String nonterminal : nonTerminals) {
            List<String> tempProductions = grammar.get(nonterminal);
            List<String> newProductions = new ArrayList<>();
            List<String> oldProductions = new ArrayList<>();
            String newNonTerminal = nonterminal + "'";
            boolean leftRecursive = false; // Flag to check if left recursion exists for this nonterminal
            for (String productions : tempProductions) {
                out.accept(productions);
                if (productions.charAt(0) == nonterminal.charAt(0)) {
                    leftRecursive = true;
                    oldProductions.add(productions.substring(1) + newNonTerminal);
                    out.accept(oldProductions.toString());
                } else {
                    newProductions.add(productions + newNonTerminal);
                    out.accept(newProductions.toString());
                }
            }
            if (leftRecursive) {
                oldProductions.add("#");
                newGrammar.put(newNonTerminal, oldProductions);
                newGrammar.put(nonterminal, newProductions);
            } else {
                newGrammar.put(nonterminal, tempProductions); // No left recursion, add as it is
            }
        }
        return newGrammar;
    };
    
}
