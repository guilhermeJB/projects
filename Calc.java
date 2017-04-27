import java.text.NumberFormat;
import java.util.EmptyStackException;
import java.util.Stack;

/**
 * Esta classe cria uma Calculadora que usa a notacao RPN
 * 
 * @author Guilherme Bernardo, n. 49504
 *
 */
public class Calc {

	//Atributos da Calculadora
	private Stack<Double> s;
	private NumberFormat nf;
	private String infinito= "Erro arithmético: o resultado é infinito.";


	/*
	 * Constroi uma Calculadora que use a notacao RPN
	 */
	public Calc(){
		s = new Stack<>();
		nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(4);
		nf.setMinimumFractionDigits(4);
	}

	/**
	 * Metodo que corre a calculadora
	 * 
	 * @param input Linha de texto com as operacoes a efetuar
	 * @return caso nao haja excecoes, retorna o topo da pilha, caso contrario
	 * 			retorna a mensagem da excecao.
	 * @requires input != null
	 */
	public String run(String input){

		for(String token : input.split("\\s*[ ]\\s*")){  

			if(isNumeric(token)){					//verifica se a string e um numero

				s.push(Double.parseDouble(token));  //Coloca o numero na pilha

			}else if(isOperand(token)){				//verifica se a string e um operando

				try{
					verifyOp(token);				//verifica qual o operando a usar
				}catch(EmptyStackException e){
					return "Número de argumentos insuficiente.";

				}catch(Exception e){
					return e.getMessage();			//retorna a mensagem da excecao
				}

			}else{
				return "Erro: o comando nao"		//caso nao seja nem numero nem op
						+ " é valido (" + token + ").";
			}
		}

		try {
			return nf.format(s.peek());				//retorna o numero no topo da pilha

		} catch (EmptyStackException e) {
			return "Erro: a pilha está vazia!";		//caso a pilha esteja vazia
		}
	}

	/**
	 * Soma dois operandos presentes na pilha. Apos a soma
	 * 			coloca o resultado na pilha.
	 */
	private void add(){

		double last = s.pop();			//retira o segundo numero da pilha
		double first = s.pop();			//retira o primeiro numero da pilha

		s.push(first + last);			//coloca o resultado na pilha
	}

	/**
	 * Subtrai dois operandos presentes na pilha. Apos a subtracao
	 *  		coloca o resultado na pilha.
	 */
	private void sub(){

		double last = s.pop();			//retira o segundo numero da pilha
		double first = s.pop();			//retira o primeiro numero da pilha

		s.push(first - last);			//coloca o resultado na pilha
	}

	/**
	 * Multiplica dois operandos presentes na pilha. Apos a multiplicacao
	 * 			 coloca o resultado na pilha.
	 */
	private void mul(){

		double last = s.pop();			//retira o segundo numero da pilha
		double first = s.pop();			//retira o primeiro numero da pilha

		s.push(first * last);			//coloca o resultado na pilha
	}

	/**
	 * Divide dois operandos presentes na pilha. Apos a divisao
	 * 			 coloca o resultado na pilha.
	 * @throws Exception 
	 */
	private void div() throws Exception{

		double last = s.pop();			//retira o segundo numero da pilha
		double first = s.pop();			//retira o primeiro numero da pilha

		double result = first / last;

		if(Double.isInfinite(result) ||			//caso o resultado seja infinito
				result > Double.MAX_VALUE){
			throw new Exception(infinito);

		}else{
			s.push(result);				//coloca o resultado na pilha
		}
	}

	/**
	 * Faz a tangente de um argumento presente na pilha. Apos efetuar
	 * 		a operacao coloca o resultado na pilha.
	 * @throws Exception
	 */
	private void tan() throws Exception{

		double number = s.pop();				//retira o numero da pilha
		double result = Math.tan(number);		//realiza a operacao

		if(Double.isNaN(result)){				//caso o resultado nao seja um numero
			throw new Exception("Erro arithmético: "
					+ "tangente com argumento inválido.");

		}else{
			s.push(result);						//coloca o resultado na pilha
		}
	}

	/**
	 * Faz a cotangente de um argumento presente na pilha. Apos efetuar
	 * 		a operacao coloca o resultado na pilha.
	 * @throws Exception
	 */
	private void cotg() throws Exception{

		double number = s.pop();				//retira o numero da pilha
		double result = 1.0/Math.tan(number);	//realiza a operacao

		if(Double.isNaN(result)){				//caso o resultado nao seja um numero
			throw new Exception("Erro arithmético: cotangente"
					+ " com argumento inválido.");

		}else{
			s.push(result);						//coloca o resultado na pilha
		}
	}


