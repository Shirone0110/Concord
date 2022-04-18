package views;

import concord.ConcordClient;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class DCController
{
	ConcordClient model;
	
	public void setModel(ConcordClient m)
	{
		model = m;
	}
	
	@FXML
    void onClickName(MouseEvent event) 
	{

    }

}
