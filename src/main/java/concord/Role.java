package concord;

import java.io.Serializable;
import java.util.ArrayList;

public class Role extends RoleComponent implements Serializable
{

	private static final long serialVersionUID = -7426799483229481124L;
	private String name;
	private ArrayList<RoleComponent> basic;
	private ArrayList<RoleComponent> channelRoles;
	
	public Role() {}
	
	public Role(String name, ArrayList<RoleComponent> basic)
	{
		this.name = name;
		this.basic = basic;
		channelRoles = new ArrayList<RoleComponent>();
	}
	
	public void addChannel(String name)
	{
		channelRoles.add(new ChannelRole(name));
	}
	
	public void removeChannel(String name)
	{
		for (RoleComponent role: channelRoles)
		{
			if (role.getName().equals(name))
			{
				channelRoles.remove(role);
				return;
			}
		}
	}
	
	public ArrayList<RoleComponent> getBasic()
	{
		return basic;
	}

	public void setBasic(ArrayList<RoleComponent> roleList)
	{
		this.basic = roleList;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void addBasic(RoleComponent r)
	{
		basic.add(r);
	}
	
	public void removeBasic(RoleComponent r)
	{
		basic.remove(r);
	}
	
	public RoleComponent getChild(int index)
	{
		return basic.get(index);
	}

	public ArrayList<RoleComponent> getChannelRoles()
	{
		return channelRoles;
	}

	public void setChannelRoles(ArrayList<RoleComponent> channelRoles)
	{
		this.channelRoles = channelRoles;
	}
	
	public boolean getBasicPermission(String name)
	{
		for (RoleComponent r: basic)
		{
			if (r.getName().equals(name))
				return r.getAllowed();
		}
		return false;
	}

	public void setBasicPermission(String name, boolean p)
	{
		for (RoleComponent r: basic)
		{
			if (r.getName().equals(name))
			{
				r.setAllowed(p);
				return;
			}
		}
	}
	
	public RoleComponent getChannelPermission(String name)
	{
		for (RoleComponent r: channelRoles)
		{
			if (r.getName().equals(name))
				return r;
		}
		return null;
	}
}
