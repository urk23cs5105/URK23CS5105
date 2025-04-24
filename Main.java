package hostelmanagement;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            // Use native system theme
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new HomePage());
    }
}
