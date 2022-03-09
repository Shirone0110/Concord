package concord;

import java.util.HashMap;

public class RoleBuilder
{
	HashMap<String, String> permission;
	
	public RoleBuilder()
	{
		permission = new HashMap<String, String>();
	}
	
	public void addRole(String name, String p)
	{
		permission.put(name, p);
		//return new Role(p);
	}
	
	public Role buildRole(String name)
	{
		return new Role(permission.get(name));
	}
}
