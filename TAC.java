import java.util.Scanner;

public class TAC {
    public static void main(String[] args) {
        // Prompt the user for an input expression
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter an arithmetic expression: ");
        String expr = scanner.nextLine();
        
        // Generate and print the corresponding three-address code
        generateTAC(expr);
    }

    public static void generateTAC(String expr) {
        // Split the input expression into individual tokens
        String[] tokens=expr.split("\\s+");
        int tCount=1;
        String tPrefix="t";
        String code="";
        for (int i = 0; i < tokens.length; i++) {
            if(i==0){
                code+=tPrefix+tCount+" = " +tokens[i]+"\n"; //current
            }else if(i==1){
                tCount++;
                    //current                              previous                                  current
                code+=tPrefix+tCount+" = " +tokens[i]+"\n"+tPrefix+(tCount-1)+"  " +tokens[i-1]+" "+tPrefix+tCount+" " +tokens[i]+"\n";
            }else{
                tCount++; //previous                         current                                  previous
                code+=tPrefix+(tCount-1)+"  " +tokens[i-1]+" "+tPrefix+tCount+"\n" +tPrefix+tCount+" = "+ tPrefix+(tCount-1) +"\n" ;
            }
        }
    }
}
