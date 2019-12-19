package nl.utils.dewaal.pickatag;


import java.io.InputStream;

import nl.utils.dewaal.pickatag.ui.frmPickATagController;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * @author adewaal
 * 
 */
public class Main {

	private static Logger log = Logger.getLogger(Main.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		initLog4J();
		log.info("in de ge-edite versie");
		log.debug(System.getProperty("user.home"));
		new frmPickATagController();
		//log.info(Messages.getString("Main.messageTest")); //$NON-NLS-1$

	}
	
	private static void initLog4J()
	{
		InputStream is = Main.class.getResourceAsStream("/log4j.properties");
		PropertyConfigurator.configure(is);
	}

}
