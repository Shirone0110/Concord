package views;

import java.io.IOException;

import concord.ConcordClient;
import concord.ConcordClientInterface;
import concord.Main;
import concord.Server;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import models.ConcordModel;
import models.ViewTransitionModel;
import models.ViewTransitionModelInterface;

public class ContentController
{
	ViewTransitionModel model;
	ConcordModel concordModel;
	ConcordClient client;
	
    @FXML
    private ListView<Server> serverListView;
    //private ListView<Label> serverListView;
    
    @FXML
    private MenuButton menuBtn;
    
    private void onSelectedItem()
    {
    	Server s = serverListView.getSelectionModel().getSelectedItem();
    	System.out.println("selected server");
    	if (s != null)
    		model.showServer(s);
    }
	
	public void setModel(ViewTransitionModel m, ConcordModel cModel, ConcordClient c)
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
		
		serverListView.getSelectionModel().selectedItemProperty().addListener((e)->{onSelectedItem();});
		
		menuBtn.getItems().clear();
		
		MenuItem home = new MenuItem("Home");
		home.setId("btnHome");
		home.setOnAction(new EventHandler<ActionEvent>() {
		    @Override 
		    public void handle(ActionEvent e) 
		    {
		    	System.out.println("home");
		    	model.showDc();
		    }
		});
		
		MenuItem logout = new MenuItem("Log out");
		logout.setId("btnLogout");
		logout.setOnAction( new EventHandler<ActionEvent>() {
		    @Override 
		    public void handle(ActionEvent e) 
		    {
				System.out.println("logout");
				Platform.runLater(()->{
					model.showLogin();
				});
		    }
		});
		
		menuBtn.getItems().addAll(home, logout);
	}
    
    @FXML
    void serverListViewClicked(MouseEvent event)
    {
    	/*MouseButton button = event.getButton();
        if (button == MouseButton.PRIMARY)
        {
        	Server s = serverListView.getSelectionModel().getSelectedItem();
        	model.showServer(s);
        }
        else if (button == MouseButton.SECONDARY)
        {
        	
        }*/
    }
    
    @FXML
    void onClickManageServer(ActionEvent event) 
    {
    	Stage stage = new Stage();
    	stage.initModality(Modality.APPLICATION_MODAL);
    	FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("../views/CreateServerView.fxml"));
		BorderPane view;
		try
		{
			view = loader.load();
			CreateServerController cont = loader.getController();
			cont.setModel(stage, client, concordModel, model);
			Scene s = new Scene(view);
			s.getStylesheets().add(Main.class.getResource("../Decoration.css").toExternalForm());
			stage.setScene(s);
			stage.showAndWait();
		} catch (IOException e)
		{
			model.showError();
			e.printStackTrace();
		}
    }
}
