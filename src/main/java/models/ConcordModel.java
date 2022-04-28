package models;

import concord.Channel;
import concord.DirectConversation;
import concord.Message;
import concord.Server;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;

public class ConcordModel
{
	ObservableList<Server> servers = FXCollections.observableArrayList();
	ObservableList<Channel> channels = FXCollections.observableArrayList();
	ObservableList<DirectConversation> dcs = FXCollections.observableArrayList();
	ObservableList<Message> messages = FXCollections.observableArrayList();
	
	public ConcordModel() {}
	
	public void reset()
	{
		servers = FXCollections.observableArrayList();
		dcs = FXCollections.observableArrayList();
		messages = FXCollections.observableArrayList();
		channels = FXCollections.observableArrayList();
	}
	
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
}
