/**
 * Main class. Starts the whole process.
 */
public class Driver {
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                adminUI.getUI().setVisible(true);
            }
        });
    }
}
