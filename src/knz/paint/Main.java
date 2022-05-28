package knz.paint;

import javax.swing.UIManager;

import knz.paint.view.MainWindow;

public class Main {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }
        new MainWindow();
    }

}
