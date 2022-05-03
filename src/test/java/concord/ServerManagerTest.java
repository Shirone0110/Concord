package concord;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ServerManagerTest
{
	ServerManager s;
	User admin;
	User member;
	
	@BeforeEach
	void setUp() throws Exception
	{
		s = new ServerManager();
		admin = new User("ad", "admin", "a", 0);
		member = new User("member", "member", "b", 1);
		s.createServer(admin, "test", false);
		s.createServer(member, "Cowan", true);
	}

	@Test
	void testCreateDelete() throws InvalidActionException
	{
		ArrayList<Server> tmp = new ArrayList<Server>();
		tmp.add(new Server(admin, "test", 0, false));
		tmp.add(new Server(member, "Cowan", 1, true));
		assertEquals(tmp, s.getServers());
		
		tmp.remove(0);
		s.deleteServer(admin, 0);
		assertEquals(tmp, s.getServers());
		
		tmp.add(new Server(admin, "third", 2, true));
		s.createServer(admin, "third", true);
		assertEquals(tmp, s.getServers());
	}
	
	@Test
	void testGetUserServer()
	{
		ArrayList<Server> tmp = new ArrayList<Server>();
		tmp.add(new Server(admin, "test", 0, false));
		assertEquals(s.getUserServer(admin), tmp);
	}

}
