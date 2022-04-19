package views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import models.ViewTransitionModelInterface;

public class ContentController
{
	ViewTransitionModelInterface model;
	
    @FXML
    private ListView<Label> serverListView;
	
	public void setModel(ViewTransitionModelInterface m)
	{
		model = m;
		serverListView.setItems(model.getServers());
	}
	
	@FXML
    void onClickSettings(ActionEvent event) 
	{
		model.showUser();
    }
	
	@FXML
    void onClickDC(MouseEvent event) 
	{
		model.showDc();
    }

    @FXML
    void onClickServer(ActionEvent event) 
    {
    	model.showServer();
    }

}
