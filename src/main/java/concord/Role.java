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
	
	@Override
	public void addChannel(String name)
	{
		System.out.println(this + ", New channel role " + name);
		channelRoles.add(new ChannelRole(name));
		System.out.println(this + ", " + channelRoles.size());
	}
	
	@Override
	public void removeChannel(String name)
	{
		for (RoleComponent role: channelRoles)
		{
			ChannelRole cr = (ChannelRole) role;
			if (cr.getName().equals(name))
			{
				channelRoles.remove(cr);
				return;
			}
		}
	}
	
	@Override
	public ArrayList<RoleComponent> getBasic()
	{
		return basic;
	}

	@Override
	public void setBasic(ArrayList<RoleComponent> roleList)
	{
		this.basic = roleList;
	}
	
	@Override
	public String getName()
	{
		return name;
	}
	
	@Override
	public void setName(String name)
	{
		this.name = name;
	}
	
	@Override
	public void addBasic(RoleComponent r)
	{
		basic.add(r);
	}
	
	@Override
	public void removeBasic(RoleComponent r)
	{
		basic.remove(r);
	}
	
	@Override
	public RoleComponent getChild(int index)
	{
		return basic.get(index);
	}

	@Override
	public ArrayList<RoleComponent> getChannelRoles()
	{
		return channelRoles;
	}

	@Override
	public void setChannelRoles(ArrayList<RoleComponent> channelRoles)
	{
		this.channelRoles = channelRoles;
	}
	
	@Override
	public boolean getBasicPermission(String name)
	{
		for (RoleComponent r: basic)
		{
			Permission p = (Permission) r;
			if (p.getName().equals(name))
				return p.getAllowed();
		}
		return false;
	}

	@Override
	public void setBasicPermission(String name, boolean p)
	{
		for (RoleComponent r: basic)
		{
			Permission per = (Permission) r;
			if (per.getName().equals(name))
			{
				per.setAllowed(p);
				return;
			}
		}
	}
	
	@Override
	public ChannelRole getChannelPermission(String name)
	{
		for (RoleComponent r: channelRoles)
		{
			ChannelRole cr = (ChannelRole) r;
			if (cr.getName().equals(name))
				return cr;
		}
		return null;
	}
}
