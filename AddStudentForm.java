package hostelmanagement;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddStudentForm extends JFrame {

    private JTextField nameField, emailField, phoneField, courseField;
    private JComboBox<String> genderComboBox;

    public AddStudentForm() {
        setTitle("👨‍🎓 Add Student");
        setSize(350, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(5, 2, 10, 10));
        getContentPane().setBackground(new Color(33, 33, 33));

        JLabel nameLabel = new JLabel("Name:");
        JLabel emailLabel = new JLabel("Email:");
        JLabel phoneLabel = new JLabel("Phone:");
        JLabel genderLabel = new JLabel("Gender:");
        JLabel courseLabel = new JLabel("Course:");

        nameLabel.setForeground(Color.WHITE);
        emailLabel.setForeground(Color.WHITE);
        phoneLabel.setForeground(Color.WHITE);
        genderLabel.setForeground(Color.WHITE);
        courseLabel.setForeground(Color.WHITE);

        nameField = new JTextField();
        emailField = new JTextField();
        phoneField = new JTextField();
        courseField = new JTextField();
        genderComboBox = new JComboBox<>(new String[]{"Male", "Female", "Other"});

        JButton addButton = new JButton("Add Student");
        addButton.setBackground(new Color(70, 130, 180));
        addButton.setForeground(Color.WHITE);

        addButton.addActionListener(e -> addStudent());

        add(nameLabel); add(nameField);
        add(emailLabel); add(emailField);
        add(phoneLabel); add(phoneField);
        add(genderLabel); add(genderComboBox);
        add(courseLabel); add(courseField);
        add(new JLabel()); add(addButton);

        setVisible(true);
    }

    private void addStudent() {
        String name = nameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String gender = (String) genderComboBox.getSelectedItem();
        String course = courseField.getText();

        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO students (name, email, phone, gender, course) VALUES (?, ?, ?, ?, ?)");
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, phone);
            stmt.setString(4, gender);
            stmt.setString(5, course);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Student Added Successfully!");
            nameField.setText("");
            emailField.setText("");
            phoneField.setText("");
            courseField.setText("");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error Adding Student!");
        }
    }
}
