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
        grammar.put("S", Arrays.asList("Sa", "Sb", "c","d"));
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
            for (String productions : tempProductions) {
                out.accept(productions);
                if (productions.charAt(0) == nonterminal.charAt(0)) {
                    oldProductions.add(productions.substring(1) + newNonTerminal);
                    out.accept(oldProductions.toString());
                } else {
                    newProductions.add(productions + newNonTerminal);
                    out.accept(newProductions.toString());
                }
            }
            if (!oldProductions.isEmpty()) {
                oldProductions.add("#");
                newGrammar.put(newNonTerminal, oldProductions);
                newGrammar.put(nonterminal, newProductions);
            }
        }
        return newGrammar;
    };
}
