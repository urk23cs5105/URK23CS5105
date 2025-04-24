package hostelmanagement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ViewRoomsForm extends JFrame {

    JTable table;

    public ViewRoomsForm() {
        setTitle("📄 View Rooms");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(33, 33, 33));

        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        loadRoomData();
        setVisible(true);
    }

    private void loadRoomData() {
        DefaultTableModel model = new DefaultTableModel(new String[]{"Room Number", "Capacity"}, 0);
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT room_number, capacity FROM rooms");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{rs.getString("room_number"), rs.getInt("capacity")});
            }
            table.setModel(model);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
