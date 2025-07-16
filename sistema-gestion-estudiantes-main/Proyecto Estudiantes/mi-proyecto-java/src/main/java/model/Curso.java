

public class Curso {
    private String nombre;
    private int nota; // 1–20

    public Curso(String nombre, int nota) {
        this.nombre = nombre;
        this.nota = nota;
    }

    // getters/setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public int getNota() { return nota; }
    public void setNota(int nota) { this.nota = nota; }

    @Override
    public String toString() {
        return nombre + " (" + nota + ")";
    }
}
