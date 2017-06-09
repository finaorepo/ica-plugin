package de.jkitberatung.player;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import org.apache.jmeter.control.GenericController;
import org.apache.jmeter.samplers.Sampler;
import org.apache.jmeter.threads.JMeterContextService;

import de.jkitberatung.recorder.RecordingStep;
import de.jkitberatung.util.IcaConnector;
import de.jkitberatung.util.InteractionUtil;


public class PlaySamplerControl extends GenericController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4000209595000276037L;

	private static final String ADDRESS = "PlaySamplerControl.ica_address";
	private static final String USERNAME = "PlaySamplerControl.username";
	private static final String PASSWORD = "PlaySamplerControl.password";
	private static final String INITIAL_APP = "PlaySamplerControl.initial_app";
	private static final String DOMAIN = "PlaySamplerControl.domain";
	private static final String PORT = "PlaySamplerControl.port";
	private static final String USE_ICA_FILE = "RecordControl.use_ica_file";
	private static final String ICA_FILE_PATH = "RecordControl.ica_file_path";
	private static final String INTERACTION_PATH = "PlaySamplerControl.interactions_path";
	private static final String SCREENSHOTS_PATH = "PlaySamplerControl.screenshots_path";
	private static final String SCREENSHOTS_FOLDER_PATH = "PlaySamplerControl.screenshots_folder_path";
	private static final String SLEEP_FACTOR = "PlaySamplerControl.sleep_factor";
	private static final String USE_SLEEP_TIMES = "PlaySamplerControl.use_sleep_times";
	private static final String RUNNING_MODE = "PlaySamplerControl.running_mode";
	
	private IcaConnector icaConnector;
	
	private static final Logger L =Logger.getLogger(PlaySamplerControl.class.getName());

	public void setDomain(String domain) {
		setProperty(DOMAIN, domain);
	}

	public String getDomain() {
		return getPropertyAsString(DOMAIN);
	}

	public void setPort(String port) {
		setProperty(PORT, port);
	}

	public String getPort() {
		return getPropertyAsString(PORT);
	}
	
	public boolean getUseIcaFile() {
		return getPropertyAsBoolean(USE_ICA_FILE);
	}
	
	public String getIcaFilePath() {
		return getPropertyAsString(ICA_FILE_PATH);
	}

	public void setPassword(String password) {
		setProperty(PASSWORD, password);
	}

	public String getPassword() {
		return getPropertyAsString(PASSWORD);
	}

	public void setUsername(String username) {
		setProperty(USERNAME, username);
	}

	public String getUsername() {
		return getPropertyAsString(USERNAME);
	}

	public void setIcaAddress(String icaAddress) {
		setProperty(ADDRESS, icaAddress);
	}

	public String getIcaAddress() {		
		return getPropertyAsString(ADDRESS);
	}

	public void setInitialApp(String initialApp) {
		setProperty(INITIAL_APP, initialApp);
	}

	public String getInitialApp() {
		return getPropertyAsString(INITIAL_APP);
	}

	public void setInteractionPath(String path) {
		setProperty(INTERACTION_PATH, path);
	}
	
	public String getInteractionsPath() {
		return getPropertyAsString(INTERACTION_PATH);
	}
	
	public void setScreenshotsHashesFilePath(String path) {
		setProperty(SCREENSHOTS_PATH, path);
	}
	
	public String getScreenshotsHashesFilePath() {
		return getPropertyAsString(SCREENSHOTS_PATH);
	}
	
	public void setScreenshotsFolderPath(String path) {
		setProperty(SCREENSHOTS_FOLDER_PATH, path);
	}
	
	public String getScreenshotsFolderPath() {
		return getPropertyAsString(SCREENSHOTS_FOLDER_PATH);
	}	

	public void setSleepFactor(String sleepFactor) {
		setProperty(SLEEP_FACTOR, sleepFactor);
	}

	public String getSleepFactor() {
		return getPropertyAsString(SLEEP_FACTOR);
	}	

	public void setSleepTimes(boolean checkBoxSelected) {
		setProperty(USE_SLEEP_TIMES, checkBoxSelected);
	}
	
	public void setUseIcaFile(boolean useIcaFile) {
		setProperty(USE_ICA_FILE, useIcaFile);
	}
	
	public void setIcaFilePath(String icaFilePath) {
		setProperty(ICA_FILE_PATH, icaFilePath);
	}

	public boolean useSleepTimes() {
		return getPropertyAsBoolean(USE_SLEEP_TIMES);
	}

    @Override
    public Object clone() {
    	
    	Object clone = super.clone();
		
    	((PlaySamplerControl) clone).setDomain(getDomain());
    	((PlaySamplerControl) clone).setPort(getPort());
    	((PlaySamplerControl) clone).setPassword(getPassword());
    	((PlaySamplerControl) clone).setUsername(getUsername());
    	((PlaySamplerControl) clone).setIcaAddress(getIcaAddress());
    	((PlaySamplerControl) clone).setInitialApp(getInitialApp());
    	((PlaySamplerControl) clone).setInteractionPath(getInteractionsPath());
    	((PlaySamplerControl) clone).setScreenshotsHashesFilePath(getScreenshotsHashesFilePath());
    	((PlaySamplerControl) clone).setScreenshotsFolderPath(getScreenshotsFolderPath());
    	((PlaySamplerControl) clone).setSleepFactor(getSleepFactor());
    	((PlaySamplerControl) clone).setSleepTimes(getSleepTimes());
    	((PlaySamplerControl) clone).setUseIcaFile(getUseIcaFile());
		
   	
//    	IcaConnector icaConnector = new IcaConnector();
//		icaConnector.setAddress(getIcaAddress());
//		icaConnector.setDomain(getDomain());
//		icaConnector.setPort(getPort());
//		icaConnector.setUsername(getUsername());
//		icaConnector.setPassword(getPassword());
//		icaConnector.setApp(getInitialApp());
//		icaConnector.setRunningMode(getRunningMode());
//		icaConnector.setUseIcaFile(getUseIcaFile());
//		icaConnector.setIcaFilePath(getIcaFilePath());
//    	((PlaySamplerControl) clone).setIcaConnector(icaConnector);
    	
    	return clone;
    }

	private boolean getSleepTimes() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setIcaConnector(IcaConnector icaConnector) {
		this.icaConnector = icaConnector;
	}

	public IcaConnector getIcaConnector() {
		return icaConnector;
	}

	public void setRunningMode(String mode) {
		setProperty(RUNNING_MODE, mode);
	}

	public String getRunningMode() {
		return getPropertyAsString(RUNNING_MODE);
	}
	
