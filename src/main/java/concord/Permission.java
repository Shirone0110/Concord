package concord;

import java.io.Serializable;

public class Permission extends RoleComponent implements Serializable
{

	private static final long serialVersionUID = 8562404864134435760L;
	private String name;
	private boolean allowed;
	
	public Permission() {}
	
	public Permission(String name, boolean allowed)
	{
		this.name = name;
		this.allowed = allowed;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public Boolean getAllowed()
	{
		return allowed;
	}
	
	public void setAllowed(Boolean allowed)
	{
		this.allowed = allowed;
	}
}
