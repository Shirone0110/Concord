package concord;

import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ConcordClientInterface
{
	public ArrayList<Server> getServerByUserId() throws RemoteException;
	public void createUser(String uName, String rName, String pw) throws RemoteException;
	public User findUserById(int id) throws RemoteException;
	public void verify(String username, String password) throws InvalidCredentialException, RemoteException;
	public void notify(int userId) throws RemoteException;
	public void invite(int userId, int serverId) throws InvalidActionException, RemoteException;
	public void accept(int userId, int serverId) throws RemoteException, InvalidActionException;
	public void removeMember(int userId, int serverId) throws RemoteException, InvalidActionException;
	public void changeServerName(int serverId, String name) throws RemoteException, InvalidActionException;
	public void changeChannelName(int channelId, int serverId, String name) throws RemoteException, InvalidActionException;
	public void addChannel(int serverId, String name) throws RemoteException, InvalidActionException;
	public void deleteChannel(int serverId, int channelId) throws RemoteException, InvalidActionException;
	public void addPin(int serverId, Message m) throws RemoteException;
	public void unPin(int serverId, Message m) throws RemoteException;
	public void changeRole(int userId, Role r, int serverId) throws InvalidActionException, RemoteException;
	public void addBlock(int userId) throws RemoteException;
	public void removeBlock(int userId) throws RemoteException;
	public void setProfileData(String profile) throws RemoteException;
	public void setUsername(String username) throws RemoteException;
	public void setProfilePic(String url) throws RemoteException;
	public void sendDCMessage(String message, int dcId) throws RemoteException;
	public void sendChannelMessage(String message, int serverId, int channelId) throws RemoteException;
}
