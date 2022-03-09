package concord;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserTest
{
	User a, block;
	
	@BeforeEach
	void setUp() throws Exception
	{
		a = new User("Prince Bojji", "Chantakrak", "123", 0);
		block = new User("blocker", "block", "2357", 1);
	}

	@Test
	void testProfileData()
	{
		a.setProfileData("This is profile data");
		assertEquals("This is profile data", a.getProfileData());
	}
	
	@Test
	void testUserName()
	{
		assertEquals("Prince Bojji", a.getUserName());
		a.setUserName("AAAA");
		assertEquals("AAAA", a.getUserName());
	}
	
	@Test
	void testRealName()
	{
		assertEquals("Chantakrak", a.getRealName());
		a.setRealName("chan");
		assertEquals("chan", a.getRealName());
	}
	
	@Test
	void testPassword()
	{
		assertEquals("123", a.getPassword());
		a.setPassword("234");
		assertEquals("234", a.getPassword());
	}
	
	@Test
	void testProfilePic()
	{
		a.setProfilePic("dummy pic");
		assertEquals("dummy pic", a.getProfilePic());
	}
	
	@Test
	void testId()
	{
		assertEquals(0, a.getUserId());
	}

	@Test
	void testBlock()
	{
		a.addBlock(block);
		ArrayList<User> tmp = new ArrayList<User>();
		tmp.add(block);
		assertEquals(tmp, a.getBlocks());
		
		a.removeBlock(block);
		tmp.remove(block);
		assertEquals(tmp, a.getBlocks());
	}
	
	@Test
	void testOnline()
	{
		assert(a.getStatus() == true);
		a.setStatus(false);
		assert(a.getStatus() == false);
	}
}
