package concord;

import java.util.ArrayList;

public abstract class RoleComponent
{	
	
	public String getName()
	{
		throw new UnsupportedOperationException();
	}
	
	public void setName(String name)
	{
		throw new UnsupportedOperationException();
	}
	
	public Boolean getAllowed()
	{
		throw new UnsupportedOperationException();
	}
	
	public void setAllowed(Boolean allowed)
	{
		throw new UnsupportedOperationException();
	}
	
	public void addChannel(String name)
	{
		throw new UnsupportedOperationException();
	}
	
	public void removeChannel(String name)
	{
		throw new UnsupportedOperationException();
	}
	
	public ArrayList<RoleComponent> getBasic()
	{
		throw new UnsupportedOperationException();
	}
	
	public void setBasic(ArrayList<RoleComponent> roleList)
	{
		throw new UnsupportedOperationException();
	}
	
	public void addBasic(RoleComponent r)
	{
		throw new UnsupportedOperationException();
	}
	
	public void removeBasic(RoleComponent r)
	{
		throw new UnsupportedOperationException();
	}
	
	public RoleComponent getChild(int index)
	{
		throw new UnsupportedOperationException();
	}
	
	public ArrayList<RoleComponent> getChannelRoles()
	{
		throw new UnsupportedOperationException();
	}

	public void setChannelRoles(ArrayList<RoleComponent> channelRoles)
	{
		throw new UnsupportedOperationException();
	}
	
	public boolean getBasicPermission(String name)
	{
		throw new UnsupportedOperationException();
	}
	
	public void setBasicPermission(String name, boolean p)
	{
		throw new UnsupportedOperationException();
	}
	
	public RoleComponent getChannelPermission(String name)
	{
		throw new UnsupportedOperationException();
	}
	
	public RoleComponent getViewChannel()
	{
		throw new UnsupportedOperationException();
	}
	
	public RoleComponent getSendMessage()
	{
		throw new UnsupportedOperationException();
	}
	
	public void updateSendMessage(boolean send)
	{
		throw new UnsupportedOperationException();
	}

	public void setViewChannel(RoleComponent viewChannel)
	{
		throw new UnsupportedOperationException();
	}

	public void setSendMessage(RoleComponent sendMessage)
	{
		throw new UnsupportedOperationException();
	}

	public void updateViewChannel(boolean view)
	{
		throw new UnsupportedOperationException();
	}
}
