package views;

import java.rmi.RemoteException;

import concord.ConcordClient;
import concord.ConcordClientInterface;
import concord.DirectConversation;
import concord.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import models.ConcordModel;
import models.ViewTransitionModel;
import models.ViewTransitionModelInterface;

public class DCController
{
	ConcordModel concordModel;
	ConcordClient client;
	ViewTransitionModel model;
	
    @FXML
    private ListView<DirectConversation> dcListView;
    //private ListView<Label> dcListView;

    @FXML
    private ListView<Message> dcMessageListView;

    @FXML
    private TextField dcMessageTextField;
    
    @FXML
    private Label userNameTextField;
	
	public void setModel(ViewTransitionModel model, ConcordModel m, ConcordClient c)
	{
		this.model = model;
		concordModel = m;
		client = c;
		dcListView.setItems(concordModel.getDcs());
		dcMessageListView.setItems(concordModel.getMessages());
		userNameTextField.setText(client.getUser().getUserName());
	}
	
	public Label getUserNameLabel()
	{
		return userNameTextField;
	}
	
    @FXML
    void onClickSettings(ActionEvent event) 
    {
    	model.showUser();
    }
    
    @FXML
    void onDcListViewClicked(MouseEvent event) 
    {
    	
    }
}
