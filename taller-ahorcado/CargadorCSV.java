import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CargadorCSV {
    public static List<Palabra> cargarPalabras() {
        List<Palabra> lista = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("palabras.csv"));
            String linea = br.readLine(); 
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length >= 3) {
                    lista.add(new Palabra(partes[0].trim(), partes[1].trim(), partes[2].trim()));
                }
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error al cargar el archivo de palabras: " + e.getMessage());
        }
        return lista;
    }
}
