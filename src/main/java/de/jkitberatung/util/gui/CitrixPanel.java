package de.jkitberatung.util.gui;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class CitrixPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String LABEL_COMMENTS = "Comments:";
	private static final String LABEL_DOMAIN = "Domain:";
	private static final String LABEL_HOST = "Host:";
	private static final String LABEL_INITIAL_APP = "Citrix Application:";
	private static final String LABEL_NAME = "Name:";
	private static final String LABEL_USERNAME = "Username:";
	private static final String LABEL_USE_ICA_FILE = "Use ICA File";
	private static final String PROVIDE_DETAILS = "Provide Details";
	private static final String LABEL_ICA_FILE_PATH = "ICA File:";
	private static final String TITLE_CITRIX_SETTINGS_PANEL = "Citrix Server Settings";

	private KeyListener listener;

	private JTextField jtfComment;
	private JTextField jtfDomain;
	private JTextField jtfHost;
	private JTextField jtfInitialApp;
	private JTextField jtfName;
	private JTextField jtfPass;
	private JTextField jtfPort;
	private JTextField jtfUser;
	private JRadioButton jrbUseIcaFile;
	private JRadioButton jrbProvideDetails;
	private ButtonGroup jbgConnType;
	private JTextField jtfIcaFilePath;

	private String genericName;

	public CitrixPanel(String genericName, KeyListener listener) {

		this.genericName = genericName;
		this.listener = listener;

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		add(makeTitle());
		add(Box.createVerticalStrut(GuiUtil.STRUT_HEIGHT));
		add(makeTitlePanel());
		add(Box.createVerticalStrut(GuiUtil.STRUT_HEIGHT));
		add(makeSettingsPanel());
	}

	private Component makeSettingsPanel() {
		JLabel jlblProvideDetails = new JLabel(PROVIDE_DETAILS);
		jrbProvideDetails = new JRadioButton();
		JLabel jlblUseIcaFile = new JLabel(LABEL_USE_ICA_FILE);
		jrbUseIcaFile = new JRadioButton();
		jbgConnType = new ButtonGroup();
		jbgConnType.add(jrbProvideDetails);
		jbgConnType.add(jrbUseIcaFile);
		jrbProvideDetails.setEnabled(true);	
			
		jrbProvideDetails.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent e) {
				if(((JRadioButton) e.getSource()).isSelected()) {
					jtfIcaFilePath.setEnabled(false);
					jtfDomain.setEnabled(true);
			    	jtfHost.setEnabled(true);
			    	jtfInitialApp.setEnabled(true);
			    	jtfPass.setEnabled(true);
			    	jtfPort.setEnabled(true);
			    	jtfUser.setEnabled(true);
			    }
			}
		});
		
		jrbUseIcaFile.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent e) {
				if(((JRadioButton) e.getSource()).isSelected()) {
					jtfIcaFilePath.setEnabled(true);
					jtfDomain.setEnabled(false);
			    	jtfHost.setEnabled(false);
			    	jtfInitialApp.setEnabled(false);
			    	jtfPass.setEnabled(false);
			    	jtfPort.setEnabled(false);
			    	jtfUser.setEnabled(false);
			    }
			}
		});
		
		JLabel jlblIcaFilePath = new JLabel(LABEL_ICA_FILE_PATH);
		jtfIcaFilePath = new JTextField();
		jtfIcaFilePath.setEnabled(false);

		jtfHost = new JTextField();
		JLabel jlblHost = new JLabel(LABEL_HOST);
		jtfDomain = new JTextField();
		JLabel jlblDomain = new JLabel(LABEL_DOMAIN);
		jtfPort = new JTextField();
		JLabel jlblPort = new JLabel(GuiUtil.LABEL_PORT);
		jtfUser = new JTextField();
		JLabel jlblUser = new JLabel(LABEL_USERNAME);
		jtfPass = new JTextField();
		JLabel jlblPass = new JLabel(GuiUtil.LABEL_PASSWORD);
		jtfInitialApp = new JTextField();
		JLabel jlblInitialApp = new JLabel(LABEL_INITIAL_APP);

		JPanel panel = new JPanel(new GridBagLayout());

		panel.add(jlblUseIcaFile, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, GuiUtil.INSETS, 0, 0));
		
		panel.add(jrbUseIcaFile, new GridBagConstraints(3, 0, 1, 1, 1.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, GuiUtil.INSETS, 0, 0));
		
		panel.add(jlblIcaFilePath, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, GuiUtil.INSETS, 0, 0));
		
		panel.add(GuiUtil.getMandatoryPanel(jtfIcaFilePath), new GridBagConstraints(3, 1, 1, 1, 1.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, GuiUtil.INSETS, 0, 0));
		
		panel.add(jlblProvideDetails, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, GuiUtil.INSETS, 0, 0));
		
		panel.add(jrbProvideDetails, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, GuiUtil.INSETS, 0, 0));

		panel.add(jlblHost, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, GuiUtil.INSETS, 0, 0));

		panel.add(GuiUtil.getMandatoryPanel(jtfHost), new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, GuiUtil.INSETS, 0, 0));

		panel.add(jlblDomain, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, GuiUtil.INSETS, 0, 0));

		panel.add(jtfDomain, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, GuiUtil.INSETS, 0, 0));

		panel.add(jlblPort, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, GuiUtil.INSETS, 0, 0));

		panel.add(jtfPort, new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, GuiUtil.INSETS, 0, 0));

		panel.add(jlblUser, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, GuiUtil.INSETS, 0, 0));

		panel.add(GuiUtil.getMandatoryPanel(jtfUser), new GridBagConstraints(1, 4, 1, 1, 1.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, GuiUtil.INSETS, 0, 0));

		panel.add(jlblPass, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, GuiUtil.INSETS, 0, 0));

		panel.add(GuiUtil.getMandatoryPanel(jtfPass), new GridBagConstraints(1, 5, 1, 1, 1.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, GuiUtil.INSETS, 0, 0));

		panel.add(jlblInitialApp, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, GuiUtil.INSETS, 0, 0));

		panel.add(GuiUtil.getMandatoryPanel(jtfInitialApp), new GridBagConstraints(1, 6, 1, 1, 1.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, GuiUtil.INSETS, 0, 0));

		panel.setBorder(
				BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), TITLE_CITRIX_SETTINGS_PANEL));
		panel.setAlignmentX(LEFT_ALIGNMENT);

		return panel;
	}

	private JPanel makeTitlePanel() {
		jtfName = new JTextField();
		jtfName.setToolTipText(GuiUtil.TOOLTIP_NAME);
		jtfName.addKeyListener(listener);
		JLabel jlblName = new JLabel(LABEL_NAME);

		jtfComment = new JTextField();
		jtfComment.setToolTipText(GuiUtil.TOOLTIP_COMMENT);
		jtfComment.addKeyListener(listener);
		JLabel jlblComments = new JLabel(LABEL_COMMENTS);

		JPanel panel = new JPanel(new GridBagLayout());

		panel.add(jlblName, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, GuiUtil.INSETS, 0, 0));
		panel.add(jtfName, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, GuiUtil.INSETS, 0, 0));
		panel.add(jlblComments, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, GuiUtil.INSETS, 0, 0));
		panel.add(jtfComment, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, GuiUtil.INSETS, 0, 0));

		panel.setBorder(BorderFactory.createEtchedBorder());
		panel.setAlignmentX(LEFT_ALIGNMENT);

		return panel;
	}

	private JLabel makeTitle() {
		JLabel label = new JLabel(genericName);
		label.setFont(new Font(null, Font.BOLD, 16));
		label.setAlignmentX(LEFT_ALIGNMENT);
		return label;
	}

	public String getHost() {
		return jtfHost.getText().trim();
	}

	public String getDomain() {
		return jtfDomain.getText().trim();
	}

	public String getPort() {
		return jtfPort.getText().trim();
	}

	public String getUsername() {
		return jtfUser.getText().trim();
	}

	public String getPassword() {
		return jtfPass.getText().trim();
	}

	public String getInitialApp() {
		return jtfInitialApp.getText().trim();
	}

	public boolean getUseIcaFile() {
		return jrbUseIcaFile.isSelected();
	}

	public String getIcaFilePath() {
		return jtfIcaFilePath.getText().trim();
	}

	public void setName(String name) {
		jtfName.setText(name);
	}

	public void setComment(String comment) {
		jtfComment.setText(comment);
	}

	public void setHost(String icaAddress) {
		jtfHost.setText(icaAddress);
	}

	public void setDomain(String domain) {
		jtfDomain.setText(domain);
	}

	public void setPort(String port) {
		jtfPort.setText(port);
	}

	public void setUser(String username) {
		jtfUser.setText(username);
	}

	public void setPass(String password) {
		jtfPass.setText(password);
	}

	public void setInitialApp(String initialApp) {
		jtfInitialApp.setText(initialApp);
	}

	public void setUseIcaFile(boolean useIcaFile) {
		if(useIcaFile)
			jrbUseIcaFile.setSelected(true);
		else
			jrbProvideDetails.setSelected(true);
	}

	public void setIcaFilePath(String icaFile) {
		jtfIcaFilePath.setText(icaFile);
	}
}
