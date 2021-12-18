package nl.utils.dewaal.pickatag;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class PickATagDataTest {
	PickATagData tester = new PickATagData(); //PickATagData is tested

	@SuppressWarnings("deprecation")
	@Test
	public void testGetTagLine() {
		assertNotNull("getTagLine should not be null", tester.getTagLine());
		assertThat("getTagLine() should return String", tester.getTagLine(), instanceOf(String.class));
		assertNotEquals("getTagLine() should not return zero-length string", 0, tester.getTagLine().length());

		
			}

}
