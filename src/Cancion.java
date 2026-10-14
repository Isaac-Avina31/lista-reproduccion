package mx.unam.fesa.ico;

public class Cancion {
    private final int id;
    private final String titulo;
    private final String artista;
    private final int duracion; // en segundos

    public Cancion(int id, String titulo, String artista, int duracion) {
        if (duracion <= 0) {
            throw new IllegalArgumentException("La duración debe ser mayor que 0 segundos.");
        }
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("El título no puede ser nulo ni vacío.");
        }
        if (artista == null || artista.trim().isEmpty()) {
            throw new IllegalArgumentException("El artista no puede ser nulo ni vacío.");
        }
        this.id = id;
        this.titulo = titulo.trim();
        this.artista = artista.trim();
        this.duracion = duracion;
    }

    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getArtista() { return artista; }
    public int getDuracion() { return duracion; }

    @Override
    public String toString() {
        return "[ID " + id + "] " + titulo + " - " + artista + " (" + duracion + " s)";
    }
}