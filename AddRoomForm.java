package hostelmanagement;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddRoomForm extends JFrame {

    private JTextField roomNumberField, capacityField;

    public AddRoomForm() {
        setTitle("➕ Add Room");
        setSize(350, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(3, 2, 10, 10));
        getContentPane().setBackground(new Color(33, 33, 33));

        JLabel roomLabel = new JLabel("Room Number:");
        JLabel capacityLabel = new JLabel("Capacity:");
        roomLabel.setForeground(Color.WHITE);
        capacityLabel.setForeground(Color.WHITE);

        roomNumberField = new JTextField();
        capacityField = new JTextField();
        JButton addButton = new JButton("Add Room");
        addButton.setBackground(new Color(70, 130, 180));
        addButton.setForeground(Color.WHITE);

        addButton.addActionListener(e -> addRoom());

        add(roomLabel); add(roomNumberField);
        add(capacityLabel); add(capacityField);
        add(new JLabel()); add(addButton);

        setVisible(true);
    }

    private void addRoom() {
        String roomNumber = roomNumberField.getText();
        int capacity = Integer.parseInt(capacityField.getText());

        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO rooms (room_number, capacity) VALUES (?, ?)");
            stmt.setString(1, roomNumber);
            stmt.setInt(2, capacity);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Room Added Successfully!");
            roomNumberField.setText("");
            capacityField.setText("");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error Adding Room!");
        }
    }
}
