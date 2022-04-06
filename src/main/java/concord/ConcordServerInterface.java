package concord;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ConcordServerInterface extends Remote
{
	public void verify(String username, String password) 
			throws InvalidCredentialException, RemoteException;
	public User findUserById(int userId) 
			throws RemoteException;
	public void notify(int userId) 
			throws RemoteException;
	public void invite(int adminId, int userId, int serverId) 
			throws InvalidActionException, RemoteException;
	public void accept(int adminId, int userId, int serverId) 
			throws InvalidActionException, RemoteException;
	public void removeMember(int adminId, int userId, int serverId)
			throws RemoteException, InvalidActionException;
	public void changeServerName(int userId, int serverId, String name) 
			throws RemoteException, InvalidActionException;
	public void changeChannelName(int userId, int channelId, int serverId, String name) 
			throws RemoteException, InvalidActionException;
	public void addChannel(int userId, int serverId, String name) 
			throws RemoteException, InvalidActionException;
	public void deleteChannel(int userId, int serverId, int channelId) 
			throws RemoteException, InvalidActionException;
	public void addPin(int serverId, Message m)
			throws RemoteException;
	public void unPin(int serverId, Message m)
			throws RemoteException;
	public void changeRole(int adminId, int userId, Role r, int serverId) 
			throws RemoteException, InvalidActionException;
	public void addBlock(int blockerId, int userId)
			throws RemoteException;
	public void removeBlock(int blockerId, int userId)
			throws RemoteException;
	public void setProfileData(int userId, String profile)
			throws RemoteException;
	public void setUsername(int userId, String username)
			throws RemoteException;
	public void setProfilePic(int userId, String url)
			throws RemoteException;
	public void sendMessage(int userId, String message, int dcId)
			throws RemoteException;
	public void sendMessage(int userId, String message, int serverId, int channelId)
			throws RemoteException;
	
	
}
