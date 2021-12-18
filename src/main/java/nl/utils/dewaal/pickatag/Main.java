package nl.utils.dewaal.pickatag;


import nl.utils.dewaal.pickatag.ui.frmPickATagController;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author adewaal
 * 
 */
public class Main {

	private static Logger log = LogManager.getLogger(Main.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		log.info("in de ge-edite versie");
		log.debug(System.getProperty("user.home"));
		new frmPickATagController();
		//log.info(Messages.getString("Main.messageTest")); //$NON-NLS-1$

	}
	
}
