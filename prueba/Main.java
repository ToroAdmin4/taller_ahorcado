import java.util.ArrayList;
import java.util.List;

public class Main {

    // Colores para la consola (nombres más simples)
    public static String colorNormal = "\u001B[0m";
    public static String colorAzul = "\u001B[36m";
    public static String colorAmarillo = "\u001B[33m";

    // Listas simples para la tabla de récords
    public static List<String> nombresRecords = new ArrayList<>();
    public static List<Integer> victoriasRecords = new ArrayList<>();
    
    public static String jugadorActual = "";

    public static void main(String[] args) {
        List<Palabra> todasLasPalabras = CargadorCSV.cargarPalabras();

        System.out.println(colorAzul + "========================================" + colorNormal);
        System.out.println(colorAzul + "      🕹️  TALLER AHORCADO EN JAVA 🕹️    " + colorNormal);
        System.out.println(colorAzul + "========================================" + colorNormal);

        jugadorActual = ConsoleInput.getString("Ingresa tu nombre: ");
        boolean modoDios = verificarEasterEgg(jugadorActual);

        // Buscar si el jugador ya está en los récords
        boolean existeJugador = false;
        for (int i = 0; i < nombresRecords.size(); i++) {
            String nombre = nombresRecords.get(i);
            if (nombre.equals(jugadorActual)) {
                existeJugador = true;
            }
        }
        
        // Si no existe, lo agregamos con 0 victorias
        if (existeJugador == false) {
            nombresRecords.add(jugadorActual);
            victoriasRecords.add(0);
        }

        int opcion;
        do {
            System.out.println(colorAmarillo + "\n--- MENÚ PRINCIPAL ---" + colorNormal);
            System.out.println("1. Jugar (seleccionar categoría)");
            System.out.println("2. Ver instrucciones");
            System.out.println("3. Tabla de récords");
            System.out.println("4. Salir");
            
            opcion = ConsoleInput.getInt("Selecciona una opción: ");

            if (opcion == 1) {
                jugarMenu(todasLasPalabras, modoDios);
            } else if (opcion == 2) {
                mostrarInstrucciones();
            } else if (opcion == 3) {
                mostrarRecords();
            } else if (opcion == 4) {
                System.out.println("¡Gracias por jugar! ¡Hasta la próxima!");
            } else {
                System.out.println("Opción inválida. Intenta nuevamente.");
            }
            
        } while (opcion != 4);
    }

    public static void jugarMenu(List<Palabra> todasLasPalabras, boolean modoDios) {
        List<String> categorias = new ArrayList<>();
        
        // Extraer las categorías disponibles
        for (int i = 0; i < todasLasPalabras.size(); i++) {
            Palabra p = todasLasPalabras.get(i);
            String cat = p.getCategoria();
            
            // Si la categoría no está en la lista, la agregamos
            boolean yaExiste = false;
            for (int j = 0; j < categorias.size(); j++) {
                if (categorias.get(j).equals(cat)) {
                    yaExiste = true;
                }
            }
            
            if (yaExiste == false) {
                if (cat.equals("SECRETOS")) {
                    if (modoDios == true) {
                        categorias.add(cat);
                    }
                } else {
                    categorias.add(cat);
                }
            }
        }

        if (categorias.size() == 0) {
            System.out.println("No hay categorías disponibles.");
            return;
        }

        System.out.println("\n--- CATEGORÍAS DISPONIBLES ---");
        for (int i = 0; i < categorias.size(); i++) {
            int numeroOpcion = i + 1;
            System.out.println(numeroOpcion + ". " + categorias.get(i));
        }

        int seleccion = ConsoleInput.getInt("Selecciona una categoría (0 para volver): ");
        if (seleccion == 0) {
            return; // Volver al menú
        }

        if (seleccion > 0 && seleccion <= categorias.size()) {
            String categoriaElegida = categorias.get(seleccion - 1);
            
            Juego juego = new Juego();
            boolean victoria = juego.jugar(todasLasPalabras, categoriaElegida, modoDios);
            
            if (victoria == true) {
                // Buscar al jugador y sumarle 1 victoria
                for (int i = 0; i < nombresRecords.size(); i++) {
                    if (nombresRecords.get(i).equals(jugadorActual)) {
                        int victoriasActuales = victoriasRecords.get(i);
                        victoriasRecords.set(i, victoriasActuales + 1);
                    }
                }
            }
            
        } else {
            System.out.println("Categoría inválida.");
        }
    }

    public static boolean verificarEasterEgg(String nombre) {
        if (nombre.equalsIgnoreCase("blessd")) { 
            System.out.println(colorAmarillo + "\n🌟 🌟 🌟 🌟 🌟 🌟 🌟 🌟 🌟 🌟 🌟");
            System.out.println("        ¡MODO DIOS ACTIVADO!      ");
            System.out.println("    Has desbloqueado los secretos ");
            System.out.println("🌟 🌟 🌟 🌟 🌟 🌟 🌟 🌟 🌟 🌟 🌟\n" + colorNormal);
            return true;
        }
        return false;
    }

    public static void mostrarInstrucciones() {
        System.out.println("\n--- INSTRUCCIONES ---");
        System.out.println("El objetivo es adivinar la palabra secreta.");
        System.out.println("En cada turno ingresas una letra.");
        System.out.println("Si la letra está en la palabra, se revelará su posición.");
        System.out.println("Si fallas, se dibujará una parte del ahorcado.");
        System.out.println("Tienes un máximo de 6 errores permitidos.");
        System.out.println("Puedes escribir '*' para pedir una pista a cambio de un intento.");
    }

    public static void mostrarRecords() {
        System.out.println("\n--- TABLA DE RÉCORDS ---");
        if (nombresRecords.size() == 0) {
            System.out.println("No hay récords registrados todavía.");
        } else {
            for (int i = 0; i < nombresRecords.size(); i++) {
                String nombre = nombresRecords.get(i);
                int victorias = victoriasRecords.get(i);
                System.out.println("Jugador: " + nombre + " | Victorias: " + victorias);
            }
        }
    }
}
