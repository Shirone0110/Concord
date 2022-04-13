package models;

import concord.Concord;
import concord.User;

public class ConcordModel
{
	Concord c;
	User user;
	
	public ConcordModel(Concord c, User user)
	{
		this.c = c;
		c.getU().addUser("Prince Boji", "Chantakrak", "pass");
		c.getU().addUser("Milano Cookie", "M. Cookie", "pepperidge_f@rm");
		c.getU().addUser("Shirone", "Lam", "word");
		
		this.user = c.getU().getUserList().get(0);
		User cookie = c.getU().getUserList().get(1);
		User lam = c.getU().getUserList().get(2);
		
		c.getS().createServer(user, "Boji Land", false);
		c.getS().createServer(cookie, "Cookie Land", false);
		c.getS().createServer(lam, "Shirone Land", false);
		
		c.getD().createDc(user, lam);
		c.getD().createDc(cookie, user);
		
	}
	
	public ConcordModel()
	{
		
	}
	
}
