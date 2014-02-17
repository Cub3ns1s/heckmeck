package heckmeck.unittests;

import heckmeck.server.Grill;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UTServer_GrillToString {

	private Grill mGrill;
	
	@Before
	public void setUp() throws Exception {
		mGrill = new Grill();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		System.out.println(mGrill.toString());
	}

}
