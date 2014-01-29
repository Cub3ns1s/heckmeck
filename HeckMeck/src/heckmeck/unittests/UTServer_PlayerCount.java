package heckmeck.unittests;

import static org.junit.Assert.*;
import heckmeck.exceptions.WrongPlayerCount;
import heckmeck.server.Server;

import java.lang.reflect.Array;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UTServer_PlayerCount {

//	private Server mServer;
	
	@Before
	public void setUp() throws Exception {
//		mServer = new Server(2);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void checkPlayerCount0() {
		try {
			Server.checkPlayerCount(0);
			fail("Exception which is expected for zero players didn't occur.");
		} catch (WrongPlayerCount e) {
		}
	}
	
	@Test
	public void checkPlayerCount1() {
		try {
			Server.checkPlayerCount(1);
			fail("Exception which is expected for only one player didn't occur.");
		} catch (WrongPlayerCount e) {
		}
	}
	
	@Test
	public void checkPlayerCount2() {
		try {
			Server.checkPlayerCount(2);
		} catch (WrongPlayerCount e) {
			fail("Exception which is not expected for two players did occur.");
		}
	}
	

	@Test
	public void checkPlayerCount7() {
		try {
			Server.checkPlayerCount(7);
		} catch (WrongPlayerCount e) {
			fail("Exception which is not expected for seven players did occur.");
		}
	}
	
	@Test
	public void checkPlayerCount8() {
		try {
			Server.checkPlayerCount(8);
			fail("Exception which is expected for eight players didn't occur.");
		} catch (WrongPlayerCount e) {
		}
	}
}
