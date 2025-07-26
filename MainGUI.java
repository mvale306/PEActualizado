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
        setTitle("<Gestión de Estudiantes>");
        setSize(1000, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2, 1));
        mainPanel.setBackground(new Color(245, 247, 255));

        JLabel titulo = new JLabel("<Gestión de Estudiantes>", JLabel.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titulo.setForeground(new Color(44, 62, 80));
        titulo.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 10, 10, 10));

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(2, 4, 18, 18));
        panelBotones.setBackground(new Color(245, 247, 255));
        panelBotones.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 30, 30, 30));

        JButton btnRegistrar = new JButton("Registrar estudiante");
        JButton btnActualizar = new JButton("Actualizar datos");
        JButton btnBuscar = new JButton("Buscar estudiante");
        JButton btnEliminar = new JButton("Eliminar estudiante");
        JButton btnCursos = new JButton("Gestión de cursos/notas");
        JButton btnListar = new JButton("Listar todos");
        JButton btnSalir = new JButton("Salir");

        JButton[] botones = {btnRegistrar, btnActualizar, btnBuscar, btnEliminar, btnCursos, btnListar, btnSalir};
        Color colorPrincipal = new Color(52, 152, 219);
        Color colorHover = new Color(41, 128, 185);
        Font fuenteBoton = new Font("Segoe UI", Font.BOLD, 15);
        for (JButton b : botones) {
            b.setFont(fuenteBoton);
            b.setBackground(colorPrincipal);
            b.setForeground(Color.WHITE);
            b.setFocusPainted(false);
            b.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(new Color(200, 200, 255), 2),
                javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10)));
            b.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            b.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    b.setBackground(colorHover);
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    b.setBackground(colorPrincipal);
                }
            });
        }

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnCursos);
        panelBotones.add(btnListar);
        panelBotones.add(btnSalir);
        // Espacio vacío para centrar
        panelBotones.add(new JLabel(""));

        mainPanel.add(titulo);
        mainPanel.add(panelBotones);
        add(mainPanel);

        // Gestión de cursos y notas
        btnCursos.addActionListener(_ -> {
            JPanel panelBuscar = new JPanel(new GridLayout(0, 2, 5, 5));
            panelBuscar.setBackground(new Color(235, 242, 255));
            panelBuscar.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(new Color(180, 200, 255), 2, true),
                javax.swing.BorderFactory.createEmptyBorder(10, 20, 10, 20)));
            JTextField ciField = new JTextField();
            JLabel labelCI = new JLabel("CI del estudiante:");
            labelCI.setFont(new Font("Segoe UI", Font.PLAIN, 15));
            panelBuscar.add(labelCI);
            panelBuscar.add(ciField);
            int result = JOptionPane.showConfirmDialog(this, panelBuscar, "Buscar Estudiante para Cursos", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
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
                if (encontrado == null) {
                    JOptionPane.showMessageDialog(this, "Estudiante no encontrado.");
                    return;
                }

                boolean gestionando = true;
                while (gestionando) {
                    String[] opciones = {"Ver cursos", "Agregar curso", "Modificar nota", "Eliminar curso", "Ver promedio", "Salir"};
                    int op = JOptionPane.showOptionDialog(this, "Seleccione una opción:", "Gestión de cursos/notas",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);
                    switch (op) {
                        case 0: // Ver cursos
                            List<Curso> cursos = encontrado.getCursos();
                            if (cursos.isEmpty()) {
                                JOptionPane.showMessageDialog(this, "No hay cursos registrados.");
                            } else {
                                JPanel panelCursos = new JPanel(new GridLayout(0, 2, 10, 5));
                                panelCursos.setBackground(new Color(245, 250, 255));
                                panelCursos.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                                    javax.swing.BorderFactory.createLineBorder(new Color(180, 200, 255), 1, true),
                                    javax.swing.BorderFactory.createEmptyBorder(10, 20, 10, 20)));
                                JLabel l1 = new JLabel("Curso");
                                l1.setFont(new Font("Segoe UI", Font.BOLD, 15));
                                JLabel l2 = new JLabel("Nota");
                                l2.setFont(new Font("Segoe UI", Font.BOLD, 15));
                                panelCursos.add(l1);
                                panelCursos.add(l2);
                                for (Curso c : cursos) {
                                    JLabel nc = new JLabel(c.getNombre());
                                    nc.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                                    JLabel nt = new JLabel(String.valueOf(c.getNota()));
                                    nt.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                                    panelCursos.add(nc);
                                    panelCursos.add(nt);
                                }
                                JOptionPane.showMessageDialog(this, panelCursos, "Cursos y notas", JOptionPane.INFORMATION_MESSAGE);
                            }
                            break;
                        case 1: // Agregar curso
                            JPanel panelAdd = new JPanel(new GridLayout(0, 2, 5, 5));
                            panelAdd.setBackground(new Color(235, 242, 255));
                            panelAdd.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                                javax.swing.BorderFactory.createLineBorder(new Color(180, 200, 255), 1, true),
                                javax.swing.BorderFactory.createEmptyBorder(10, 20, 10, 20)));
                            JLabel lNom = new JLabel("Nombre del curso:");
                            lNom.setFont(new Font("Segoe UI", Font.PLAIN, 15));
                            JLabel lNota = new JLabel("Nota (1-20):");
                            lNota.setFont(new Font("Segoe UI", Font.PLAIN, 15));
                            JTextField nombreCurso = new JTextField();
                            JTextField notaCurso = new JTextField();
                            panelAdd.add(lNom);
                            panelAdd.add(nombreCurso);
                            panelAdd.add(lNota);
                            panelAdd.add(notaCurso);
                            int resAdd = JOptionPane.showConfirmDialog(this, panelAdd, "Agregar Curso", JOptionPane.OK_CANCEL_OPTION);
                            if (resAdd == JOptionPane.OK_OPTION) {
                                String nombre = nombreCurso.getText().trim();
                                String notaStr = notaCurso.getText().trim();
                                if (nombre.isEmpty() || notaStr.isEmpty()) {
                                    JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
                                    break;
                                }
                                int nota;
                                try { nota = Integer.parseInt(notaStr); }
                                catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "Nota inválida."); break; }
                                if (nota < 1 || nota > 20) {
                                    JOptionPane.showMessageDialog(this, "La nota debe estar entre 1 y 20.");
                                    break;
                                }
                                if (encontrado.buscarCurso(nombre) != null) {
                                    JOptionPane.showMessageDialog(this, "El curso ya existe para este estudiante.");
                                    break;
                                }
                                encontrado.addCurso(new Curso(nombre, nota));
                                JOptionPane.showMessageDialog(this, "Curso agregado.");
                            }
                            break;
                        case 2: // Modificar nota
                            String nombreMod = JOptionPane.showInputDialog(this, "Nombre del curso a modificar:");
                            if (nombreMod == null || nombreMod.trim().isEmpty()) break;
                            Curso cursoMod = encontrado.buscarCurso(nombreMod.trim());
                            if (cursoMod == null) {
                                JOptionPane.showMessageDialog(this, "Curso no encontrado.");
                                break;
                            }
                            JPanel panelNota = new JPanel(new GridLayout(0, 2, 5, 5));
                            panelNota.setBackground(new Color(235, 242, 255));
                            panelNota.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                                javax.swing.BorderFactory.createLineBorder(new Color(180, 200, 255), 1, true),
                                javax.swing.BorderFactory.createEmptyBorder(10, 20, 10, 20)));
                            JLabel lNuevaNota = new JLabel("Nueva nota (1-20):");
                            lNuevaNota.setFont(new Font("Segoe UI", Font.PLAIN, 15));
                            JTextField nuevaNotaField = new JTextField(String.valueOf(cursoMod.getNota()));
                            panelNota.add(lNuevaNota);
                            panelNota.add(nuevaNotaField);
                            int resNota = JOptionPane.showConfirmDialog(this, panelNota, "Modificar Nota", JOptionPane.OK_CANCEL_OPTION);
                            if (resNota == JOptionPane.OK_OPTION) {
                                String nuevaNotaStr = nuevaNotaField.getText().trim();
                                int nuevaNota;
                                try { nuevaNota = Integer.parseInt(nuevaNotaStr); }
                                catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "Nota inválida."); break; }
                                if (nuevaNota < 1 || nuevaNota > 20) {
                                    JOptionPane.showMessageDialog(this, "La nota debe estar entre 1 y 20.");
                                    break;
                                }
                                cursoMod.setNota(nuevaNota);
                                JOptionPane.showMessageDialog(this, "Nota actualizada.");
                            }
                            break;
                        case 3: // Eliminar curso
                            String nombreDel = JOptionPane.showInputDialog(this, "Nombre del curso a eliminar:");
                            if (nombreDel == null || nombreDel.trim().isEmpty()) break;
                            boolean eliminado = encontrado.removeCurso(nombreDel.trim());
                            if (eliminado) {
                                JOptionPane.showMessageDialog(this, "Curso eliminado.");
                            } else {
                                JOptionPane.showMessageDialog(this, "Curso no encontrado.");
                            }
                            break;
                        case 4: // Ver promedio
                            double promedio = encontrado.calcularPromedio();
                            String estatus = encontrado.getEstatus();
                            JPanel panelProm = new JPanel(new GridLayout(0, 1, 5, 5));
                            panelProm.setBackground(new Color(245, 250, 255));
                            panelProm.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                                javax.swing.BorderFactory.createLineBorder(new Color(180, 200, 255), 1, true),
                                javax.swing.BorderFactory.createEmptyBorder(10, 20, 10, 20)));
                            JLabel lProm = new JLabel(String.format("Promedio: %.2f", promedio));
                            lProm.setFont(new Font("Segoe UI", Font.BOLD, 16));
                            JLabel lEst = new JLabel("Estatus: " + estatus);
                            lEst.setFont(new Font("Segoe UI", Font.BOLD, 15));
                            panelProm.add(lProm);
                            panelProm.add(lEst);
                            JOptionPane.showMessageDialog(this, panelProm, "Promedio y Estatus", JOptionPane.INFORMATION_MESSAGE);
                            break;
                        default:
                            gestionando = false;
                    }
                }
            }
        });

        // Registrar estudiante
        btnRegistrar.addActionListener(_ -> {
            JPanel panel1 = new JPanel(new GridLayout(0, 2, 5, 5));
            panel1.setBackground(new Color(235, 242, 255));
            panel1.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(new Color(180, 200, 255), 2, true),
                javax.swing.BorderFactory.createEmptyBorder(10, 20, 10, 20)));
            JTextField ciField = new JTextField();
            JTextField nombreField = new JTextField();
            JTextField edadField = new JTextField();
            JComboBox<String> TcarreraCombo = new JComboBox<>(new String[]{"Administrativa", "Tecnológica"});
            JComboBox<String> carreraCombo = new JComboBox<>();
            JComboBox<String> semestreCombo = new JComboBox<>(new String[]{"2025-1"});

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
            TcarreraCombo.setSelectedIndex(0);

            JLabel lCI = new JLabel("CI:");
            lCI.setFont(new Font("Segoe UI", Font.PLAIN, 15));
            JLabel lNom = new JLabel("Nombre:");
            lNom.setFont(new Font("Segoe UI", Font.PLAIN, 15));
            JLabel lEdad = new JLabel("Edad:");
            lEdad.setFont(new Font("Segoe UI", Font.PLAIN, 15));
            JLabel lTC = new JLabel("Tipo de Carrera:");
            lTC.setFont(new Font("Segoe UI", Font.PLAIN, 15));
            JLabel lCarr = new JLabel("Carrera:");
            lCarr.setFont(new Font("Segoe UI", Font.PLAIN, 15));
            JLabel lSem = new JLabel("Semestre:");
            lSem.setFont(new Font("Segoe UI", Font.PLAIN, 15));
            panel1.add(lCI);
            panel1.add(ciField);
            panel1.add(lNom);
            panel1.add(nombreField);
            panel1.add(lEdad);
            panel1.add(edadField);
            panel1.add(lTC);
            panel1.add(TcarreraCombo);
            panel1.add(lCarr);
            panel1.add(carreraCombo);
            panel1.add(lSem);
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
            JPanel panelLista = new JPanel(new GridLayout(0, 1, 2, 2));
            panelLista.setBackground(new Color(245, 250, 255));
            panelLista.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(new Color(180, 200, 255), 1, true),
                javax.swing.BorderFactory.createEmptyBorder(10, 20, 10, 20)));
            JLabel cabecera = new JLabel(String.format("%-12s %-20s %-6s %-15s %-8s", "CI", "Nombre", "Edad", "Carrera", "Semestre"));
            cabecera.setFont(new Font("Segoe UI", Font.BOLD, 14));
            panelLista.add(cabecera);
            for (Estudiante est : copiaOrdenada) {
                JLabel fila = new JLabel(String.format("%-12d %-20s %-6d %-15s %-8d",
                    est.getCi(), est.getNombre(), est.getEdad(), est.getCarrera(), est.getSemestre()));
                fila.setFont(new Font("Segoe UI", Font.PLAIN, 13));
                panelLista.add(fila);
            }
            JOptionPane.showMessageDialog(this, panelLista, "Lista de Estudiantes", JOptionPane.INFORMATION_MESSAGE);
        });

        // Buscar estudiante
        btnBuscar.addActionListener(_ -> {
            JPanel panelBuscar = new JPanel(new GridLayout(0, 2, 5, 5));
            panelBuscar.setBackground(new Color(235, 242, 255));
            panelBuscar.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(new Color(180, 200, 255), 2, true),
                javax.swing.BorderFactory.createEmptyBorder(10, 20, 10, 20)));
            JTextField ciField = new JTextField();
            JLabel lCI = new JLabel("CI:");
            lCI.setFont(new Font("Segoe UI", Font.PLAIN, 15));
            panelBuscar.add(lCI);
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
            panelBuscar.setBackground(new Color(235, 242, 255));
            panelBuscar.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(new Color(180, 200, 255), 2, true),
                javax.swing.BorderFactory.createEmptyBorder(10, 20, 10, 20)));
            JTextField ciField = new JTextField();
            JLabel lCI = new JLabel("CI:");
            lCI.setFont(new Font("Segoe UI", Font.PLAIN, 15));
            panelBuscar.add(lCI);
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
                    panel1.setBackground(new Color(235, 242, 255));
                    panel1.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                        javax.swing.BorderFactory.createLineBorder(new Color(180, 200, 255), 2, true),
                        javax.swing.BorderFactory.createEmptyBorder(10, 20, 10, 20)));
                    JTextField nombreField = new JTextField(encontrado.getNombre());
                    JTextField edadField = new JTextField(String.valueOf(encontrado.getEdad()));
                    JComboBox<String> TcarreraCombo = new JComboBox<>(new String[]{"Administrativa", "Tecnológica"});
                    JComboBox<String> carreraCombo = new JComboBox<>();
                    JComboBox<String> semestreCombo = new JComboBox<>(new String[]{"2025-1"});

                    String carreraActual = encontrado.getCarrera();
                    String[] carrerasTec = {"Informática", "Electrónica", "Electricidad", "Mecánica", "Diseño de obras civiles", "Diseño gráfico", "Seguridad industrial"};
                    String[] carrerasAdm = {"Publicidad","Comercio exterior", "Administración", "Turismo", "Relaciones Industriales", "Secretaría"};
                    boolean esTec = false;
                    for (String c : carrerasTec) if (c.equals(carreraActual)) esTec = true;
                    TcarreraCombo.setSelectedItem(esTec ? "Tecnológica" : "Administrativa");
                    TcarreraCombo.addActionListener(e -> {
                        carreraCombo.removeAllItems();
                        if (TcarreraCombo.getSelectedItem().equals("Tecnológica")) {
                            for (String c : carrerasTec) carreraCombo.addItem(c);
                        } else {
                            for (String c : carrerasAdm) carreraCombo.addItem(c);
                        }
                    });
                    TcarreraCombo.setSelectedItem(esTec ? "Tecnológica" : "Administrativa");
                    carreraCombo.setSelectedItem(carreraActual);

                    JLabel lNom = new JLabel("Nombre:");
                    lNom.setFont(new Font("Segoe UI", Font.PLAIN, 15));
                    JLabel lEdad = new JLabel("Edad:");
                    lEdad.setFont(new Font("Segoe UI", Font.PLAIN, 15));
                    JLabel lTC = new JLabel("Tipo de Carrera:");
                    lTC.setFont(new Font("Segoe UI", Font.PLAIN, 15));
                    JLabel lCarr = new JLabel("Carrera:");
                    lCarr.setFont(new Font("Segoe UI", Font.PLAIN, 15));
                    JLabel lSem = new JLabel("Semestre:");
                    lSem.setFont(new Font("Segoe UI", Font.PLAIN, 15));
                    panel1.add(lNom);
                    panel1.add(nombreField);
                    panel1.add(lEdad);
                    panel1.add(edadField);
                    panel1.add(lTC);
                    panel1.add(TcarreraCombo);
                    panel1.add(lCarr);
                    panel1.add(carreraCombo);
                    panel1.add(lSem);
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
            panelBuscar.setBackground(new Color(235, 242, 255));
            panelBuscar.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(new Color(180, 200, 255), 2, true),
                javax.swing.BorderFactory.createEmptyBorder(10, 20, 10, 20)));
            JTextField ciField = new JTextField();
            JLabel lCI = new JLabel("CI:");
            lCI.setFont(new Font("Segoe UI", Font.PLAIN, 15));
            panelBuscar.add(lCI);
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
                    panelDatos.setBackground(new Color(245, 250, 255));
                    panelDatos.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                        javax.swing.BorderFactory.createLineBorder(new Color(180, 200, 255), 1, true),
                        javax.swing.BorderFactory.createEmptyBorder(10, 20, 10, 20)));
                    JLabel lCI2 = new JLabel("CI:");
                    lCI2.setFont(new Font("Segoe UI", Font.PLAIN, 15));
                    JLabel lNom2 = new JLabel("Nombre:");
                    lNom2.setFont(new Font("Segoe UI", Font.PLAIN, 15));
                    JLabel lEdad2 = new JLabel("Edad:");
                    lEdad2.setFont(new Font("Segoe UI", Font.PLAIN, 15));
                    JLabel lCarr2 = new JLabel("Carrera:");
                    lCarr2.setFont(new Font("Segoe UI", Font.PLAIN, 15));
                    JLabel lSem2 = new JLabel("Semestre:");
                    lSem2.setFont(new Font("Segoe UI", Font.PLAIN, 15));
                    panelDatos.add(lCI2);
                    panelDatos.add(new JLabel(String.valueOf(encontrado.getCi())));
                    panelDatos.add(lNom2);
                    panelDatos.add(new JLabel(encontrado.getNombre()));
                    panelDatos.add(lEdad2);
                    panelDatos.add(new JLabel(String.valueOf(encontrado.getEdad())));
                    panelDatos.add(lCarr2);
                    panelDatos.add(new JLabel(encontrado.getCarrera()));
                    panelDatos.add(lSem2);
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