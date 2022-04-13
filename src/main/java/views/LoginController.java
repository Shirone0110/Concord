package views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import models.ViewTransitionModel;

public class LoginController
{
	ViewTransitionModel model;
	
	public void setModel(ViewTransitionModel m)
	{
		model = m;
	}
	
	@FXML
	void onClickSubmit(ActionEvent event) 
	{
		model.showContent();
    }
}
