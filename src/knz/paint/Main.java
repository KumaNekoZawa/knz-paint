package knz.paint;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import knz.paint.model.Config;
import knz.paint.view.MainWindow;

public class Main {

    public static void main(String[] args) {
        if (Config.getConfig().printLookAndFeelInfo()) {
            for (LookAndFeelInfo lafi : UIManager.getInstalledLookAndFeels()) {
                System.err.println(lafi.getName() + ": " + lafi.getClassName());
            }
        }
        final String lookAndFeel = Config.getConfig().getLookAndFeel();
        if (lookAndFeel != null && !lookAndFeel.isEmpty()) {
            try {
                UIManager.setLookAndFeel(lookAndFeel);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        new MainWindow();
    }

}
