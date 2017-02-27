package nl.utils.dewaal.pickatag;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.io.InputStream;

import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;

public class PickATagDataTest {
	PickATagData tester = new PickATagData(); //PickATagData is tested

	@Test
	public void testGetTagLine() {
		initLog4J();
		assertNotNull("getTagLine should not be null", tester.getTagLine());
		assertThat("getTagLine() should return String", tester.getTagLine(), instanceOf(String.class));
		assertNotEquals("getTagLine() should not return zero-length string", 0, tester.getTagLine().length());

		
			}
	private static void initLog4J()
	{
		InputStream is = Main.class.getResourceAsStream("/log4j.properties");
		PropertyConfigurator.configure(is);
	}

}
