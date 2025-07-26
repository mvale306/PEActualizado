

import java.util.List;


public class CalculoPromedio {
    public static double promedio(List<Curso> cursos) {
        return cursos.stream().mapToInt(Curso::getNota).average().orElse(0);
    }
    public static String estatus(double promedio) {
        return promedio >= 10 ? "Aprobado" : "Reprobado";
    }
}
