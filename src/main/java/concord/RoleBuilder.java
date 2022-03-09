package concord;

import java.util.HashMap;

public class RoleBuilder
{
	private HashMap<String, String> permission;
	
	public RoleBuilder()
	{
		permission = new HashMap<String, String>();
	}
	
	public void addRole(String name, String p)
	{
		if (!permission.containsKey(name))
			permission.put(name, p);
		else buildRole(name);
	}
	
	public Role buildRole(String name)
	{
		return new Role(permission.get(name));
	}
}
