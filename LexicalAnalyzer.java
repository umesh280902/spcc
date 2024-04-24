import java.util.*;
import java.util.function.*;

public class LexicalAnalyzer {
    static Consumer<String> print = (String str) -> { System.out.println(str); };

    public static void main(String args[]) {
        String input = "void main { int a = 20; int b = 30; int c = a + b ; printf( c );}";
        print.accept(input);
        ClassifyLexeme classifyLexeme = new ClassifyLexeme();
        StringBuilder lexemeBuilder = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            if (Character.isWhitespace(ch) || terminator.test(ch) || separator.test(ch) || operator.test(ch)) {
                classifyLexeme.classify(lexemeBuilder.toString());
                lexemeBuilder.setLength(0); // Clear the StringBuilder for the next lexeme
                if (separator.test(ch)) {
                    print.accept(ch + " is a separator.");
                } else if (terminator.test(ch)) {
                    print.accept(ch + " is a terminator.");
                } else if (operator.test(ch)) {
                    print.accept(ch + " is an operator.");
                }
            } else {
                lexemeBuilder.append(ch);
            }
        }

        // Process the last lexeme if it's not empty
        if (lexemeBuilder.length() > 0) {
            classifyLexeme.classify(lexemeBuilder.toString());
        }
    }

    static Predicate<Character> separator = ch -> {
        List<Character> separators = Arrays.asList('{', '}', '(', ')');
        return separators.contains(ch);
    };

    static Predicate<Character> terminator = ch -> ch == ';';

    static Predicate<Character> operator = ch -> {
        List<Character> operators = Arrays.asList('+', '-', '/', '*', '%', '=');
        return operators.contains(ch);
    };

    static class ClassifyLexeme {
        Predicate<String> isKeyword = (String str) -> {
            List<String> keyword = Arrays.asList("void", "main", "if", "else", "include", "int", "float", "const", "printf", "scanf", "else if");
            return keyword.contains(str);
        };

        Predicate<String> isAlphabet = (String lexeme) -> lexeme.matches("[a-zA-Z]+");
        Predicate<String> isNumber = (String lexeme) -> lexeme.matches("\\d+");
        Predicate<String> isVariable = (String lexeme) -> lexeme.matches("[a-zA-Z][a-zA-Z0-9_]*");

        public void classify(String lexeme) {
            // Classification logic here
            if (lexeme.isEmpty()) {
                return; // Skip empty lexemes
            }

            if (isAlphabet.test(lexeme) && isKeyword.test(lexeme)) {
                print.accept(lexeme + " is a keyword.");
            } else if (isNumber.test(lexeme)) {
                print.accept(lexeme + " is a number.");
            } else if (isVariable.test(lexeme)) {
                print.accept(lexeme + " is a variable.");
            }
        }
    }
}
