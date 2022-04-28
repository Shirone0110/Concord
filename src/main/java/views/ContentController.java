package views;

import java.io.IOException;

import concord.ConcordClient;
import concord.Main;
import concord.Server;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import models.ConcordModel;
import models.ViewTransitionModelInterface;

public class ContentController
{
	ViewTransitionModelInterface model;
	ConcordModel concordModel;
	ConcordClient client;
	
    @FXML
    private ListView<Server> serverListView;
    //private ListView<Label> serverListView;
	
	public void setModel(ViewTransitionModelInterface m, ConcordModel cModel, ConcordClient c)
	{
		model = m;
		concordModel = cModel;
		client = c;
		//serverListView.setEditable(true);
		
		serverListView.setCellFactory(new Callback<ListView<Server>, ListCell<Server>>()
				{
					@Override
					public ListCell<Server> call(ListView<Server> param)
					{	
						return new ServerCell(param);
					}
				});
		
		
		for (Server s: concordModel.getServers())
		{
			System.out.println("server in list: " + s.getServerName());
		}
		serverListView.setItems(concordModel.getServers());
	}
	
    @FXML
    void onClickLogOut(ActionEvent event) 
    {
    	concordModel.reset();
    	model.showLogin();
    }
    
    @FXML
    void serverListViewClicked(MouseEvent event) 
    {
    	Server s = serverListView.getSelectionModel().getSelectedItem();
    	model.showServer(s);
    }
    
    @FXML
    void onClickNewServer(ActionEvent event) throws IOException 
    {
    	Stage stage = new Stage();
    	stage.initModality(Modality.APPLICATION_MODAL);
    	FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("../views/CreateServerView.fxml"));
		BorderPane view = loader.load();
		
		CreateServerController cont = loader.getController();
		cont.setModel(stage, client);
		Scene s = new Scene(view);
		stage.setScene(s);
		stage.showAndWait();
    }
	
	/*@FXML
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
    }*/

}
