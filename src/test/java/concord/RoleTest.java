package concord;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RoleTest
{
	RoleBuilder rb;
	RoleComponent ad, noob;
	
	@BeforeEach
	void setUp() throws Exception
	{
		rb = new RoleBuilder();
		ad = rb.makeAdmin();
		noob = rb.makeMember();
	}

	@Test
	void test()
	{
		assertTrue(ad.getBasicPermission("modify admin"));
		assertTrue(ad.getBasicPermission("modify channel"));
		assertTrue(ad.getBasicPermission("add member"));
		assertTrue(ad.getBasicPermission("remove member"));
		assertTrue(ad.getBasicPermission("modify role"));
		assertTrue(ad.getBasicPermission("modify moderator"));
		assertTrue(ad.getBasicPermission("view channel"));
		assertTrue(ad.getBasicPermission("send message"));
		assertTrue(ad.getBasicPermission("modify message"));
		assertTrue(noob.getBasicPermission("send message"));
		assertTrue(noob.getBasicPermission("view channel"));
		assert(noob.getBasicPermission("modify admin") == false);
		assert(noob.getBasicPermission("modify channel") == false);
		assert(noob.getBasicPermission("add member") == false);
		assert(noob.getBasicPermission("remove member") == false);
		assert(noob.getBasicPermission("modify moderator") == false);
		assert(noob.getBasicPermission("modify role") == false);
		assert(noob.getBasicPermission("modify message") == false);
	}

}
