package concord;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RoleTest
{
	RoleBuilder rb;
	Role ad, noob;
	
	@BeforeEach
	void setUp() throws Exception
	{
		rb = new RoleBuilder();
		rb.addRole("admin", "11111");
		rb.addRole("noob", "00000");
		ad = rb.buildRole("admin");
		noob = rb.buildRole("noob");
	}

	@Test
	void test()
	{
		assert(ad.getModifyAdmin() == true);
		assert(ad.getModifyChannel() == true);
		assert(ad.getModifyMember() == true);
		assert(ad.getModifyModerator() == true);
		assert(ad.getModifyRole() == true);
		assert(noob.getModifyAdmin() == false);
		assert(noob.getModifyChannel() == false);
		assert(noob.getModifyMember() == false);
		assert(noob.getModifyModerator() == false);
		assert(noob.getModifyRole() == false);
	}

}
