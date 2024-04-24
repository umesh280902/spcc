import java.util.*;

public class parser {
    static Map<String, List<List<String>>> productions;
    static Set<String> nonTerminals;
    static Set<String> terminals;
    static Map<String, Set<String>> firsts;
    static Map<String, Set<String>> follow;

    public static void main(String[] args) {
        // Example inputs
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
        
        follow = new HashMap<>();
        follow.put("E", new HashSet<>(Arrays.asList("$", ")")));
        follow.put("R", new HashSet<>(Arrays.asList("$", ")")));
        follow.put("T", new HashSet<>(Arrays.asList("$", ")", "+")));
        follow.put("Y", new HashSet<>(Arrays.asList("$", ")", "+")));
        follow.put("F", new HashSet<>(Arrays.asList("$", ")", "*", "+")));
        
        Map<String,Map<String,List<String>>> parserTable=createParsingTable(firsts, follow, productions);
        printParsingTable(parserTable);
    }
    /*
     * Prequisite: Define the headers in headers there would be Non Terminal in right of this there would be all the Terminals with start symbol and below there would be Non Terminals and in the right the cell would be filled on the basis of the following cases 
     * 
     * Case 1: putting the production on the basis of first 
     * Case 2: when we encounter epsilon in the first then we go to the follow and fill the cells of the terminals present in follow
     * 
     * 
     */
    
    static Map<String,Map<String,List<String>>> createParsingTable(Map<String,Set<String>> first,Map<String,Set<String>> follow,Map<String, List<List<String>>> productions){
        Map<String,Map<String,List<String>>> parsingTable=new HashMap<>();
        for(String nonTerminal:productions.keySet()){
            parsingTable.put(nonTerminal,new HashMap<>());
            for(List<String> production:productions.get(nonTerminal)){
                Set<String> firstSet=new HashSet<>();
                if(Character.isUpperCase(production.get(0).charAt(0))){
                    firstSet=first.get(production.get(0));
                }else{
                    firstSet.add(production.get(0));
                }
                for(String terminal:firstSet){
                    if(!terminal.equals("#")){
                        parsingTable.get(nonTerminal).put(terminal, production);
                    }
                }
                if(firstSet.contains("#")){
                    for(String nonTerm:follow.get(nonTerminal)){
                        parsingTable.get(nonTerminal).put(nonTerm,production);
                    }
                }

            }
        
        }

        return parsingTable;
    }

    static void printParsingTable(Map<String,Map<String,List<String>>> parserTable){
        System.out.println("""
                ***********PARSING TABLE***********
                """);
                Set<String> terminal=new HashSet<>();
                for(Map<String,List<String>> row:parserTable.values()){
                    terminal.addAll(row.keySet());
                }
                List<String> terminalList=new ArrayList<>(terminal);
                Collections.sort(terminalList);
                System.out.print("Non Terminal\t");
                for(String term:terminalList){
                    System.out.print(term+"\t\t");
                }
                System.out.println();
                for (String nonTerminal : parserTable.keySet()) {
                    System.out.print(nonTerminal+"\t\t");
                    for(String term:terminalList){
                        List<String> production=parserTable.get(nonTerminal).get(term);
                        if(production!=null){
                            System.out.print(String.join(" ",production)+"\t\t");
                        }else{
                            System.out.print("-\t\t");
                        }
                    }
                    System.out.println();
                }

            }

    
}   
