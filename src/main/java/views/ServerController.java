package views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import models.ConcordModel;

public class ServerController
{
	ConcordModel model;
	public void setModel(ConcordModel m)
	{
		model = m;
	}
	
	@FXML
    void onClickPins(ActionEvent event) {

    }
}