	/**
	 * Realiza a y-esima potencia de x (x^y). Coloca o resultado da operacao na pilha.
	 * 
	 * @throws Exception
	 */
	private void elev() throws Exception{

		double y = s.pop();					//retira o expoente da operacao da pilha
		double x = s.pop();					//retira a base da operacao da pilha
		double result = Math.pow(x, y);		//realiza a operacao

		if(Double.isInfinite(result) 		//caso seja infinito
				|| result > Double.MAX_VALUE){
			throw new Exception(infinito);

		}else if(Double.isNaN(result)){		//caso nao seja um numero
			throw new Exception("Erro arithmético: uso de argumentos inválidos.");

		}else{
			s.push(result);					//coloca o resultado na pilha
		}
	}


	/**
	 * Realiza a y-esima potencia do numero de Euler, e. Coloca o resultado na pilha.
	 * 
	 * @throws Exception
	 */
	private void e() throws Exception{

		double y = s.pop();				//retira o expoente a usar da pilha
		double result = Math.exp(y);	//realiza a operacao

		if(Double.isNaN(result)){		//caso nao seja um numero
			throw new Exception("Erro arithmético: expoente inválido.");

		}else if(Double.isInfinite(result) 	//caso seja infinito
				|| result > Double.MAX_VALUE){
			throw new Exception(infinito);

		}else{
			s.push(result);				//coloca o resultado na pilha
		}
	}

	/**
	 * Realiza o logaritmo do ultimo argumento colocado na pilha. Coloca o resultado na pilha.
	 * 
	 * @throws Exception
	 */
	private void ln() throws Exception{

		double number = s.pop();			//retira o numero a usar da pilha
		double result = Math.log(number);	//realiza a operacao

		if(Double.isNaN(result)){			//caso nao seja um numero
			throw new Exception("Erro arithmético: logaritmo com argumento negativo.");

		}else if(Double.isInfinite(result)){	//caso seja infinito
			throw new Exception(infinito);
		}else{
			s.push(result);					//coloca o resultado na pilha
		}
	}


	/**
	 * Coloca o inverso do ultimo numero passado na pilha.
	 */
	private void mudSinal(){

		double number = s.pop();		//retira o numero da pilha

		s.push(- number);				//coloca na pilha o inverso do numero
	}


	/**
	 * Muda o numero de casas decimais para o valor do ultimo argumento passado.
	 */
	private void dec(){

		int number = s.pop().intValue();		//retira da pilha o numero a usar

		nf.setMaximumFractionDigits(number);	//minimo de casas decimais
		nf.setMinimumFractionDigits(number);	//maximo de casas decimais
	}


	/**
	 * Verifica qual a operacao a usar 
	 * 
	 * @param in Operador passado pelo utilizador
	 * @throws Exception
	 * @requires in != null
	 */
	private void verifyOp(String in) throws Exception{

		switch(in){
		case "+": add(); break;

		case "-": sub(); break;

		case "*": mul(); break;

		case "/": div(); break;

		case "#": mudSinal(); break;

		case "^": elev(); break;

		case "tan": tan(); break;

		case "cotg": cotg(); break;

		case "e": e(); break;

		case "ln": ln(); break;

		case "dec": dec(); break;

		default:
			break;
		}

	}

	/**
	 * Verifica se uma dada string eh um double.
	 * 
	 * @param in Texto passado pelo utilizador
	 * @return true se in e um double; false c.c.
	 * @requires in != null
	 */
	private boolean isNumeric(String in){

		try {
			Double numero = Double.parseDouble(in);	//tenta converter a string em Double

		} catch (NumberFormatException e) {
			return false;

		}
		return true;
	}

	/**
	 * Verifica se uma dada string eh um operando
	 * 
	 * @param in Texto passado pelo utilizador
	 * @return true se in e um operando, false c.c.
	 * @requires in != null
	 */
	private boolean isOperand(String in){

		switch(in){

		case "+": case "-": case "*": case "/": case "#": case "^":
		case "tan": case "cotg": case "e": case "ln": case "dec":
			return true;

		default:
			return false;
		}
	}

}
