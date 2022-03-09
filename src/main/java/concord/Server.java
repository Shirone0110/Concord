package concord;

import java.util.ArrayList;
import java.util.HashMap;

public class Server
{
	private String serverName;
	private int serverId;
	private ArrayList<Channel> channels;
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

	public void addChannel(User u, Channel channel) throws InvalidActionException
	{
		Role role = roleMap.get(u);
		if (role.isModifyChannel())
			channels.add(channel);
		else 
			throw new InvalidActionException();
	}
	
	public void deleteChannel(User u, Channel channel) throws InvalidActionException
	{
		Role role = roleMap.get(u);
		if (role.isModifyChannel())
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
		if (adRole.isModifyMember())
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
		if (role.isModifyMember())
			users.remove(member.getUserId());
		else 
			throw new InvalidActionException();
	}
	
	public void createRole(User admin, String name, String p) throws InvalidActionException
	{
		Role role = roleMap.get(admin);
		if (role.isModifyRole())
			roleBuilder.addRole(name, p);
		else 
			throw new InvalidActionException();
	}
	
	public void changeRole(User admin, User user, Role role) throws InvalidActionException
	{
		Role r = roleMap.get(admin);
		if (r.isModifyRole())
			roleMap.put(user, role);
		else 
			throw new InvalidActionException();
	}
	
	public String invite(User admin, User user) throws InvalidActionException
	{
		Role role = roleMap.get(admin);
		if (role.isModifyMember())
			return "dummy url";
		else 
			throw new InvalidActionException();
	}
	
	public ArrayList<User> getUsers()
	{
		return users;
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
