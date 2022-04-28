package models;

import java.util.ArrayList;

import concord.Channel;
import concord.DirectConversation;
import concord.Message;
import concord.Server;
import concord.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;

public class ConcordModel
{
	ObservableList<Server> servers = FXCollections.observableArrayList();
	ObservableList<Channel> channels = FXCollections.observableArrayList();
	ObservableList<DirectConversation> dcs = FXCollections.observableArrayList();
	ObservableList<Message> messages = FXCollections.observableArrayList();
	ObservableList<User> users = FXCollections.observableArrayList();
	
	public ConcordModel() {}
	
	public ObservableList<Server> getServers()
	{
		return servers;
	}
	
	public ObservableList<Channel> getChannels()
	{
		return channels;
	}
	
	public ObservableList<DirectConversation> getDcs()
	{
		return dcs;
	}
	
	public ObservableList<Message> getMessages()
	{
		return messages;
	}
	
	public ObservableList<User> getUsers()
	{
		return users;
	}
	
	public void setServers(ArrayList<Server> a)
	{
		servers.clear();
		for (Server s: a) 
		{
			servers.add(s);
		}
	}
	
	public void setChannels(ArrayList<Channel> a)
	{
		channels.clear();
		for (Channel c: a)
		{
			channels.add(c);
		}
	}
	
	public void setMessages(ArrayList<Message> a)
	{
		messages.clear();
		for (Message m: a)
		{
			messages.add(m);
		}
	}
	
	public void setDcs(ArrayList<DirectConversation> a)
	{
		dcs.clear();
		for (DirectConversation d: a)
		{
			dcs.add(d);
		}
	}
	
	public void setUsers(ArrayList<User> a)
	{
		users.clear();
		for (User u: a)
		{
			users.add(u);
		}
	}
}
