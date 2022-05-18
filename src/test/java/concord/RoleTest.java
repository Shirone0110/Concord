package concord;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RoleTest
{
	RoleBuilder rb;
	RoleComponent ad, noob, mod;
	
	@BeforeEach
	void setUp() throws Exception
	{
		rb = new RoleBuilder();
		ad = rb.makeAdmin();
		noob = rb.makeMember();
		mod = rb.makeModerator();
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
		
		assert(mod.getBasicPermission("modify admin") == false);
		assert(mod.getBasicPermission("modify moderator") == false);
		assertTrue(mod.getBasicPermission("add member"));
		assertTrue(mod.getBasicPermission("remove member"));
		assertTrue(mod.getBasicPermission("modify role"));
		assertTrue(mod.getBasicPermission("modify channel"));
		assertTrue(mod.getBasicPermission("view channel"));
		assertTrue(mod.getBasicPermission("send message"));
		assertTrue(mod.getBasicPermission("modify message"));
	}
	
	@Test
	void testNewRole()
	{
		ArrayList<Boolean> perm = new ArrayList<Boolean>();
		perm.add(false);
		perm.add(true);
		perm.add(false);
		perm.add(true);
		perm.add(false);
		perm.add(true);
		perm.add(false);
		perm.add(true);
		perm.add(false);
		RoleComponent r = rb.makeRole("New", perm);
		assert(r.getBasicPermission("view channel") == false);
		assertTrue(r.getBasicPermission("send message"));
		assert(r.getBasicPermission("add member") == false);
		assertTrue(r.getBasicPermission("remove member"));
		assert(r.getBasicPermission("modify admin") == false);
		assertTrue(r.getBasicPermission("modify moderator"));
		assert(r.getBasicPermission("modify channel") == false);
		assertTrue(r.getBasicPermission("modify role"));
		assert(r.getBasicPermission("modify message") == false);
	}
	
	@Test
	void testRoleChannel()
	{
		ad.addChannel("general");
		ad.addChannel("new");
		assertEquals(2, ad.getChannelRoles().size());
	}
}
