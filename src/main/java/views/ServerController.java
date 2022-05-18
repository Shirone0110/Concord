package views;

import java.io.IOException;
import java.rmi.RemoteException;

import concord.Channel;
import concord.ChannelRole;
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
    		try
			{
				if (client.checkSendMessage(channelId, serverId)) 
					messageTextField.setDisable(false);
				else 
					messageTextField.setDisable(true);
			} catch (RemoteException e)
			{
				model.showError();
			}
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
		if (concordModel.getChannels().size() > 0)
			channelListView.getSelectionModel().select(0);
		
		messageListView.setItems(concordModel.getMessages());
		usersListView.setItems(concordModel.getUsers());
		
		// set ids for later use
		if (channelListView.getItems().size() > 0)
		{
			Channel chan = channelListView.getItems().get(0);
			channelNameLabel.setText(chan.getChannelName());
			channelId = chan.getChannelId();
			serverId = chan.getFrom().getServerId();
		}
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
    void onClickManageChannel(ActionEvent event) 
    {
    	try
		{
			if (!client.checkBasicPermission(serverId, "modify channel"))
			{
				model.showWarning("You do not have the permission to modify channels");
			}
			else
			{
				Stage stage = new Stage();
		    	stage.initModality(Modality.APPLICATION_MODAL);
		    	FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("../views/ManageChannelView.fxml"));
				BorderPane view;
				try
				{
					view = loader.load();
					ManageChannelController cont = loader.getController();
					cont.setModel(concordModel, stage, client, serverId, model);
					Scene s = new Scene(view);
					stage.setScene(s);
					stage.showAndWait();
				} catch (IOException e)
				{
					model.showError();
					//e.printStackTrace();
				}
			}
		} 
    	catch (RemoteException e1)
		{
			model.showError();
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
    	try
		{
			if (!client.checkBasicPermission(serverId, "add member") 
			 && !client.checkBasicPermission(serverId, "remove member")
			 && !client.checkBasicPermission(serverId, "modify role")) 
			{
				model.showWarning("You do not have the permission to manage members");
			}
			else
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
					cont.setModel(concordModel, stage, client, serverId, model);
					Scene s = new Scene(view);
					stage.setScene(s);
					stage.showAndWait();
				} catch (IOException e)
				{
					model.showError();
				}
			}
		} catch (RemoteException e1)
		{
			model.showError();
		}
    }
    
    @FXML
    void onClickManageRole(ActionEvent event) 
    {
    	try
		{
			if (!client.checkBasicPermission(serverId, "modify role")) 
			{
				model.showWarning("You do not have the permission to manage roles");
			}
			else
			{
				Stage stage = new Stage();
		    	stage.initModality(Modality.APPLICATION_MODAL);
		    	FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("../views/ManageRoleView.fxml"));
				BorderPane view;
				try
				{
					view = loader.load();
					ManageRoleController cont = loader.getController();
					cont.setModel(concordModel, stage, client, serverId, model);
					Scene s = new Scene(view);
					stage.setScene(s);
					stage.showAndWait();
				} catch (IOException e)
				{
					model.showError();
				}
			}
		} catch (RemoteException e1)
		{
			model.showError();
		}
    }
}
