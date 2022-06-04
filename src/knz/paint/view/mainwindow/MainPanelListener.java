package knz.paint.view.mainwindow;

public interface MainPanelListener {

    void mouseMoved(MainPanelMouseMovedEvent e);

    void sizeChanged(MainPanelSizeChangedEvent e);

    void toolStateChanged(MainPanelToolStateChangedEvent e);

}
