package hostelmanagement;

import javax.swing.*;
import java.awt.*;

public class HomePage extends JFrame {

    public HomePage() {
        setTitle("🏠 Hostel Management System");
        setSize(400, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(7, 1, 10, 10));

        JButton addRoom = new JButton("➕ Add Room");
        JButton viewRooms = new JButton("📄 View Rooms");
        JButton addStudent = new JButton("👨‍🎓 Add Student");
        JButton allocateRoom = new JButton("🛏️ Allocate Room");
        JButton manageFees = new JButton("💸 Manage Fees");
        JButton viewDetails = new JButton("📊 View Student Details");

        addRoom.addActionListener(e -> new AddRoomForm());
        viewRooms.addActionListener(e -> new ViewRoomsForm());
        addStudent.addActionListener(e -> new AddStudentForm());
        allocateRoom.addActionListener(e -> new AllocateRoomForm());
        manageFees.addActionListener(e -> new ManageFeesForm());
        viewDetails.addActionListener(e -> new ViewStudentDetailsForm());

        add(addRoom);
        add(viewRooms);
        add(addStudent);
        add(allocateRoom);
        add(manageFees);
        add(viewDetails);

        setVisible(true);
    }
}
