package nl.utils.dewaal.pickatag.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import nl.utils.dewaal.pickatag.Messages;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


public class frmPAT extends JFrameTray {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BorderLayout layoutMain = new BorderLayout();
	private JPanel panelCenter = new JPanel();
	private JLabel statusBar = new JLabel();
	private Label lblWriteTo = new Label();
	private Button btnGetTag = new Button();
	private Button btnAbout = new Button();
	private static frmPickATagController myController = null;
	private static final Logger log = LogManager.getLogger(frmPAT.class);
	private JTextField txtTemplateFile = new JTextField();
	private Label lblTemplates = new Label();
	private JTextField txtTemplateFile1 = new JTextField();
	private JSeparator jSeparator1 = new JSeparator();
	private JTextField txtTemplateFile2 = new JTextField();
	private Button btnSetTimer = new Button();
	private Label lblWriteTo1 = new Label();


	/**The default constructor
	 */
	public frmPAT() {
		this(null);

	}

	/**Constructor takes a TrayIcon and initializes the form so that it'll contain the specified System Tray Icon.
	 * @param icon
	 */
	public frmPAT(TrayIcon icon) {
		super(icon);
		//super();
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void jbInit() throws Exception {
		this.getContentPane().setLayout(layoutMain);
		panelCenter.setLayout(null);

		this.setSize(new Dimension(742, 300));
		this.setResizable(false);
		this.setTitle(Messages.getString("title"));
		statusBar.setText(Messages.getString("loading"));

		lblWriteTo.setText("Hier de map");
		lblWriteTo.setBounds(new Rectangle(25, 200, 95, 15));
		btnGetTag.setLabel("Get TagLine!");
		btnGetTag.setBounds(new Rectangle(580, 165, 90, 20));
		btnGetTag.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnGetTag_actionPerformed(e);
			}
		});
		btnAbout.setLabel("About...");
		btnAbout.setBounds(new Rectangle(580, 195, 90, 20));
		btnAbout.setActionCommand("About...");
		btnAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnAbout_actionPerformed(e);
			}
		});
		txtTemplateFile.setBounds(new Rectangle(130, 195, 435, 20));
		txtTemplateFile.setText("C:\\Documents and Settings\\awaal\\Application Data\\Microsoft\\Signatures");
		lblTemplates.setText("Hier de templates");
		lblTemplates.setBounds(new Rectangle(25, 170, 95, 15));
		txtTemplateFile1.setBounds(new Rectangle(130, 165, 435, 20));
		txtTemplateFile1.setText("C:\\Documents and Settings\\awaal\\Application Data\\Microsoft\\Signatures");
		jSeparator1.setBounds(new Rectangle(0, 255, 735, 2));
		txtTemplateFile2.setBounds(new Rectangle(580, 105, 50, 20));
		txtTemplateFile2.setText("5");
		txtTemplateFile2.setEditable(true);
		btnSetTimer.setLabel("Set Timer");
		btnSetTimer.setBounds(new Rectangle(580, 135, 90, 20));
		btnSetTimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnSetTimer_actionPerformed(e);
			}
		});
		lblWriteTo1.setText("min.");
		lblWriteTo1.setBounds(new Rectangle(635, 110, 95, 15));
		this.getContentPane().add(panelCenter, BorderLayout.CENTER);
		this.getContentPane().add(statusBar, BorderLayout.SOUTH);
		panelCenter.add(lblWriteTo1, null);
		panelCenter.add(btnSetTimer, null);
		panelCenter.add(txtTemplateFile2, null);
		panelCenter.add(lblTemplates, null);
		panelCenter.add(txtTemplateFile1, null);
		panelCenter.add(txtTemplateFile, null);
		panelCenter.add(lblWriteTo, null);
		panelCenter.add(btnAbout, null);
		panelCenter.add(jSeparator1, null);
		panelCenter.add(btnGetTag, null);
		panelCenter.validate();
	}

	private void btnGetTag_actionPerformed(ActionEvent e) {
		log.debug(e.getActionCommand());
		this.getMyController().iets();
		//System.out.println(e.getActionCommand());
	}
	private void btnSetTimer_actionPerformed(ActionEvent e) {
		log.debug(e.getActionCommand());
		this.getMyController().setTimer();
		//System.out.println(e.getActionCommand());
	}

	/**ActionEvent Listener for the About button
	 * @param e
	 */
	private void btnAbout_actionPerformed(ActionEvent e) {
		setStatus(Messages.getString("showabout"));
		JOptionPane.showMessageDialog(this, new frmPAT_AboutBoxPanel1(), 
				Messages.getString("Abouttitle"), JOptionPane.PLAIN_MESSAGE);
		setStatusNormal();
		System.out.println(e.getActionCommand());
	}

	public void setStatus(String statustext) {
		if (!statustext.isEmpty())
			statusBar.setText(statustext);
	}

	public void setStatusNormal() {
		statusBar.setText(myController.currentTagLine);
	}

	public void setMyController(frmPickATagController myController) {
		frmPAT.myController = myController;
	}
	public String getTimerDelay (){
		return this.txtTemplateFile2.getText();
	}
	private frmPickATagController getMyController() {
		return myController;
	}


}

