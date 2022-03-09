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
		assert(ad.isModifyAdmin() == true);
		assert(ad.isModifyChannel() == true);
		assert(ad.isModifyMember() == true);
		assert(ad.isModifyModerator() == true);
		assert(ad.isModifyRole() == true);
		assert(noob.isModifyAdmin() == false);
		assert(noob.isModifyChannel() == false);
		assert(noob.isModifyMember() == false);
		assert(noob.isModifyModerator() == false);
		assert(noob.isModifyRole() == false);
	}

}
