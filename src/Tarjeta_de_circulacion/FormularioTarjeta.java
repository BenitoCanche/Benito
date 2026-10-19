package Tarjeta_de_circulacion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import java.sql.PreparedStatement;
import javax.swing.table.DefaultTableModel;
import java.sql.Statement;
import java.sql.ResultSet;

public class FormularioTarjeta extends JFrame implements ActionListener {

    JMenuBar bm;
    JMenu mArchivo, mBD;
    JMenuItem mBD_C, mBD_R, mBD_U, mBD_D;

    JLabel lb1, lbId, lbNumero, lbAsientos, lbModelo, lbMotor;
    JTextField txtId, txtNumero, txtAsientos, txtModelo, txtMotor;
    JButton insertar, consultar, actualizar, eliminar;
    JTable tabla = new JTable();
    JScrollPane scrollTabla;

    /**
     *
     */
    public FormularioTarjeta() {
        this.setBounds(150, 50, 850, 650);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setTitle("Gestion de Tarjeta de Circulacion");

        bm = new JMenuBar();
        this.setJMenuBar(bm);
        this.add(bm);

        mArchivo = new JMenu("Archivo");
        bm.add(mArchivo);

        mBD = new JMenu("Base de datos");
        bm.add(mBD);

        mBD_C = new JMenuItem("Insertar");
        mBD_C.addActionListener(e -> insertarRegistro());
        mBD.add(mBD_C);

        mBD_R = new JMenuItem("Consultar");
        mBD_R.addActionListener(e -> consultarRegistros());
        mBD.add(mBD_R);

        mBD_U = new JMenuItem("Actualizar");
        mBD_U.addActionListener(e -> actualizarRegistro());
        mBD.add(mBD_U);

        mBD_D = new JMenuItem("Eliminar");
        mBD_D.addActionListener(e -> eliminarRegistro());
        mBD.add(mBD_D);

        this.lb1 = new JLabel();
        this.lb1.setBounds(300, 15, 300, 30);
        this.lb1.setText("Welcome al Formulario de la Tarjeta de Circulacion");
        this.add(lb1);

        this.lbId = new JLabel("Id");
        this.lbId.setBounds(50, 70, 100, 30);
        this.add(lbId);

        txtId = new JTextField();
        txtId.setBounds(150, 70, 200, 30);
        this.add(txtId);

        this.lbNumero = new JLabel("Numero");
        this.lbNumero.setBounds(50, 120, 100, 30);
        this.add(lbNumero);

        txtNumero = new JTextField();
        txtNumero.setBounds(150, 120, 200, 30);
        this.add(txtNumero);

        this.lbAsientos = new JLabel("Asientos");
        this.lbAsientos.setBounds(50, 170, 100, 30);
        this.add(lbAsientos);

        txtAsientos = new JTextField();
        txtAsientos.setBounds(150, 170, 200, 30);
        this.add(txtAsientos);

        this.lbModelo = new JLabel("Modelo");
        this.lbModelo.setBounds(450, 70, 100, 30);
        this.add(lbModelo);

        txtModelo = new JTextField();
        txtModelo.setBounds(530, 70, 200, 30);
        this.add(txtModelo);

        this.lbMotor = new JLabel("Motor");
        this.lbMotor.setBounds(450, 120, 100, 30);
        this.add(lbMotor);

        txtMotor = new JTextField();
        txtMotor.setBounds(530, 120, 200, 30);
        this.add(txtMotor);

        insertar = new JButton("Insertar");
        insertar.setBounds(80, 230, 130, 35);
        insertar.addActionListener(this);
        this.add(insertar);

        consultar = new JButton("Consultar");
        consultar.setBounds(230, 230, 130, 35);
        consultar.addActionListener(this);
        this.add(consultar);

        actualizar = new JButton("Actualizar");
        actualizar.setBounds(380, 230, 130, 35);
        actualizar.addActionListener(this);
        this.add(actualizar);

        eliminar = new JButton("Eliminar");
        eliminar.setBounds(530, 230, 130, 35);
        eliminar.addActionListener(this);
        this.add(eliminar);

        // --- TABLA Y SCROLL ---
        tabla = new JTable();
        scrollTabla = new JScrollPane(tabla);
        scrollTabla.setBounds(50, 290, 735, 280);
        this.add(scrollTabla);

        consultarRegistros();

        this.setVisible(true);
    }

