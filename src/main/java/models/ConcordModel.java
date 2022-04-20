package models;

import concord.DirectConversation;
import concord.Message;
import concord.Server;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ConcordModel
{
	ObservableList<Server> servers = FXCollections.observableArrayList();
	ObservableList<DirectConversation> dcs = FXCollections.observableArrayList();
	ObservableList<Message> messages = FXCollections.observableArrayList();
	
	public ConcordModel() {}
	
	public ObservableList<Server> getServers()
	{
		return servers;
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
