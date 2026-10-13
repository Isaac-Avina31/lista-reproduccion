package mx.unam.fesa.ico;
import java.util.Scanner;

public class Menu {

    private final ListaReproduccion lista;
    private final Scanner scanner;
    private boolean finEntrada;

    public Menu() {
        this.lista = new ListaReproduccion();
        this.scanner = new Scanner(System.in);
        this.finEntrada = false;
    }

    public void iniciar() {
        boolean salir = false;
        while (!salir && !finEntrada) {
            mostrarMenu();
            String linea = leerLinea("Elige una opción: ");
            if (linea == null) {
                break;
            }
            Integer opcion = parsearEntero(linea);
            if (opcion == null || opcion < 1 || opcion > 13) {
                System.out.println("Opción inválida. Escribe un número del 1 al 13.");
                continue;
            }
            salir = ejecutarOpcion(opcion.intValue());
        }
        if (finEntrada && !salir) {
            System.out.println();
            System.out.println("Se terminó la entrada. Programa finalizado.");
        }
        scanner.close();
    }

    static Integer parsearEntero(String texto) {
        if (texto == null) {
            return null;
        }
        String limpio = texto.trim();
        if (limpio.isEmpty()) {
            return null;
        }
        try {
            return Integer.valueOf(Integer.parseInt(limpio));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private void mostrarMenu() {
        System.out.println();
        System.out.println("===== GESTOR DE LISTAS DE REPRODUCCIÓN =====");
        System.out.println(" 1. Agregar canción al inicio");
        System.out.println(" 2. Agregar canción al final");
        System.out.println(" 3. Buscar canción por ID");
        System.out.println(" 4. Seleccionar canción por ID (canción actual)");
        System.out.println(" 5. Eliminar canción por ID");
        System.out.println(" 6. Eliminar la canción actual");
        System.out.println(" 7. Consultar la canción actual");
        System.out.println(" 8. Avanzar a la siguiente");
        System.out.println(" 9. Retroceder a la anterior");
        System.out.println("10. Mostrar la lista completa en ambos sentidos");
        System.out.println("11. Consultar la cantidad de canciones");
        System.out.println("12. Simular reproducción de k canciones");
        System.out.println("13. Salir");
    }

    private boolean ejecutarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                agregarCancion(true);
                break;
            case 2:
                agregarCancion(false);
                break;
            case 3:
                buscarCancion();
                break;
            case 4:
                seleccionarCancion();
                break;
            case 5:
                eliminarPorId();
                break;
            case 6:
                eliminarActual();
                break;
            case 7:
                consultarActual();
                break;
            case 8:
                avanzar();
                break;
            case 9:
                retroceder();
                break;
            case 10:
                mostrarLista();
                break;
            case 11:
                consultarCantidad();
                break;
            case 12:
                simularReproduccion();
                break;
            case 13:
                System.out.println("¡Hasta luego!");
                return true;
            default:
                System.out.println("Opción inválida. Escribe un número del 1 al 13.");
                break;
        }
        return false;
    }

    private String leerLinea(String mensaje) {
        System.out.print(mensaje);
        if (!scanner.hasNextLine()) {
            finEntrada = true;
            return null;
        }
        return scanner.nextLine();
    }

    private Integer leerEntero(String mensaje, String mensajeError) {
        String texto = leerLinea(mensaje);
        if (texto == null) {
            return null;
        }
        Integer valor = parsearEntero(texto);
        if (valor == null) {
            System.out.println(mensajeError);
        }
        return valor;
    }

