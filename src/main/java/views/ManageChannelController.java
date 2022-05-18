package views;

import java.rmi.RemoteException;

import concord.Channel;
import concord.ConcordClient;
import concord.InvalidActionException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.ConcordModel;
import models.ViewTransitionModel;
import models.ViewTransitionModelInterface;

public class ManageChannelController 
{

	Stage stage;
	ConcordClient client;
	private int serverId;
	ConcordModel model;
	ViewTransitionModel viewModel;
	Channel delTarget;
	
	@FXML
    private TextField nameTextField;
    
    @FXML
    private MenuButton deleteDropDown;
	
	public void setModel(ConcordModel m, Stage s, ConcordClient c, int serverId, ViewTransitionModel vm)
	{
		model = m;
		stage = s;
		client = c;
		viewModel = vm;
		this.serverId = serverId;
		
		deleteDropDown.getItems().clear();
		try
		{
			for (Channel chan: client.getChannelByUserId(serverId))
			{
				MenuItem item = new MenuItem(chan.getChannelName());
				item.setId(chan.getChannelName());
				item.setOnAction(new EventHandler<ActionEvent>() {
			    @Override 
			    public void handle(ActionEvent e) 
			    {
			    	delTarget = chan;
			    	deleteDropDown.setText(chan.getChannelName());
			    }
				});
				deleteDropDown.getItems().add(item);
			}
		} catch (RemoteException e)
		{
			viewModel.showError();
		}
	}

    @FXML
    void onClickCancel(ActionEvent event) 
    {
    	stage.close();
    }

    @FXML
    void onClickSubmit(ActionEvent event) throws RemoteException, InvalidActionException 
    {
    	String addName = nameTextField.getText();
    	if (addName != "")
			client.addChannel(serverId, nameTextField.getText());
    	
    	if (delTarget != null)
    	{
    		client.deleteChannel(serverId, delTarget.getChannelId());
    	}
    	stage.close();
    }

}

