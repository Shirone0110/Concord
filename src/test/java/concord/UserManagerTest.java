package concord;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserManagerTest
{
	UserManager u;

	@BeforeEach
	void setUp() throws Exception
	{
		u = new UserManager();
		u.addUser("Prince Bojji", "Chantakrak", "123");
		u.addUser("Shirone", "Lam", "456");
	}

	@Test
	void testCreateDelete()
	{
		ArrayList<User> tmp = new ArrayList<User>();
		tmp.add(new User("Prince Bojji", "Chantakrak", "123", 0));
		tmp.add(new User("Shirone", "Lam", "456", 1));
		assertEquals(tmp, u.getUserList());
		
		tmp.remove(0);
		u.removeUser(0);
		assertEquals(tmp, u.getUserList());
		
		tmp.add(new User("Chaekyung", "Kiana", "789", 2));
		u.addUser("Chaekyung", "Kiana", "789");
		assertEquals(tmp, u.getUserList());
	}
}