    private void agregarCancion(boolean alInicio) {
        String titulo = leerLinea("Título: ");
        if (titulo == null) {
            return;
        }
        titulo = titulo.trim();
        if (titulo.isEmpty()) {
            System.out.println("El título no puede estar vacío.");
            return;
        }

        String artista = leerLinea("Artista: ");
        if (artista == null) {
            return;
        }
        artista = artista.trim();
        if (artista.isEmpty()) {
            System.out.println("El artista no puede estar vacío.");
            return;
        }

        Integer duracion = leerEntero("Duración en segundos: ",
                "Duración inválida: debe ser un número entero.");
        if (duracion == null) {
            return;
        }
        if (duracion.intValue() <= 0) {
            System.out.println("Duración inválida: debe ser mayor que 0.");
            return;
        }

        try {
            Cancion creada;
            if (alInicio) {
                creada = lista.agregarAlInicio(titulo, artista, duracion.intValue());
            } else {
                creada = lista.agregarAlFinal(titulo, artista, duracion.intValue());
            }
            System.out.println("Canción agregada: " + creada);
        } catch (IllegalArgumentException e) {
            System.out.println("No se pudo agregar la canción: " + e.getMessage());
        }
    }

    private void buscarCancion() {
        Integer id = leerEntero("ID de la canción: ", "ID inválido: debe ser un número entero.");
        if (id == null) {
            return;
        }
        Cancion c = lista.buscarPorId(id.intValue());
        if (c == null) {
            System.out.println("No existe una canción con el ID " + id + ".");
        } else {
            System.out.println("Canción encontrada: " + c);
        }
    }

    private void seleccionarCancion() {
        Integer id = leerEntero("ID de la canción: ", "ID inválido: debe ser un número entero.");
        if (id == null) {
            return;
        }
        if (lista.seleccionarPorId(id.intValue())) {
            System.out.println("Canción actual: " + lista.consultarActual());
        } else {
            System.out.println("No existe una canción con el ID " + id + ".");
        }
    }

    private void eliminarPorId() {
        if (lista.estaVacia()) {
            System.out.println("La lista está vacía.");
            return;
        }
        Integer id = leerEntero("ID de la canción: ", "ID inválido: debe ser un número entero.");
        if (id == null) {
            return;
        }
        if (lista.eliminarPorId(id.intValue())) {
            System.out.println("Canción eliminada.");
            if (lista.estaVacia()) {
                System.out.println("La lista quedó vacía.");
            }
        } else {
            System.out.println("No existe una canción con el ID " + id + ".");
        }
    }

    private void eliminarActual() {
        Cancion eliminada = lista.eliminarActual();
        if (eliminada == null) {
            System.out.println("La lista está vacía.");
            return;
        }
        System.out.println("Canción eliminada: " + eliminada);
        Cancion nueva = lista.consultarActual();
        if (nueva == null) {
            System.out.println("La lista quedó vacía.");
        } else {
            System.out.println("Nueva canción actual: " + nueva);
        }
    }

    private void consultarActual() {
        Cancion actual = lista.consultarActual();
        if (actual == null) {
            System.out.println("La lista está vacía.");
        } else {
            System.out.println("Canción actual: " + actual);
        }
    }

    private void avanzar() {
        Cancion nueva = lista.siguiente();
        if (nueva == null) {
            System.out.println("La lista está vacía.");
        } else {
            System.out.println("Canción actual: " + nueva);
        }
    }

    private void retroceder() {
        Cancion nueva = lista.anterior();
        if (nueva == null) {
            System.out.println("La lista está vacía.");
        } else {
            System.out.println("Canción actual: " + nueva);
        }
    }

    private void mostrarLista() {
        if (lista.estaVacia()) {
            System.out.println("La lista está vacía.");
            return;
        }
        System.out.println("--- Lista hacia adelante ---");
        lista.mostrarAdelante();
        System.out.println("--- Lista hacia atrás ---");
        lista.mostrarAtras();
    }

    private void consultarCantidad() {
        System.out.println("Cantidad de canciones: " + lista.getTamanio());
    }

    private void simularReproduccion() {
        if (lista.estaVacia()) {
            System.out.println("La lista está vacía. No hay canciones para reproducir.");
            return;
        }
        Integer k = leerEntero("¿Cuántas canciones reproducir (k)? ",
                "Valor inválido: k debe ser un número entero.");
        if (k == null) {
            return;
        }
        if (k.intValue() <= 0) {
            System.out.println("Valor inválido: k debe ser mayor que 0.");
            return;
        }
        try {
            lista.reproducir(k.intValue());
        } catch (IllegalArgumentException e) {
            System.out.println("No se pudo reproducir: " + e.getMessage());
        }
    }
}