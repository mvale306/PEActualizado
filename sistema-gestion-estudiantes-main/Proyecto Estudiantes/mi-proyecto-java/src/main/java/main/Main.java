import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<Estudiante> lista = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion;
        do {
            showMenu();
            opcion = sc.nextInt(); sc.nextLine();
            switch(opcion) {
                case 1: registrar(); break;
                case 2: actualizar(); break;
                case 3: buscar(); break;
                case 4: eliminar(); break;
                case 5: gestionarCursos(); break;
                case 6: listarTodos(); break;
                case 0: System.out.println("Saliendo..."); break;
                default: System.out.println("Opción inválida");
            }
        } while (opcion != 0);
    }

    private static void showMenu() {
        System.out.println("\n=== GESTIÓN DE ESTUDIANTES ===");
        System.out.println("1. Registrar estudiante");
        System.out.println("2. Actualizar datos");
        System.out.println("3. Buscar estudiante");
        System.out.println("4. Eliminar estudiante");
        System.out.println("5. Gestión de cursos/notas");
        System.out.println("6. Listar todos");
        System.out.println("0. Salir");
        System.out.print("Elige una opción: ");
    }

    private static void registrar() {
        System.out.print("CI: ");
        int ci =  sc.nextInt(); sc.nextLine();
        if (!Validaciones.ciValida(ci) || findByCi(ci) != null) {
            System.out.println("CI inválida o duplicada."); return;
        }
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();
        if (!Validaciones.nombreValido(nombre)) {
            System.out.println("Nombre inválido."); return;
        }
        System.out.print("Edad: ");
        int edad = sc.nextInt(); sc.nextLine();
        if (!Validaciones.edadValida(edad)) {
            System.out.println("Edad inválida."); return;
        }
        System.out.print("Carrera: ");
        String carrera = sc.nextLine();
        System.out.print("Semestre: ");
        int sem = sc.nextInt(); sc.nextLine();
        if (!Validaciones.semestreValido(sem)) {
            System.out.println("Semestre inválido."); return;
        }
        lista.add(new Estudiante(ci, nombre, edad, carrera, sem));
        System.out.println("Estudiante registrado.");
    }

    private static void actualizar() {
        Estudiante e = pedirEstudiante("actualizar");
        if (e == null) return;
        System.out.println("1.Nombre 2.Edad 3.Carrera 4.Semestre");
        int op = sc.nextInt(); sc.nextLine();
        switch (op) {
            case 1:
                System.out.print("Nuevo nombre: ");
                String nom = sc.nextLine();
                if (Validaciones.nombreValido(nom)) e.setNombre(nom);
                break;
            case 2:
                System.out.print("Nueva edad: ");
                int ed = sc.nextInt(); sc.nextLine();
                if (Validaciones.edadValida(ed)) e.setEdad(ed);
                break;
            case 3:
                System.out.print("Nueva carrera: ");
                e.setCarrera(sc.nextLine());
                break;
            case 4:
                System.out.print("Nuevo semestre: ");
                int sem = sc.nextInt(); sc.nextLine();
                if (Validaciones.semestreValido(sem)) e.setSemestre(sem);
                break;
            default: System.out.println("Opción inválida");
        }
        System.out.println("Datos actualizados.");
    }

    private static void buscar() {
        Estudiante e = pedirEstudiante("buscar");
        if (e != null) e.mostrarDetalle();
    }

    private static void eliminar() {
        Estudiante e = pedirEstudiante("eliminar");
        if (e == null) return;
        System.out.print("Confirmar eliminación (S/N): ");
        if (sc.nextLine().equalsIgnoreCase("S")) {
            lista.remove(e);
            System.out.println("Eliminado.");
        }
    }

    private static void gestionarCursos() {
        Estudiante e = pedirEstudiante("gestión de cursos");
        if (e == null) return;
        int op;
        do {
            System.out.println("\n1.Añadir 2.Eliminar 3.Modificar nota 4.Ver detalle 0.Salir");
            op = sc.nextInt(); sc.nextLine();
            switch (op) {
                case 1:
                    System.out.print("Curso: "); String n = sc.nextLine();
                    System.out.print("Nota: "); int nt = sc.nextInt(); sc.nextLine();
                    if (Validaciones.notaValida(nt))
                        e.addCurso(new Curso(n, nt));
                    break;
                case 2:
                    System.out.print("Nombre curso a eliminar: ");
                    e.removeCurso(sc.nextLine());
                    break;
                case 3:
                    System.out.print("Nombre curso: ");
                    String nom = sc.nextLine();
                    Curso c = e.buscarCurso(nom);
                    if (c != null) {
                        System.out.print("Nueva nota: ");
                        int nueva = sc.nextInt(); sc.nextLine();
                        if (Validaciones.notaValida(nueva)) c.setNota(nueva);
                    }
                    break;
                case 4:
                    e.mostrarDetalle();
                    break;
            }
        } while (op != 0);
    }

    private static void listarTodos() {
        if (lista.isEmpty()) {
            System.out.println("Sin estudiantes registrados.");
            return;
        }
        lista.forEach(Estudiante::mostrarDetalle);
    }

    private static Estudiante pedirEstudiante(String accion) {
        System.out.printf("CI a %s: ", accion);
        int ci = sc.nextInt(); sc.nextLine();
        if (ci < 10000000 || ci > 100000000) {
            System.out.println("El número de CI debe tener entre 8 y 9 dígitos.");
            return null;
        }
        Estudiante e = findByCi(ci);
        if (e == null) System.out.println("No encontrado.");
        return e;
    }

    private static Estudiante findByCi(int ci) {
        return lista.stream()
                    .filter(x -> x.getCi() == ci)
                    .findFirst().orElse(null);
    }
}
