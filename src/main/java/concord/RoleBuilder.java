package concord;

import java.io.Serializable;
import java.util.ArrayList;

public class RoleBuilder implements Serializable
{
	
	private static final long serialVersionUID = -5506648028671478543L;
	private ArrayList<String> basicNames;
	private ArrayList<RoleComponent> roles;
	
	public RoleBuilder()
	{
		basicNames = new ArrayList<String>();
		// never delete these 2
		basicNames.add("view channel");
		basicNames.add("send message");
		
		// might be deleted
		basicNames.add("add member");
		basicNames.add("remove member");
		basicNames.add("modify admin");
		basicNames.add("modify moderator");
		basicNames.add("modify channel");
		basicNames.add("modify role");
		basicNames.add("modify message");
		
		roles = new ArrayList<RoleComponent>();
		
		roles.add(makeAdmin());
		roles.add(makeModerator());
		roles.add(makeMember());
	}
	
	public RoleComponent makeRole(String name, ArrayList<Boolean> permissions)
	{
		ArrayList<RoleComponent> basic = new ArrayList<RoleComponent>();
		for (int i = 0; i < basicNames.size(); i++)
		{
			basic.add(new Permission(basicNames.get(i), permissions.get(i)));
		}
		RoleComponent r = new Role(name, basic);
		roles.add(r);
		return r;
	}
	
	public RoleComponent makeAdmin()
	{
		ArrayList<RoleComponent> basic = new ArrayList<RoleComponent>();
		for (String s: basicNames)
		{
			basic.add(new Permission(s, true));
		}
		return new Role("Admin", basic);
	}
	
	public RoleComponent makeMember()
	{
		ArrayList<RoleComponent> basic = new ArrayList<RoleComponent>();
		for (int i = 0; i < basicNames.size(); i++)
			basic.add(new Permission(basicNames.get(i), false));
		RoleComponent r = new Role("Member", basic);
		r.setBasicPermission("view channel", true);
		r.setBasicPermission("send message", true);
		return r;
	}
	
	public RoleComponent makeModerator()
	{
		RoleComponent r = makeAdmin();
		r.setBasicPermission("modify admin", false);
		r.setBasicPermission("modify moderator", false);
		return r;
	}

	public ArrayList<RoleComponent> getRoles()
	{
		return roles;
	}

	public void setRoles(ArrayList<RoleComponent> roles)
	{
		this.roles = roles;
	}
}
