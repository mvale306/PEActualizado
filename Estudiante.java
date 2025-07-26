

import java.util.ArrayList;
import java.util.List;

public class Estudiante {
    private int ci;
    private String nombre;
    private int edad;
    private String Tcarrera;
    private String carrera;
    private int semestre;
    private List<Curso> cursos = new ArrayList<>();

    public Estudiante(int ci, String nombre, int edad, String carrera, int semestre) {
        this.ci = ci;
        this.nombre = nombre;
        this.edad = edad;
        this.carrera = carrera;
        this.semestre = semestre;
    }

    // getters/setters (ci solo getter):
    public int getCi() { return ci; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }
    public String getCarrera() { return carrera; }
    public void setCarrera(String carrera) { this.carrera = carrera; }
    public int getSemestre() { return semestre; }
    public void setSemestre(int semestre) { this.semestre = semestre; }
    public List<Curso> getCursos() { return cursos; }

    public void addCurso(Curso c) { cursos.add(c); }
    public boolean removeCurso(String nombreCurso) {
        return cursos.removeIf(c -> c.getNombre().equalsIgnoreCase(nombreCurso));
    }
    public Curso buscarCurso(String nombreCurso) {
        return cursos.stream()
            .filter(c -> c.getNombre().equalsIgnoreCase(nombreCurso))
            .findFirst().orElse(null);
    }

    public double calcularPromedio() {
        return cursos.stream()
                      .mapToInt(Curso::getNota)
                      .average().orElse(0);
    }

    public String getEstatus() {
        return calcularPromedio() >= 10 ? "Aprobado" : "Reprobado";
    }

    public void mostrarDetalle() {
        System.out.printf("CI: %s | Nombre: %s | Edad: %d | Carrera: %s | Semestre: %d%n",
                          ci, nombre, edad, carrera, semestre);

        System.out.println("Cursos:");
        cursos.forEach(c -> System.out.println("  - " + c));
        System.out.printf("Promedio: %.2f | Estatus: %s%n", calcularPromedio(), getEstatus());
    }
}
