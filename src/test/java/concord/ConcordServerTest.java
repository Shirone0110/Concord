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
					cc.cs.verify("c", "123");
				});
		assertThrows(InvalidCredentialException.class, 
				()->{ 
					cs.verify("a", "456");
					cc.cs.verify("a", "456");
				});
		assertThrows(InvalidCredentialException.class, 
				()->{ 
					cs.verify("c", "789");
					cc.cs.verify("c", "789");
				});
		try
		{
			cs.verify("a", "123");
			cc.cs.verify("a", "123");
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
		Server s = cs.concord.getS().findServerById(0);
		try
		{
			s.addMember(user_1, user_2);
			assertThrows(InvalidActionException.class, 
					()->{ 
						cs.invite(user_2.getUserId(), user_3.getUserId(), 0);
						cc.cs.invite(user_2.getUserId(), user_3.getUserId(), 0);
					});
		} catch (InvalidActionException e)
		{
			fail();
			e.printStackTrace();
		}
		try
		{
			cs.invite(user_1.getUserId(), user_2.getUserId(), 0);
			cc.cs.invite(user_1.getUserId(), user_2.getUserId(), 0);
		} 
		catch (RemoteException | InvalidActionException e)
		{
			fail();
			e.printStackTrace();
		} 
	}
	
	@Test
	void testAccept()
	{
		User user_3 = new User();
		Server s = cs.concord.getS().findServerById(0);
		try
		{
			s.addMember(user_1, user_2);
			assertThrows(InvalidActionException.class, 
					()->{ 
						cs.accept(user_2.getUserId(), user_3.getUserId(), 0);
						cc.cs.accept(user_2.getUserId(), user_3.getUserId(), 0);
					});
		} catch (InvalidActionException e)
		{
			fail();
			e.printStackTrace();
		}
		
		try
		{
			cs.accept(user_1.getUserId(), user_2.getUserId(), 0);
			cc.cs.accept(user_1.getUserId(), user_2.getUserId(), 0);
		} 
		catch (RemoteException | InvalidActionException e)
		{
			fail();
			e.printStackTrace();
		} 
	}
	
	@Test
	void testRemoveMember()
	{
		User user_3 = new User();
		Server s = cs.concord.getS().findServerById(0);
		try
		{
			s.addMember(user_1, user_2);
			cs.removeMember(user_1.getUserId(), user_2.getUserId(), 0);
		} catch (InvalidActionException | RemoteException e)
		{
			fail();
			e.printStackTrace();
		}
		
		try
		{
			s.addMember(user_1, user_2);
			cc.cs.removeMember(user_1.getUserId(), user_2.getUserId(), 0);
		} catch (InvalidActionException | RemoteException e)
		{
			fail();
			e.printStackTrace();
		}
		
		try
		{
			s.addMember(user_1, user_2);
			assertThrows(InvalidActionException.class, 
					()->{ 
						cs.removeMember(user_2.getUserId(), user_3.getUserId(), 0);
					});
		} catch (InvalidActionException e)
		{
			fail();
			e.printStackTrace();
		}
		
		try
		{
			s.addMember(user_1, user_2);
			assertThrows(InvalidActionException.class, 
					()->{ 
						cc.cs.removeMember(user_2.getUserId(), user_3.getUserId(), 0);
					});
		} catch (InvalidActionException e)
		{
			fail();
			e.printStackTrace();
		}
	}
	
	@Test
	void testChangeServerName() 
	{
		try
		{
			cs.changeServerName(user_1.getUserId(), 0, "servername");
			cc.cs.changeServerName(user_1.getUserId(), 0, "servername");
		} catch (RemoteException | InvalidActionException e)
		{
			fail();
			e.printStackTrace();
		}
		
		assertThrows(InvalidActionException.class, 
				()->{ 
					cs.changeServerName(user_2.getUserId(), 0, "servername");
					cc.cs.changeServerName(user_2.getUserId(), 0, "servername");
				});
	}
	
	@Test
	void testChangeChannelName()
	{
		Server s = cs.concord.getS().findServerById(0);
		try
		{
			Channel c = s.addChannel(user_1, "channel");
			cs.changeChannelName(user_1.getUserId(), c.getChannelId(), 0, "newchannel");
			cc.cs.changeChannelName(user_1.getUserId(), c.getChannelId(), 0, "newnewchannel");
		} catch (InvalidActionException e)
		{
			fail();
			e.printStackTrace();
		} catch (RemoteException e)
		{
			fail();
			e.printStackTrace();
		}
		
		assertThrows(InvalidActionException.class, 
				()->{ 
					cs.changeChannelName(user_2.getUserId(), 0, 0, "channelname");
					cc.cs.changeChannelName(user_2.getUserId(), 0, 0, "channelname");
				});
	}
	
	@Test
	void testAddChannel()
	{
		try
		{
			cs.addChannel(user_1.getUserId(), 0, "channel");
			cc.cs.addChannel(user_1.getUserId(), 0, "another channel");
		} catch (InvalidActionException e)
		{
			fail();
			e.printStackTrace();
		} catch (RemoteException e)
		{
			fail();
			e.printStackTrace();
		}
		
		assertThrows(InvalidActionException.class, 
				()->{ 
					cs.addChannel(user_2.getUserId(), 0, "channel");
					cc.cs.addChannel(user_2.getUserId(), 0, "channel");
				});
	}
	
	@Test
	void testDeleteChannel()
	{
		Server s = cs.concord.getS().findServerById(0);
		try
		{
			Channel c = s.addChannel(user_1, "channel");
			cs.deleteChannel(user_1.getUserId(), s.getServerId(), c.getChannelId());
		} catch (InvalidActionException e)
		{
			fail();
			e.printStackTrace();
		} catch (RemoteException e)
		{
			fail();
			e.printStackTrace();
		}
		
		try
		{
			Channel c = s.addChannel(user_1, "channel");
			cc.cs.deleteChannel(user_1.getUserId(), s.getServerId(), c.getChannelId());
		} catch (InvalidActionException e)
		{
			fail();
			e.printStackTrace();
		} catch (RemoteException e)
		{
			fail();
			e.printStackTrace();
		}
		
		assertThrows(InvalidActionException.class, 
				()->{ 
					s.addMember(user_1, user_2);
					Channel c = s.addChannel(user_1, "name");
					cs.deleteChannel(user_2.getUserId(), s.getServerId(), c.getChannelId());
					cc.cs.deleteChannel(user_2.getUserId(), s.getServerId(), c.getChannelId());
				});
	}
	
	@Test
	void testPin()
	{
		Message m = new Message(user_1, "hello");
		try
		{
			cs.addPin(0, m);
			cs.unPin(0, m);
		} catch (RemoteException e)
		{
			fail();
			e.printStackTrace();
		}
		
		Message m2 = new Message(user_2, "hi");
		try
		{
			cc.cs.addPin(0, m2);
			cc.cs.unPin(0, m2);
		} catch (RemoteException e)
		{
			fail();
			e.printStackTrace();
		}
	}
	
	@Test
	void testChangeRole()
	{
		Server s = cs.concord.getS().findServerById(0);
		RoleBuilder rb = s.getRoleBuilder();
		Role mod = rb.buildRole("Moderator");
		Role noob = new Role();
		try
		{
			cs.changeRole(user_1.getUserId(), user_2.getUserId(), mod, 0);
			cs.changeRole(user_1.getUserId(), user_2.getUserId(), noob, 0);
		} catch (InvalidActionException e)
		{
			fail();
			e.printStackTrace();
		} catch (RemoteException e)
		{
			fail();
			e.printStackTrace();
		}
		
		/*try
		{
			cc.cs.changeRole(user_1.getUserId(), user_2.getUserId(), noob, 0);
		}
		catch (InvalidActionException e)
		{
			fail();
			e.printStackTrace();
		} catch (RemoteException e)
		{
			fail();
			e.printStackTrace();
		}*/
	}
	
	@Test
	void testBlock()
	{
		try
		{
			cs.addBlock(user_1.getUserId(), user_2.getUserId());
			cs.removeBlock(user_1.getUserId(), user_2.getUserId());
		} 
		catch (RemoteException e)
		{
			fail();
			e.printStackTrace();
		}
		
		try
		{
			cc.cs.addBlock(user_1.getUserId(), user_2.getUserId());
			cc.cs.removeBlock(user_1.getUserId(), user_2.getUserId());
		} 
		catch (RemoteException e)
		{
			fail();
			e.printStackTrace();
		}
	}
	
	@Test
	void testProfile()
	{
		// test profile data
		try
		{
			cs.setProfileData(user_1.getUserId(), "new profile");
			cc.cs.setProfileData(user_1.getUserId(), "new new profile");
		} 
		catch (RemoteException e)
		{
			fail();
			e.printStackTrace();
		}
		
		// test profile pic
		try
		{
			cs.setProfilePic(user_1.getUserId(), "new url");
			cc.cs.setProfilePic(user_1.getUserId(), "new new url");
		} 
		catch (RemoteException e)
		{
			fail();
			e.printStackTrace();
		}
	}
	
	@Test
	void testSetUsername()
	{
		try
		{
			cs.setUsername(user_1.getUserId(), "new name");
			cc.cs.setUsername(user_1.getUserId(), "a");
		} 
		catch (RemoteException e)
		{
			fail();
			e.printStackTrace();
		}
	}
	
	@Test
	void testSendMessage()
	{
		// send message in direct conversation
		try
		{
			cs.sendMessage(user_1.getUserId(), "hi", 0);
			cc.cs.sendMessage(user_2.getUserId(), "hello", 0);
		} 
		catch (RemoteException e)
		{
			fail();
			e.printStackTrace();
		}
		
		// send message in server
		try
		{
			Server s = cs.concord.getS().findServerById(0);
			Channel c = s.addChannel(user_1, "general");
			cs.sendMessage(user_1.getUserId(), "hi", 0, c.getChannelId());
			cc.cs.sendMessage(user_2.getUserId(), "hello", 0, c.getChannelId());
		} 
		catch (RemoteException e)
		{
			fail();
			e.printStackTrace();
		} catch (InvalidActionException e)
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
