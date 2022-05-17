package concord;

import java.util.Date;

public class Invitation extends Message
{
	
	private static final long serialVersionUID = -8873630852215278110L;
	private User other;
	private Server server;
	
	public Invitation() {}
	
	public Invitation(User ad, User o, Server s)
	{
		user = ad;
		other = o;
		server = s;
		content = "Hi " + other.getUserName() + "! Click on this message to join my server!";
		time = new Date();
	}

	/**
	 * @return the other
	 */
	public User getOther()
	{
		return other;
	}
	/**
	 * @param other the other to set
	 */
	public void setOther(User other)
	{
		this.other = other;
	}
	/**
	 * @return the server
	 */
	public Server getServer()
	{
		return server;
	}
	/**
	 * @param server the server to set
	 */
	public void setServer(Server server)
	{
		this.server = server;
	}
}
