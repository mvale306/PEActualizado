

public class Validaciones {
    public static boolean ciValida(int ci) {
        return ci >= 10000000  && ci <= 100000000;
    }
    public static boolean nombreValido(String nombre) {
        return nombre != null && nombre.matches("[A-Za-z ]");
    }
    public static boolean edadValida(int edad) {
        return edad >= 15 && edad <= 95;
    }
    public static boolean semestreValido(int sem) {
        return sem >= 1 && sem <= 6;
    }
    public static boolean notaValida(int nota) {
        return nota >= 1 && nota <= 20;
    }
}
