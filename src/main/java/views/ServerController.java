package views;

import concord.ConcordClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class ServerController
{
	ConcordClient model;
	public void setModel(ConcordClient m)
	{
		model = m;
	}
	
	@FXML
    void onClickPins(ActionEvent event) 
	{

    }
}
