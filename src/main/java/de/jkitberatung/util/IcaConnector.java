package de.jkitberatung.util;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import java.util.logging.Logger;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;

import org.apache.jmeter.threads.JMeterContextService;

import de.jkitberatung.ica.wsh.IICAClient;
import de.jkitberatung.ica.wsh.IWindows;
import de.jkitberatung.ica.wsh.events._IICAClientEvents;
import de.jkitberatung.recorder.RecordingStep;

public class IcaConnector {

	private String app;
	private String address;
	private String port;
	private String domain;
	private String username;
	private String password;
	private double sleepingTimeFactor;
	private String locationHashFile;
	private String locationHashFolder;
	private String runningMode;
	private boolean useIcaFile;
	private String icaFilePath;

	private List<RecordingStep> steps;
	private static HashMap<String, IcaConnector> connMap;

	private JCheckBox jckbSleepCheckBox;
	private JComboBox jcbSleepComboBox;

	private HashMap<String, IICAClient> icaMap;
	private static final String VERSION = "1.0.1";
	private static final Logger L = Logger.getLogger(IcaConnector.class
			.getName());
	private static boolean once = true;

	private void showVersion() {
		if (once) {
			Enumeration<URL> resources = null;
			try {
				resources = Thread.currentThread()
						.getContextClassLoader()
						.getResources("META-INF/MANIFEST.MF");
			} catch (IOException e) {
				e.printStackTrace();
			}
			while (resources != null && resources.hasMoreElements()) {
				try {
					Manifest manifest = new Manifest(resources.nextElement()
							.openStream());
					Attributes attr = manifest.getMainAttributes();
					String ts = attr.getValue("Build-Timestamp");
					if (ts != null) {
						L.info("This is ICAFun Version " + VERSION + " built " + ts);
					}
				} catch (IOException E) {
					E.printStackTrace();
				}
			}

			once = false;
		}
	}

	public double getSleepingTimeFactor() {
		return sleepingTimeFactor;
	}

	public void setSleepingTimeFactor(double sleepingTimeFactor) {
		this.sleepingTimeFactor = sleepingTimeFactor;
	}

	public String getLocationHashFile() {
		return locationHashFile;
	}

	public void setLocationHashFile(String locationHashFile) {
		this.locationHashFile = locationHashFile;
	}

	public String getLocationHashFolder() {
		return locationHashFolder;
	}

	public void setLocationHashFolder(String locationHashFolder) {
		this.locationHashFolder = locationHashFolder;
	}

	public IcaConnector() {
		showVersion();
	}
	
	public static IcaConnector getInstance(String key) {
		if(connMap == null)
			connMap = new HashMap<String, IcaConnector>();
		return connMap.get(key);
	}
	
	public static void putInstance(String key, IcaConnector conn) {
		connMap.put(key, conn);
	}

	public static void resetInstance(String key) {
		IcaConnector conn = connMap.get(key);
		conn = null;
		connMap.remove(key);
	}

	private String getRunningMode() {
		return runningMode;
	}

	private String getPassword() {
		return password;
	}

	private String getUsername() {
		return username;
	}

	private String getDomain() {
		return domain;
	}

	private String getPort() {
		return port;
	}

	private String getIcaAddress() {
		return address;
	}

	private String getInitialApp() {
		return app;
	}
	
	public boolean getUseIcaFile() {
		return this.useIcaFile;
	}
	
	public String getIcaFilePath() {
		return this.icaFilePath;
	}
	
	public void setApp(String app) {
		this.app = app;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setUseIcaFile(boolean useIcaFile) {
		this.useIcaFile = useIcaFile;
	}
	
	public void setIcaFilePath(String icaFilePath) {
		this.icaFilePath = icaFilePath;
	}

	//PlaySampler uses threadName for key
	public IICAClient createNewIca(String key, boolean addToMap) {
		IICAClient ica = icaMap.get(key);
		
		if (ica == null || ica.session() == null) {
			IcaConnHelper connHelper = new IcaConnHelper(); 
			connHelper.initAndConnect();
			
			// Wait until ica session becomes available
			while (!connHelper.isICALoggedOn && !connHelper.error) {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					L.fine("Interrupting connection attempt loop... done.");
				}
			}
			
			ica = connHelper.ica;
		}
		
		if(addToMap)
			icaMap.put(key, ica);
		
		return ica;
	}

