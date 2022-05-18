package concord;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import models.ConcordModel;
import models.ViewTransitionModel;
import views.MainController;

public class Main extends Application
{
	ConcordServer cs;
	Stage stage;
	Registry registry;
	
	@Override
	public void start(Stage stage) throws Exception
	{
		cs = new ConcordServer();
		registry = LocateRegistry.createRegistry(2099);
		registry.rebind("CONCORD", cs);
		ConcordModel model = new ConcordModel();
		ConcordClient client = new ConcordClient();
		cs.addObserver(client);
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("../views/MainView.fxml"));
		BorderPane view = loader.load();
		
		MainController cont = loader.getController();
		ViewTransitionModel vm = new ViewTransitionModel(view, client, model);
		vm.showLogin();
		cont.setModel(vm);
		client.setModel(model);
		
		this.stage = stage;
		Scene s = new Scene(view);
		s.getStylesheets().add(Main.class.getResource("../Decoration.css").toExternalForm());
		stage.setScene(s);
		stage.show();
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() 
		{
		    @Override
		    public void handle(WindowEvent t) 
		    {
		    	stop();
		        Platform.exit();
		        System.exit(0);
		    }
		});
	}
	
	@Override
	public void stop()
	{
	    cs.getConcord().storeToDisk();
	    System.out.println("writing concord to disk");
	    try
		{
			registry.unbind("CONCORD");
		} catch (RemoteException | NotBoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    stage.close();
	    // Save file
	}

	public static void main(String[] args)
	{
		launch(args);
	}

}
