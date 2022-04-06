package concord;

import java.util.ArrayList;
import java.util.HashMap;

public class Server
{
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
	private HashMap<User, Role> roleMap;
	
	public Server(User admin, String name, int id, boolean priv)
	{
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
		roleMap = new HashMap<User, Role>();
		
		roleBuilder.addRole("Admin", "11111");
		roleBuilder.addRole("Moderator", "10101");
		roleBuilder.addRole("Member", "00000");
		
		users.add(admin);
		roleMap.put(admin, roleBuilder.buildRole("Admin"));
	}
	
	public Server()
	{
		new Server(new User(), "default", 0, false);
	}

	public void addChannel(User u, String name) throws InvalidActionException
	{
		Role role = roleMap.get(u);
		if (role.getModifyChannel())
		{
			Channel c = new Channel(name, this, channelCount);
			channelCount++;
			channels.add(c);
		}
		else 
			throw new InvalidActionException();
	}
	
	public void deleteChannel(User u, Channel channel) throws InvalidActionException
	{
		Role role = roleMap.get(u);
		if (role.getModifyChannel())
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
	
	public void addMember(User admin, User member) throws InvalidActionException
	{
		Role adRole = roleMap.get(admin);
		if (adRole.getModifyMember())
		{
			users.add(member);
			roleMap.put(member, roleBuilder.buildRole("Member"));
		}
		else 
			throw new InvalidActionException();
	}
	
	public void removeMember(User admin, User member) throws InvalidActionException
	{
		Role role = roleMap.get(admin);
		if (role.getModifyMember())
			users.remove(member.getUserId());
		else 
			throw new InvalidActionException();
	}
	
	public void createRole(User admin, String name, String p) throws InvalidActionException
	{
		Role role = roleMap.get(admin);
		if (role.getModifyRole())
			roleBuilder.addRole(name, p);
		else 
			throw new InvalidActionException();
	}
	
	public void changeRole(User admin, User user, Role role) throws InvalidActionException
	{
		Role r = roleMap.get(admin);
		if (r.getModifyRole())
			roleMap.put(user, role);
		else 
			throw new InvalidActionException();
	}
	
	public void changeServerName(User admin, String name) throws InvalidActionException
	{
		Role r = roleMap.get(admin);
		if (r.getModifyAdmin())
			setServerName(name);
		else 
			throw new InvalidActionException();
	}
	
	public void changeChannelName(User admin, Channel c, String name) throws InvalidActionException
	{
		Role r = roleMap.get(admin);
		if (r.getModifyChannel())
			c.setChannelName(name);
		else
			throw new InvalidActionException();
	}
	
	public String invite(User admin, User user) throws InvalidActionException
	{
		Role role = roleMap.get(admin);
		if (role.getModifyMember())
			return "dummy url";
		else 
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
	private void setServerName(String serverName)
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
	
	public void setServerId(int id)
	{
		serverId = id;
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
	public void setPrivate(boolean isPrivate)
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
	public HashMap<User, Role> getRoleMap()
	{
		return roleMap;
	}

	/**
	 * @param roleMap the roleMap to set
	 */
	public void setRoleMap(HashMap<User, Role> roleMap)
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
