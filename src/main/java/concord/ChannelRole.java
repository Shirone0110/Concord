package concord;

import java.io.Serializable;

public class ChannelRole extends RoleComponent implements Serializable
{
	
	private static final long serialVersionUID = -748275403747665957L;
	private RoleComponent viewChannel;
	private RoleComponent sendMessage;
	private String name;
	
	public ChannelRole() {}
	
	public ChannelRole(String channelName)
	{
		viewChannel = new Permission("view " + channelName, true);
		sendMessage = new Permission("send message " + channelName, true);	
		name = channelName;
	}

	@Override
	public RoleComponent getViewChannel()
	{
		return viewChannel;
	}

	@Override
	public void setViewChannel(RoleComponent viewChannel)
	{
		this.viewChannel = viewChannel;
	}

	@Override
	public void setSendMessage(RoleComponent sendMessage)
	{
		this.sendMessage = sendMessage;
	}

	@Override
	public void updateViewChannel(boolean view)
	{
		viewChannel.setAllowed(view);
	}

	@Override
	public RoleComponent getSendMessage()
	{
		return sendMessage;
	}

	@Override
	public void updateSendMessage(boolean send)
	{
		sendMessage.setAllowed(send);
	}
	
	@Override
	public String getName()
	{
		return name;
	}
	
	@Override
	public void setName(String channelName)
	{
		name = channelName;
	}
}
