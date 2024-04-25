import java.util.*;
import java.util.function.*;

public class DeadCodeElimination {
    static class Operator {
        char l;
        String r;

        Operator(char l, String r) {
            this.l = l;
            this.r = r;
        }
    }

    static Scanner scanner = new Scanner(System.in);
    static List<Operator> op;
    static List<Operator> pr;
    static Consumer<String> output = message -> {
        System.out.println(message);
    };

    public static void main(String args[]) {
        op = new LinkedList<>();
        pr = new LinkedList<>();
        output.accept("Enter the number of values");
        int n = scanner.nextInt();
        output.accept(Integer.toString(n));
        for (int i = 0; i < n; i++) {
            output.accept("Enter left");
            char left = scanner.next().charAt(0);
            output.accept("Enter right");
            String right = scanner.next();
            op.add(new Operator(left, right));
        }
        output.accept("Intermediate Code");
        op.forEach(o -> output.accept(o.l + "=" + o.r));

        for (int i = 0; i < n - 1; i++) {
            char temp = op.get(i).l;
            for (int j = 0; j < n; j++) {
                if (op.get(j).r.indexOf(temp) != -1) {
                    pr.add(new Operator(op.get(i).l, op.get(i).r));
                    break;
                }
            }

        }
        pr.add(new Operator(op.get(n - 1).l, op.get(n - 1).r));
        System.out.println("\nAfter Dead Code Elimination");
        pr.forEach(operator -> System.out.println(operator.l + "=" + operator.r));

        for (int i = 0; i < pr.size(); i++) {
            String temp = pr.get(i).r;
            for (int j = i + 1; j < pr.size(); j++) {
                if (temp.contains(pr.get(j).r)) {
                    char t = pr.get(j).l;
                    pr.get(j).l = pr.get(i).l;
                    for (int k = 0; k < pr.size(); k++) {
                        int a = pr.get(i).r.indexOf(temp);
                        if (a != -1) {
                            pr.get(i).r = pr.get(i).r.substring(0, a) + pr.get(i).l + pr.get(i).r.substring(a + 1);
                        }
                    }
                }
            }
        }
        System.out.println("\nEliminate Common Expression");
        pr.forEach(operator -> System.out.println(operator.l + "=" + operator.r));
        for (int i = 0; i < pr.size(); i++) {
            for (int j = i + 1; j < pr.size(); j++) {
                if (pr.get(i).l == pr.get(j).l && pr.get(i).r.equals(pr.get(j).r)) {
                    pr.get(i).l = '\0';
                }
            }
        }

        System.out.println("Optimized Code");
        pr.stream().filter(operator -> operator.l != '\0')
                .forEach(operator -> System.out.println(operator.l + "=" + operator.r));
    }
}
