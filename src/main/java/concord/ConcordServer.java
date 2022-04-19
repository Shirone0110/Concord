package concord;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ConcordServer extends UnicastRemoteObject implements ConcordServerInterface 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4145358012223845871L;
	
	private Concord concord;
	UserManager UM;
	ServerManager SM;
	DirectConversationManager DCM;

	public ConcordServer() throws RemoteException
	{
		super();
		setConcord(new Concord());
		UM = getConcord().getU();
		SM = getConcord().getS();
		DCM = getConcord().getD();
	}
	
	public void createUser(String userName, String realName, String password)
			throws RemoteException
	{
		UM.addUser(userName, realName, password);
	}
	
	public ArrayList<Server> getServerByUserId(int userId) throws RemoteException
	{
		return SM.getUserServer(findUserById(userId));
	}
	
	@Override
	public User verify(String username, String password) 
			throws InvalidCredentialException, RemoteException
	{
		return UM.verify(username, password);
	}

	@Override
	public User findUserById(int userId) throws RemoteException
	{
		return UM.findUserById(userId);
	}

	@Override
	public void notify(int userId) throws RemoteException
	{
		// contact the other client 
	}

	@Override
	public void invite(int adminId, int userId, int serverId) 
			throws InvalidActionException, RemoteException
	{
		Server s = SM.findServerById(serverId);
		s.invite(findUserById(adminId), findUserById(userId));
	}

	@Override
	public void accept(int adminId, int userId, int serverId) 
			throws RemoteException, InvalidActionException
	{
		Server s = SM.findServerById(serverId);
		s.addMember(findUserById(adminId), findUserById(userId));
	}

	@Override
	public void removeMember(int adminId, int userId, int serverId) 
			throws RemoteException, InvalidActionException
	{
		Server s = SM.findServerById(serverId);
		s.removeMember(findUserById(adminId), findUserById(userId));
	}

	@Override
	public void changeServerName(int userId, int serverId, String name) 
			throws RemoteException, InvalidActionException
	{
		Server s = SM.findServerById(serverId);
		s.changeServerName(findUserById(userId), name);
	}

	@Override
	public void changeChannelName(int userId, int channelId, int serverId, String name) 
			throws RemoteException, InvalidActionException
	{
		Server s = SM.findServerById(serverId);
		Channel c = s.getChannelById(channelId);
		s.changeChannelName(findUserById(userId), c, name);
	}

	@Override
	public void addChannel(int userId, int serverId, String name) 
			throws RemoteException, InvalidActionException
	{
		Server s = SM.findServerById(serverId);
		s.addChannel(findUserById(userId), name);
	}

	@Override
	public void deleteChannel(int userId, int serverId, int channelId) 
			throws RemoteException, InvalidActionException
	{
		Server s = SM.findServerById(serverId);
		s.deleteChannel(findUserById(userId), s.getChannelById(channelId));
	}

	@Override
	public void addPin(int serverId, Message m) throws RemoteException
	{
		Server s = SM.findServerById(serverId);
		s.addPin(m);
	}

	@Override
	public void unPin(int serverId, Message m) throws RemoteException
	{
		Server s = SM.findServerById(serverId);
		s.removePin(m);
	}

	@Override
	public void changeRole(int adminId, int userId, Role r, int serverId) 
			throws InvalidActionException, RemoteException
	{
		Server s = SM.findServerById(serverId);
		s.changeRole(findUserById(adminId), findUserById(userId), r);
	}

	@Override
	public void addBlock(int blockerId, int userId) throws RemoteException
	{
		User u = findUserById(blockerId);
		u.addBlock(findUserById(userId));
	}

	@Override
	public void removeBlock(int blockerId, int userId) throws RemoteException
	{
		User u = findUserById(blockerId);
		u.removeBlock(findUserById(userId));
	}

	@Override
	public void setProfileData(int userId, String profile) throws RemoteException
	{
		User u = findUserById(userId);
		u.setProfileData(profile);
	}

	@Override
	public void setUsername(int userId, String username) throws RemoteException
	{
		User u = findUserById(userId);
		u.setUserName(username);
	}

	@Override
	public void setProfilePic(int userId, String url) throws RemoteException
	{
		User u = findUserById(userId);
		u.setProfilePic(url);
	}

	@Override
	public void sendDcMessage(int userId, String message, int dcId) throws RemoteException
	{
		Message m = new Message(findUserById(userId), message);
		DirectConversation dc = DCM.findDcById(dcId);
		dc.addMessage(m);
	}

	@Override
	public void sendChannelMessage(int userId, String message, int serverId, int channelId)
			throws RemoteException
	{
		Message m = new Message(findUserById(userId), message);
		Server s = SM.findServerById(serverId);
		Channel c = s.getChannelById(channelId);
		c.addMessage(m);
	}

	public Concord getConcord()
	{
		return concord;
	}

	public void setConcord(Concord concord)
	{
		this.concord = concord;
	}

}
