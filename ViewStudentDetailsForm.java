package hostelmanagement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ViewStudentDetailsForm extends JFrame {

    JTable table;

    public ViewStudentDetailsForm() {
        setTitle("📊 View Student Details");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(33, 33, 33));

        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        loadStudentDetails();
        setVisible(true);
    }

    private void loadStudentDetails() {
        DefaultTableModel model = new DefaultTableModel(new String[]{"Student Name", "Room Number", "Fee Amount", "Fee Status"}, 0);
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM student_room_fee_view");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("student_name"),
                        rs.getString("room_number"),
                        rs.getDouble("fee_amount"),
                        rs.getString("fee_status")
                });
            }
            table.setModel(model);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
