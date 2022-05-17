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
		c = new Channel("channel", s, 1);
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
		s.setIsPrivate(false);
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
		tmpChannel.add(new Channel("general", s, 0));
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
		
		tmpChannel.remove(1);
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
			s.invite(ad, noob);
		} 
		catch (InvalidActionException e)
		{
			e.printStackTrace();
			fail("threw exception when adding user");
		}
		
		s.addMember(noob);
		
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
					s.invite(noob, rando);
				});
		
		assertThrows(InvalidActionException.class, 
				()->{ 
					s.removeMember(noob, rando);
				});
	}
	
	@Test
	void testRole()
	{
		ArrayList<Boolean> tmp = new ArrayList<Boolean>();
		for (int i = 0; i < 9; i++) tmp.add(false);
		try
		{
			RoleComponent r = s.createRole(ad, "noob", tmp);
			s.changeRole(ad, noob, r);
		} 
		catch (InvalidActionException e)
		{
			e.printStackTrace();
			fail("threw exception when create role");
		}
		
		assertThrows(InvalidActionException.class, 
				()->{ 
					s.createRole(noob, "rando", tmp);
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
			s.invite(ad, noob);
		} 
		catch (InvalidActionException e)
		{
			e.printStackTrace();
			fail("threw error on invite");
		}
		
		s.addMember(noob);
		
		assertThrows(InvalidActionException.class, 
				()->{ 
					s.invite(noob, rando);
				});
	}
}
