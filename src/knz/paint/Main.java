package knz.paint;

//import javax.swing.UIManager;

import knz.paint.view.MainWindow;

public class Main {

    public static void main(String[] args) {
        /* XXX Look & Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
        new MainWindow();
    }

}
