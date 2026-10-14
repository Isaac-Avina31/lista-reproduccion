package mx.unam.fesa.ico;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class PruebasLista {

    private static int total = 0;
    private static int pass = 0;
    private static int fail = 0;

    private interface Prueba {
        void ejecutar() throws Exception;
    }

    public static void main(String[] args) {
        System.out.println("########## PRUEBAS DE LA LISTA DE REPRODUCCIÓN ##########");

        correr("Operaciones sobre lista vacía", PruebasLista::listaVacia);
        correr("Inserción de la primera canción (al final)", PruebasLista::primeraAlFinal);
        correr("Inserción de la primera canción (al inicio)", PruebasLista::primeraAlInicio);
        correr("Navegación en ambos sentidos con un solo nodo", PruebasLista::unSoloNodo);
        correr("Inserciones al inicio y al final", PruebasLista::insercionesInicioFinal);
        correr("Recorrido con 5 canciones (mostrar y navegar)", PruebasLista::recorridoCincoCanciones);
        correr("Avance desde la última hacia la primera", PruebasLista::avanceUltimaPrimera);
        correr("Retroceso desde la primera hacia la última", PruebasLista::retrocesoPrimeraUltima);
        correr("Eliminación del primer nodo", PruebasLista::eliminarPrimero);
        correr("Eliminación del último nodo", PruebasLista::eliminarUltimo);
        correr("Eliminación de un nodo intermedio", PruebasLista::eliminarIntermedio);
        correr("Eliminación de la actual (nueva actual = la siguiente)", PruebasLista::eliminarActualIntermedia);
        correr("Eliminación de la actual cuando es la última (circular)", PruebasLista::eliminarActualUltima);
        correr("Eliminación de una canción distinta de la actual", PruebasLista::eliminarDistintaDeActual);
        correr("Eliminación de la única canción", PruebasLista::eliminarUnica);
        correr("Búsqueda y eliminación de ID inexistente", PruebasLista::idInexistente);
        correr("Reproducción simulada con k > tamaño", PruebasLista::reproducirKMayor);
        correr("Reproducción simulada con k múltiplo del tamaño", PruebasLista::reproducirKMultiplo);

        correr("Entrada inválida: duración 0 (al inicio)", () -> agregarInvalido(true, "Titulo", "Artista", 0));
        correr("Entrada inválida: duración 0 (al final)", () -> agregarInvalido(false, "Titulo", "Artista", 0));
        correr("Entrada inválida: duración negativa (al inicio)", () -> agregarInvalido(true, "Titulo", "Artista", -5));
        correr("Entrada inválida: duración negativa (al final)", () -> agregarInvalido(false, "Titulo", "Artista", -5));
        correr("Entrada inválida: título vacío (al inicio)", () -> agregarInvalido(true, "", "Artista", 100));
        correr("Entrada inválida: título vacío (al final)", () -> agregarInvalido(false, "", "Artista", 100));
        correr("Entrada inválida: artista vacío (al inicio)", () -> agregarInvalido(true, "Titulo", "", 100));
        correr("Entrada inválida: artista vacío (al final)", () -> agregarInvalido(false, "Titulo", "", 100));
        correr("Entrada inválida: reproducir(0)", () -> reproducirInvalido(0));
        correr("Entrada inválida: reproducir(-3)", () -> reproducirInvalido(-3));

        correr("Menu.parsearEntero(\"abc\")", () -> parseo("abc", null));
        correr("Menu.parsearEntero(\"\")", () -> parseo("", null));
        correr("Menu.parsearEntero(null)", () -> parseo(null, null));
        correr("Menu.parsearEntero(\"  \")", () -> parseo("  ", null));
        correr("Menu.parsearEntero(\"12\")", () -> parseo("12", Integer.valueOf(12)));

        System.out.println();
        System.out.println("########## RESUMEN ##########");
        System.out.println("Total de pruebas: " + total);
        System.out.println("PASS: " + pass);
        System.out.println("FAIL: " + fail);
    }

    private static void correr(String nombre, Prueba prueba) {
        try {
            prueba.ejecutar();
        } catch (Throwable t) {
            registrar(nombre, "(la prueba no pudo completarse)", "(ver excepción)",
                    "Sin excepciones inesperadas", "Excepción inesperada: " + t);
        }
    }

    private static void registrar(String nombre, String estadoInicial, String operacion,
                                  String esperado, String obtenido) {
        total++;
        boolean ok = esperado.equals(obtenido);
        if (ok) {
            pass++;
        } else {
            fail++;
        }
        System.out.println();
        System.out.println("=== Prueba " + total + ": " + nombre + " ===");
        System.out.println("Estado inicial:      " + estadoInicial);
        System.out.println("Operación:           " + operacion);
        System.out.println("Resultado esperado:  " + esperado);
        System.out.println("Resultado obtenido:  " + obtenido);
        System.out.println("Resultado:           " + (ok ? "PASS" : "FAIL"));
    }

    private static ListaReproduccion conCanciones(int n) {
        ListaReproduccion l = new ListaReproduccion();
        for (int i = 1; i <= n; i++) {
            l.agregarAlFinal("Cancion " + i, "Artista " + i, 100 + i);
        }
        return l;
    }

    private static String id(Cancion c) {
        return c == null ? "null" : String.valueOf(c.getId());
    }

    private static String adelante(ListaReproduccion l) {
        StringBuilder sb = new StringBuilder();
        int n = l.getTamanio();
        for (int i = 0; i < n; i++) {
            sb.append(l.consultarActual().getId());
            if (i < n - 1) {
                sb.append(",");
            }
            l.siguiente();
        }
        return sb.toString();
    }

    private static String atras(ListaReproduccion l) {
        StringBuilder sb = new StringBuilder();
        int n = l.getTamanio();
        for (int i = 0; i < n; i++) {
            sb.append(l.consultarActual().getId());
            if (i < n - 1) {
                sb.append(",");
            }
            l.anterior();
        }
        return sb.toString();
    }

    private static String resumen(ListaReproduccion l) {
        return "tam=" + l.getTamanio()
                + ", consistente=" + l.verificarConsistencia()
                + ", actual=" + id(l.consultarActual())
                + ", adelante=[" + adelante(l) + "]"
                + ", atras=[" + atras(l) + "]";
    }

    private static String esp(int tam, String actual, String adelante, String atras) {
        return "tam=" + tam + ", consistente=true, actual=" + actual
                + ", adelante=[" + adelante + "], atras=[" + atras + "]";
    }
    private static String capturar(Runnable accion) {
        PrintStream original = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buffer, true));
        try {
            accion.run();
        } finally {
            System.setOut(original);
        }
        return buffer.toString();
    }

    private static String extraerIds(String texto) {
        StringBuilder sb = new StringBuilder();
        int pos = 0;
        while (true) {
            int ini = texto.indexOf("[ID ", pos);
            if (ini < 0) {
                break;
            }
            int fin = texto.indexOf(']', ini);
            if (fin < 0) {
                break;
            }
            if (sb.length() > 0) {
                sb.append(",");
            }
            sb.append(texto.substring(ini + 4, fin).trim());
            pos = fin + 1;
        }
        return sb.toString();
    }

    private static int contar(String texto, String buscado) {
        int cuenta = 0;
        int pos = 0;
        while ((pos = texto.indexOf(buscado, pos)) >= 0) {
            cuenta++;
            pos += buscado.length();
        }
        return cuenta;
    }

    private static void listaVacia() {
        final ListaReproduccion l = new ListaReproduccion();
        String obtenido;
        try {
            String r1 = id(l.consultarActual());
            String r2 = id(l.siguiente());
            String r3 = id(l.anterior());
            String r4 = id(l.buscarPorId(1));
            boolean r5 = l.seleccionarPorId(1);
            boolean r6 = l.eliminarPorId(1);
            String r7 = id(l.eliminarActual());
            String salidaAdelante = capturar(new Runnable() {
                public void run() { l.mostrarAdelante(); }
            });
            capturar(new Runnable() {
                public void run() { l.mostrarAtras(); }
            });
            capturar(new Runnable() {
                public void run() { l.reproducir(3); }
            });
            obtenido = "consultarActual=" + r1 + ", siguiente=" + r2 + ", anterior=" + r3
                    + ", buscarPorId=" + r4 + ", seleccionarPorId=" + r5
                    + ", eliminarPorId=" + r6 + ", eliminarActual=" + r7
                    + ", mostrarAdelante dice vacía=" + salidaAdelante.contains("La lista está vacía.")
                    + ", mostrarAtras y reproducir sin fallar, " + resumen(l);
        } catch (Exception e) {
            obtenido = "Excepción inesperada: " + e;
        }
        String esperado = "consultarActual=null, siguiente=null, anterior=null, buscarPorId=null,"
                + " seleccionarPorId=false, eliminarPorId=false, eliminarActual=null,"
                + " mostrarAdelante dice vacía=true, mostrarAtras y reproducir sin fallar, "
                + esp(0, "null", "", "");
        registrar("Operaciones sobre lista vacía",
                "Lista nueva, tam=0",
                "consultarActual, siguiente, anterior, buscarPorId(1), seleccionarPorId(1), eliminarPorId(1),"
                        + " eliminarActual, mostrarAdelante, mostrarAtras, reproducir(3)",
                esperado, obtenido);
    }

    private static void primeraAlFinal() {
        ListaReproduccion l = new ListaReproduccion();
        Cancion c = l.agregarAlFinal("Cancion 1", "Artista 1", 100);
        registrar("Inserción de la primera canción (al final)",
                "Lista nueva, tam=0",
                "agregarAlFinal(\"Cancion 1\", \"Artista 1\", 100)",
                "id=1, " + esp(1, "1", "1", "1"),
                "id=" + id(c) + ", " + resumen(l));
    }

    private static void primeraAlInicio() {
        ListaReproduccion l = new ListaReproduccion();
        Cancion c = l.agregarAlInicio("Cancion 1", "Artista 1", 100);
        registrar("Inserción de la primera canción (al inicio)",
                "Lista nueva, tam=0",
                "agregarAlInicio(\"Cancion 1\", \"Artista 1\", 100)",
                "id=1, " + esp(1, "1", "1", "1"),
                "id=" + id(c) + ", " + resumen(l));
    }

    private static void unSoloNodo() {
        ListaReproduccion l = conCanciones(1);
        String sig = id(l.siguiente());
        String ant = id(l.anterior());
        registrar("Navegación en ambos sentidos con un solo nodo",
                "Lista con 1 canción (ID 1), actual=1",
                "siguiente() y luego anterior()",
                "siguiente=1, anterior=1, " + esp(1, "1", "1", "1"),
                "siguiente=" + sig + ", anterior=" + ant + ", " + resumen(l));
    }

    private static void insercionesInicioFinal() {
        ListaReproduccion l = new ListaReproduccion();
        l.agregarAlFinal("A", "X", 100);
        l.agregarAlInicio("B", "X", 100);
        l.agregarAlFinal("C", "X", 100);
        l.agregarAlInicio("D", "X", 100);
        registrar("Inserciones al inicio y al final",
                "Lista nueva, tam=0",
                "agregarAlFinal(A), agregarAlInicio(B), agregarAlFinal(C), agregarAlInicio(D)"
                        + " => orden inicio->fin 4,2,1,3",
                esp(4, "1", "1,3,4,2", "1,2,4,3"),
                resumen(l));
    }

    private static void recorridoCincoCanciones() {
        final ListaReproduccion l = conCanciones(5);
        String adelanteMostrado = extraerIds(capturar(new Runnable() {
            public void run() { l.mostrarAdelante(); }
        }));
        String atrasMostrado = extraerIds(capturar(new Runnable() {
            public void run() { l.mostrarAtras(); }
        }));
        registrar("Recorrido con 5 canciones (mostrar y navegar)",
                "Lista con 5 canciones (IDs 1..5), actual=1",
                "mostrarAdelante(), mostrarAtras() y recorrido con siguiente()/anterior()",
                "mostrarAdelante=1,2,3,4,5, mostrarAtras=5,4,3,2,1, "
                        + esp(5, "1", "1,2,3,4,5", "1,5,4,3,2"),
                "mostrarAdelante=" + adelanteMostrado + ", mostrarAtras=" + atrasMostrado
                        + ", " + resumen(l));
    }

    private static void avanceUltimaPrimera() {
        ListaReproduccion l = conCanciones(3);
        l.seleccionarPorId(3);
        String r = id(l.siguiente());
        registrar("Avance desde la última hacia la primera",
                "Lista [1,2,3], actual=3 (la última)",
                "siguiente()",
                "siguiente=1, " + esp(3, "1", "1,2,3", "1,3,2"),
                "siguiente=" + r + ", " + resumen(l));
    }

    private static void retrocesoPrimeraUltima() {
        ListaReproduccion l = conCanciones(3);
        String r = id(l.anterior());
        registrar("Retroceso desde la primera hacia la última",
                "Lista [1,2,3], actual=1 (la primera)",
                "anterior()",
                "anterior=3, " + esp(3, "3", "3,1,2", "3,2,1"),
                "anterior=" + r + ", " + resumen(l));
    }

    private static void eliminarPrimero() {
        ListaReproduccion l = conCanciones(5);
        l.seleccionarPorId(3);
        boolean r = l.eliminarPorId(1);
        registrar("Eliminación del primer nodo",
                "Lista [1,2,3,4,5], actual=3",
                "eliminarPorId(1)",
                "eliminada=true, " + esp(4, "3", "3,4,5,2", "3,2,5,4"),
                "eliminada=" + r + ", " + resumen(l));
    }

    private static void eliminarUltimo() {
        ListaReproduccion l = conCanciones(5);
        l.seleccionarPorId(3);
        boolean r = l.eliminarPorId(5);
        registrar("Eliminación del último nodo",
                "Lista [1,2,3,4,5], actual=3",
                "eliminarPorId(5)",
                "eliminada=true, " + esp(4, "3", "3,4,1,2", "3,2,1,4"),
                "eliminada=" + r + ", " + resumen(l));
    }

    private static void eliminarIntermedio() {
        ListaReproduccion l = conCanciones(5);
        boolean r = l.eliminarPorId(3);
        registrar("Eliminación de un nodo intermedio",
                "Lista [1,2,3,4,5], actual=1",
                "eliminarPorId(3)",
                "eliminada=true, " + esp(4, "1", "1,2,4,5", "1,5,4,2"),
                "eliminada=" + r + ", " + resumen(l));
    }

    private static void eliminarActualIntermedia() {
        ListaReproduccion l = conCanciones(4);
        l.seleccionarPorId(2);
        Cancion eliminada = l.eliminarActual();
        registrar("Eliminación de la actual (nueva actual = la siguiente)",
                "Lista [1,2,3,4], actual=2",
                "eliminarActual()",
                "eliminada=2, " + esp(3, "3", "3,4,1", "3,1,4"),
                "eliminada=" + id(eliminada) + ", " + resumen(l));
    }

    private static void eliminarActualUltima() {
        ListaReproduccion l = conCanciones(4);
        l.seleccionarPorId(4);
        Cancion eliminada = l.eliminarActual();
        registrar("Eliminación de la actual cuando es la última (circular)",
                "Lista [1,2,3,4], actual=4 (la última)",
                "eliminarActual()",
                "eliminada=4, " + esp(3, "1", "1,2,3", "1,3,2"),
                "eliminada=" + id(eliminada) + ", " + resumen(l));
    }

    private static void eliminarDistintaDeActual() {
        ListaReproduccion l = conCanciones(4);
        boolean r = l.eliminarPorId(3);
        registrar("Eliminación de una canción distinta de la actual",
                "Lista [1,2,3,4], actual=1",
                "eliminarPorId(3)",
                "eliminada=true, " + esp(3, "1", "1,2,4", "1,4,2"),
                "eliminada=" + r + ", " + resumen(l));
    }

    private static void eliminarUnica() {
        ListaReproduccion l = conCanciones(1);
        Cancion eliminada = l.eliminarActual();
        registrar("Eliminación de la única canción",
                "Lista con 1 canción (ID 1), actual=1",
                "eliminarActual()",
                "eliminada=1, " + esp(0, "null", "", ""),
                "eliminada=" + id(eliminada) + ", " + resumen(l));
    }

    private static void idInexistente() {
        ListaReproduccion l = conCanciones(3);
        String buscada = id(l.buscarPorId(99));
        boolean seleccion = l.seleccionarPorId(99);
        boolean elimino99 = l.eliminarPorId(99);
        boolean elimino0 = l.eliminarPorId(0);
        boolean eliminoNeg = l.eliminarPorId(-1);
        registrar("Búsqueda y eliminación de un ID inexistente",
                "Lista [1,2,3], actual=1",
                "buscarPorId(99), seleccionarPorId(99), eliminarPorId(99), eliminarPorId(0), eliminarPorId(-1)",
                "buscar=null, seleccionar=false, eliminar99=false, eliminar0=false, eliminarNeg=false, "
                        + esp(3, "1", "1,2,3", "1,3,2"),
                "buscar=" + buscada + ", seleccionar=" + seleccion + ", eliminar99=" + elimino99
                        + ", eliminar0=" + elimino0 + ", eliminarNeg=" + eliminoNeg + ", " + resumen(l));
    }

    private static void reproducirKMayor() {
        final ListaReproduccion l = conCanciones(4);
        l.seleccionarPorId(2);
        String salida = capturar(new Runnable() {
            public void run() { l.reproducir(10); }
        });
        registrar("Reproducción simulada con k > tamaño",
                "Lista [1,2,3,4], actual=2 (posición 1)",
                "reproducir(10)  => actual final en posición (1+10)%4=3, es decir ID 4",
                "reproducciones=10, " + esp(4, "4", "4,1,2,3", "4,3,2,1"),
                "reproducciones=" + contar(salida, "Reproduciendo:") + ", " + resumen(l));
    }

    private static void reproducirKMultiplo() {
        final ListaReproduccion l = conCanciones(4);
        l.seleccionarPorId(2);
        String salida = capturar(new Runnable() {
            public void run() { l.reproducir(8); }
        });
        registrar("Reproducción simulada con k múltiplo del tamaño",
                "Lista [1,2,3,4], actual=2 (posición 1)",
                "reproducir(8)  => actual final en posición (1+8)%4=1, es decir ID 2",
                "reproducciones=8, " + esp(4, "2", "2,3,4,1", "2,1,4,3"),
                "reproducciones=" + contar(salida, "Reproduciendo:") + ", " + resumen(l));
    }

    private static void agregarInvalido(boolean alInicio, String titulo, String artista, int duracion) {
        ListaReproduccion l = conCanciones(1);
        boolean lanzo = false;
        try {
            if (alInicio) {
                l.agregarAlInicio(titulo, artista, duracion);
            } else {
                l.agregarAlFinal(titulo, artista, duracion);
            }
        } catch (IllegalArgumentException e) {
            lanzo = true;
        }
        String trasFallo = resumen(l);
        Cancion valida = l.agregarAlFinal("Valida", "Artista", 120);
        String metodo = alInicio ? "agregarAlInicio" : "agregarAlFinal";
        registrar("Entrada inválida: " + descripcionInvalida(titulo, artista, duracion)
                        + (alInicio ? " (al inicio)" : " (al final)"),
                "Lista [1], actual=1",
                metodo + "(\"" + titulo + "\", \"" + artista + "\", " + duracion + ") y luego una canción válida",
                "lanzó IllegalArgumentException=true, tras fallo{" + esp(1, "1", "1", "1")
                        + "}, siguienteIdValido=2",
                "lanzó IllegalArgumentException=" + lanzo + ", tras fallo{" + trasFallo
                        + "}, siguienteIdValido=" + id(valida));
    }

    private static String descripcionInvalida(String titulo, String artista, int duracion) {
        if (titulo.isEmpty()) {
            return "título vacío";
        }
        if (artista.isEmpty()) {
            return "artista vacío";
        }
        return duracion == 0 ? "duración 0" : "duración negativa";
    }

    private static void reproducirInvalido(final int k) {
        final ListaReproduccion l = conCanciones(3);
        boolean lanzo = false;
        try {
            capturar(new Runnable() {
                public void run() { l.reproducir(k); }
            });
        } catch (IllegalArgumentException e) {
            lanzo = true;
        }
        registrar("Entrada inválida: reproducir(" + k + ")",
                "Lista [1,2,3], actual=1",
                "reproducir(" + k + ")",
                "lanzó IllegalArgumentException=true, " + esp(3, "1", "1,2,3", "1,3,2"),
                "lanzó IllegalArgumentException=" + lanzo + ", " + resumen(l));
    }

    private static void parseo(String texto, Integer esperado) {
        Integer obtenido = Menu.parsearEntero(texto);
        String textoMostrado = texto == null ? "null" : "\"" + texto + "\"";
        registrar("Menu.parsearEntero(" + textoMostrado + ")",
                "No aplica (método estático)",
                "Menu.parsearEntero(" + textoMostrado + ")",
                String.valueOf(esperado),
                String.valueOf(obtenido));
    }
}