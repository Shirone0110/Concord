package concord;

import static org.junit.jupiter.api.Assertions.*;

//import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.junit.After;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ConcordServerTest
{
	static ConcordServer cs;
	static Registry registry;
	static ConcordClient cc;
	static User user_1, user_2;

	@BeforeAll
	static void setUpBeforeClass() throws Exception
	{
		cs = new ConcordServer();
		registry = LocateRegistry.createRegistry(2099);
		registry.rebind("CONCORD", cs);
		cc = new ConcordClient();
		//cc = (ConcordServerInterface) 
		//	Naming.lookup("rmi://127.0.0.1/CONCORD");
		UserManager UM = cs.concord.getU();
		user_1 = UM.addUser("a", "abc", "123");
		user_2 = UM.addUser("b", "def", "456");
		
		DirectConversationManager DM = cs.concord.getD();
		DirectConversation dc = DM.createDc(user_1, user_2);
		
		Message m = new Message();
		m.setContent("hello");
		m.setUser(user_1);
		dc.addMessage(m);
		
		ServerManager SM = cs.concord.getS();
		SM.createServer(user_1, "server", false);
	}
	
	@After
	void tearDown() throws Exception
	{
		registry.unbind("CONCORD");
	}

	@Test
	void testFindUserById()
	{
		try
		{
			assertEquals(user_1, cs.findUserById(user_1.getUserId()));
			assertEquals(user_1, cc.cs.findUserById(user_1.getUserId()));
		} 
		catch (RemoteException e)
		{
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	void testVerify()
	{
		assertThrows(InvalidCredentialException.class, 
				()->{ 
					cs.verify("c", "123");
				});
		assertThrows(InvalidCredentialException.class, 
				()->{ 
					cs.verify("a", "456");
				});
		assertThrows(InvalidCredentialException.class, 
				()->{ 
					cs.verify("c", "789");
				});
		try
		{
			cs.verify("a", "123");
		} 
		catch (RemoteException | InvalidCredentialException e)
		{
			fail();
			e.printStackTrace();
		}
	}
	
	@Test
	void testInvite()
	{
		User user_3 = new User();
		try
		{
			cs.invite(user_1.getUserId(), user_2.getUserId(), 0);
			assertThrows(InvalidActionException.class, 
					()->{ 
						cs.invite(user_2.getUserId(), user_3.getUserId(), 0);
					});
		} 
		catch (RemoteException | InvalidActionException e)
		{
			fail();
			e.printStackTrace();
		} 
	}
	
	@Test
	void testXML()
	{
		cs.concord.storeToDisk();
		Concord diskConcord = Concord.loadFromDisk();
		assertTrue(cs.concord.equals(diskConcord));
	}

}
