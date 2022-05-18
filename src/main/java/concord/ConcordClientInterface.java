package concord;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ConcordClientInterface extends Remote
{
	public void notifyChanged(int index) 
			throws RemoteException;
	public ArrayList<Integer> getAllUser() 
			throws RemoteException;
	public ArrayList<Server> getServerByUserId() 
			throws RemoteException;
	public ArrayList<DirectConversation> getDcByUserId() 
			throws RemoteException;
	public ArrayList<Channel> getChannelByUserId(int serverId) 
			throws RemoteException;
	public ArrayList<RoleComponent> getRoleByServerId(int serverId) 
			throws RemoteException;
	public RoleBuilder getRoleBuilderByServerId(int serverId)
			throws RemoteException;
	public void createUser(String uName, String rName, String pw) 
			throws RemoteException;
	public User findUserById(int id) 
			throws RemoteException;
	public void createServer(String name, boolean priv) 
			throws RemoteException;
	public void createDc(int firstId, int secondId) 
			throws RemoteException;
	public void verify(String username, String password) 
			throws InvalidCredentialException, RemoteException;
	public void invite(int userId, int serverId) 
			throws InvalidActionException, RemoteException;
	public void accept(int serverId) 
			throws RemoteException;
	public void removeMember(int userId, int serverId) 
			throws RemoteException, InvalidActionException;
	public void changeServerName(int serverId, String name) 
			throws RemoteException, InvalidActionException;
	public void changeChannelName(int channelId, int serverId, String name) 
			throws RemoteException, InvalidActionException;
	public void addChannel(int serverId, String name) 
			throws RemoteException, InvalidActionException;
	public void deleteChannel(int serverId, int channelId) 
			throws RemoteException, InvalidActionException;
	public void addPin(int serverId, Message m) 
			throws RemoteException;
	public void unPin(int serverId, Message m) 
			throws RemoteException;
	public void createRole(int serverId, String roleName, ArrayList<Boolean> perm) 
			throws InvalidActionException, RemoteException;
	public void changeRole(int userId, RoleComponent r, int serverId) 
			throws InvalidActionException, RemoteException;
	public void addBlock(int userId) 
			throws RemoteException;
	public void removeBlock(int userId) 
			throws RemoteException;
	public void setProfileData(String profile) 
			throws RemoteException;
	public void setUsername(String username) 
			throws RemoteException;
	public void setProfilePic(String url)
			throws RemoteException;
	public void setRealname(String realname) 
			throws RemoteException;
	public void setPassword(String pw) 
			throws RemoteException;
	public void sendDcMessage(String message, int dcId) 
			throws RemoteException;
	public void sendChannelMessage(String message, int serverId, int channelId) 
			throws RemoteException;
	public void updateRole(String roleName, int serverId, ArrayList<Boolean> permission)
			throws RemoteException;
	public void updateChannelRole(String roleName, int channelId, int serverId, boolean view, boolean send)
			throws RemoteException;
}
