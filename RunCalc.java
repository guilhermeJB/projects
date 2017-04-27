import java.util.Scanner;

/**
 * Esta classe executa uma Calculadora RPN
 * 
 * @author Guilherme Bernardo, n. 49504
 *
 */
public class RunCalc {

    public static void main(String[] args) {

    	Calc c = new Calc();	//cria uma nova calculadora
    	
    	Scanner in = new Scanner(System.in); 
    	
    	while(in.hasNext()){
    		System.out.println(c.run(in.nextLine()));
    	}
    	in.close();
    	
	}

}
