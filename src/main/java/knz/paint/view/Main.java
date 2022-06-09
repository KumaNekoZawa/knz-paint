package knz.paint.view;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import knz.paint.model.Config;
import knz.paint.view.mainwindow.MainWindow;

public class Main {

    private Main() {
        super();
    }

    public static void main(String[] args) {
        if (Config.getConfig().printLookAndFeelInfo()) {
            for (final LookAndFeelInfo lookAndFeelInfo : UIManager.getInstalledLookAndFeels()) {
                System.err.println(lookAndFeelInfo.getName() + ": " + lookAndFeelInfo.getClassName());
            }
        }
        final String lookAndFeel = Config.getConfig().getLookAndFeel();
        if (lookAndFeel != null && !lookAndFeel.isEmpty()) {
            try {
                UIManager.setLookAndFeel(lookAndFeel);
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
                e.printStackTrace();
            }
        }
        new MainWindow();
    }

}
