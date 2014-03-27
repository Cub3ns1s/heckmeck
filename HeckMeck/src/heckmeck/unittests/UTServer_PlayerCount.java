package heckmeck.unittests;

import static org.junit.Assert.fail;
import heckmeck.exceptions.WrongPlayerCountException;
import heckmeck.server.Server;
import heckmeck.server.SysoLog;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UTServer_PlayerCount {

//	private Server mServer;
//	
//	@Before
//	public void setUp() throws Exception {
//		mServer = new Server(2, new SysoLog());
//	}
//
//	@After
//	public void tearDown() throws Exception {
////		mServer.shutdown();
//	}
//
//	@Test
//	public void checkPlayerCount0() {
//		try {
//			mServer.checkPlayerCount(0);
//			fail("Exception which is expected for zero players didn't occur.");
//		} catch (WrongPlayerCountException e) {
//		}
//	}
//	
//	@Test
//	public void checkPlayerCount1() {
//		try {
//			mServer.checkPlayerCount(1);
//			fail("Exception which is expected for only one player didn't occur.");
//		} catch (WrongPlayerCountException e) {
//		}
//	}
//	
//	@Test
//	public void checkPlayerCount2() {
//		try {
//			mServer.checkPlayerCount(2);
//		} catch (WrongPlayerCountException e) {
//			fail("Exception which is not expected for two players did occur.");
//		}
//	}
//	
//
//	@Test
//	public void checkPlayerCount7() {
//		try {
//			mServer.checkPlayerCount(7);
//		} catch (WrongPlayerCountException e) {
//			fail("Exception which is not expected for seven players did occur.");
//		}
//	}
//	
//	@Test
//	public void checkPlayerCount8() {
//		try {
//			mServer.checkPlayerCount(8);
//			fail("Exception which is expected for eight players didn't occur.");
//		} catch (WrongPlayerCountException e) {
//		}
//	}
}
