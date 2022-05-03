package views;

import java.rmi.RemoteException;

import concord.ConcordClient;
import concord.ConcordClientInterface;
import concord.InvalidCredentialException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import models.ViewTransitionModel;
import models.ViewTransitionModelInterface;

public class LoginController
{
	ViewTransitionModel model;
	ConcordClient client;
	
	@FXML
    private TextField passwordTextField;

    @FXML
    private TextField userNameTextField;
	
	public void setModel(ViewTransitionModel m, ConcordClient model2)
	{
		model = m;
		client = model2;
	}
	
	@FXML
	void onClickSubmit(ActionEvent event) throws InvalidCredentialException 
	{
		String name = userNameTextField.textProperty().get();
		String pw = passwordTextField.textProperty().get();
		try
		{
			client.verify(name, pw);
			userNameTextField.textProperty().set("");
			passwordTextField.textProperty().set("");
			model.showContent();
		} 
		catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }
	
	@FXML
	void onClickCreate(ActionEvent event)
	{
		model.showCreate();
	}
}
