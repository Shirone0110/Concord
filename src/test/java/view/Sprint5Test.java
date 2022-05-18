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
import concord.Message;
import concord.Role;
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
public class Sprint5Test
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
	public void switchToAdmin(FxRobot robot)
	{
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
		robot.write("lam");
		
		robot.clickOn("#passwordTextField");
		robot.write("123");
		
		robot.clickOn("#loginSubmitButton");
		
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
	}
	
	@SuppressWarnings("unchecked")
	public void testOneChannel(FxRobot robot)
	{
		Platform.runLater(()->{			
			ListView<Server> svList = (ListView<Server>) robot.lookup("#svListView")
					.queryAll().iterator().next();
			svList.scrollTo(0);
			svList.getSelectionModel().clearAndSelect(0);
		});
		
		// test that there are 1 channel
		Platform.runLater(()->{
			ListView<Channel> cnList = (ListView<Channel>) robot.lookup("#cnListView")
					.queryAll().iterator().next();
			
			assertEquals(1, cnList.getItems().size());
			assertEquals("general", cnList.getItems().get(0).getChannelName());
		});
		
		// go back and change permission of Member to cannot view channel
		switchToAdmin(robot);
		
		robot.clickOn("#btnManageRole");
		
		try
		{
			Thread.sleep(1000);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		robot.clickOn("#ddChooseRole");
		robot.clickOn("#Member");
		
		robot.clickOn("#chkViewChannel");
		robot.clickOn("#btnSubmit");
		
		// go back to member account and verify that cannot view channel
		switchToUser(robot);
		
		// test that there are no channel
		Platform.runLater(()->{
			ListView<Channel> cnList = (ListView<Channel>) robot.lookup("#cnListView")
					.queryAll().iterator().next();
			
			assertEquals(0, cnList.getItems().size());
		});
		
		// go back and turn on view channel in general permission to check that undoing works
		switchToAdmin(robot);
		
		robot.clickOn("#btnManageRole");
		
		try
		{
			Thread.sleep(1000);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		robot.clickOn("#ddChooseRole");
		robot.clickOn("#Member");
		
		robot.clickOn("#chkViewChannel");
		robot.clickOn("#btnSubmit");
		
		// go back to member account and verify that can now view channel again
		switchToUser(robot);
		
		// test that there are 1 channel
		Platform.runLater(()->{
			ListView<Channel> cnList = (ListView<Channel>) robot.lookup("#cnListView")
					.queryAll().iterator().next();
			
			assertEquals(1, cnList.getItems().size());
			assertEquals("general", cnList.getItems().get(0).getChannelName());
		});
		
		// go back and test turn off view channel in channel permission instead of general role
		switchToAdmin(robot);
		
		robot.clickOn("#btnManageRole");
		try
		{
			Thread.sleep(1000);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		robot.clickOn("#ddChooseRole");
		robot.clickOn("#Member");
		
		robot.clickOn("#ddChooseChannel");
		robot.clickOn("#general");
		
		robot.clickOn("#chkSpecViewChannel");
		robot.clickOn("#btnSubmit");
		
		// go back to member account and verify that cannot view channel
		switchToUser(robot);
		
		Platform.runLater(()->{
			ListView<Channel> cnList = (ListView<Channel>) robot.lookup("#cnListView")
					.queryAll().iterator().next();
			
			assertEquals(0, cnList.getItems().size());
		});
		
		// go back and test turn off send message in general permission
		switchToAdmin(robot);
		
		robot.clickOn("#btnManageRole");
		try
		{
			Thread.sleep(1000);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		robot.clickOn("#ddChooseRole");
		robot.clickOn("#Member");
		
		robot.clickOn("#chkSendMessage");
		robot.clickOn("#ddChooseChannel");
		robot.clickOn("#general");
		
		robot.clickOn("#chkSpecViewChannel");
		robot.clickOn("#btnSubmit");
		
		// go back to member account and verify that cannot send message
		switchToUser(robot);
		
		Platform.runLater(()->{
			ListView<Channel> cnList = (ListView<Channel>) robot.lookup("#cnListView")
					.queryAll().iterator().next();
			
			cnList.scrollTo(0);
			cnList.getSelectionModel().clearAndSelect(0);
			
			assertTrue(robot.lookup("#channelTxtFld").queryAll().iterator().next().isDisabled());
		});
		
		// go back and turn on send message in general permission to make sure that undoing works
		switchToAdmin(robot);
		
		robot.clickOn("#btnManageRole");
		try
		{
			Thread.sleep(1000);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		robot.clickOn("#ddChooseRole");
		robot.clickOn("#Member");
		
		robot.clickOn("#chkSendMessage");
		robot.clickOn("#btnSubmit");
		
		// switch to user to make sure we can send message now
		switchToUser(robot);
		
		Platform.runLater(()->{
			ListView<Channel> cnList = (ListView<Channel>) robot.lookup("#cnListView")
					.queryAll().iterator().next();
			
			cnList.scrollTo(0);
			cnList.getSelectionModel().clearAndSelect(0);
		});
		
		robot.clickOn("#channelTxtFld");
		robot.write("sending message");
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
			assertEquals("bojii: sending message", msgList.getItems().get(0).toString());
		});
		
		// go back and test turn off view channel in channel permission instead of general role
		switchToAdmin(robot);
		
		robot.clickOn("#btnManageRole");
		try
		{
			Thread.sleep(1000);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		robot.clickOn("#ddChooseRole");
		robot.clickOn("#Member");
		
		robot.clickOn("#ddChooseChannel");
		robot.clickOn("#general");
		
		robot.clickOn("#chkSpecSendMessage");
		robot.clickOn("#btnSubmit");
		
		// go back to member account and verify that cannot send message
		switchToUser(robot);
		
		Platform.runLater(()->{
			ListView<Channel> cnList = (ListView<Channel>) robot.lookup("#cnListView")
					.queryAll().iterator().next();
			
			cnList.scrollTo(0);
			cnList.getSelectionModel().clearAndSelect(0);
			
			assertTrue(robot.lookup("#channelTxtFld").queryAll().iterator().next().isDisabled());
		});
	}
	
	@SuppressWarnings("unchecked")
	public void switchToUser(FxRobot robot)
	{
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
		robot.write("234");
		
		robot.clickOn("#loginSubmitButton");
		
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
	}
	
	@SuppressWarnings("unchecked")
	public void testMoreChannel(FxRobot robot) throws RemoteException
	{
		switchToAdmin(robot);
		
		robot.clickOn("#btnManageChannel");
		
		robot.clickOn("#newChannelName");
		robot.write("Chan1");
		
		robot.clickOn("#btnCreateChannel");
		
		// go back and change permission of Member to cannot view any channel
		switchToAdmin(robot);
		
		robot.clickOn("#btnManageRole");
		
		try
		{
			Thread.sleep(1000);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		robot.clickOn("#ddChooseRole");
		robot.clickOn("#Member");
		
		robot.clickOn("#chkViewChannel");
		robot.clickOn("#ddChooseChannel");
		robot.clickOn("#general");
		
		robot.clickOn("#chkSpecSendMessage");
		robot.clickOn("#btnSubmit");
		
		// go back to member account and verify that cannot view channel
		switchToUser(robot);
		
		// test that there are no channel
		Platform.runLater(()->{
			ListView<Channel> cnList = (ListView<Channel>) robot.lookup("#cnListView")
					.queryAll().iterator().next();
			
			assertEquals(0, cnList.getItems().size());
		});
		
		// go back and turn on view channel in general permission to check that undoing works
		switchToAdmin(robot);
		
		robot.clickOn("#btnManageRole");
		
		try
		{
			Thread.sleep(1000);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		robot.clickOn("#ddChooseRole");
		robot.clickOn("#Member");
		
		robot.clickOn("#chkViewChannel");
		robot.clickOn("#btnSubmit");
		
		// go back to member account and verify that can now view all channel again
		switchToUser(robot);
		
		// test that there are 1 channel
		Platform.runLater(()->{
			ListView<Channel> cnList = (ListView<Channel>) robot.lookup("#cnListView")
					.queryAll().iterator().next();
			
			assertEquals(2, cnList.getItems().size());
			assertEquals("general", cnList.getItems().get(0).getChannelName());
			assertEquals("Chan1", cnList.getItems().get(1).getChannelName());
		});
		
		// go back and test turn off view channel in channel permission of Chan1 instead of general role
		switchToAdmin(robot);
		
		robot.clickOn("#btnManageRole");
		try
		{
			Thread.sleep(1000);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		robot.clickOn("#ddChooseRole");
		robot.clickOn("#Member");
		
		robot.clickOn("#ddChooseChannel");
		robot.clickOn("#Chan1");
		
		robot.clickOn("#chkSpecViewChannel");
		robot.clickOn("#btnSubmit");
		
		// go back to member account and verify that cannot view channel Chan1
		switchToUser(robot);
		
		Platform.runLater(()->{
			ListView<Channel> cnList = (ListView<Channel>) robot.lookup("#cnListView")
					.queryAll().iterator().next();
			
			assertEquals(1, cnList.getItems().size());
			assertEquals("general", cnList.getItems().get(0).getChannelName());
		});
		
		// go back and test turn off send message in general permission
		switchToAdmin(robot);
		
		robot.clickOn("#btnManageRole");
		try
		{
			Thread.sleep(1000);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		robot.clickOn("#ddChooseRole");
		robot.clickOn("#Member");
		robot.clickOn("#chkSendMessage");
		
		robot.clickOn("#ddChooseChannel");
		robot.clickOn("#Chan1");
		robot.clickOn("#chkSpecViewChannel");
		robot.clickOn("#btnSubmit");
		
		// go back to member account and verify that cannot send message in any channel and can view Chan1 again
		switchToUser(robot);
		
		Platform.runLater(()->{
			ListView<Channel> cnList = (ListView<Channel>) robot.lookup("#cnListView")
					.queryAll().iterator().next();
			
			assertEquals(2, cnList.getItems().size());
			assertEquals("general", cnList.getItems().get(0).getChannelName());
			assertEquals("Chan1", cnList.getItems().get(1).getChannelName());
			
			cnList.scrollTo(0);
			cnList.getSelectionModel().clearAndSelect(0);
			
			assertTrue(robot.lookup("#channelTxtFld").queryAll().iterator().next().isDisabled());
			
			cnList.getSelectionModel().clearAndSelect(1);
			assertTrue(robot.lookup("#channelTxtFld").queryAll().iterator().next().isDisabled());
		});
		
		// go back and turn on send message in general permission to make sure that undoing works
		switchToAdmin(robot);
		
		robot.clickOn("#btnManageRole");
		try
		{
			Thread.sleep(5000);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		robot.clickOn("#ddChooseRole");
		robot.clickOn("#Member");
		
		robot.clickOn("#chkSendMessage");
		robot.clickOn("#btnSubmit");
		
		// switch to user to make sure we can send message in both channel now
		switchToUser(robot);

		try
		{
			Thread.sleep(5000);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Platform.runLater(()->{
			ListView<Channel> cnList = (ListView<Channel>) robot.lookup("#cnListView")
					.queryAll().iterator().next();
			
			cnList.scrollTo(0);
			cnList.getSelectionModel().clearAndSelect(0);
		
		});
		
		robot.clickOn("#channelTxtFld");
		robot.write("general message");
		robot.press(KeyCode.ENTER).release(KeyCode.ENTER);
		
		Platform.runLater(()->{
			ListView<Message> msgList = (ListView<Message>) robot.lookup("#msgListView")
					.queryAll().iterator().next();
			
			assertEquals(2, msgList.getItems().size());
			assertEquals("bojii: sending message", msgList.getItems().get(0).toString());
			assertEquals("bojii: general message", msgList.getItems().get(1).toString());
		});
		
		Platform.runLater(()->{
			ListView<Channel> cnList = (ListView<Channel>) robot.lookup("#cnListView")
					.queryAll().iterator().next();
			
			cnList.scrollTo(0);
			cnList.getSelectionModel().clearAndSelect(1);
		});
		
		robot.clickOn("#channelTxtFld");
		robot.write("Chan 1 message");
		robot.press(KeyCode.ENTER).release(KeyCode.ENTER);
		
		Platform.runLater(()->{
			ListView<Message> msgList = (ListView<Message>) robot.lookup("#msgListView")
					.queryAll().iterator().next();
			
			assertEquals(1, msgList.getItems().size());
			assertEquals("bojii: Chan 1 message", msgList.getItems().get(0).toString());
		});

		try
		{
			Thread.sleep(1000);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// go back and test turn off send message in channel permission instead of general role
		switchToAdmin(robot);
		
		robot.clickOn("#btnManageRole");
		try
		{
			Thread.sleep(1000);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		robot.clickOn("#ddChooseRole");
		robot.clickOn("#Member");
		
		robot.clickOn("#ddChooseChannel");
		robot.clickOn("#Chan1");
		
		robot.clickOn("#chkSpecSendMessage");
		robot.clickOn("#btnSubmit");
		
		// go back to member account and verify that cannot send message
		switchToUser(robot);
		
		Platform.runLater(()->{
			ListView<Channel> cnList = (ListView<Channel>) robot.lookup("#cnListView")
					.queryAll().iterator().next();
			
			cnList.scrollTo(1);
			cnList.getSelectionModel().clearAndSelect(1);
			
			assertTrue(robot.lookup("#channelTxtFld").queryAll().iterator().next().isDisabled());
		});
	}
	
	public void testCheckBoxes(FxRobot robot) throws RemoteException
	{
		switchToAdmin(robot);
		
		robot.clickOn("#btnManageRole");
		try
		{
			Thread.sleep(1000);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		robot.clickOn("#ddChooseRole");
		robot.clickOn("#Member");
		
		robot.clickOn("#chkRemoveMember");
		robot.clickOn("#chkModifyChannel");
		robot.clickOn("#chkModifyMessage");
		
		robot.clickOn("#btnSubmit");
		
		Role r = (Role) cs.getRoleBuilderByServerId(0).getRole("Member");
		assert(r.getBasicPermission("add member") == false);
		assertTrue(r.getBasicPermission("remove member"));
		assert(r.getBasicPermission("modify admin") == false);
		assert(r.getBasicPermission("modify moderator") == false);
		assertTrue(r.getBasicPermission("modify channel"));
		assert(r.getBasicPermission("modify role") == false);
		assertTrue(r.getBasicPermission("modify message"));
		
		robot.clickOn("#btnManageRole");
		try
		{
			Thread.sleep(1000);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		robot.clickOn("#ddChooseRole");
		robot.clickOn("#Member");
		
		robot.clickOn("#chkRemoveMember");
		robot.clickOn("#chkModifyChannel");
		robot.clickOn("#chkModifyMessage");
		robot.clickOn("#chkAddMember");
		robot.clickOn("#chkModifyAdmin");
		robot.clickOn("#chkModifyMod");
		
		robot.clickOn("#btnSubmit");
		
		r = (Role) cs.getRoleBuilderByServerId(0).getRole("Member");
		assert(r.getBasicPermission("remove member") == false);
		assertTrue(r.getBasicPermission("add member"));
		assertTrue(r.getBasicPermission("modify admin"));
		assertTrue(r.getBasicPermission("modify moderator"));
		assert(r.getBasicPermission("modify channel") == false);
		assert(r.getBasicPermission("modify role") == false);
		assert(r.getBasicPermission("modify message") == false);
		
		robot.clickOn("#btnManageRole");
		try
		{
			Thread.sleep(1000);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		robot.clickOn("#ddChooseRole");
		robot.clickOn("#Member");
		
		robot.clickOn("#chkAddMember");
		robot.clickOn("#chkModifyAdmin");
		robot.clickOn("#chkModifyMod");
		
		robot.clickOn("#btnSubmit");
	}
	
	public void testAddMember(FxRobot robot) throws RemoteException
	{
		switchToAdmin(robot);
		
		robot.clickOn("#btnManageRole");
		try
		{
			Thread.sleep(1000);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		robot.clickOn("#ddChooseRole");
		robot.clickOn("#Member");
		
		robot.clickOn("#chkAddMember");
		robot.clickOn("#chkRemoveMember");
		robot.clickOn("#btnSubmit");
		
		switchToUser(robot);

		robot.clickOn("#btnNewUser");
		
		try
		{
			Thread.sleep(1000); // wait for new server to load up before clicking
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		robot.clickOn("#btnAddUser");
		robot.clickOn("#new");
		
		robot.clickOn("#btnSubmit");
	}
	
	@SuppressWarnings("unchecked")
	void testModifyChannel(FxRobot robot)
	{
		switchToAdmin(robot);
		
		robot.clickOn("#btnManageRole");
		try
		{
			Thread.sleep(1000);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		robot.clickOn("#ddChooseRole");
		robot.clickOn("#Member");
		
		robot.clickOn("#chkModifyChannel");
		robot.clickOn("#btnSubmit");
		
		switchToUser(robot);
		
		robot.clickOn("#btnManageChannel");
		robot.clickOn("#newChannelName");
		robot.write("newchannel");
		robot.clickOn("#btnCreateChannel");
		
		Platform.runLater(()->{
			ListView<Channel> cnList = (ListView<Channel>) robot.lookup("#cnListView")
					.queryAll().iterator().next();
			
			assertEquals(3, cnList.getItems().size());
		});
		
		robot.clickOn("#btnManageChannel");
		robot.clickOn("#deleteChannel");
		
		try
		{
			Thread.sleep(1000);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		robot.clickOn("#newchannel");
		robot.clickOn("#btnCreateChannel");
		
		Platform.runLater(()->{
			ListView<Channel> cnList = (ListView<Channel>) robot.lookup("#cnListView")
					.queryAll().iterator().next();
			
			assertEquals(2, cnList.getItems().size());
		});
	}
	
	public void testRemoveMember(FxRobot robot)
	{
		switchToAdmin(robot);
		robot.clickOn("#btnNewUser");
		
		try
		{
			Thread.sleep(1000); // wait for new server to load up before clicking
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		robot.clickOn("#btnRemoveUser");
		robot.clickOn("#bojii");
		
		robot.clickOn("#btnSubmit");
		
		robot.clickOn("#btnMenu");
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
		robot.write("123");
		
		robot.clickOn("#btnCreateAcc");
		robot.clickOn("#btnBack");
		
		robot.clickOn("#btnNewAcc");
		
		robot.clickOn("#newUsername");
		robot.write("bojii");
		
		robot.clickOn("#newRealname");
		robot.write("bojii");
		
		robot.clickOn("#newPassword");
		robot.write("234");
		
		robot.clickOn("#btnCreateAcc");
		robot.clickOn("#btnBack");
		
		robot.clickOn("#btnNewAcc");
		
		robot.clickOn("#newUsername");
		robot.write("new");
		
		robot.clickOn("#newRealname");
		robot.write("new");
		
		robot.clickOn("#newPassword");
		robot.write("345");
		
		robot.clickOn("#btnCreateAcc");
		robot.clickOn("#btnBack");
		
		robot.clickOn("#userNameTextField");
		robot.write("lam");
		
		robot.clickOn("#passwordTextField");
		robot.write("123");
		
		robot.clickOn("#loginSubmitButton");
		
		robot.clickOn("#btnManageServer");
		
		robot.clickOn("#newServerName");
		robot.write("server 1");
		
		robot.clickOn("#btnCreateServer");
		
		try
		{
			Thread.sleep(1000); // wait for new server to load up before clicking
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Platform.runLater(()->{
			ListView<Server> svList = (ListView<Server>) robot.lookup("#svListView")
					.queryAll().iterator().next();
			svList.scrollTo(0);
			svList.getSelectionModel().clearAndSelect(0);
		});
		
		try
		{
			Thread.sleep(1000); // wait for new server to load up before clicking
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		robot.clickOn("#btnNewUser");
		
		try
		{
			Thread.sleep(1000); // wait for new server to load up before clicking
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		robot.clickOn("#btnAddUser");
		robot.clickOn("#bojii");
		
		robot.clickOn("#btnSubmit");
		
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
		robot.write("234");
		
		robot.clickOn("#loginSubmitButton");
		
		Platform.runLater(()->{			
			ListView<DirectConversation> dcList = (ListView<DirectConversation>) robot.lookup("#dcListView")
					.queryAll().iterator().next();
			
			dcList.scrollTo(0);
			dcList.getSelectionModel().clearAndSelect(0);
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
			msgList.scrollTo(0);
			msgList.getSelectionModel().clearAndSelect(0);
		});
		
		try
		{
			Thread.sleep(1000);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		testOneChannel(robot);
		
		// go back to admin and add a new channel and test all over to test that works with new channel
		testMoreChannel(robot);
		
		// test check boxes working
		testCheckBoxes(robot);
		
		testAddMember(robot);
		
		testModifyChannel(robot);
		
		testRemoveMember(robot);
	}
	
}