package views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import models.ViewTransitionModelInterface;

public class ContentController
{
	ViewTransitionModelInterface model;
	
	public void setModel(ViewTransitionModelInterface m)
	{
		model = m;
	}
	
	@FXML
    void onClickSettings(ActionEvent event) 
	{
		model.showUser();
    }
	
	@FXML
    void onClickDC(MouseEvent event) {
		model.showDc();
    }

    @FXML
    void onClickServer(ActionEvent event) {
    	model.showServer();
    }

}
