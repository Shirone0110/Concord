package concord;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import javafx.application.Platform;
import models.ConcordModel;
import models.ViewTransitionModel;

//import concord.ConcordServerInterface;

public class ConcordClient extends UnicastRemoteObject implements ConcordClientInterface, Serializable
{
	
	private static final long serialVersionUID = 7190830633178659712L;
	ConcordServerInterface cs;
	ConcordModel model;
	User u;
	Server curServer;
	Channel curChannel;
	DirectConversation curDc;
	
	public ConcordClient() throws RemoteException
	{
		super();
		try
		{
			cs = (ConcordServerInterface) 
					Naming.lookup("rmi://127.0.0.1:2099/CONCORD");
		} 
		catch (MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (NotBoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ConcordClient(ConcordServerInterface newCs) throws RemoteException
	{
		cs = newCs;
	}
	
	public void setModel(ConcordModel m)
	{
		model = m;
	}
	
	public void setUser(User a)
	{
		u = a;
	}
	
	public User getUser()
	{
		return u;
	}
	
	public void notifyChanged(int index) throws RemoteException
	{
		/*
		 * 0 = create new User <-> no change
		 * 1 = change on Server <-> update ContentView
		 * 2 = change on Channel/Users in Server <-> update ServerView
		 * 3 = change on Dc <-> update DcView
		 * 4 = change on UserInfo <-> update UserInfoView
		 * 5 = change on Block <-> update BlockView
		 * 6 = send channel message <-> update channel messages
		 * 7 = send dc message <-> update dc messages
		 */
		System.out.println("something changed");
		switch (index)
		{
			case 1:
				Platform.runLater(()->{
					try
					{
						model.setServers(getServerByUserId());
					} catch (RemoteException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
				break;
			case 2:
				Platform.runLater(()->{
					model.setChannels(curServer.getChannels());
				});
				break;
			case 3:
				Platform.runLater(()->{
					try
					{
						model.setDcs(getDcByUserId());
					} catch (RemoteException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
				break;
			case 4:
				
				break;
			case 5:
				
				break;
			case 6:
				Platform.runLater(()->{
					model.setMessages(curChannel.getMessages());
				});
				break;
			case 7:
				Platform.runLater(()->{
					model.setMessages(curDc.getMessages());
				});
				break;
		}
	}
	
	public ArrayList<Integer> getAllUser() throws RemoteException
	{
		return cs.getAllUser();
	}
	
	public ArrayList<Server> getServerByUserId() throws RemoteException
	{
		return cs.getServerByUserId(u.getUserId());
	}
	
	public ArrayList<DirectConversation> getDcByUserId() throws RemoteException
	{
		return cs.getDcByUserId(u.getUserId());
	}
	
	public void createUser(String uName, String rName, String pw) throws RemoteException
	{
		cs.createUser(uName, rName, pw);
		System.out.println(uName + " " + rName + " " + pw);
	}
	
	public User findUserById(int id) throws RemoteException
	{
		return cs.findUserById(id);
	}
	
	public void verify(String username, String password) 
			throws InvalidCredentialException, RemoteException
	{
		u = cs.verify(username, password);
		System.out.println(u.getUserName() + " " + u.getPassword());
	}
	
	public void invite(int userId, int serverId) 
			throws InvalidActionException, RemoteException
	{
		cs.invite(u.getUserId(), userId, serverId);
	}
	
	public void accept(int userId, int serverId) 
			throws RemoteException, InvalidActionException
	{
		cs.accept(u.getUserId(), userId, serverId);
		curServer = cs.getServerById(serverId);
	}
	
	public void removeMember(int userId, int serverId) 
			throws RemoteException, InvalidActionException
	{
		cs.removeMember(u.getUserId(), userId, serverId);
		curServer = cs.getServerById(serverId);
	}
	
	public void createServer(int userId, String svName, boolean priv) 
			throws RemoteException
	{
		curServer = cs.createServer(userId, svName, priv);
	}
	
	public void deleteServer(int serverId)
			throws RemoteException, InvalidActionException
	{
		cs.deleteServer(u.getUserId(), serverId);
	}
	
	public void changeServerName(int serverId, String name) 
			throws RemoteException, InvalidActionException
	{
		cs.changeServerName(u.getUserId(), serverId, name); 
		curServer = cs.getServerById(serverId);
	}
	
	public void changeChannelName(int channelId, int serverId, String name) 
			throws RemoteException, InvalidActionException
	{
		cs.changeChannelName(u.getUserId(), channelId, serverId, name);
		curServer = cs.getServerById(serverId);
	}
	
	public void addChannel(int serverId, String name) 
			throws RemoteException, InvalidActionException
	{
		cs.addChannel(u.getUserId(), serverId, name);
		curServer = cs.getServerById(serverId);
	}
	
	public void deleteChannel(int serverId, int channelId) 
			throws RemoteException, InvalidActionException
	{
		cs.deleteChannel(u.getUserId(), serverId, channelId);
		curServer = cs.getServerById(serverId);
	}
	
	public void addPin(int serverId, Message m) throws RemoteException
	{
		cs.addPin(serverId, m);
		curServer = cs.getServerById(serverId);
	}
	
	public void unPin(int serverId, Message m) throws RemoteException
	{
		cs.unPin(serverId, m);
		curServer = cs.getServerById(serverId);
	}
	
	public void changeRole(int userId, Role r, int serverId) 
			throws InvalidActionException, RemoteException
	{
		cs.changeRole(u.getUserId(), userId, r, serverId);
		curServer = cs.getServerById(serverId);
	}
	
	public void addBlock(int userId) throws RemoteException
	{
		cs.addBlock(u.getUserId(), userId);
	}
	
	public void removeBlock(int userId) throws RemoteException
	{
		cs.removeBlock(u.getUserId(), userId);
	}
	
	public void setProfileData(String profile) throws RemoteException
	{
		cs.setProfileData(u.getUserId(), profile);
	}
	
	public void setUsername(String username) throws RemoteException
	{
		cs.setUsername(u.getUserId(), username);
	}
	
	public void setProfilePic(String url) throws RemoteException
	{
		cs.setProfilePic(u.getUserId(), url);
	}
	
	public void setPassword(String pw) throws RemoteException
	{
		cs.setPassword(u.getUserId(), pw);
	}
	
	public void setRealname(String rname) throws RemoteException
	{
		cs.setRealname(u.getUserId(), rname);
	}
	
	public void sendDcMessage(String message, int dcId) throws RemoteException
	{
		cs.sendDcMessage(u.getUserId(), message, dcId);
		curDc = cs.getDcById(dcId);
	}
	
	public void createDc(int firstId, int secondId) throws RemoteException
	{
		curDc = cs.createDc(firstId, secondId);
	}
	
	public void sendChannelMessage(String message, int serverId, int channelId)
			throws RemoteException
	{
		cs.sendChannelMessage(u.getUserId(), message, serverId, channelId);
		curServer = cs.getServerById(serverId);
		curChannel = cs.getChannelById(serverId, channelId);
	}
}
