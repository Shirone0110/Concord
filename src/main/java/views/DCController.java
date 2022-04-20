package views;

import concord.DirectConversation;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import models.ConcordModel;

public class DCController
{
	ConcordModel model;
	
    @FXML
    private ListView<DirectConversation> dcList;

    @FXML
    private ListView<Label> dcMessageList;

    @FXML
    private TextField dcMessageTextField;
	
	public void setModel(ConcordModel m)
	{
		model = m;
		dcList.setItems(model.getDcs());
		//dcList.setCellFactory(null);
	}
	
	@FXML
    void onClickName(MouseEvent event) 
	{

    }

}
