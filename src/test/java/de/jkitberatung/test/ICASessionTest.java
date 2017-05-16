
package de.jkitberatung.test;
import org.testng.Assert;
import org.testng.annotations.Test;

import de.jkitberatung.ica.wsh.IICAClient;
import de.jkitberatung.util.ICAInitializer;
import de.jkitberatung.util.IcaConnector;

public class ICASessionTest {
	public static void main(String[] args) {
		ICASessionTest t = new ICASessionTest();
		t.testICASession();
	}
	
   @Test
   public void testICASession() {
	  String icaFileName = "C:/Users/kdesautels/Downloads/WGVuQXBwUHJvZCA3LjkuU01RQQ-- (11).ica";
	   
      IICAClient client = ICAInitializer.initICASession(icaFileName, "NORMAL");
      client.connect();
   }

}