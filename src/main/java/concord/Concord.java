package concord;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

public class Concord
{
	private DirectConversationManager d;
	private ServerManager s;
	private UserManager u;
	
	public DirectConversationManager getD()
	{
		return d;
	}
	
	public void setD(DirectConversationManager d)
	{
		this.d = d;
	}

	public ServerManager getS()
	{
		return s;
	}
	
	public void setS(ServerManager s)
	{
		this.s = s;
	}

	public UserManager getU()
	{
		return u;
	}
	
	public void setU(UserManager u)
	{
		this.u = u;
	}

	public Concord()
	{
		d = new DirectConversationManager();
		s = new ServerManager();
		u = new UserManager();
		File xml = new File("Concord.xml");
		try
		{
			xml.createNewFile();
		} 
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void storeToDisk()
	{
		XMLEncoder encoder = null;
		try
		{
			encoder = new XMLEncoder(new BufferedOutputStream(
					  new FileOutputStream("Concord.xml")));
		}
		catch (FileNotFoundException e)
		{
			System.out.println("File not Found");
		}
		encoder.writeObject(this);
		encoder.close();
	}
	
	public static Concord loadFromDisk() 
	{
		XMLDecoder decoder = null;
		try
		{
			decoder = new XMLDecoder(new BufferedInputStream(
					  new FileInputStream("Concord.xml")));
		} catch (FileNotFoundException e)
		{
			return new Concord();
		}
		Concord c = (Concord)decoder.readObject();
		return c;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Concord other = (Concord) obj;
		return Objects.equals(d, other.d) && Objects.equals(s, other.s) && Objects.equals(u, other.u);
	}
	
	
}