    public static void main(String[] args) {
        FormularioTarjeta formularioTarjeta = new FormularioTarjeta();
    }

    @Override

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.insertar) {
            System.out.println("Se inserto");
            insertarRegistro();
        }
        if (e.getSource() == this.consultar) {
            System.out.println("Se consulto");
            consultarRegistros();
        }
        if (e.getSource() == this.actualizar) {
            System.out.println("Se actualizo");
            actualizarRegistro();
        }
        if (e.getSource() == this.eliminar) {
            System.out.println("Se elimino");
            eliminarRegistro();
        }
    }

    private void insertarRegistro() {
        String sql = "INSERT INTO tarjeta (id, numero, asientos, modelo, motor) VALUES (?, ?, ?, ?, ?)";
        try (java.sql.Connection con = Conexion.conectar(); PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, Integer.parseInt(txtId.getText().trim()));
            pst.setString(2, txtNumero.getText().trim());
            pst.setInt(3, Integer.parseInt(txtAsientos.getText().trim()));
            pst.setString(4, txtModelo.getText().trim());
            pst.setString(5, txtMotor.getText().trim());

            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Registro insertado exitosamente.");
            consultarRegistrosTabla();
            limpiarCampos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al insertar: " + ex.getMessage());
        }
    }

    private void consultarRegistrosTabla() {
        DefaultTableModel modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Número");
        modeloTabla.addColumn("Asientos");
        modeloTabla.addColumn("Modelo");
        modeloTabla.addColumn("Motor");

        String sql = "SELECT * FROM tarjeta";
        try (java.sql.Connection con = Conexion.conectar(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Object[] fila = new Object[5];
                fila[0] = rs.getInt("id");
                fila[1] = rs.getString("numero");
                fila[2] = rs.getInt("asientos");
                fila[3] = rs.getString("modelo");
                fila[4] = rs.getString("motor");
                modeloTabla.addRow(fila);
            }
            tabla.setModel(modeloTabla);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar la tabla: " + ex.getMessage());
        }
    }

    private void actualizarRegistro() {
        String sql = "UPDATE tarjeta SET numero = ?, asientos = ?, modelo = ?, motor = ? WHERE id = ?";
        try (java.sql.Connection con = Conexion.conectar(); PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, txtNumero.getText().trim());
            pst.setInt(2, Integer.parseInt(txtAsientos.getText().trim()));
            pst.setString(3, txtModelo.getText().trim());
            pst.setString(4, txtMotor.getText().trim());
            pst.setInt(5, Integer.parseInt(txtId.getText().trim()));

            int filasAfectadas = pst.executeUpdate();
            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(this, "Registro actualizado exitosamente.");
                consultarRegistrosTabla();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo actualizar. Verifique el ID.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar: " + ex.getMessage());
        }
    }

    private void eliminarRegistro() {
        String idStr = txtId.getText().trim();
        if (idStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese o consulte el ID que desea eliminar.");
            return;
        }

        String sql = "DELETE FROM tarjeta WHERE id = ?";
        try (java.sql.Connection con = Conexion.conectar(); PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, Integer.parseInt(idStr));
            int filasAfectadas = pst.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(this, "Registro eliminado exitosamente.");
                consultarRegistrosTabla();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró un registro con ese ID.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al eliminar: " + ex.getMessage());
        }
    }

    private void consultarRegistros() {
        consultarRegistrosTabla();

    }

    private void limpiarCampos() {
        txtId.setText("");
        txtNumero.setText("");
        txtAsientos.setText("");
        txtModelo.setText("");
        txtMotor.setText("");
        txtId.requestFocus();
    }
}