	public RecordingStep getStepByName(String stepName) {
		for (RecordingStep step : steps)
			if (step.getName().equals(stepName))
				return step;
		return null;
	}

	public void setSteps(List<RecordingStep> steps) {
		this.steps = steps;
	}

	public void setSleepingCheckBox(JCheckBox jckbSleepCheckBox) {
		this.jckbSleepCheckBox = jckbSleepCheckBox;
	}

	public void setSleepingComboBox(JComboBox jcbSleepComboBox) {
		this.jcbSleepComboBox = jcbSleepComboBox;
	}

	public void setRunningMode(String runningMode) {
		this.runningMode = runningMode;
	}

	public JCheckBox getSleepingCheckBox() {
		return jckbSleepCheckBox;
	}

	public JComboBox getSleepingComboBox() {
		return jcbSleepComboBox;
	}

	public HashMap<String, IICAClient> getIcaMap() {
		if (null == icaMap)
			icaMap = new HashMap<String, IICAClient>();
		return icaMap;
	}
	
	//returns ${variable} value if it exists; returns arg otherwise
	public String getVariableValue(String val) {
		if(val.trim().startsWith("${") && val.trim().endsWith("}")) {
			return JMeterContextService.getContext().getVariables().
					get(val.substring(2, val.length() - 1));
		}
		return val;
	}
	
	class IcaConnHelper {
		private IICAClient ica;
		private boolean isICALoggedOn = false;
		boolean error = false;
		
		private void initAndConnect() {
			init();
			connect();
		}
		
		private void init() {
			if(getUseIcaFile()) 
				ica = ICAInitializer.initICASession(getVariableValue(getIcaFilePath()), 
						getRunningMode());
			else
				ica = ICAInitializer.initICASession(getVariableValue(getInitialApp()),
						getVariableValue(getIcaAddress()), getVariableValue(getPort()), 
						getVariableValue(getDomain()), getVariableValue(getUsername()), 
						getVariableValue(getPassword()), getRunningMode());
		}

		private void connect() {
			// Add listeners to ICA Client
			setIcaClientListener();
			// Connect to our session
			L.fine("Connecting to server...");
			ica.connect();
		}
		
		private void setIcaClientListener() {
			
			ica.advise(_IICAClientEvents.class, new _IICAClientEvents() {

				public void onLogon() {
					// Right now the session is not null anymore

					L.fine("ICAClientEvent: Logged on.");
					L.fine("ICAClientEvent: ICA session: " + ica.session());

					ica.session().replayMode(true);

					isICALoggedOn = true;

					try {
						IWindows windows = ica.session().topLevelWindows();
						L.fine("ICAClientEvent: TopLevelWindow count:"
								+ windows.count());

					} catch (Throwable e) {
						e.printStackTrace();
					}
				}

				@Override
				public void onConnect() {
					super.onConnect();
				}

				@Override
				public void onWindowCreated(int wndType, int xPos, int yPos,
						int width, int height) {
					L.fine("ICAClientEvent: Window created (type):" + wndType);
					super.onWindowCreated(wndType, xPos, yPos, width, height);
				}

				@Override
				public void onWindowDisplayed(int wndType) {
					L.fine("ICAClientEvent: Window displayed (type):" + wndType);
				}

				@Override
				public void onWindowCloseRequest() {
					L.fine("ICAClientEvent: OnWindow close request");
				}

				@Override
				public void onDisconnect() {
					L.fine("ICAClientEvent: onDisconnect");
					isICALoggedOn = false;
					ica = null;
				}

				@Override
				public void onDisconnectFailed() {
					L.fine("ICAClientEvent: Disconnecting failed");
					error = true;
					// JOptionPane.showMessageDialog(null,
					// "Failed disconnecting from the server", "Error",
					// JOptionPane.ERROR_MESSAGE);
				}

				@Override
				public void onLogonFailed() {
					L.fine("ICAClientEvent: Logging-in failed");
					error = true;
					// JOptionPane.showMessageDialog(null,
					// "Error logging into the server", "Error",
					// JOptionPane.ERROR_MESSAGE);
				}

				@Override
				public void onConnectFailed() {
					L.fine("ICAClientEvent: Connecting failed");
					error = true;
					// JOptionPane.showMessageDialog(null,
					// "Error connecting to the server", "Error",
					// JOptionPane.ERROR_MESSAGE);
				}
			});
		}
	}
}