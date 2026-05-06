import java.util.ArrayList;
import java.util.List;

public class Juego {

    public String colorRojo = "\u001B[31m";
    public String colorVerde = "\u001B[32m";
    public String colorNormal = "\u001B[0m";

    public boolean jugar(List<Palabra> todasLasPalabras, String categoria, boolean modoDios) {
        Palabra palabraSeleccionada = seleccionarPalabraAleatoria(todasLasPalabras, categoria);
        
        if (palabraSeleccionada == null) {
            System.out.println("No hay palabras en la categoría seleccionada.");
            return false;
        }

        String secreta = palabraSeleccionada.getTexto();
        secreta = secreta.toUpperCase(); 
        
        char[] estado = new char[secreta.length()];
        for (int i = 0; i < estado.length; i++) {
            estado[i] = '_';
        }

        if (modoDios == true) {
            char primeraLetra = secreta.charAt(0);
            validarLetra(primeraLetra, secreta, estado);
            System.out.println("⭐ Modo Dios activado: Se ha revelado la primera letra.");
        }

        int errores = 0;
        int maxErrores = 6;
        boolean victoria = false;

        while (errores < maxErrores && victoria == false) {
            mostrarAhorcado(errores);
            System.out.println();
            mostrarPalabra(estado);
            
            int intentosRestantes = maxErrores - errores;
            System.out.println("\nIntentos restantes: " + intentosRestantes);
            System.out.println("Si deseas una pista, escribe '*' (cuesta 1 intento)");

            char letra = ConsoleInput.getChar("Ingresa una letra: ");

            if (letra == '*') {
                if (errores < maxErrores - 1) {
                    System.out.println("\n💡 PISTA: " + palabraSeleccionada.getPista());
                    errores = errores + 1;
                } else {
                    System.out.println("\n⚠️ No tienes suficientes intentos para pedir una pista.");
                }
                continue; 
            }

            boolean acierto = validarLetra(letra, secreta, estado);

            if (acierto == false) {
                System.out.println("\n❌ La letra '" + letra + "' no está en la palabra.");
                errores = errores + 1;
            } else {
                System.out.println("\n✅ ¡Bien! La letra '" + letra + "' está en la palabra.");
            }

            victoria = estaCompleta(estado);
        }

        mostrarAhorcado(errores);
        System.out.println();
        mostrarPalabra(estado);

        if (victoria == true) {
            System.out.println(colorVerde + "\n🎉 ¡Felicidades! Has adivinado la palabra: " + secreta + colorNormal);
        } else {
            System.out.println(colorRojo + "\n💀 Fin del juego. La palabra era: " + secreta + colorNormal);
        }
        
        return victoria;
    }

    public Palabra seleccionarPalabraAleatoria(List<Palabra> lista, String categoria) {
        List<Palabra> filtradas = new ArrayList<>();
        
        for (int i = 0; i < lista.size(); i++) {
            Palabra p = lista.get(i);
            if (p.getCategoria().equalsIgnoreCase(categoria)) {
                filtradas.add(p);
            }
        }
        
        if (filtradas.size() == 0) {
            return null;
        }

        int indiceAleatorio = (int) (Math.random() * filtradas.size());
        
        return filtradas.get(indiceAleatorio);
    }

    public boolean validarLetra(char letra, String palabra, char[] estado) {
        boolean encontrada = false;
        
        for (int i = 0; i < palabra.length(); i++) {
            char letraEnPalabra = palabra.charAt(i);
            
            if (letraEnPalabra == letra) {
                if (estado[i] == '_') {
                    estado[i] = letra;
                }
                encontrada = true;
            }
        }
        
        return encontrada;
    }

    public void mostrarPalabra(char[] estado) {
        System.out.print("Palabra: ");
        for (int i = 0; i < estado.length; i++) {
            char c = estado[i];
            System.out.print(c + " ");
        }
        System.out.println();
    }

    public boolean estaCompleta(char[] estado) {
        for (int i = 0; i < estado.length; i++) {
            char c = estado[i];
            if (c == '_') {
                return false; 
            }
        }
        return true; 
    }

    public void mostrarAhorcado(int errores) {
        String[] ahorcado = {
            "  +---+\n  |   |\n      |\n      |\n      |\n      |\n=========\n",
            "  +---+\n  |   |\n  O   |\n      |\n      |\n      |\n=========\n",
            "  +---+\n  |   |\n  O   |\n  |   |\n      |\n      |\n=========\n",
            "  +---+\n  |   |\n  O   |\n /|   |\n      |\n      |\n=========\n",
            "  +---+\n  |   |\n  O   |\n /|\\  |\n      |\n      |\n=========\n",
            "  +---+\n  |   |\n  O   |\n /|\\  |\n /    |\n      |\n=========\n",
            "  +---+\n  |   |\n  O   |\n /|\\  |\n / \\  |\n      |\n=========\n"
        };
        
        if (errores >= 0 && errores <= 6) {
            System.out.print(ahorcado[errores]);
        }
    }
}
