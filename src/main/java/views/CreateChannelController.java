package views;

import java.rmi.RemoteException;

import concord.ConcordClient;
import concord.InvalidActionException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.ViewTransitionModel;
import models.ViewTransitionModelInterface;

public class CreateChannelController 
{

	Stage stage;
	ConcordClient client;
	private int serverId;
	ViewTransitionModel model;
	
	public void setModel(ViewTransitionModel vm, Stage s, ConcordClient c, int serverId)
	{
		model = vm;
		stage = s;
		client = c;
		this.serverId = serverId;
	}
	
    @FXML
    private TextField nameTextField;

    @FXML
    void onClickCancel(ActionEvent event) 
    {
    	stage.close();
    }

    @FXML
    void onClickCreate(ActionEvent event) 
    {
    	try
		{
			client.addChannel(serverId, nameTextField.getText());
		} catch (RemoteException e)
		{
			model.showError();
			e.printStackTrace();
		} catch (InvalidActionException e)
		{
			model.showWarning("You do not have the permission to create channel");
			e.printStackTrace();
		}
    	stage.close();
    }

}

