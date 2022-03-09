package concord;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConcordTest
{
	Concord concord;
	DirectConversationManager dcm;
	ServerManager s;
	UserManager u;
	
	@BeforeEach
	void setUp() throws Exception
	{
		 concord = new Concord();
		 dcm = concord.d;
		 s = concord.s;
		 u = concord.u;
	}


	@Test
	void testInit()
	{
		assertEquals(0, dcm.getNumberOfDc());
		assertNotNull(dcm.dcList);
		assertEquals(0, 0);
	}

}
