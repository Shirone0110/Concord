package concord;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class ConcordMockClient implements ConcordClientInterface
{
	
	@Override
	public ArrayList<Server> getServerByUserId() throws RemoteException
	{
		return null;
	}

	@Override
	public void createUser(String uName, String rName, String pw) throws RemoteException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public User findUserById(int id) throws RemoteException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void verify(String username, String password) throws InvalidCredentialException, RemoteException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void notify(int userId) throws RemoteException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void invite(int userId, int serverId) throws InvalidActionException, RemoteException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void accept(int userId, int serverId) throws RemoteException, InvalidActionException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void removeMember(int userId, int serverId) throws RemoteException, InvalidActionException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void changeServerName(int serverId, String name) throws RemoteException, InvalidActionException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void changeChannelName(int channelId, int serverId, String name)
			throws RemoteException, InvalidActionException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void addChannel(int serverId, String name) throws RemoteException, InvalidActionException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteChannel(int serverId, int channelId) throws RemoteException, InvalidActionException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void addPin(int serverId, Message m) throws RemoteException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void unPin(int serverId, Message m) throws RemoteException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void changeRole(int userId, Role r, int serverId) throws InvalidActionException, RemoteException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void addBlock(int userId) throws RemoteException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void removeBlock(int userId) throws RemoteException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setProfileData(String profile) throws RemoteException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setUsername(String username) throws RemoteException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setProfilePic(String url) throws RemoteException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void sendDCMessage(String message, int dcId) throws RemoteException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void sendChannelMessage(String message, int serverId, int channelId) throws RemoteException
	{
		// TODO Auto-generated method stub

	}

}
