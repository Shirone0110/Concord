package view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;

import concord.ConcordClient;
import concord.ConcordServer;
import concord.DirectConversation;
import concord.DirectConversationManager;
import concord.Message;
import concord.Server;
import concord.ServerManager;
import concord.User;
import concord.UserManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import models.ConcordModel;
import models.ViewTransitionModel;
import views.DCController;
import views.MainController;

@ExtendWith(ApplicationExtension.class)
public class TestLogin
{
	ConcordServer cs;
	Registry registry;
	ConcordClient cc;
	User user_1, user_2, user_3;
	ConcordModel model;
	
	@Start	//Before
	private void start(Stage stage) throws RemoteException
	{
		cs = new ConcordServer();
		registry = LocateRegistry.createRegistry(2099);
		registry.rebind("CONCORD", cs);
		cc = new ConcordClient();
		model = new ConcordModel();
		
		UserManager UM = cs.getConcord().getU();
		user_1 = UM.addUser("a", "abc", "123");
		user_2 = UM.addUser("b", "def", "456");
		user_3 = UM.addUser("c", "ghi", "aaa");
		
		DirectConversationManager DM = cs.getConcord().getD();
		DirectConversation dc = DM.createDc(user_1, user_2);
		
		Message m = new Message();
		m.setContent("hello");
		m.setUser(user_1);
		dc.addMessage(m);
		
		ServerManager SM = cs.getConcord().getS();
		SM.createServer(user_1, "Test1", false);
		SM.createServer(user_1, "Test2", true);
		SM.createServer(user_1, "Test3", false);
		SM.createServer(user_2, "Test4", false);
		SM.createServer(user_2, "Test5", false);
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(ViewTransitionModel.class.getResource("../views/MainView.fxml"));
		try
		{
			BorderPane view = loader.load();
			MainController cont = loader.getController();
			ViewTransitionModel vm = new ViewTransitionModel(view, cc, model);
			cont.setModel(vm);
			vm.showLogin();
			
			Scene s = new Scene(view);
			stage.setScene(s);
			stage.show();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	void tearDown() throws Exception
	{
		registry.unbind("CONCORD");
	}
	
	@Test
	public void testLogin(FxRobot robot) throws RemoteException
	{
		robot.clickOn("#userNameTextField");
		robot.write("a");
		
		robot.clickOn("#passwordTextField");
		robot.write("123");
		
		robot.clickOn("#loginSubmitButton");
		testServer(user_1, 3);
		
		testDc(user_1);
		
		Assertions.assertThat(robot.lookup("#usernameLabel")
				.queryAs(Label.class)).hasText(user_1.getUserName());
		//assertEquals(user_1.getUserName(), l.getText());
		//testUserName(user_1);
		
		/*@SuppressWarnings("unchecked")
		ListView<Server> lv = (ListView<Server>) robot.lookup("#svListView")
				.queryAll().iterator().next();
		
		lv.getSelectionModel().clearAndSelect(0);*/
		
		robot.clickOn(robot.lookup("#dcListView").nth(0).queryAs(Node.class));
		robot.clickOn("#dcMessageTextBox");
		robot.write("hi");
		robot.press(KeyCode.ENTER).release(KeyCode.ENTER);
		
		testMessage(user_1, 0);
		
		robot.clickOn(robot.lookup("#svListView").nth(0).queryAs(Node.class));
		robot.clickOn("#btnSettings");
		robot.clickOn("#btnUserInfo");
		
		robot.clickOn("#btnBlockList");
		
		robot.clickOn("#btnBack");
		
		robot.clickOn("#splitMenuButton");
		try
		{
			Thread.sleep(5000);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		/*robot.clickOn("#userNameTextField");
		robot.write("b");
		
		robot.clickOn("#passwordTextField");
		robot.write("456");
		
		robot.clickOn("#loginSubmitButton");

		testServer(user_2, 2);
		
		testDc(user_2);
		
		testMessage(user_2);
		
		Assertions.assertThat(robot.lookup("#usernameLabel")
				.queryAs(Label.class)).hasText(user_2.getUserName());
		//testUserName(user_2);
		
		robot.clickOn("#btnSettings");
		robot.clickOn("#btnUserInfo");
		robot.clickOn("#btnBlockList");
		robot.clickOn("#btnBack");
		
		robot.clickOn("#buttonLogout");*/
	}
	
	void testServer(User user, int amt) throws RemoteException
	{
		assertEquals(amt, model.getServers().size());
		for (Server s: cs.getServerByUserId(user.getUserId()))
		{
			//System.out.print(s.getServerName() + " ");
			boolean exist = false;
			for (Server iconS: model.getServers())
			{
				//System.out.println(l.getText());
				if (iconS.getServerName().equals(s.getServerName())) exist = true;
			}
			assertTrue(exist);
		}
	}
	
	void testDc(User user)
	{
		for (DirectConversation d: cs.getConcord().getD().getDcListByUserId(user.getUserId()))
		{
			boolean exist = false;
			for (DirectConversation curD: model.getDcs())
				if (curD.getName(user.getUserId()).equals(d.getName(user.getUserId()))) exist = true;
			assertTrue(exist);
		}
	}
	
	void testMessage(User user, int Id)
	{
		DirectConversation d = model.getDcs().get(Id);
		for (Message m: d.getMessages())
		{
			boolean exist = false;
			for (Message curM: model.getMessages())
				if (curM.getContent().equals(m.getContent())) exist = true;
			assertTrue(exist);
		}
	}
	
	void testUserName(User user)
	{
		/*FXMLLoader loader = new FXMLLoader();
		loader.setLocation(ViewTransitionModel.class
			  .getResource("../views/DcAlterView.fxml"));
		try
		{
			BorderPane view = loader.load();
			DCController cont = loader.getController();	
			cont.setModel(model, cc);
			assertEquals(user.getUserName(), cont.getUserNameLabel().getText());
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
}
