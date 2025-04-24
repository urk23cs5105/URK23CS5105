package hostelmanagement;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ManageFeesForm extends JFrame {

    private JComboBox<String> studentComboBox;
    private JTextField amountField, dueDateField, statusField;

    public ManageFeesForm() {
        setTitle("💸 Manage Fees");
        setSize(350, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(5, 2, 10, 10));
        getContentPane().setBackground(new Color(33, 33, 33));

        JLabel studentLabel = new JLabel("Select Student:");
        JLabel amountLabel = new JLabel("Fee Amount:");
        JLabel statusLabel = new JLabel("Status:");
        JLabel dueDateLabel = new JLabel("Due Date:");

        studentLabel.setForeground(Color.WHITE);
        amountLabel.setForeground(Color.WHITE);
        statusLabel.setForeground(Color.WHITE);
        dueDateLabel.setForeground(Color.WHITE);

        studentComboBox = new JComboBox<>();
        amountField = new JTextField();
        dueDateField = new JTextField();
        statusField = new JTextField();

        loadStudents();

        JButton manageButton = new JButton("Manage Fee");
        manageButton.setBackground(new Color(70, 130, 180));
        manageButton.setForeground(Color.WHITE);

        manageButton.addActionListener(e -> manageFee());

        add(studentLabel); add(studentComboBox);
        add(amountLabel); add(amountField);
        add(statusLabel); add(statusField);
        add(dueDateLabel); add(dueDateField);
        add(new JLabel()); add(manageButton);

        setVisible(true);
    }

    private void loadStudents() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT student_id, name FROM students");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                studentComboBox.addItem(rs.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void manageFee() {
        String studentName = (String) studentComboBox.getSelectedItem();
        double amount = Double.parseDouble(amountField.getText());
        String status = statusField.getText();
        String dueDate = dueDateField.getText();

        try (Connection conn = DatabaseConnection.getConnection()) {
            // Get student_id from student name
            PreparedStatement stmt1 = conn.prepareStatement("SELECT student_id FROM students WHERE name = ?");
            stmt1.setString(1, studentName);
            ResultSet rs1 = stmt1.executeQuery();
            int studentId = 0;
            if (rs1.next()) {
                studentId = rs1.getInt("student_id");
            }

            // Insert fee details
            PreparedStatement stmt2 = conn.prepareStatement("INSERT INTO fees (student_id, amount, status, due_date) VALUES (?, ?, ?, ?)");
            stmt2.setInt(1, studentId);
            stmt2.setDouble(2, amount);
            stmt2.setString(3, status);
            stmt2.setString(4, dueDate);
            stmt2.executeUpdate();
            JOptionPane.showMessageDialog(this, "Fee Updated Successfully!");
            amountField.setText("");
            statusField.setText("");
            dueDateField.setText("");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error Managing Fee!");
        }
    }
}
