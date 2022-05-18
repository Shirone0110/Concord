package concord;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Server implements Serializable
{
	private static final long serialVersionUID = 2051177441727882118L;
	private String serverName;
	private int serverId;
	private ArrayList<Channel> channels;
	private int channelCount;
	private ArrayList<Message> pins;
	private boolean isPrivate;
	private ArrayList<User> users;
	private String serverLogo;
	private String serverDescription;
	private RoleBuilder roleBuilder;
	private HashMap<User, RoleComponent> roleMap;
	
	public Server(User admin, String name, int id, boolean priv)
	{
		//System.out.println("server name: " + name);
		serverName = name;
		serverId = id;
		isPrivate = priv;
		serverLogo = "";
		serverDescription = "";
		channelCount = 0;
		channels = new ArrayList<Channel>();
		pins = new ArrayList<Message>();
		users = new ArrayList<User>();
		roleBuilder = new RoleBuilder();
		roleMap = new HashMap<User, RoleComponent>();
		
		users.add(admin);
		roleMap.put(admin, roleBuilder.getRole("Admin"));
		try
		{
			addChannel(admin, "general");
		} 
		catch (InvalidActionException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Server()
	{
		new Server(new User(), "default", 0, false);
	}
	
	public boolean checkViewChannel(User u, Channel c) 
	{
		/*
		 * If general permission = true -> check child
		 * else -> false
		 */
		Role role = (Role) roleMap.get(u);
		System.out.println("User role: " + role.getName() + " view: " + role.getBasicPermission("view channel"));
		if (!role.getBasicPermission("view channel")) return false;
		System.out.println(c.getChannelName() + ": " + role.getChannelPermission(c.getChannelName()));
		return role.getChannelPermission(c.getChannelName()).getViewChannel().getAllowed();
	}
	
	public boolean checkSendMessage(User u, Channel c)
	{
		/*
		 * If general permission = true -> check child
		 * else -> false
		 */
		Role role = (Role) roleMap.get(u);
		System.out.println(role.getName());
		if (!role.getBasicPermission("send message")) return false;
		return role.getChannelPermission(c.getChannelName()).getSendMessage().getAllowed();
	}

	public Channel addChannel(User u, String name) throws InvalidActionException
	{
		RoleComponent role = roleMap.get(u);
		if (role.getBasicPermission("modify channel"))
		{
			Channel c = new Channel(name, this, channelCount);
			for (RoleComponent r: roleBuilder.getRoleList())
			{
				Role cur = (Role) r;
				cur.addChannel(name);
			}
			channelCount++;
			channels.add(c);
			return c;
		}
		else 
			throw new InvalidActionException();
	}
	
	public void deleteChannel(User u, Channel channel) throws InvalidActionException
	{
		RoleComponent role = roleMap.get(u);
		if (role.getBasicPermission("modify channel"))
			channels.remove(channel);
		else 
			throw new InvalidActionException();
	}
	
	public Channel getChannelById(int id)
	{
		for (Channel c: channels)
		{
			if (c.getChannelId() == id) return c;
		}
		return null;
	}
	
	public void addPin(Message m)
	{
		pins.add(m);
	}
	
	public void removePin(Message m)
	{
		pins.remove(m);
	}
	
	public void addMember(User member) 
	{
		for (User u: users)
		{
			if (u.equals(member)) return;
		}
		users.add(member);
		roleMap.put(member, roleBuilder.getRole("Member"));
	}
	
	public void removeMember(User admin, User member) throws InvalidActionException
	{
		RoleComponent adRole = roleMap.get(admin);
		if (adRole.getBasicPermission("remove member"))
			users.remove(member.getUserId());
		else 
			throw new InvalidActionException();
	}
	
	public RoleComponent createRole(User admin, String name, ArrayList<Boolean> p) throws InvalidActionException
	{
		RoleComponent adRole = roleMap.get(admin);
		if (adRole.getBasicPermission("modify role"))
		{
			Role newRole = (Role) roleBuilder.makeRole(name, p);
			for (Channel c: channels)
				newRole.addChannel(c.getChannelName());
			return newRole;
		}
		else 
			throw new InvalidActionException();
	}
	
	public void changeRole(User admin, User user, RoleComponent role) throws InvalidActionException
	{
		RoleComponent adRole = roleMap.get(admin);
		if (adRole.getBasicPermission("modify role"))
			roleMap.put(user, role);
		else 
			throw new InvalidActionException();
	}
	
	public void updateRole(String roleName, ArrayList<Boolean> perm) 
	{
		ArrayList<String> nameList = roleBuilder.getBasicNames();
		Role cur = (Role) roleBuilder.getRole(roleName);
		System.out.println("Updating " + cur.getName());
		for (int i = 0; i < perm.size(); i++)
		{
			cur.setBasicPermission(nameList.get(i), perm.get(i));
			System.out.print(perm.get(i) + " ");
		}
		System.out.println();
	}
	
	public void updateChannelRole(String roleName, Channel c, boolean view, boolean send)
	{
		Role cur = (Role) roleBuilder.getRole(roleName);
		ChannelRole chanRole = cur.getChannelPermission(c.getChannelName());
		chanRole.updateSendMessage(send);
		chanRole.updateViewChannel(view);
	}
	

	public ChannelRole getChannelRole(User u, Channel c)
	{
		Role cur = (Role) roleMap.get(u);
		return cur.getChannelPermission(c.getChannelName());
	}
	
	public void changeServerName(User admin, String name) throws InvalidActionException
	{
		RoleComponent adRole = roleMap.get(admin);
		if (adRole.getBasicPermission("modify admin"))
			setServerName(name);
		else 
			throw new InvalidActionException();
	}
	
	public void changeChannelName(User admin, Channel c, String name) throws InvalidActionException
	{
		RoleComponent adRole = roleMap.get(admin);
		if (adRole.getBasicPermission("modify channel"))
			c.setChannelName(name);
		else
			throw new InvalidActionException();
	}
	
	public void invite(User admin, User user) throws InvalidActionException
	{
		RoleComponent adRole = roleMap.get(admin);
		if (!adRole.getBasicPermission("add member"))
			throw new InvalidActionException();
	}
	
	public ArrayList<Channel> getChannels()
	{
		return channels;
	}
	
	/**
	 * @param channels the channels to set
	 */
	public void setChannels(ArrayList<Channel> channels)
	{
		this.channels = channels;
	}

	public ArrayList<User> getUsers()
	{
		return users;
	}
	
	/**
	 * @param users the users to set
	 */
	public void setUsers(ArrayList<User> users)
	{
		this.users = users;
	}

	/**
	 * @return the serverName
	 */
	public String getServerName()
	{
		return serverName;
	}

	/**
	 * @param serverName the serverName to set
	 */
	public void setServerName(String serverName)
	{
		this.serverName = serverName;
	}

	/**
	 * @return the serverId
	 */
	public int getServerId()
	{
		return serverId;
	}

	/**
	 * @param serverId the serverId to set
	 */
	public void setServerId(int serverId)
	{
		this.serverId = serverId;
	}

	/**
	 * @return the isPrivate
	 */
	public boolean getIsPrivate()
	{
		return isPrivate;
	}

	/**
	 * @param isPrivate the isPrivate to set
	 */
	public void setIsPrivate(boolean isPrivate)
	{
		this.isPrivate = isPrivate;
	}

	/**
	 * @return the channelCount
	 */
	public int getChannelCount()
	{
		return channelCount;
	}

	/**
	 * @param channelCount the channelCount to set
	 */
	public void setChannelCount(int channelCount)
	{
		this.channelCount = channelCount;
	}
	
	/**
	 * @return the pins
	 */
	public ArrayList<Message> getPins()
	{
		return pins;
	}

	/**
	 * @param pins the pins to set
	 */
	public void setPins(ArrayList<Message> pins)
	{
		this.pins = pins;
	}

	/**
	 * @return the roleBuilder
	 */
	public RoleBuilder getRoleBuilder()
	{
		return roleBuilder;
	}

	/**
	 * @param roleBuilder the roleBuilder to set
	 */
	public void setRoleBuilder(RoleBuilder roleBuilder)
	{
		this.roleBuilder = roleBuilder;
	}

	/**
	 * @return the roleMap
	 */
	public HashMap<User, RoleComponent> getRoleMap()
	{
		return roleMap;
	}

	/**
	 * @param roleMap the roleMap to set
	 */
	public void setRoleMap(HashMap<User, RoleComponent> roleMap)
	{
		this.roleMap = roleMap;
	}

	/**
	 * @return the serverLogo
	 */
	public String getServerLogo()
	{
		return serverLogo;
	}

	/**
	 * @param serverLogo the serverLogo to set
	 */
	public void setServerLogo(String serverLogo)
	{
		this.serverLogo = serverLogo;
	}

	/**
	 * @return the serverDescription
	 */
	public String getServerDescription()
	{
		return serverDescription;
	}

	/**
	 * @param serverDescription the serverDescription to set
	 */
	public void setServerDescription(String serverDescription)
	{
		this.serverDescription = serverDescription;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj == this) return true;
		if (!(obj instanceof Server)) return false;
		Server s = (Server) obj;
		if (serverId == s.getServerId()) return true;
		return false;
	}
}
