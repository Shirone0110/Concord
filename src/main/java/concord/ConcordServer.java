package concord;

import java.io.FileNotFoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ConcordServer extends UnicastRemoteObject implements ConcordServerInterface 
{
	
	private static final long serialVersionUID = 4145358012223845871L;
	
	private Concord concord;
	UserManager UM;
	ServerManager SM;
	DirectConversationManager DCM;
	ArrayList<ConcordClientInterface> observers = new ArrayList<ConcordClientInterface>();

	public ConcordServer() throws RemoteException
	{
		super();
		concord = Concord.loadFromDisk();
		UM = concord.getU();
		SM = concord.getS();
		DCM = concord.getD();
	}
	
	@Override 
	public void addObserver(ConcordClientInterface c) throws RemoteException
	{
		observers.add(c);
	}
	
	@Override 
	public void removeObserver(ConcordClientInterface c) throws RemoteException
	{
		observers.remove(c);
	}
	
	public void notifyObservers(int index) throws RemoteException
	{
		/*
		 * 0 = create new User <-> no change
		 * 1 = change on Server <-> update ContentView
		 * 2 = change on Channel/Users in Server <-> update ServerView
		 * 3 = change on Dc <-> update DcView
		 * 4 = change on UserInfo <-> update UserInfoView
		 * 5 = change on Block <-> update BlockView
		 * 6 = new Channel message <-> update Channel Message
		 * 7 = new Dc message <-> update Dc Message
		 */
		for (ConcordClientInterface c: observers)
		{
			c.notifyChanged(index);
		}
	}
	
	@Override 
	public ArrayList<Integer> getAllUser() throws RemoteException
	{
		ArrayList<User> users = UM.getUserList();
		ArrayList<Integer> ans = new ArrayList<Integer>();
		for (User u: users)
		{
			ans.add(u.getUserId());
		}
		return ans;
	}
	
	@Override
	public void createUser(String userName, String realName, String password)
			throws RemoteException
	{
		UM.addUser(userName, realName, password);
		notifyObservers(0);
	}
	
	@Override
	public Server getServerById(int serverId) throws RemoteException
	{
		return SM.findServerById(serverId);
	}
	
	@Override 
	public Channel getChannelById(int serverId, int channelId) throws RemoteException
	{
		return SM.findServerById(serverId).getChannelById(channelId);
	}
	
	@Override 
	public DirectConversation getDcById(int dcId) throws RemoteException
	{
		return DCM.findDcById(dcId);
	}
	
	@Override
	public ArrayList<Server> getServerByUserId(int userId) throws RemoteException
	{
		return SM.getUserServer(findUserById(userId));
	}
	
	@Override
	public ArrayList<DirectConversation> getDcByUserId(int userId) throws RemoteException
	{
		return DCM.getDcListByUserId(userId);
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
	public void invite(int adminId, int userId, int serverId) 
			throws InvalidActionException, RemoteException
	{
		Server s = SM.findServerById(serverId);
		s.invite(findUserById(adminId), findUserById(userId));
		notifyObservers(3);
	}

	@Override
	public void accept(int adminId, int userId, int serverId) 
			throws RemoteException, InvalidActionException
	{
		Server s = SM.findServerById(serverId);
		s.addMember(findUserById(adminId), findUserById(userId));
		notifyObservers(2);
	}

	@Override
	public void removeMember(int adminId, int userId, int serverId) 
			throws RemoteException, InvalidActionException
	{
		Server s = SM.findServerById(serverId);
		s.removeMember(findUserById(adminId), findUserById(userId));
		notifyObservers(2);
	}
	
	@Override
	public Server createServer(int adminId, String svName, boolean priv) 
			throws RemoteException
	{
		Server s = SM.createServer(findUserById(adminId), svName, priv);
		System.out.println("created server");
		notifyObservers(1);
		return s;
	}

	@Override 
	public void deleteServer(int adminId, int serverId)
			throws RemoteException, InvalidActionException
	{
		SM.deleteServer(findUserById(adminId), serverId);
	}
	
	@Override
	public void changeServerName(int userId, int serverId, String name) 
			throws RemoteException, InvalidActionException
	{
		Server s = SM.findServerById(serverId);
		s.changeServerName(findUserById(userId), name);
		notifyObservers(1);
	}

	@Override
	public void changeChannelName(int userId, int channelId, int serverId, String name) 
			throws RemoteException, InvalidActionException
	{
		Server s = SM.findServerById(serverId);
		Channel c = s.getChannelById(channelId);
		s.changeChannelName(findUserById(userId), c, name);
		notifyObservers(2);
	}

	@Override
	public void addChannel(int userId, int serverId, String name) 
			throws RemoteException, InvalidActionException
	{
		Server s = SM.findServerById(serverId);
		s.addChannel(findUserById(userId), name);
		notifyObservers(2);
	}

	@Override
	public void deleteChannel(int userId, int serverId, int channelId) 
			throws RemoteException, InvalidActionException
	{
		Server s = SM.findServerById(serverId);
		s.deleteChannel(findUserById(userId), s.getChannelById(channelId));
		notifyObservers(2);
	}

	@Override
	public void addPin(int serverId, Message m) throws RemoteException
	{
		Server s = SM.findServerById(serverId);
		s.addPin(m);
		notifyObservers(2);
	}

	@Override
	public void unPin(int serverId, Message m) throws RemoteException
	{
		Server s = SM.findServerById(serverId);
		s.removePin(m);
		notifyObservers(2);
	}

	@Override
	public void changeRole(int adminId, int userId, Role r, int serverId) 
			throws InvalidActionException, RemoteException
	{
		Server s = SM.findServerById(serverId);
		s.changeRole(findUserById(adminId), findUserById(userId), r);
		notifyObservers(2);
	}

	@Override
	public void addBlock(int blockerId, int userId) throws RemoteException
	{
		User u = findUserById(blockerId);
		u.addBlock(findUserById(userId));
		notifyObservers(5);
	}

	@Override
	public void removeBlock(int blockerId, int userId) throws RemoteException
	{
		User u = findUserById(blockerId);
		u.removeBlock(findUserById(userId));
		notifyObservers(5);
	}

	@Override
	public void setProfileData(int userId, String profile) throws RemoteException
	{
		User u = findUserById(userId);
		u.setProfileData(profile);
		notifyObservers(4);
	}

	@Override
	public void setUsername(int userId, String username) throws RemoteException
	{
		User u = findUserById(userId);
		u.setUserName(username);
		notifyObservers(4);
	}

	@Override
	public void setProfilePic(int userId, String url) throws RemoteException
	{
		User u = findUserById(userId);
		u.setProfilePic(url);
		notifyObservers(4);
	}
	
	@Override 
	public void setPassword(int userId, String pw) throws RemoteException
	{
		User u = findUserById(userId);
		u.setPassword(pw);
		notifyObservers(4);
	}
	
	@Override 
	public void setRealname(int userId, String rn) throws RemoteException
	{
		User u = findUserById(userId);
		u.setRealName(rn);
		notifyObservers(4);
	}
	
	@Override 
	public DirectConversation createDc(int firstId, int secondId) throws RemoteException
	{
		DirectConversation d = DCM.createDc(findUserById(firstId), findUserById(secondId));
		notifyObservers(3);
		return d;
	}

	@Override
	public void sendDcMessage(int userId, String message, int dcId) throws RemoteException
	{
		Message m = new Message(findUserById(userId), message);
		DirectConversation dc = DCM.findDcById(dcId);
		dc.addMessage(m);
		notifyObservers(7);
	}

	@Override
	public void sendChannelMessage(int userId, String message, int serverId, int channelId)
			throws RemoteException
	{
		Message m = new Message(findUserById(userId), message);
		Server s = SM.findServerById(serverId);
		Channel c = s.getChannelById(channelId);
		c.addMessage(m);
		notifyObservers(6);
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
