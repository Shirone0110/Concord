package view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import concord.Channel;
import concord.ConcordClient;
import concord.ConcordServer;
import concord.DirectConversation;
import concord.DirectConversationManager;
import concord.InvalidCredentialException;
import concord.Main;
import concord.Message;
import concord.Server;
import concord.ServerManager;
import concord.User;
import concord.UserManager;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import models.ConcordModel;
import models.ViewTransitionModel;
import views.MainController;

@ExtendWith(ApplicationExtension.class)
public class TestAll
{
	ConcordServer cs;
	Registry registry;
	ConcordClient cc;
	ConcordModel model;
	
	@Start	//Before
	private void start(Stage stage) throws RemoteException
	{
		File file = new File("Concord.xml");
		file.delete();
		cs = new ConcordServer();
		registry = LocateRegistry.createRegistry(2099);
		registry.rebind("CONCORD", cs);
		cc = new ConcordClient();
		cs.addObserver(cc);
		model = new ConcordModel();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(ViewTransitionModel.class.getResource("../views/MainView.fxml"));
		try
		{
			BorderPane view = loader.load();
			MainController cont = loader.getController();
			ViewTransitionModel vm = new ViewTransitionModel(view, cc, model);
			cont.setModel(vm);
			vm.showLogin();
			cc.setModel(model);
			
			Scene s = new Scene(view);
			s.getStylesheets().add(Main.class.getResource("../Decoration.css").toExternalForm());
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
	
	@SuppressWarnings("unchecked")
	@Test
	public void testAll(FxRobot robot) throws RemoteException
	{
		robot.clickOn("#btnNewAcc");
		
		robot.clickOn("#newUsername");
		robot.write("lam");
		
		robot.clickOn("#newRealname");
		robot.write("lam");
		
		robot.clickOn("#newPassword");
		robot.write("abc");
		
		robot.clickOn("#btnCreateAcc");
		robot.clickOn("#btnBack");
		
		robot.clickOn("#userNameTextField");
		robot.write("lam");
		
		robot.clickOn("#passwordTextField");
		robot.write("abc");
		
		robot.clickOn("#loginSubmitButton");
		
		Platform.runLater(()->{
			ListView<Server> svList = (ListView<Server>) robot.lookup("#svListView")
					.queryAll().iterator().next();
			assertEquals(0, svList.getItems().size());
			
			ListView<DirectConversation> dcList = (ListView<DirectConversation>) robot.lookup("#dcListView")
					.queryAll().iterator().next();
			assertEquals(0, dcList.getItems().size());
		});
		
		robot.clickOn("#btnManageServer");
		
		robot.clickOn("#newServerName");
		robot.write("server 1");
		
		robot.clickOn("#btnCreateServer");
		
		try
		{
			Thread.sleep(5000);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Platform.runLater(()->{
			ListView<Server> svList = (ListView<Server>) robot.lookup("#svListView")
					.queryAll().iterator().next();
			
			assertEquals(1, svList.getItems().size());
			System.out.println();
			try
			{
				for (Server s: cs.getServerByUserId(0))
				{
					boolean exist = false;
					System.out.println(s.getServerName());
					for (Server server: svList.getItems())
					{
						System.out.println(server.getServerName());
						if (server.equals(s)) exist = true;
					}
					assertTrue(exist);
				}
			} catch (RemoteException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			assertEquals("server 1", svList.getItems().get(0).getServerName());
			svList.scrollTo(0);
	
			svList.getSelectionModel().clearAndSelect(0);
			System.out.println(svList.getSelectionModel().getSelectedIndex());
			
			ListView<Channel> cnList = (ListView<Channel>) robot.lookup("#cnListView")
					.queryAll().iterator().next();
			
			assertEquals(1, cnList.getItems().size());
			assertEquals("general", cnList.getItems().get(0).getChannelName());
			
			cnList.scrollTo(0);
			cnList.getSelectionModel().clearAndSelect(0);
			System.out.println(cnList.getSelectionModel().getSelectedIndex());
			
			ListView<User> userList = (ListView<User>) robot.lookup("#userListView")
					.queryAll().iterator().next();
			assertEquals(1, userList.getItems().size());
			assertEquals("lam", userList.getItems().get(0).getUserName());
		});
		
		try
		{
			Thread.sleep(1000);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Platform.runLater(()->{
			ListView<Message> msgList = (ListView<Message>) robot.lookup("#msgListView")
					.queryAll().iterator().next();
			
			assertEquals(0, msgList.getItems().size());
		});
		
		robot.clickOn("#channelTxtFld");
		robot.write("hi");
		robot.press(KeyCode.ENTER).release(KeyCode.ENTER);
		
		try
		{
			Thread.sleep(1000);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Platform.runLater(()->{
			ListView<Message> msgList = (ListView<Message>) robot.lookup("#msgListView")
					.queryAll().iterator().next();
			
			assertEquals(1, msgList.getItems().size());
			try
			{
				for (Message m: cs.getChannelById(0, 0).getMessages())
				{
					boolean exist = false;
					for (Message mess: msgList.getItems())
					{
						if (m.toString().equals(mess.toString())) exist = true;
					}
					assertTrue(exist);
				}
			} catch (RemoteException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			assertEquals("lam: hi", msgList.getItems().get(0).toString());
		});
		
		robot.clickOn("#btnManageChannel");
		
		robot.clickOn("#newChannelName");
		robot.write("Chan1");
		
		robot.clickOn("#btnCreateChannel");
		
		Platform.runLater(()->{			
			ListView<Channel> cnList = (ListView<Channel>) robot.lookup("#cnListView")
					.queryAll().iterator().next();
			
			assertEquals(2, cnList.getItems().size());
			assertEquals("general", cnList.getItems().get(0).getChannelName());
			assertEquals("Chan1", cnList.getItems().get(1).getChannelName());
			
			cnList.scrollTo(1);
			cnList.getSelectionModel().clearAndSelect(1);
			System.out.println(cnList.getSelectionModel().getSelectedIndex());
		});
		
		try
		{
			Thread.sleep(1000);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		robot.clickOn("#channelTxtFld");
		robot.write("new channel hey");
		robot.press(KeyCode.ENTER).release(KeyCode.ENTER);
		
		Platform.runLater(()->{
			ListView<Message> msgList = (ListView<Message>) robot.lookup("#msgListView")
					.queryAll().iterator().next();
			
			assertEquals(1, msgList.getItems().size());
			try
			{
				for (Message m: cs.getChannelById(0, 1).getMessages())
				{
					boolean exist = false;
					for (Message mess: msgList.getItems())
					{
						if (m.toString().equals(mess.toString())) exist = true;
					}
					assertTrue(exist);
				}
			} catch (RemoteException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			assertEquals("lam: new channel hey", msgList.getItems().get(0).toString());
		});
		
		robot.clickOn("#btnSettings");
		robot.clickOn("#btnUserInfo");
		
		TextField username = (TextField) robot.lookup("#usernameTextField").queryAll().iterator().next();
		assertEquals("lam", username.getText());
		
		TextField realname = (TextField) robot.lookup("#realnameTextField").queryAll().iterator().next();
		assertEquals("lam", realname.getText());
		
		TextField password = (TextField) robot.lookup("#passwordTextField").queryAll().iterator().next();
		assertEquals("abc", password.getText());
		
		robot.clickOn("#btnBack");
		
		robot.clickOn("#btnMenu");
		
		try
		{
			Thread.sleep(1000);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		robot.clickOn("#btnLogout");
		
		robot.clickOn("#btnNewAcc");
		
		robot.clickOn("#newUsername");
		robot.write("bojii");
		
		robot.clickOn("#newRealname");
		robot.write("chantakrak");
		
		robot.clickOn("#newPassword");
		robot.write("123");
		
		robot.clickOn("#btnCreateAcc");
		robot.clickOn("#btnBack");
		
		robot.clickOn("#userNameTextField");
		robot.write("lam");
		
		robot.clickOn("#passwordTextField");
		robot.write("abc");
		
		robot.clickOn("#loginSubmitButton");
		
		robot.clickOn("#btnNewDc");
		
		robot.clickOn("#btnChooseUser");
		
		try
		{
			Thread.sleep(1000);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		robot.clickOn("#bojii");
		robot.clickOn("#btnCreateDc");
		
		try
		{
			Thread.sleep(1000);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Platform.runLater(()->{			
			ListView<DirectConversation> dcList = (ListView<DirectConversation>) robot.lookup("#dcListView")
					.queryAll().iterator().next();
			
			assertEquals(1, dcList.getItems().size());
			assertEquals("lam, bojii", dcList.getItems().get(0).toString());
			
			dcList.scrollTo(0);
			dcList.getSelectionModel().clearAndSelect(0);
			System.out.println(dcList.getSelectionModel().getSelectedIndex());
		});
		
		try
		{
			Thread.sleep(1000);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		robot.clickOn("#dcMessageTextBox");
		robot.write("hi");
		robot.press(KeyCode.ENTER).release(KeyCode.ENTER);
		
		Platform.runLater(()->{
			ListView<Message> msgList = (ListView<Message>) robot.lookup("#msgListView")
					.queryAll().iterator().next();
			
			assertEquals(1, msgList.getItems().size());
			try
			{
				for (Message m: cs.getChannelById(0, 0).getMessages())
				{
					boolean exist = false;
					for (Message mess: msgList.getItems())
					{
						if (m.toString().equals(mess.toString())) exist = true;
					}
					assertTrue(exist);
				}
			} catch (RemoteException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			assertEquals("lam: hi", msgList.getItems().get(0).toString());
		});
		
		Platform.runLater(()->{
			ListView<Server> svList = (ListView<Server>) robot.lookup("#svListView")
					.queryAll().iterator().next();

			svList.scrollTo(0);	
			svList.getSelectionModel().clearAndSelect(0);
		});
		
		try
		{
			Thread.sleep(1000);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		robot.clickOn("#btnManageChannel");
		
		robot.clickOn("#deleteChannel");
		robot.clickOn("#Chan1");
		
		robot.clickOn("#btnCreateChannel");
		Platform.runLater(()->{			
			ListView<Channel> cnList = (ListView<Channel>) robot.lookup("#cnListView")
					.queryAll().iterator().next();
			
			assertEquals(1, cnList.getItems().size());
			assertEquals("general", cnList.getItems().get(0).getChannelName());
		});
		
		robot.clickOn("#btnMenu");
		
		try
		{
			Thread.sleep(1000);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		robot.clickOn("#btnLogout");
		
		robot.clickOn("#userNameTextField");
		robot.write("bojii");
		
		robot.clickOn("#passwordTextField");
		robot.write("123");
		robot.clickOn("#loginSubmitButton");
		
		Platform.runLater(()->{
			ListView<DirectConversation> dcList = (ListView<DirectConversation>) robot.lookup("#dcListView")
					.queryAll().iterator().next();

			assertEquals(1, dcList.getItems().size());
			assertEquals("lam, bojii", dcList.getItems().get(0).toString());
			
			dcList.scrollTo(0);	
			dcList.getSelectionModel().clearAndSelect(0);
		});
		
		robot.clickOn("#dcMessageTextBox");
		robot.write("hello");
		robot.press(KeyCode.ENTER).release(KeyCode.ENTER);
		
		Platform.runLater(()->{
			ListView<Message> msgList = (ListView<Message>) robot.lookup("#msgListView")
					.queryAll().iterator().next();
			
			assertEquals(2, msgList.getItems().size());
			assertEquals("lam: hi", msgList.getItems().get(0).toString());
			assertEquals("bojii: hello", msgList.getItems().get(1).toString());
		});
		
		robot.clickOn("#btnSettings");
		robot.clickOn("#btnUserInfo");
		
		username = (TextField) robot.lookup("#usernameTextField").queryAll().iterator().next();
		assertEquals("bojii", username.getText());
		
		realname = (TextField) robot.lookup("#realnameTextField").queryAll().iterator().next();
		assertEquals("chantakrak", realname.getText());
		
		password = (TextField) robot.lookup("#passwordTextField").queryAll().iterator().next();
		assertEquals("123", password.getText());
	}
}
