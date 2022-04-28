package views;

import java.rmi.RemoteException;

import concord.ConcordClient;
import concord.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import models.UserViewTransitionModel;
import models.UserViewTransitionModelInterface;

public class UserInfoController
{
	UserViewTransitionModelInterface model;
	ConcordClient client;
	
	@FXML
    private TextField passwordTextField;

    @FXML
    private TextField pfDataTextField;

    @FXML
    private TextField pfpTextField;

    @FXML
    private TextField realNameTextField;

    @FXML
    private TextField userNameTextField;
	
	public void setViewModel(UserViewTransitionModel m, ConcordClient c)
	{
		model = m;
		client = c;
	}
	

    @FXML
    void onUserInfoSaveButtonClick(ActionEvent event) 
    {
    	String uName = userNameTextField.getText();
    	String rName = realNameTextField.getText();
    	String pw = passwordTextField.getText();
    	String pfp = pfpTextField.getText();
    	String pfdata = pfDataTextField.getText();
    	
    	if (uName == "" || rName == "" || pw == "" || pfp == "" || pfdata == "")
    	{
    		model.showWarning("Please enter all information");
    	}
    	else 
    	{
    		try
			{
				client.setUsername(uName);
				client.setProfilePic(pfp);
	    		client.setProfileData(pfdata);
	    		client.setRealname(rName);
	    		client.setPassword(pw);
			} 
    		catch (RemoteException e)
			{
				model.showError();
				e.printStackTrace();
			}
    	}
    	System.out.println("INFO SAVED!!!!!");
    }


}
