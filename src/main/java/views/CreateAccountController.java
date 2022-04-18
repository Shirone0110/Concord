package views;

import java.rmi.RemoteException;

import concord.ConcordClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import models.ViewTransitionModel;

public class CreateAccountController 
{
	ViewTransitionModel model;
	ConcordClient client;
	
    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField realNameTextField;

    @FXML
    private TextField userNameTextField;
	
    @FXML
    void onClickSubmit(ActionEvent event) throws RemoteException 
    {
    	String username = userNameTextField.textProperty().get();
    	String realname = realNameTextField.textProperty().get();
    	String password = passwordTextField.textProperty().get();
    	
    	if (username != "" && realname != "" && password != "")
    	{
    		client.createUser(username, realname, password);
    	}
    	else 
    	{
    		// screams
    		System.out.println("A");
    	}
    }
    
    @FXML
    void onClickBack(ActionEvent event) 
    {
    	model.showLogin();
    }

	public void setModel(ViewTransitionModel m, ConcordClient model2)
	{
		model = m;
		client = model2;
	}

}
