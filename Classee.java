package nome;
import java.util.*;
class Classee {
	public static Scanner sc = new Scanner(System.in);
	public static void main (String args[]) {
		int num1, num2, soma; 
		System.out.println("Digite um número: ");
		num1 = sc.nextInt();
		System.out.println("Digite outro número: ");
		num2 = sc.nextInt();
		soma = num1 + num2;
		System.out.println("Soma = " + soma);
	}
	public static Scanner getSc() {
		return sc;
	}
	public static void setSc(Scanner sc) {
		Classee.sc = sc;
	}
}
