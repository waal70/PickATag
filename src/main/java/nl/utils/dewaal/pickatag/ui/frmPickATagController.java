package nl.utils.dewaal.pickatag.ui;

import java.awt.Image;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.util.Timer;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import nl.utils.dewaal.pickatag.GetTagLineTask;
import nl.utils.dewaal.pickatag.PickATagData;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**This is the main controller class for the frmPAT. This class implements the methods that are called from the form, and also handles the database and file handlers.
 * It will display in the system tray (when available) and will periodically change the taglines in your e-mail signature-files.
 * The base for this is an MS ACCESS database, that is made available through the JDBC->ODBC bridge.
 */
public class frmPickATagController {
	private static PickATagData db = new PickATagData();
	//private static SignatureFileHandler fh = new SignatureFileHandler();
	private static frmPAT myForm = null;
	private static final Logger log = 
			LogManager.getLogger(frmPickATagController.class);
	public String currentTagLine = "";

	public frmPickATagController() {
		init();
	}

	public static void main(String[] args) {

	}

	private void init() {
		frmPAT myForm = doSystemTrayStuff();
		myForm.setMyController((frmPickATagController)this);
		myForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myForm.setVisible(true);
		if (myForm.getClass().getSuperclass().getSimpleName().equals("JFrameTray")) {
			log.debug("Special Constructor for JFramTray entered:");
			myForm.setTrayVisibilityMode(JFrameTray.TrayVisibilityMode.ALWAYS_VISIBLE);
			myForm.setTrayOnClose(false);
		}
		myForm.setStatusNormal();
		log.debug("End of init() method - Form init ready");
		//kickstartTimer();
	}

	private void kickstartTimer(long delay) {
		GetTagLineTask gtt = new GetTagLineTask();
		gtt.setMyController(this);
		Timer tmr = new Timer();

		//scheduleAtFixedRate (timerthread, long delay (ms), long period (ms))
		tmr.scheduleAtFixedRate(gtt, 1, delay);
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			log.error("Interrupted!");
		}
		//tmr.cancel();


	}

	private frmPAT doSystemTrayStuff() {
		log.debug("Entering System Tray preparation...");
		InputStream is = this.getClass().getResourceAsStream("/tag_blue_edit.png");
		Image img = null;
		try {
			img = ImageIO.read(is);
		} catch (Exception e) {
			log.error("Error retrieving picture. Think what to do...");
		}
		TrayIcon icon = new TrayIcon(img);
		myForm = new frmPAT(icon);
		icon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				log.debug("untray() called.");
				myForm.untray();
			}
		});
		log.debug("Succesful System Tray preparation.");
		return myForm;
	}

	public void iets() {
		this.currentTagLine = db.getTagLine();
		this.getMyForm().setStatus(currentTagLine);
		if (myForm.getClass().getSuperclass().getSimpleName().equals("JFrameTray")) {
			log.debug("Special JFrameTray constructor entered");
			TrayIcon currentIcon = this.getMyForm().getTrayIcon();
			currentIcon.setToolTip(currentTagLine);
		}
		log.debug(currentTagLine);
	}
	public void setTimer(){
		long lngDelay = 5 * 60 * 1000;
		log.debug("blaat");
		try{
			lngDelay = Long.parseLong(this.getMyForm().getTimerDelay());
			lngDelay = lngDelay * 60 * 1000;
		}
		catch (Exception e) {
			log.error("Value specified not cast-able to long. No number?");
			log.debug("Assuming default value of 5 minutes");
		}
		log.debug("Setting timer to: " + lngDelay + " milliseconds.");
		kickstartTimer(lngDelay);
	}

	private frmPAT getMyForm() {
		return myForm;
	}
}
