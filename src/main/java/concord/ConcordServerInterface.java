package concord;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ConcordServerInterface extends Remote
{
	public void addObserver(ConcordClientInterface c) 
			throws RemoteException;
	public void removeObserver(ConcordClientInterface c)
			throws RemoteException;
	public ArrayList<Server> getServerByUserId(int userId) 
			throws RemoteException;
	public ArrayList<DirectConversation> getDcByUserId(int userId) 
			throws RemoteException;
	public void createUser(String userName, String realName, String password)
			throws RemoteException;
	public User verify(String username, String password) 
			throws InvalidCredentialException, RemoteException;
	public User findUserById(int userId) 
			throws RemoteException;
	public Server createServer(int adminId, String svName, boolean priv)
			throws RemoteException;
	public void invite(int adminId, int userId, int serverId) 
			throws InvalidActionException, RemoteException;
	public void accept(int userId, int serverId) 
			throws RemoteException;
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
	public void changeRole(int adminId, int userId, RoleComponent r, int serverId) 
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
	public void setPassword(int userId, String pw)
			throws RemoteException;
	public void setRealname(int userId, String rname)
			throws RemoteException;
	public void sendDcMessage(int userId, String message, int dcId)
			throws RemoteException;
	public void sendChannelMessage(int userId, String message, int serverId, int channelId)
			throws RemoteException;
	public Server getServerById(int serverId) 
			throws RemoteException;
	public DirectConversation getDcById(int dcId) 
			throws RemoteException;
	public Channel getChannelById(int serverId, int channelId) 
			throws RemoteException;
	public ArrayList<Integer> getAllUser() 
			throws RemoteException;
	public DirectConversation createDc(int firstId, int secondId) 
			throws RemoteException;
	public void deleteServer(int adminId, int serverId)
			throws RemoteException, InvalidActionException;
	boolean checkBasicPermission(int userId, int serverId, String name)
			throws RemoteException;
	public ArrayList<Channel> getChannelByUserId(int userId, int serverId)
			throws RemoteException;
	public ArrayList<RoleComponent> getRoleByServerId(int serverId)
			throws RemoteException;
	public void createRole(int userId, int serverId, String roleName, ArrayList<Boolean> perm)
			throws RemoteException, InvalidActionException;
	public RoleBuilder getRoleBuilderByServerId(int serverId) 
			throws RemoteException;
	public void updateRole(String roleName, int serverId, ArrayList<Boolean> permission)
			throws RemoteException;
	public void updateChannelRole(String roleName, int channelId, int serverId, boolean view, boolean send)
			throws RemoteException;
	public boolean checkSendMessage(int userId, int channelId, int serverId) 
			throws RemoteException;
}
