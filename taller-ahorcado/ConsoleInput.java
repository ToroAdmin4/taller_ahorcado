import java.util.Scanner;

public class ConsoleInput {
    
    public static Scanner scanner = new Scanner(System.in);

    public static String getString(String prompt) {
        System.out.print(prompt);
        String texto = scanner.nextLine();
        texto = texto.trim();
        return texto;
    }

    public static int getInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String texto = scanner.nextLine();
            texto = texto.trim();
            
            try {
                int valor = Integer.parseInt(texto);
                return valor;
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido.");
            }
        }
    }

    public static char getChar(String prompt) {
        while (true) {
            System.out.print(prompt);
            String texto = scanner.nextLine();
            texto = texto.trim();
            texto = texto.toUpperCase(); 
            
            if (texto.length() > 0) {
                if (texto.length() > 1 && texto.equals("*") == false) {
                    char primeraLetra = texto.charAt(0);
                    System.out.println("⚠️ Ingresaste varias letras. Se tomará solo la primera: '" + primeraLetra + "'");
                }
                
                char primeraLetra = texto.charAt(0);
                return primeraLetra;
            }
            
            System.out.println("Entrada vacía. Intente nuevamente.");
        }
    }
}
