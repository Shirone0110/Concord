package views;

import java.io.IOException;
import java.rmi.RemoteException;

import concord.Channel;
import concord.ConcordClient;
import concord.ConcordClientInterface;
import concord.Main;
import concord.Message;
import concord.Server;
import concord.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.ConcordModel;
import models.ViewTransitionModel;
import models.ViewTransitionModelInterface;

public class ServerController
{
	ConcordClient client;
	ConcordModel concordModel;
	ViewTransitionModel model;
	private int channelId;
	private int serverId;
	
	@FXML
    private ListView<Channel> channelListView;

    @FXML
    private Label channelNameLabel;

    @FXML
    private ListView<Message> messageListView;

    @FXML
    private TextField messageTextField;

    @FXML
    private Label userNameLabel;

    @FXML
    private ListView<User> usersListView;
    
    private void onSelectedChannel()
    {
    	Channel c = channelListView.getSelectionModel().getSelectedItem();
    	if (c != null)
    	{
    		System.out.println("selected channel");
    		channelNameLabel.setText(c.getChannelName());
    		concordModel.setMessages(c.getMessages());
    		messageListView.setItems(concordModel.getMessages());
    		channelId = c.getChannelId();
    		serverId = c.getFrom().getServerId();
    	}
    }
	
	public void setModel(ViewTransitionModel vModel, ConcordModel m, ConcordClient c)
	{
		// set models
		model = vModel;
		client = c;
		concordModel = m;
		
		// set views
		userNameLabel.setText(client.getUser().getUserName());
		
		channelListView.setItems(concordModel.getChannels());
		channelListView.getSelectionModel().selectedItemProperty().addListener((e)->{onSelectedChannel();});
		
		messageListView.setItems(concordModel.getMessages());
		usersListView.setItems(concordModel.getUsers());
		
		// set ids for later use
		Channel chan = channelListView.getItems().get(0);
		channelNameLabel.setText(chan.getChannelName());
		channelId = chan.getChannelId();
		serverId = chan.getFrom().getServerId();
	}

    @FXML
    void btnPinsClicked(ActionEvent event) 
    {

    }

    @FXML
    void btnSettingsClicked(ActionEvent event) 
    {
    	model.showUser();
    }

    @FXML
    void channelListViewClicked(MouseEvent event) 
    {
    	Channel c = channelListView.getSelectionModel().getSelectedItem();
    	channelNameLabel.setText(c.getChannelName());
    	concordModel.setMessages(c.getMessages());
		messageListView.setItems(concordModel.getMessages());
		channelId = c.getChannelId();
		serverId = c.getFrom().getServerId();
    }
    
    @FXML
    void onClickManageChannel(ActionEvent event) 
    {
    	Stage stage = new Stage();
    	stage.initModality(Modality.APPLICATION_MODAL);
    	FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("../views/CreateChannelView.fxml"));
		BorderPane view;
		try
		{
			view = loader.load();
			CreateChannelController cont = loader.getController();
			cont.setModel(concordModel, stage, client, serverId);
			Scene s = new Scene(view);
			stage.setScene(s);
			stage.showAndWait();
		} catch (IOException e)
		{
			model.showError();
			e.printStackTrace();
		}
    }
    
    @FXML
    void onEnterPressed(ActionEvent event) 
    {
    	String message = messageTextField.getText();
    	try
		{
    		messageTextField.setText("");
			client.sendChannelMessage(message, serverId, channelId);
		} catch (RemoteException e)
		{
			model.showError();
			e.printStackTrace();
		}
    }
    
    @FXML
    void onManageUserClick(ActionEvent event) 
    {
    	Stage stage = new Stage();
    	stage.initModality(Modality.APPLICATION_MODAL);
    	FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("../views/ManageUserView.fxml"));
		BorderPane view;
		try
		{
			view = loader.load();
			ManageUserController cont = loader.getController();
			cont.setModel(concordModel, stage, client, serverId);
			Scene s = new Scene(view);
			stage.setScene(s);
			stage.showAndWait();
		} catch (IOException e)
		{
			model.showError();
			e.printStackTrace();
		}
    }
}
