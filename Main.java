import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        for (int i = 1; i <= 5; i++) {
            System.out.println(i);
        }
        System.out.println("Input number: ");
        int inputNumber = scanner.nextInt();
        for (int i = 6; i <= 10; i++) {
            System.out.println(i + inputNumber);
        }
        scanner.close();
    }
}