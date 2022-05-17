package views;

import java.io.IOException;
import java.rmi.RemoteException;

import concord.Channel;
import concord.ConcordClient;
import concord.ConcordClientInterface;
import concord.DirectConversation;
import concord.Invitation;
import concord.Main;
import concord.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.ConcordModel;
import models.ViewTransitionModel;
import models.ViewTransitionModelInterface;

public class DCController
{
	ConcordModel concordModel;
	ConcordClient client;
	ViewTransitionModel model;
	int dcId;
	
    @FXML
    private ListView<DirectConversation> dcListView;
    //private ListView<Label> dcListView;

    @FXML
    private ListView<Message> dcMessageListView;

    @FXML
    private TextField dcMessageTextField;
    
    @FXML
    private Label userNameTextField;

    @FXML
    private Label otherUserLabel;
    
    private void onSelectedDc()
    {
    	DirectConversation d = dcListView.getSelectionModel().getSelectedItem();
    	if (d != null)
    	{
    		System.out.println("selected dc");
    		concordModel.setMessages(d.getMessages());
        	dcMessageListView.setItems(concordModel.getMessages());
        	dcId = d.getDirectConvoId();
        	otherUserLabel.setText(d.toString());
    	}
    }
    
    private void onSelectedMessage()
    {
    	Message m = dcMessageListView.getSelectionModel().getSelectedItem();
    	if (m == null) return;
    	String compare = "Hi " + client.getUser().getUserName() + "! Click on this message to join my server!";
    	System.out.println(m.getContent());
    	System.out.println(compare);
    	if (m.getContent().equals(compare))
    	{
    		Invitation invi = (Invitation) m;
    		if (invi.getOther().equals(client.getUser()))
				try
				{
					client.accept(invi.getServer().getServerId());
				} catch (RemoteException e)
				{
					model.showError();
				}
    	}
    }
	
	public void setModel(ViewTransitionModel model, ConcordModel m, ConcordClient c)
	{
		this.model = model;
		concordModel = m;
		client = c;
		dcListView.setItems(concordModel.getDcs());
		dcListView.getSelectionModel().selectedItemProperty().addListener((e)->{onSelectedDc();});
		dcMessageListView.getSelectionModel().selectedItemProperty().addListener((e)->{onSelectedMessage();});
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
    	DirectConversation d = dcListView.getSelectionModel().getSelectedItem();
    	concordModel.setMessages(d.getMessages());
    	dcMessageListView.setItems(concordModel.getMessages());
    	dcId = d.getDirectConvoId();
    	otherUserLabel.setText(d.toString());
    }
    
    @FXML
    void onNewDcClick(ActionEvent event) 
    {
    	Stage stage = new Stage();
    	stage.initModality(Modality.APPLICATION_MODAL);
    	FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("../views/CreateDcView.fxml"));
		BorderPane view;
		try
		{
			view = loader.load();
			CreateDcController cont = loader.getController();
			cont.setModel(stage, client);
			Scene s = new Scene(view);
			stage.setScene(s);
			stage.showAndWait();
		} catch (IOException e)
		{
			model.showError();
			e.printStackTrace();
		}
    }
    
    @FXML
    void onEnterPressed(ActionEvent event) 
    {
    	String message = dcMessageTextField.getText();
    	try
		{
    		dcMessageTextField.setText("");
			client.sendDcMessage(message, dcId);
		} catch (RemoteException e)
		{
			model.showError();
			e.printStackTrace();
		}
    }
}
