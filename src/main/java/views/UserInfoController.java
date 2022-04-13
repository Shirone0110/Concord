package views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import models.UserViewTransitionModel;
import models.UserViewTransitionModelInterface;

public class UserInfoController
{
	UserViewTransitionModelInterface model;
	
	public void setViewModel(UserViewTransitionModel m)
	{
		model = m;
	}
	

    @FXML
    void onUserInfoSaveButtonClick(ActionEvent event) 
    {
    	System.out.println("INFO SAVED!!!!!");
    }


}
