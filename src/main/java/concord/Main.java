package concord;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import models.ViewTransitionModel;
import views.MainController;

public class Main extends Application
{

	@Override
	public void start(Stage stage) throws Exception
	{
		ConcordClient model = new ConcordClient();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("../views/MainView.fxml"));
		BorderPane view = loader.load();
		
		MainController cont = loader.getController();
		ViewTransitionModel vm = new ViewTransitionModel(view, model);
		vm.showLogin();
		cont.setModel(vm, model);
		
		Scene s = new Scene(view);
		stage.setScene(s);
		stage.show();
	}

	public static void main(String[] args)
	{
		launch(args);
	}

}
