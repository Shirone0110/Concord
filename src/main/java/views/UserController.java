package views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import models.UserViewTransitionModel;
import models.ViewTransitionModel;

public class UserController
{
	UserViewTransitionModel userModel;
	ViewTransitionModel viewModel;
	
	public UserController()
	{
		
	}
	
	public void setViewModel(ViewTransitionModel v)
	{
		viewModel = v;
		userModel = v.getUserModel();
	}
	
    @FXML
    void onBackButtonClick(ActionEvent event) 
    {
    	viewModel.showContent();

    }

    @FXML
    void onBlockListClick(ActionEvent event) 
    {
    	userModel.showBlock();
    }

    @FXML
    void onUserInfoClick(ActionEvent event) 
    {
    	System.out.println("bing bong");
    	userModel.showUserInfo();
    }
	
}