//steps = InteractionUtil.readInteractions(jtfInteractionsFile.getText());
//IcaConnector.getInstance().setSteps(steps);
	
	@Override
	public Sampler next() {
		String threadGroupName = JMeterContextService.getContext().getThreadGroup().getName();
		System.out.println("***next()***\nthreadGroupName:"+threadGroupName+"\n**************");

		if(IcaConnector.getInstance(threadGroupName) == null) {
			L.fine("PlaySamplerControl creating icaConn for thread " + threadGroupName);
			List<RecordingStep> steps = null;
			try {
				steps = InteractionUtil.readInteractions(getInteractionsPath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("NULL IN PlayerSamplerControl next()");
			}
			IcaConnector icaConn = new IcaConnector();
			icaConn.setAddress(getIcaAddress());
			icaConn.setDomain(getDomain());
			icaConn.setPort(getPort());
			icaConn.setUsername(getUsername());
			icaConn.setPassword(getPassword());
			icaConn.setApp(getInitialApp());
			icaConn.setRunningMode(getRunningMode());
			icaConn.setUseIcaFile(getUseIcaFile());
			icaConn.setIcaFilePath(getIcaFilePath());
			icaConn.setSteps(steps);
			icaConn.setLocationHashFile(getScreenshotsHashesFilePath());
			icaConn.setLocationHashFolder(getScreenshotsFolderPath());
			icaConn.setSleepingTimeFactor(
					new Double(getSleepFactor().split(" ")[0]));
			IcaConnector.putInstance(threadGroupName, icaConn);
		}
		return null;
	}
	
	@Override
	public void initialize() {
		super.initialize();
		String threadName = JMeterContextService.getContext().getThread().getThreadName();
		System.out.println("***initialize()***\nthreadName:"+threadName+"\n**************");
	}
	
}
