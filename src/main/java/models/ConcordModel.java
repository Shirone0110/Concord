package models;

import concord.DirectConversation;
import concord.Message;
import concord.Server;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;

public class ConcordModel
{
	//ObservableList<Server> servers = FXCollections.observableArrayList();
	//ObservableList<DirectConversation> dcs = FXCollections.observableArrayList();
	//ObservableList<Message> messages = FXCollections.observableArrayList();
	ObservableList<Label> servers = FXCollections.observableArrayList();
	ObservableList<Label> dcs = FXCollections.observableArrayList();
	ObservableList<Label> messages = FXCollections.observableArrayList();
	
	public ConcordModel() {}
	
	public void reset()
	{
		servers = FXCollections.observableArrayList();
		dcs = FXCollections.observableArrayList();
		messages = FXCollections.observableArrayList();
	}
	
	/*public ObservableList<Server> getServers()
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
	}*/
	
	public ObservableList<Label> getServers()
	{
		return servers;
	}
	
	public ObservableList<Label> getDcs()
	{
		return dcs;
	}
	
	public ObservableList<Label> getMessages()
	{
		return messages;
	}
}
