package mx.unam.fesa.ico;

public class ListaReproduccion {
    private Nodo inicio;
    private Nodo fin;
    private Nodo actual;
    private int tamanio;
    private int contadorId = 1;

    public boolean estaVacia() {
        return tamanio == 0;
    }

    public int getTamanio() {
        return tamanio;
    }

    public Cancion agregarAlInicio(String titulo, String artista, int duracion) {
        Cancion cancion = new Cancion(contadorId, titulo, artista, duracion);
        contadorId++;
        Nodo nuevo = new Nodo(cancion);

        if (estaVacia()) {
            nuevo.setAnterior(nuevo);
            nuevo.setSiguiente(nuevo);
            inicio = nuevo;
            fin = nuevo;
            actual = nuevo;
        } else {
            nuevo.setSiguiente(inicio);
            nuevo.setAnterior(fin);
            inicio.setAnterior(nuevo);
            fin.setSiguiente(nuevo);
            inicio = nuevo;
        }
        tamanio++;
        return cancion;
    }

    public Cancion agregarAlFinal(String titulo, String artista, int duracion) {
        Cancion cancion = new Cancion(contadorId, titulo, artista, duracion);
        contadorId++;
        Nodo nuevo = new Nodo(cancion);

        if (estaVacia()) {
            nuevo.setAnterior(nuevo);
            nuevo.setSiguiente(nuevo);
            inicio = nuevo;
            fin = nuevo;
            actual = nuevo;
        } else {
            nuevo.setAnterior(fin);
            nuevo.setSiguiente(inicio);
            fin.setSiguiente(nuevo);
            inicio.setAnterior(nuevo);
            fin = nuevo;
        }
        tamanio++;
        return cancion;
    }

    public Cancion consultarActual() {
        if (estaVacia()) {
            return null;
        }
        return actual.getDato();
    }

    public Cancion siguiente() {
        if (estaVacia()) {
            return null;
        }
        actual = actual.getSiguiente();
        return actual.getDato();
    }

    public Cancion anterior() {
        if (estaVacia()) {
            return null;
        }
        actual = actual.getAnterior();
        return actual.getDato();
    }

    public void mostrarAdelante() {
        if (estaVacia()) {
            System.out.println("La lista está vacía.");
            return;
        }
        Nodo nodo = inicio;
        do {
            System.out.println(nodo.getDato());
            nodo = nodo.getSiguiente();
        } while (nodo != inicio);
    }

    public void mostrarAtras() {
        if (estaVacia()) {
            System.out.println("La lista está vacía.");
            return;
        }
        Nodo nodo = fin;
        do {
            System.out.println(nodo.getDato());
            nodo = nodo.getAnterior();
        } while (nodo != fin);
    }

    public void reproducir(int k) {
        if (k <= 0) {
            throw new IllegalArgumentException("La cantidad de canciones a reproducir debe ser mayor que 0.");
        }
        if (estaVacia()) {
            System.out.println("La lista está vacía. No hay canciones para reproducir.");
            return;
        }
        for (int i = 0; i < k; i++) {
            System.out.println("Reproduciendo: " + actual.getDato());
            actual = actual.getSiguiente();
        }
    }

    private Nodo buscarNodo(int id) {
        if (inicio == null) {
            return null;
        }
        Nodo n = inicio;
        do {
            if (n.getDato().getId() == id) {
                return n;
            }
            n = n.getSiguiente();
        } while (n != inicio);
        return null;
    }

    private void desenlazar(Nodo n) {
        if (n == null) {
            return;
        }
        if (n.getSiguiente() == n) {
            inicio = null;
            fin = null;
            actual = null;
            tamanio = 0;
            n.setAnterior(null);
            n.setSiguiente(null);
            return;
        }
        Nodo ant = n.getAnterior();
        Nodo sig = n.getSiguiente();
        ant.setSiguiente(sig);
        sig.setAnterior(ant);
        if (n == inicio) {
            inicio = sig;
        }
        if (n == fin) {
            fin = ant;
        }
        if (n == actual) {
            actual = sig;
        }
        tamanio--;
        n.setAnterior(null);
        n.setSiguiente(null);
    }

    public Cancion buscarPorId(int id) {
        Nodo n = buscarNodo(id);
        return (n == null) ? null : n.getDato();
    }

    public boolean seleccionarPorId(int id) {
        Nodo n = buscarNodo(id);
        if (n == null) {
            return false;
        }
        actual = n;
        return true;
    }

    public boolean eliminarPorId(int id) {
        Nodo n = buscarNodo(id);
        if (n == null) {
            return false;
        }
        desenlazar(n);
        return true;
    }

    public Cancion eliminarActual() {
        if (actual == null) {
            return null;
        }
        Cancion eliminada = actual.getDato();
        desenlazar(actual);
        return eliminada;
    }

    public boolean verificarConsistencia() {
        if (tamanio < 0) {
            return false;
        }
        if (tamanio == 0) {
            return inicio == null && fin == null && actual == null;
        }
        if (inicio == null || fin == null || actual == null) {
            return false;
        }
        if (inicio.getAnterior() != fin || fin.getSiguiente() != inicio) {
            return false;
        }
        if (tamanio == 1 && (inicio != fin || inicio.getSiguiente() != inicio
                || inicio.getAnterior() != inicio)) {
            return false;
        }
        boolean actualEncontrado = false;
        Nodo n = inicio;
        Nodo ultimo = null;
        for (int i = 0; i < tamanio; i++) {
            if (n == null || n.getDato() == null) {
                return false;
            }
            if (i > 0 && n == inicio) {
                return false;
            }
            Nodo sig = n.getSiguiente();
            Nodo ant = n.getAnterior();
            if (sig == null || ant == null) {
                return false;
            }
            if (sig.getAnterior() != n || ant.getSiguiente() != n) {
                return false;
            }
            if (n == actual) {
                actualEncontrado = true;
            }
            ultimo = n;
            n = sig;
        }
        return n == inicio && ultimo == fin && actualEncontrado;
    }
}