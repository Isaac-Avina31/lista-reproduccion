package mx.unam.fesa.ico;

public class Nodo {
    private Cancion dato;
    private Nodo anterior;
    private Nodo siguiente;

    public Nodo(Cancion dato) {
        this.dato = dato;
        this.anterior = null;
        this.siguiente = null;
    }

    public Cancion getDato() { return dato; }
    public Nodo getAnterior() { return anterior; }
    public void setAnterior(Nodo anterior) { this.anterior = anterior; }
    public Nodo getSiguiente() { return siguiente; }
    public void setSiguiente(Nodo siguiente) { this.siguiente = siguiente; }
}