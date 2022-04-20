package views;

import concord.ConcordClient;
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
	ConcordClient client;
	
    @FXML
    //private ListView<DirectConversation> dcList;
    private ListView<Label> dcListView;

    @FXML
    private ListView<Label> dcMessageListView;

    @FXML
    private TextField dcMessageTextField;
    
    @FXML
    private Label userNameTextField;
	
	public void setModel(ConcordModel m, ConcordClient c)
	{
		model = m;
		client = c;
		dcListView.setItems(model.getDcs());
		dcMessageListView.setItems(model.getMessages());
		userNameTextField.setText(client.getUser().getUserName());
		//dcList.setCellFactory(null);
	}
	
	public String getUserNameLabelText()
	{
		return userNameTextField.getText();
	}
	
	@FXML
    void onClickName(MouseEvent event) 
	{

    }

}
