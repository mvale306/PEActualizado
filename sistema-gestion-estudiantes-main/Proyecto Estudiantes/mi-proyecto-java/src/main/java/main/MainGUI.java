import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class MainGUI extends JFrame {
    private List<Estudiante> lista = new ArrayList<>();

    public MainGUI() {
        setTitle("Gestión de Estudiantes");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1, 10, 10));

        JButton btnRegistrar = new JButton("Registrar estudiante");
        JButton btnActualizar = new JButton("Actualizar datos");
        JButton btnBuscar = new JButton("Buscar estudiante");
        JButton btnEliminar = new JButton("Eliminar estudiante");
        JButton btnCursos = new JButton("Gestión de cursos/notas");
        JButton btnListar = new JButton("Listar todos");
        JButton btnSalir = new JButton("Salir");

        panel.setBackground(new Color(230, 240, 255));
        Font fuenteBoton = new Font("Arial", Font.BOLD, 14);
        for (Component c : new JButton[]{btnRegistrar, btnActualizar, btnBuscar, btnEliminar, btnCursos, btnListar, btnSalir}) {
            c.setFont(fuenteBoton);
            c.setBackground(new Color(100, 149, 237));
            c.setForeground(Color.WHITE);
        }

        panel.add(btnRegistrar);
        panel.add(btnActualizar);
        panel.add(btnBuscar);
        panel.add(btnEliminar);
        panel.add(btnCursos);
        panel.add(btnListar);
        panel.add(btnSalir);

        add(panel);

        // Registrar estudiante
        btnRegistrar.addActionListener(_ -> {
            JPanel panel1 = new JPanel(new GridLayout(0, 2, 5, 5));
            JTextField ciField = new JTextField();
            JTextField nombreField = new JTextField();
            JTextField edadField = new JTextField();
            JComboBox<String> TcarreraCombo = new JComboBox<>(new String[]{"Administrativa", "Tecnológica"});
            JComboBox<String> carreraCombo = new JComboBox<>();
            JComboBox<String> semestreCombo = new JComboBox<>(new String[]{"2025-1"});

            // Inicializar carreras según tipo de carrera
            TcarreraCombo.addActionListener(e -> { 
                carreraCombo.removeAllItems();
                if (TcarreraCombo.getSelectedItem().equals("Tecnológica")) {
                    String[] carrerasTec = {"Informática", "Electrónica", "Electricidad", "Mecánica", "Diseño de obras civiles", "Diseño gráfico", "Seguridad industrial"};
                    for (String c : carrerasTec) carreraCombo.addItem(c);
                } else {
                    String[] carrerasAdm = {"Publicidad","Comercio exterior", "Administración", "Turismo", "Relaciones Industriales", "Secretaría"};
                    for (String c : carrerasAdm) carreraCombo.addItem(c);
                }
            });
            // Disparar evento para cargar opciones iniciales
            TcarreraCombo.setSelectedIndex(0);

            panel1.add(new JLabel("CI:"));
            panel1.add(ciField);
            panel1.add(new JLabel("Nombre:"));
            panel1.add(nombreField);
            panel1.add(new JLabel("Edad:"));
            panel1.add(edadField);
            panel1.add(new JLabel("Tipo de Carrera:"));
            panel1.add(TcarreraCombo);
            panel1.add(new JLabel("Carrera:"));
            panel1.add(carreraCombo);
            panel1.add(new JLabel("Semestre:"));
            panel1.add(semestreCombo);

            int result = JOptionPane.showConfirmDialog(this, panel1, "Registrar Estudiante", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                String ciStr = ciField.getText().trim();
                String nombre = nombreField.getText().trim();
                String edadStr = edadField.getText().trim();
                String tipoCarrera = (String) TcarreraCombo.getSelectedItem();
                String carrera = (String) carreraCombo.getSelectedItem();
                String semestreStr = (String) semestreCombo.getSelectedItem();
                // Validaciones básicas
                if (ciStr.isEmpty() || nombre.isEmpty() || edadStr.isEmpty() || tipoCarrera == null || carrera == null || semestreStr == null) {
                    JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
                    return;
                }
                int ci, edad, semestre;
                try { ci = Integer.parseInt(ciStr); }
                catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "CI inválido."); return; }
                if (ci < 10000000 || ci > 100000000) {
                    JOptionPane.showMessageDialog(this, "El número de CI debe tener entre 8 y 9 dígitos.");
                    return;
                }
                try { edad = Integer.parseInt(edadStr); }
                catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "Edad inválida."); return; }
                // Mostrar resumen antes de registrar
                String resumen = String.format(
                    "CI: %d\nNombre: %s\nEdad: %d\nTipo de Carrera: %s\nCarrera: %s\nSemestre: %s",
                    ci, nombre, edad, tipoCarrera, carrera, semestreStr
                );
                int confirm = JOptionPane.showConfirmDialog(this, resumen, "Confirmar datos", JOptionPane.OK_CANCEL_OPTION);
                if (confirm == JOptionPane.OK_OPTION) {
                    lista.add(new Estudiante(ci, nombre, edad, carrera, 20251));
                    JOptionPane.showMessageDialog(this, "Estudiante registrado.");
                }
            }
        });

        // Listar estudiantes
        btnListar.addActionListener(_ -> {
            if (lista.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay estudiantes registrados.");
                return;
            }
            // Ordenar la lista por CI antes de mostrar
            List<Estudiante> copiaOrdenada = new ArrayList<>(lista);
            copiaOrdenada.sort((a, b) -> Integer.compare(a.getCi(), b.getCi()));
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("%-12s %-20s %-6s %-15s %-8s\n", "CI", "Nombre", "Edad", "Carrera", "Semestre"));
            sb.append("---------------------------------------------------------------\n");
            for (Estudiante est : copiaOrdenada) {
                sb.append(String.format("%-12d %-20s %-6d %-15s %-8d\n",
                    est.getCi(), est.getNombre(), est.getEdad(), est.getCarrera(), est.getSemestre()));
            }
            JOptionPane.showMessageDialog(this, sb.toString());
        });

        // Buscar estudiante
        btnBuscar.addActionListener(_ -> {
            JPanel panelBuscar = new JPanel(new GridLayout(0, 2, 5, 5));
            JTextField ciField = new JTextField();
            panelBuscar.add(new JLabel("CI:"));
            panelBuscar.add(ciField);
            int result = JOptionPane.showConfirmDialog(this, panelBuscar, "Buscar Estudiante", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                String ciStr = ciField.getText().trim();
                if (ciStr.isEmpty()) { JOptionPane.showMessageDialog(this, "Debe ingresar el CI."); return; }
                int ci;
                try { ci = Integer.parseInt(ciStr); }
                catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "CI inválido."); return; }
                Estudiante encontrado = null;
                for (Estudiante est : lista) {
                    if (est.getCi() == ci) {
                        encontrado = est;
                        break;
                    }
                }
                if (encontrado != null) {
                    JPanel panelDatos = new JPanel(new GridLayout(0, 2, 5, 5));
                    panelDatos.add(new JLabel("CI:"));
                    panelDatos.add(new JLabel(String.valueOf(encontrado.getCi())));
                    panelDatos.add(new JLabel("Nombre:"));
                    panelDatos.add(new JLabel(encontrado.getNombre()));
                    panelDatos.add(new JLabel("Edad:"));
                    panelDatos.add(new JLabel(String.valueOf(encontrado.getEdad())));
                    panelDatos.add(new JLabel("Carrera:"));
                    panelDatos.add(new JLabel(encontrado.getCarrera()));
                    panelDatos.add(new JLabel("Semestre:"));
                    panelDatos.add(new JLabel(String.valueOf(encontrado.getSemestre())));
                    JOptionPane.showMessageDialog(this, panelDatos, "Datos del Estudiante", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Estudiante no encontrado.");
                }
            }
        });

        // Actualizar estudiante
        btnActualizar.addActionListener(_ -> {
            JPanel panelBuscar = new JPanel(new GridLayout(0, 2, 5, 5));
            JTextField ciField = new JTextField();
            panelBuscar.add(new JLabel("CI:"));
            panelBuscar.add(ciField);
            int result = JOptionPane.showConfirmDialog(this, panelBuscar, "Actualizar Estudiante", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                String ciStr = ciField.getText().trim();
                if (ciStr.isEmpty()) { JOptionPane.showMessageDialog(this, "Debe ingresar el CI."); return; }
                int ci;
                try { ci = Integer.parseInt(ciStr); }
                catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "CI inválido."); return; }
                Estudiante encontrado = null;
                for (Estudiante est : lista) {
                    if (est.getCi() == ci) {
                        encontrado = est;
                        break;
                    }
                }
                if (encontrado != null) {
                    JPanel panel1 = new JPanel(new GridLayout(0, 2, 5, 5));
                    JTextField nombreField = new JTextField(encontrado.getNombre());
                    JTextField edadField = new JTextField(String.valueOf(encontrado.getEdad()));
                    JComboBox<String> TcarreraCombo = new JComboBox<>(new String[]{"Administrativa", "Tecnológica"});
                    JComboBox<String> carreraCombo = new JComboBox<>();
                    JComboBox<String> semestreCombo = new JComboBox<>(new String[]{"2025-1"});

                    // Seleccionar tipo de carrera y carrera actuales
                    String carreraActual = encontrado.getCarrera();
                    String tipoCarreraActual = "Administrativa";
                    String[] carrerasTec = {"Informática", "Electrónica", "Electricidad", "Mecánica", "Diseño de obras civiles", "Diseño gráfico", "Seguridad industrial"};
                    String[] carrerasAdm = {"Publicidad","Comercio exterior", "Administración", "Turismo", "Relaciones Industriales", "Secretaría"};
                    boolean esTec = false;
                    for (String c : carrerasTec) if (c.equals(carreraActual)) esTec = true;
                    TcarreraCombo.setSelectedItem(esTec ? "Tecnológica" : "Administrativa");
                    // Inicializar carreras según tipo de carrera
                    TcarreraCombo.addActionListener(e -> {
                        carreraCombo.removeAllItems();
                        if (TcarreraCombo.getSelectedItem().equals("Tecnológica")) {
                            for (String c : carrerasTec) carreraCombo.addItem(c);
                        } else {
                            for (String c : carrerasAdm) carreraCombo.addItem(c);
                        }
                    });
                    // Disparar evento para cargar opciones iniciales
                    TcarreraCombo.setSelectedItem(esTec ? "Tecnológica" : "Administrativa");
                    // Seleccionar carrera actual
                    carreraCombo.setSelectedItem(carreraActual);

                    panel1.add(new JLabel("Nombre:"));
                    panel1.add(nombreField);
                    panel1.add(new JLabel("Edad:"));
                    panel1.add(edadField);
                    panel1.add(new JLabel("Tipo de Carrera:"));
                    panel1.add(TcarreraCombo);
                    panel1.add(new JLabel("Carrera:"));
                    panel1.add(carreraCombo);
                    panel1.add(new JLabel("Semestre:"));
                    panel1.add(semestreCombo);

                    int confirm = JOptionPane.showConfirmDialog(this, panel1, "Editar Datos", JOptionPane.OK_CANCEL_OPTION);
                    if (confirm == JOptionPane.OK_OPTION) {
                        String nombre = nombreField.getText().trim();
                        String edadStr = edadField.getText().trim();
                        String tipoCarrera = (String) TcarreraCombo.getSelectedItem();
                        String carrera = (String) carreraCombo.getSelectedItem();
                        String semestreStr = (String) semestreCombo.getSelectedItem();
                        if (nombre.isEmpty() || edadStr.isEmpty() || tipoCarrera == null || carrera == null || semestreStr == null) {
                            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios."); return;
                        }
                        int edad;
                        try { edad = Integer.parseInt(edadStr); }
                        catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "Edad inválida."); return; }
                        encontrado.setNombre(nombre);
                        encontrado.setEdad(edad);
                        encontrado.setCarrera(carrera);
                        encontrado.setSemestre(20251);
                        JOptionPane.showMessageDialog(this, "Datos actualizados.");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Estudiante no encontrado.");
                }
            }
        });

        // Eliminar estudiante
        btnEliminar.addActionListener(_ -> {
            JPanel panelBuscar = new JPanel(new GridLayout(0, 2, 5, 5));
            JTextField ciField = new JTextField();
            panelBuscar.add(new JLabel("CI:"));
            panelBuscar.add(ciField);
            int result = JOptionPane.showConfirmDialog(this, panelBuscar, "Eliminar Estudiante", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                String ciStr = ciField.getText().trim();
                if (ciStr.isEmpty()) { JOptionPane.showMessageDialog(this, "Debe ingresar el CI."); return; }
                int ci;
                try { ci = Integer.parseInt(ciStr); }
                catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "CI inválido."); return; }
                Estudiante encontrado = null;
                for (Estudiante est : lista) {
                    if (est.getCi() == ci) {
                        encontrado = est;
                        break;
                    }
                }
                if (encontrado != null) {
                    JPanel panelDatos = new JPanel(new GridLayout(0, 2, 5, 5));
                    panelDatos.add(new JLabel("CI:"));
                    panelDatos.add(new JLabel(String.valueOf(encontrado.getCi())));
                    panelDatos.add(new JLabel("Nombre:"));
                    panelDatos.add(new JLabel(encontrado.getNombre()));
                    panelDatos.add(new JLabel("Edad:"));
                    panelDatos.add(new JLabel(String.valueOf(encontrado.getEdad())));
                    panelDatos.add(new JLabel("Carrera:"));
                    panelDatos.add(new JLabel(encontrado.getCarrera()));
                    panelDatos.add(new JLabel("Semestre:"));
                    panelDatos.add(new JLabel(String.valueOf(encontrado.getSemestre())));
                    int confirm = JOptionPane.showConfirmDialog(this, panelDatos, "Confirmar eliminación", JOptionPane.OK_CANCEL_OPTION);
                    if (confirm == JOptionPane.OK_OPTION) {
                        lista.remove(encontrado);
                        JOptionPane.showMessageDialog(this, "Estudiante eliminado.");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Estudiante no encontrado.");
                }
            }
        });

        btnSalir.addActionListener(_ -> System.exit(0));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainGUI().setVisible(true));
    }
}