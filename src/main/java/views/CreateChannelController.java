package views;

import java.rmi.RemoteException;

import concord.Channel;
import concord.ConcordClient;
import concord.InvalidActionException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.ConcordModel;
import models.ViewTransitionModel;
import models.ViewTransitionModelInterface;

public class CreateChannelController 
{

	Stage stage;
	ConcordClient client;
	private int serverId;
	ConcordModel model;
	
	public void setModel(ConcordModel m, Stage s, ConcordClient c, int serverId)
	{
		model = m;
		stage = s;
		client = c;
		this.serverId = serverId;
	}
	
    @FXML
    private TextField nameTextField;
    
    @FXML
    private TextField deleteNameTextField;

    @FXML
    void onClickCancel(ActionEvent event) 
    {
    	stage.close();
    }

    @FXML
    void onClickSubmit(ActionEvent event) throws RemoteException, InvalidActionException 
    {
    	String addName = nameTextField.getText();
    	String delName = deleteNameTextField.getText();
    	if (addName != "")
			client.addChannel(serverId, nameTextField.getText());
    	
    	if (delName != "")
    	{
    		for (Channel c: model.getChannels())
    		{
    			if (c.getChannelName().equals(delName))
    				client.deleteChannel(serverId, c.getChannelId());
    		}
    	}
    	stage.close();
    }

}

