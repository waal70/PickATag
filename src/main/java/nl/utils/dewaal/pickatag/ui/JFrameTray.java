package nl.utils.dewaal.pickatag.ui;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


/**
 * When enabling the usage of the System Tray for an application, the most
 * annoying part definitely is to make sure that an application will behave
 * normally on platforms which do not have a System Tray!
 * 
 * This JFrameTray class (yes I know the name is pretty ugly) will minimize
 * to the SystemTray according to the settings you are using (goes to tray
 * when window is minimized or closed, or both, or none) using the
 * TrayVisibilityMode you wish (Always visible, Never visible, Visible when
 * JFrame isn't) on platforms which provide a SystemTray, and will use the
 * defaultCloseOperation otherwise.
 * 
 * @author André de Waal
 */
public class JFrameTray extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger log = LogManager.getLogger(JFrameTray.class);
    /**
	 *
	 */

    /**
     * Indicates whether System Tray support is enabled
     */
    private boolean canTray;

    /**
     * Indicates whether the JFrame should minimize to tray with the Close button
     */
    private boolean trayOnClose = false;

    /**
     * Indicates whether the JFrame should minimize to tray with the Minimize button
     */
    private boolean trayOnMinimize = true;

    /**
     * Overrides the defaultCloseOperation from JFrame
     */
    private int defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE;

    /**
     * The SystemTray.
     */
    private SystemTray tray;

    /**
     * The TrayIcon associated with this JFrame.
     */
    private TrayIcon trayIcon;

    /**
     * The TrayVisibilityMode used to display the TrayIcon.
     */
    private TrayVisibilityMode trayVisibilityMode;

    public JFrameTray() {
        super();
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructs a default JFrame initially invisible with a SystemTray invisible
     * as well.
     * 
     * @param trayIcon the TrayIcon to be displayed in the SystemTray.
     */
    public JFrameTray(TrayIcon trayIcon) {
        this.trayIcon = trayIcon;
        initDefaults();
    }

    /**
     * Constructs a default JFrame initially invisible with a SystemTray invisible
     * as well with the specified title.
     * 
     * @param s the title for the frame
     * @param trayIcon the TrayIcon to be displayed in the SystemTray.
     */
    public JFrameTray(String s, TrayIcon trayIcon) {
        super(s);
        this.trayIcon = trayIcon;
        initDefaults();
    }

    /**
     * Creates a Frame in the specified GraphicsConfiguration of a screen device and a blank title.
     * The SystemTray is initially invisible.
     * 
     * @param gc the GraphicsConfiguration that is used to construct the new Frame; if gc is null, the system default GraphicsConfiguration is assumed
     * @param trayIcon the TrayIcon to be displayed in the SystemTray.
     */
    public JFrameTray(GraphicsConfiguration gc, TrayIcon trayIcon) {
        super(gc);
        this.trayIcon = trayIcon;
        initDefaults();
    }

    /**
     * Creates a JFrame with the specified title and the specified GraphicsConfiguration of a screen device.
     * The SystemTray is initially invisible.
     * 
     * @param s the title for the frame
     * @param gc the GraphicsConfiguration that is used to construct the new Frame; if gc is null, the system default GraphicsConfiguration is assumed
     * @param trayIcon the TrayIcon to be displayed in the SystemTray.
     */
    public JFrameTray(String s, GraphicsConfiguration gc, TrayIcon trayIcon) {
        super(s, gc);
        this.trayIcon = trayIcon;
        initDefaults();
    }

    /**
     * Initializes the SystemTray support.
     */
    private void initDefaults() {
        addWindowListener(new AndreWindowAdapter());
        super.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        try {
            tray = SystemTray.getSystemTray();
            canTray = true;
            setTrayVisibilityMode(JFrameTray.TrayVisibilityMode.NEVER_VISIBLE);
        } catch (UnsupportedOperationException e) {
            canTray = false;
        } catch (SecurityException e) {
            canTray = false;
        }
    }

    /**
     * This overrides the JFrame's defaultCloseOperation so that we can
     * support both behaviors (minimize to tray and defaultCloseOperation)
     * with the same code on systems with a SystemTray and systems without
     * one.
     */
    private void defaultCloseOperation() {
        switch (defaultCloseOperation) {
        case WindowConstants.DISPOSE_ON_CLOSE:
            dispose();
            break;
        case WindowConstants.EXIT_ON_CLOSE:
            System.exit(0);
            break;
        case WindowConstants.HIDE_ON_CLOSE:
            setVisible(false);
        default:
        }
    }

    /**
     * We override the JFrame's defaultCloseOperation so that we can handle
     * it ourselves.
     */
    @Override
    public void setDefaultCloseOperation(int operation) {
        defaultCloseOperation = operation;
    }

    /**
     * When setVisible is used, we have to react accordingly in case
     * the TrayVisibilityMode requires some changes to be made.
     */
    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        if (canTray) {
            trayVisibilityMode.init(this, tray);
        }
    }

    /**
     * Minimizes the JFrame to the SystemTray.
     * 
     * @return true if we could minimize to SystemTray, false otherwise
     */
    public boolean tray() {
        if (!canTray) {
            return false;
        }
        return trayVisibilityMode.tray(this, tray);
    }

    /**
     * Restores the JFrame from the SystemTray.
     */
    public void untray() {
        if (!canTray) {
            return;
        }
        trayVisibilityMode.untray(this, tray);
        if ((getExtendedState() & ICONIFIED) == ICONIFIED) {
            setExtendedState(NORMAL);
        }
    }

    /**
     * Indicates whether or not the TrayIcon for this JFrame
     * is currently visible or not.
     */
    public boolean isTrayIconVisible() {
        if (!canTray) {
            return false;
        }
        TrayIcon[] icons = tray.getTrayIcons();
        for (TrayIcon i: icons) {
            if (i.equals(trayIcon)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Indicates whether or not this JFrame will minimize to tray when
     * the the window is closed.
     * @return true if this JFrame minimizes to tray when the window is closed, false otherwise
     */
    public boolean isTrayOnClose() {
        return trayOnClose;
    }

    /**
     * Sets whether or not this JFrame will minimize to tray when
     * the the window is closed.
     * @param trayOnClose true if this JFrame minimizes to tray when the window is closed, false otherwise
     */
    public void setTrayOnClose(boolean trayOnClose) {
        this.trayOnClose = trayOnClose;
    }

    /**
     * Indicates whether or not this JFrame will minimize to tray when
     * the the window is iconified.
     * @return true if this JFrame minimizes to tray when the window is iconified, false otherwise
     */
    public boolean isTrayOnMinimize() {
        return trayOnMinimize;
    }

    /**
     * Sets whether or not this JFrame will minimize to tray when
     * the the window is iconified.
     * @param trayOnMinimize true if this JFrame minimizes to tray when the window is iconified, false otherwise
     */
    public void setTrayOnMinimize(boolean trayOnMinimize) {
        this.trayOnMinimize = trayOnMinimize;
    }

    /**
     * Sets the TrayVisibilityMode to be used.
     * 
     * @param trayVisibilityMode One of TrayVisibilityMode.ALWAYS_VISIBLE, TrayVisibilityMode.NEVER_VISIBLE and TrayVisibilityMode.VISIBLE_WHEN_WINDOW_IS_NOT
     */
    public void setTrayVisibilityMode(TrayVisibilityMode trayVisibilityMode) {
        this.trayVisibilityMode = trayVisibilityMode;
        if (canTray) {
            this.trayVisibilityMode.init(this, tray);
        }
    }

    public void setTrayIcon(TrayIcon trayIcon) {
        this.trayIcon = trayIcon;
    }

    public TrayIcon getTrayIcon() {
        return trayIcon;
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(395, 311));
    }

    /**
     * 
     * @author Gabriel Aubut-Lussier
     */
    public enum TrayVisibilityMode {
        /**
         * The TrayIcon will always be displayed in the SystemTray.
         */
        ALWAYS_VISIBLE {
            public void init(JFrameTray window, SystemTray tray) {
                if (!window.isTrayIconVisible()) {
                    try {
                        tray.add(window.trayIcon);
                    } catch (AWTException e) {
                        window.canTray = false;
                    }
                }
            }

            public boolean tray(JFrameTray window, SystemTray tray) {
                window.setVisible(false);
                return true;
            }

            public void untray(JFrameTray window, SystemTray tray) {
                window.setVisible(true);
            }
        },

        /**
         * The TrayIcon will only be displayed when the JFrame is hidden.
         */
        VISIBLE_WHEN_WINDOW_IS_NOT {
            public void init(JFrameTray window, SystemTray tray) {
                if (window.isTrayIconVisible()) {
                    if (window.isVisible()) {
                        tray.remove(window.trayIcon);
                    }
                } else {
                    if (!window.isVisible()) {
                        try {
                            tray.add(window.trayIcon);
                        } catch (AWTException e) {
                            window.canTray = false;
                        }
                    }
                }
            }

            public boolean tray(JFrameTray window, SystemTray tray) {
                log.debug("tray()-ing window...");
                try {
                    tray.add(window.trayIcon);
                    window.setVisible(false);
                    return true;
                } catch (AWTException e) {
                    window.canTray = false;
                    return false;
                }
            }

            public void untray(JFrameTray window, SystemTray tray) {
                tray.remove(window.trayIcon);
                window.setVisible(true);
            }
        },

        /**
         * The TrayIcon will never be displayed.
         */
        NEVER_VISIBLE {
            public void init(JFrameTray window, SystemTray tray) {
                if (window.isTrayIconVisible()) {
                    tray.remove(window.trayIcon);
                }

            }

            public boolean tray(JFrameTray window, SystemTray tray) {
                return false;
            }

            public void untray(JFrameTray window, SystemTray tray) {

            }
        },
        ;

        /**
         * Makes sure the SystemTray and the JFrame are both in proper states according to this TrayVisibilityMode.
         * @param window The JFrameTray to sync with the SystemTray.
         * @param tray The SystemTray to sync with the JFrameTray.
         */
        public abstract void init(JFrameTray window, SystemTray tray);

        /**
         * Minimizes the JFrame to tray according to this TrayVisibilityMode.
         * @param window The JFrameTray to sync with the SystemTray.
         * @param tray The SystemTray to sync with the JFrameTray.
         * @return true true if we could minimize to SystemTray, false otherwise
         */
        public abstract boolean tray(JFrameTray window, SystemTray tray);

        /**
         * Restores the JFrame from tray according to this TrayVisibilityMode.
         * @param window The JFrameTray to sync with the SystemTray.
         * @param tray The SystemTray to sync with the JFrameTray.
         */
        public abstract void untray(JFrameTray window, SystemTray tray);
    }

    /**
     * This Listener makes it possible to minimize to tray as well as
     * overriding the defaultCloseOperation behavior.
     * 
     * @author Gabriel Aubut-Lussier
     */
    private class AndreWindowAdapter extends WindowAdapter {
        /**
         * Attempts to minimize to tray or uses the defaultCloseOperation otherwise.
         */
        public
        //		@Override
        void windowClosing(WindowEvent e) {
            if (canTray && trayOnClose) {
                if (!tray()) {
                    defaultCloseOperation();
                }
            } else {
                defaultCloseOperation();
            }
        }

        /**
         * Attempts to minimize to tray.
         */
        public
        //		@Override
        void windowIconified(WindowEvent e) {
            if (canTray && trayOnMinimize) {
                tray();
            }
        }
    }
}
//The following demonstration shows you a JFrame which will minimize to tray using the close button where a SystemTray is supported, and will terminate the application where it isn't. 

