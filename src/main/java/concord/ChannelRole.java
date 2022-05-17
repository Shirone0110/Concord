package concord;

import java.io.Serializable;

public class ChannelRole extends RoleComponent implements Serializable
{
	
	private static final long serialVersionUID = -748275403747665957L;
	private RoleComponent viewChannel;
	private RoleComponent sendMessage;
	
	public ChannelRole() {}
	
	public ChannelRole(String channelName)
	{
		viewChannel = new Permission("view " + channelName, true);
		sendMessage = new Permission("send message " + channelName, true);	 
	}

	public RoleComponent getViewChannel()
	{
		return viewChannel;
	}

	public void setViewChannel(RoleComponent viewChannel)
	{
		this.viewChannel = viewChannel;
	}

	public RoleComponent getSendMessage()
	{
		return sendMessage;
	}

	public void setSendMessage(RoleComponent sendMessage)
	{
		this.sendMessage = sendMessage;
	}
	
	
}
