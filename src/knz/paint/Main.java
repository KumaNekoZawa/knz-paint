package knz.paint;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import knz.paint.view.MainWindow;

public class Main {

    public static void main(String[] args) {
        for (LookAndFeelInfo lafi : UIManager.getInstalledLookAndFeels()) {
            System.out.println(lafi.getName() + ": " + lafi.getClassName());
        }
        if (args.length > 0) {
            final String lookAndFeel = args[0];
            try {
                UIManager.setLookAndFeel(lookAndFeel);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        new MainWindow();
    }

}
