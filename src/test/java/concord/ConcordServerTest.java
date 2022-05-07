package concord;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.rmi.AccessException;
//import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

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
	static void setUpBeforeClass() throws RemoteException 
	{
		File file = new File("Concord.xml");
		file.delete();
		cs = new ConcordServer();
		registry = LocateRegistry.createRegistry(2099);
		try
		{
			registry.rebind("CONCORD", cs);
		} catch (AccessException e)
		{
			fail();
			e.printStackTrace();
		} 
		cc = new ConcordClient();
		//cc = (ConcordServerInterface) 
		//	Naming.lookup("rmi://127.0.0.1/CONCORD");
		UserManager UM = cs.getConcord().getU();
		user_1 = UM.addUser("a", "abc", "123");
		user_2 = UM.addUser("b", "def", "456");
		
		DirectConversationManager DM = cs.getConcord().getD();
		DirectConversation dc = DM.createDc(user_1, user_2);
		
		Message m = new Message();
		m.setContent("hello");
		m.setUser(user_1);
		dc.addMessage(m);
		
		ServerManager SM = cs.getConcord().getS();
		SM.createServer(user_1, "server", false);
	}
	
	@After
	void tearDown() throws Exception
	{
		registry.unbind("CONCORD");
	}
	
	@Test
	void testGetServerByUserId()
	{
		try
		{
			ArrayList<Server> test = cs.getServerByUserId(user_1.getUserId());
			assertEquals(1, test.size());
			assertEquals("server", test.get(0).getServerName());
		} catch (RemoteException e)
		{
			fail();
			e.printStackTrace();
		}
		
		try
		{
			cc.setUser(user_1);
			ArrayList<Server> test = cc.getServerByUserId();
			assertEquals(1, test.size());
			assertEquals("server", test.get(0).getServerName());
		} catch (RemoteException e)
		{
			fail();
			e.printStackTrace();
		}
	}
	
	@Test
	void testGetDcByUserId()
	{
		try
		{
			ArrayList<DirectConversation> test = cs.getDcByUserId(0);
			assertEquals(1, test.size());
			boolean exist_1 = false, exist_2 = false;
			for (User u: test.get(0).getUsers())
			{
				if (u.equals(user_1)) exist_1 = true;
				if (u.equals(user_2)) exist_2 = true;
			}
			assertTrue(exist_1);
			assertTrue(exist_2);
		} catch (RemoteException e)
		{
			fail();
			e.printStackTrace();
		}
		
		try
		{
			cc.setUser(user_2);
			ArrayList<DirectConversation> test = cc.getDcByUserId();
			assertEquals(1, test.size());
			boolean exist_1 = false, exist_2 = false;
			for (User u: test.get(0).getUsers())
			{
				if (u.equals(user_1)) exist_1 = true;
				if (u.equals(user_2)) exist_2 = true;
			}
			assertTrue(exist_1);
			assertTrue(exist_2);
		} catch (RemoteException e)
		{
			fail();
			e.printStackTrace();
		}
	}
	
	/*
	 * Test create/delete server and dc because
	 * it is unstable to have a separate testing due to race condition
	 */
	@Test
	void testCreateUserServerDc()
	{
		try
		{
			assertEquals(2, cs.getAllUser().size());
			cs.createUser("def", "ghi", "jkl");
			assertEquals(3, cs.getAllUser().size());
			assertEquals("def", cs.findUserById(2).getUserName());
			assertEquals("ghi", cs.findUserById(2).getRealName());
			assertEquals("jkl", cs.findUserById(2).getPassword());
		} 
		catch (RemoteException e)
		{
			fail();
			e.printStackTrace();
		}
		
		try
		{
			Server s = cs.createServer(2, "server 2", false);
			assertEquals(1, cs.getServerByUserId(2).size());
			assertEquals(s, cs.getServerByUserId(2).get(0));
			
			// test get server by Id
			assertEquals(s, cs.getServerById(s.getServerId()));
			
			// test delete Server
			try
			{
				s.addMember(cs.findUserById(2), user_1);
			} catch (InvalidActionException e1)
			{
				fail();
				e1.printStackTrace();
			}
			assertThrows(InvalidActionException.class, 
					()->{ 
						cs.deleteServer(user_1.getUserId(), s.getServerId());
					});
	
			assertThrows(InvalidActionException.class,
					()->{
						cc.setUser(user_1);
						cc.deleteServer(s.getServerId());
					});
			
			try
			{
				cs.deleteServer(2, s.getServerId());
				assertEquals(0, cs.getServerByUserId(2).size());
			} catch (InvalidActionException e)
			{
				fail();
				e.printStackTrace();
			}
		} catch (RemoteException e1)
		{
			fail();
			e1.printStackTrace();
		}
		
		try
		{
			assertEquals(3, cc.getAllUser().size());
			cc.createUser("mno", "pqr", "stu");
			assertEquals(4, cc.getAllUser().size());
			assertEquals("mno", cc.findUserById(3).getUserName());
			assertEquals("pqr", cc.findUserById(3).getRealName());
			assertEquals("stu", cc.findUserById(3).getPassword());
		} 
		catch (RemoteException e)
		{
			fail();
			e.printStackTrace();
		}
		
		try
		{
			cc.setUser(cc.findUserById(3));
			cc.createServer("server 3", false);
			assertEquals(1, cc.getServerByUserId().size());
			
			Server s = cc.getServerByUserId().get(0);
			assertEquals("server 3", s.getServerName());
			
			cc.deleteServer(s.getServerId());
		} catch (RemoteException e1)
		{
			fail();
			e1.printStackTrace();
		} catch (InvalidActionException e)
		{
			fail();
			e.printStackTrace();
		} 
		
		try
		{
			DirectConversation d = cs.createDc(2, 3);
			assertEquals(1, cs.getDcByUserId(2).size());
			assertEquals(1, cs.getDcByUserId(3).size());
			assertEquals(d, cs.getDcById(d.getDirectConvoId()));
		} catch (RemoteException e)
		{
			fail();
			e.printStackTrace();
		}

		try
		{
			cc.setUser(cc.findUserById(3));
			cc.createDc(3, 2);
			assertEquals(2, cc.getDcByUserId().size());
			cc.setUser(cc.findUserById(2));
			assertEquals(2, cc.getDcByUserId().size());
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	void testFindUserById()
	{
		try
		{
			assertEquals(user_1, cs.findUserById(user_1.getUserId()));
			assertEquals(user_1, cc.findUserById(user_1.getUserId()));
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
					cc.verify("c", "123");
				});
		assertThrows(InvalidCredentialException.class, 
				()->{ 
					cs.verify("a", "456");
					cc.verify("a", "456");
				});
		assertThrows(InvalidCredentialException.class, 
				()->{ 
					cs.verify("c", "789");
					cc.verify("c", "789");
				});
		try
		{
			cs.verify("a", "123");
			cc.verify("a", "123");
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
		Server s = cs.getConcord().getS().findServerById(0);
		try
		{
			s.addMember(user_1, user_2);
			assertThrows(InvalidActionException.class, 
					()->{ 
						cs.invite(user_2.getUserId(), user_3.getUserId(), 0);
						cc.setUser(user_2);
						cc.invite(user_3.getUserId(), 0);
					});
		} catch (InvalidActionException e)
		{
			fail();
			e.printStackTrace();
		}
		try
		{
			cs.invite(user_1.getUserId(), user_2.getUserId(), 0);
			cc.setUser(user_1);
			cc.invite(user_2.getUserId(), 0);
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
		Server s = cs.getConcord().getS().findServerById(0);
		try
		{
			s.addMember(user_1, user_2);
			assertThrows(InvalidActionException.class, 
					()->{ 
						cs.accept(user_2.getUserId(), user_3.getUserId(), 0);
						cc.setUser(user_2);
						cc.accept(user_3.getUserId(), 0);
					});
		} catch (InvalidActionException e)
		{
			fail();
			e.printStackTrace();
		}
		
		try
		{
			cs.accept(user_1.getUserId(), user_2.getUserId(), 0);
			cc.setUser(user_1);
			cc.accept(user_2.getUserId(), 0);
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
		Server s = cs.getConcord().getS().findServerById(0);
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
			cc.setUser(user_1);
			cc.removeMember(user_2.getUserId(), 0);
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
						cc.setUser(user_2);
						cc.removeMember(user_3.getUserId(), 0);
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
			cc.setUser(user_1);
			cc.changeServerName(0, "servername");
		} catch (RemoteException | InvalidActionException e)
		{
			fail();
			e.printStackTrace();
		}
		
		assertThrows(InvalidActionException.class, 
				()->{ 
					cs.changeServerName(user_2.getUserId(), 0, "servername");
					cc.setUser(user_2);
					cc.changeServerName(0, "servername");
				});
	}
	
	@Test
	void testChangeChannelName()
	{
		Server s = cs.getConcord().getS().findServerById(0);
		try
		{
			Channel c = s.addChannel(user_1, "channel");
			cs.changeChannelName(user_1.getUserId(), c.getChannelId(), 0, "newchannel");
			cc.setUser(user_1);
			cc.changeChannelName(c.getChannelId(), 0, "newnewchannel");
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
					cc.setUser(user_2);
					cc.changeChannelName( 0, 0, "channelname");
				});
	}
	
	@Test
	void testAddChannel()
	{
		try
		{
			cs.addChannel(user_1.getUserId(), 0, "channel");
			cc.setUser(user_1);
			cc.addChannel(0, "another channel");
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
					cc.setUser(user_2);
					cc.addChannel(0, "channel");
				});
	}
	
	@Test
	void testDeleteChannel()
	{
		Server s = cs.getConcord().getS().findServerById(0);
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
			cc.setUser(user_1);
			cc.deleteChannel(s.getServerId(), c.getChannelId());
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
					cc.setUser(user_2);
					cc.deleteChannel(s.getServerId(), c.getChannelId());
				});
	}
	
	@Test
	void testPin()
	{
		Message m = new Message(user_1, "hello");
		//Message m = new Message();
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
		//Message m2 = new Message();
		try
		{
			cs.addPin(0, m2);
			cc.unPin(0, m2);
		} catch (RemoteException e)
		{
			fail();
			e.printStackTrace();
		}
	}
	
	@Test
	void testChangeRole()
	{
		Server s = cs.getConcord().getS().findServerById(0);
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
		
		try
		{
			cc.setUser(user_1);
			cc.changeRole(user_2.getUserId(), mod, 0);
			cc.changeRole(user_2.getUserId(), noob, 0);
		}
		catch (InvalidActionException e)
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
					cs.changeRole(user_2.getUserId(), user_1.getUserId(), noob, 0);
					cc.setUser(user_2);
					cc.changeRole(user_1.getUserId(), noob, 0);
				});
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
			cc.setUser(user_1);
			cc.addBlock(user_2.getUserId());
			cc.removeBlock(user_2.getUserId());
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
		cc.setUser(user_1);
		// test profile data
		try
		{
			cs.setProfileData(user_1.getUserId(), "new profile");
			cc.setProfileData("new new profile");
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
			cc.setProfilePic("new new url");
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
		cc.setUser(user_1);
		try
		{
			cs.setUsername(user_1.getUserId(), "new name");
			cc.setUsername("a");
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
			cs.sendDcMessage(user_1.getUserId(), "hi", 0);
			cc.setUser(user_2);
			cc.sendDcMessage("hello", 0);
		} 
		catch (RemoteException e)
		{
			fail();
			e.printStackTrace();
		}
		
		// send message in server
		try
		{
			Server s = cs.getConcord().getS().findServerById(0);
			Channel c = s.addChannel(user_1, "general");
			cs.sendChannelMessage(user_1.getUserId(), "hi", 0, c.getChannelId());
			cc.setUser(user_2);
			cc.sendChannelMessage("hello", 0, c.getChannelId());
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
		cs.getConcord().storeToDisk();
		Concord diskConcord;
		diskConcord = Concord.loadFromDisk();
		assertTrue(cs.getConcord().equals(diskConcord));
	}

}
