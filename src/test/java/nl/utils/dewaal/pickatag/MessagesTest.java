package nl.utils.dewaal.pickatag;

import static org.junit.Assert.*;

import org.junit.Test;

public class MessagesTest {
	
	Messages tester; //Messages is tested

	@Test
	public void testGetString() {
		assertEquals("Resourcebundle must be available", "ABCDEFG", Messages.getString("JUnit.Testing"));
		//fail("Not yet implemented");
	}

}
