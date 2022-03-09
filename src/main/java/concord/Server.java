package concord;

import java.util.ArrayList;
import java.util.HashMap;

public class Server
{
	String serverName;
	int serverId;
	ArrayList<Channel> channels;
	ArrayList<Message> pins;
	boolean isPrivate;
	ArrayList<User> users;
	String serverLogo;
	String serverDescription;
	RoleBuilder roleBuilder;
	HashMap<User, Role> roleMap;
	
	public Server(User admin, String name, int id, boolean priv)
	{
		serverName = name;
		serverId = id;
		isPrivate = priv;
		channels = new ArrayList<Channel>();
		pins = new ArrayList<Message>();
		users = new ArrayList<User>();
		roleBuilder = new RoleBuilder();
		roleMap = new HashMap<User, Role>();
		
		roleBuilder.addRole("Admin", "111111111");
		roleBuilder.addRole("Moderator", "101010011");
		roleBuilder.addRole("Member", "000000000");
		
		users.add(admin);
		roleMap.put(admin, roleBuilder.buildRole("Admin"));
	}

	public void addChannel(User u, Channel channel) throws InvalidActionException
	{
		Role role = roleMap.get(u);
		if (role.isAddChannel())
			channels.add(channel);
		else 
			throw new InvalidActionException();
	}
	
	public void deleteChannel(User u, Channel channel) throws InvalidActionException
	{
		Role role = roleMap.get(u);
		if (role.isRemoveChannel())
			channels.remove(channel);
		else 
			throw new InvalidActionException();
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
		if (adRole.isAddMember())
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
		if (role.isRemoveMember())
			users.remove(member.getUserId());
		else 
			throw new InvalidActionException();
	}
	
	public void createRole(User admin, String name, String p) throws InvalidActionException
	{
		Role role = roleMap.get(admin);
		if (role.isRemoveMember())
			roleBuilder.addRole(name, p);
		else 
			throw new InvalidActionException();
	}
	
	public void changeRole(User admin, User user, Role role) throws InvalidActionException
	{
		Role r = roleMap.get(admin);
		if (r.isChangeRole())
			roleMap.put(user, role);
		else 
			throw new InvalidActionException();
	}
	
	public String invite(User admin, User user) throws InvalidActionException
	{
		Role role = roleMap.get(admin);
		if (role.isAddMember())
			return "dummy url";
		else 
			throw new InvalidActionException();
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
	 * @return the isPrivate
	 */
	public boolean isPrivate()
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
}
