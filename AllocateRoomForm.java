package hostelmanagement;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class AllocateRoomForm extends JFrame {

    private JComboBox<String> studentComboBox, roomComboBox;
    private JTextField allocationDateField;

    public AllocateRoomForm() {
        setTitle("🛏️ Allocate Room");
        setSize(350, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(4, 2, 10, 10));
        getContentPane().setBackground(new Color(33, 33, 33));

        JLabel studentLabel = new JLabel("Select Student:");
        JLabel roomLabel = new JLabel("Select Room:");
        JLabel allocationDateLabel = new JLabel("Allocation Date:");

        studentLabel.setForeground(Color.WHITE);
        roomLabel.setForeground(Color.WHITE);
        allocationDateLabel.setForeground(Color.WHITE);

        studentComboBox = new JComboBox<>();
        roomComboBox = new JComboBox<>();
        allocationDateField = new JTextField();

        loadStudents();
        loadRooms();

        JButton allocateButton = new JButton("Allocate Room");
        allocateButton.setBackground(new Color(70, 130, 180));
        allocateButton.setForeground(Color.WHITE);

        allocateButton.addActionListener(e -> allocateRoom());

        add(studentLabel); add(studentComboBox);
        add(roomLabel); add(roomComboBox);
        add(allocationDateLabel); add(allocationDateField);
        add(new JLabel()); add(allocateButton);

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

    private void loadRooms() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT room_id, room_number FROM rooms");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                roomComboBox.addItem(rs.getString("room_number"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void allocateRoom() {
        String studentName = (String) studentComboBox.getSelectedItem();
        String roomNumber = (String) roomComboBox.getSelectedItem();
        String allocationDate = allocationDateField.getText();

        try (Connection conn = DatabaseConnection.getConnection()) {
            // Get student_id from student name
            PreparedStatement stmt1 = conn.prepareStatement("SELECT student_id FROM students WHERE name = ?");
            stmt1.setString(1, studentName);
            ResultSet rs1 = stmt1.executeQuery();
            int studentId = 0;
            if (rs1.next()) {
                studentId = rs1.getInt("student_id");
            }

            // Get room_id from room number
            PreparedStatement stmt2 = conn.prepareStatement("SELECT room_id FROM rooms WHERE room_number = ?");
            stmt2.setString(1, roomNumber);
            ResultSet rs2 = stmt2.executeQuery();
            int roomId = 0;
            if (rs2.next()) {
                roomId = rs2.getInt("room_id");
            }

            // Insert allocation
            PreparedStatement stmt3 = conn.prepareStatement("INSERT INTO allocations (student_id, room_id, allocation_date) VALUES (?, ?, ?)");
            stmt3.setInt(1, studentId);
            stmt3.setInt(2, roomId);
            stmt3.setString(3, allocationDate);
            stmt3.executeUpdate();
            JOptionPane.showMessageDialog(this, "Room Allocated Successfully!");
            allocationDateField.setText("");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error Allocating Room!");
        }
    }
}
