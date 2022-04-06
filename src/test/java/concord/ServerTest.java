package concord;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ServerTest
{
	Server s;
	User ad, noob, rando;
	Channel c;
	
	@BeforeEach
	void setUp() throws Exception
	{
		ad = new User("admin", "admin", "123", 0);
		s = new Server(ad, "test", 0, true);
		noob = new User("noob", "noob", "234", 1);
		rando = new User("rando", "rando", "345", 2);
		c = new Channel("channel", s, 0);
	}

	@Test
	void testName()
	{
		assertEquals("test", s.getServerName());
		try
		{
			s.changeServerName(ad, "haha");
			assertEquals("haha", s.getServerName());
		} catch (InvalidActionException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	void testId()
	{
		assertEquals(0, s.getServerId());
	}

	@Test
	void testPrivate()
	{
		assert(s.getIsPrivate() == true);
		s.setPrivate(false);
		assert(s.getIsPrivate() == false);
	}
	
	@Test
	void testLogo()
	{
		s.setServerLogo("logo url");
		assertEquals("logo url", s.getServerLogo());
	}
	
	@Test
	void testDescription()
	{
		s.setServerDescription("description");
		assertEquals("description", s.getServerDescription());
	}
	
	@Test
	void testChannel()
	{
		ArrayList<Channel> tmpChannel = new ArrayList<Channel>();
		tmpChannel.add(c);
		try
		{
			s.addChannel(ad, "channel");
			assertEquals(tmpChannel, s.getChannels());
		} 
		catch (InvalidActionException e)
		{
			e.printStackTrace();
			fail("threw exception when adding channel");
		}
		
		tmpChannel.remove(0);
		try
		{
			s.deleteChannel(ad, c);
			assertEquals(tmpChannel, s.getChannels());
		} 
		catch (InvalidActionException e)
		{
			e.printStackTrace();
			fail("threw exception when deleting channel");
		}
	}
	
	@Test
	void testRoleMember()
	{
		ArrayList<User> tmpUser = new ArrayList<User>();
		tmpUser.add(ad);
		assertEquals(tmpUser, s.getUsers());
		
		try
		{
			s.addMember(ad, noob);
			tmpUser.add(noob);
			assertEquals(tmpUser, s.getUsers());
		} 
		catch (InvalidActionException e)
		{
			e.printStackTrace();
			fail("threw exception when adding user");
		}
		
		try
		{
			s.removeMember(ad, noob);
			tmpUser.remove(noob);
			assertEquals(tmpUser, s.getUsers());
		} 
		catch (InvalidActionException e)
		{
			e.printStackTrace();
			fail("threw exception when removing user");
		}
		
		assertThrows(InvalidActionException.class, 
				()->{ 
					s.addMember(noob, rando);
				});
		
		assertThrows(InvalidActionException.class, 
				()->{ 
					s.removeMember(noob, rando);
				});
	}
	
	@Test
	void testRole()
	{
		try
		{
			s.createRole(ad, "noob", "00000");
			s.changeRole(ad, noob, new Role());
		} 
		catch (InvalidActionException e)
		{
			e.printStackTrace();
			fail("threw exception when create role");
		}
		
		assertThrows(InvalidActionException.class, 
				()->{ 
					s.createRole(noob, "rando", "00001");
				});
		
		assertThrows(InvalidActionException.class, 
				()->{ 
					s.changeRole(noob, ad, new Role());
				});
	}
	
	@Test
	void testInvite()
	{
		try
		{
			assertEquals("dummy url", s.invite(ad, noob));
			s.addMember(ad, noob);
		} 
		catch (InvalidActionException e)
		{
			e.printStackTrace();
			fail("threw error on invite");
		}
		
		assertThrows(InvalidActionException.class, 
				()->{ 
					s.invite(noob, rando);
				});
	}
}
