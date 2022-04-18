package concord;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

//import concord.ConcordServerInterface;

public class ConcordClient implements ConcordClientInterface
{
	ConcordServerInterface cs;
	User u;
	
	public ConcordClient()
	{
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
	
	public ConcordClient(ConcordServerInterface newCs)
	{
		cs = newCs;
	}
	
	public void createUser(String uName, String rName, String pw) throws RemoteException
	{
		//cs.createUser(uName, rName, pw);
		System.out.println(uName + " " + rName + " " + pw);
	}
	
	public User findUserById(int id) throws RemoteException
	{
		return cs.findUserById(id);
	}
	
	public void verify(String username, String password) 
			throws InvalidCredentialException, RemoteException
	{
		//u = cs.verify(username, password);
		//System.out.println(u.getUserName() + " " + u.getPassword())

	}
	
	public void notify(int userId) throws RemoteException
	{
		cs.notify(userId);
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
	}
	
	public void removeMember(int userId, int serverId) 
			throws RemoteException, InvalidActionException
	{
		cs.removeMember(u.getUserId(), userId, serverId);
	}
	
	public void changeServerName(int serverId, String name) 
			throws RemoteException, InvalidActionException
	{
		cs.changeServerName(u.getUserId(), serverId, name); 
	}
	
	public void changeChannelName(int channelId, int serverId, String name) 
			throws RemoteException, InvalidActionException
	{
		cs.changeChannelName(u.getUserId(), channelId, serverId, name);
	}
	
	public void addChannel(int serverId, String name) 
			throws RemoteException, InvalidActionException
	{
		cs.addChannel(u.getUserId(), serverId, name);
	}
	
	public void deleteChannel(int serverId, int channelId) 
			throws RemoteException, InvalidActionException
	{
		cs.deleteChannel(u.getUserId(), serverId, channelId);
	}
	
	public void addPin(int serverId, Message m) throws RemoteException
	{
		cs.addPin(serverId, m);
	}
	
	public void unPin(int serverId, Message m) throws RemoteException
	{
		cs.unPin(serverId, m);
	}
	
	public void changeRole(int userId, Role r, int serverId) 
			throws InvalidActionException, RemoteException
	{
		cs.changeRole(u.getUserId(), userId, r, serverId);
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
	
	public void sendDCMessage(String message, int dcId) throws RemoteException
	{
		cs.sendDcMessage(u.getUserId(), message, dcId);
	}
	
	public void sendChannelMessage(String message, int serverId, int channelId)
			throws RemoteException
	{
		cs.sendChannelMessage(u.getUserId(), message, serverId, channelId);
	}
}
