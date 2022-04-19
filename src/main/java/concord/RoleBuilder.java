package concord;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;

public class RoleBuilder implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -321992487966906928L;
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

	/**
	 * @return the permission
	 */
	public HashMap<String, String> getPermission()
	{
		return permission;
	}

	/**
	 * @param permission the permission to set
	 */
	public void setPermission(HashMap<String, String> permission)
	{
		this.permission = permission;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(permission);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RoleBuilder other = (RoleBuilder) obj;
		return Objects.equals(permission, other.permission);
	}

}
