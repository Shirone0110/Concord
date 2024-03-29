package views;

import java.rmi.RemoteException;

import concord.ConcordClient;
import concord.ConcordClientInterface;
import concord.InvalidActionException;
import concord.Server;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import models.ConcordModel;
import models.ViewTransitionModel;
import models.ViewTransitionModelInterface;

public class CreateServerController 
{
    @FXML
    private TextField newNameTextField;
    
    @FXML
    private TextField deleteServerTextField;

    @FXML
    private RadioButton radbtnNo;

    @FXML
    private RadioButton radbtnYes;

    private ToggleGroup privGroup;
    Stage stage;
    ConcordClient client;
    ConcordModel model;
    ViewTransitionModel viewModel;
    
    public void setModel(Stage s, ConcordClient c, ConcordModel m, ViewTransitionModel vm)
    {
    	stage = s;
    	client = c;
    	model = m;
    	viewModel = vm;
    	privGroup = new ToggleGroup();
    	radbtnYes.setToggleGroup(privGroup);
    	radbtnNo.setToggleGroup(privGroup);
    	radbtnYes.setSelected(true);
    }
    
    @FXML
    void onClickCancel(ActionEvent event) 
    {
    	stage.close();
    }

    @FXML
    void onClickSubmit(ActionEvent event) 
    {
    	String nameCreate = newNameTextField.getText();
    	boolean priv = radbtnYes.isSelected();
    	String nameDel = deleteServerTextField.getText();
    	stage.close();
    	try
    	{
    		if (nameCreate != "")
        		client.createServer(nameCreate, priv);
        	if (nameDel != "")
        	{
        		for (Server s: model.getServers())
        		{
        			if (s.getServerName().equals(nameDel))
        				client.deleteServer(s.getServerId());
        		}
        	}
    	}	
    	catch (RemoteException e)
		{
			viewModel.showError();
		} catch (InvalidActionException e)
		{
			viewModel.showWarning("You do not have the permission to delete this server.");
		}
    }
}
