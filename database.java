// Phone Book - Advanced Programming Assignment
// Michael Russell; 21st December 2005
// BSc Computing with Software Engineering, Bradford College

//NOTE TO STUDENT
//---------------
//Please do NOT alter method names or name of class


//addition imports can be added if needed

import java.io.*;
import java.util.*;
import javax.swing.*; // needed for JOptionPane

// CLASSES SHOULD ALWAYS START WITH A CAPITAL LETTER !!!!!!!
public class database
{
	String[][] contact;
	int position = 0;
	
	public database(int noOfContact)
	{
		if(noOfContact <4) // won't work unless at least 4 places for fields
			noOfContact = 4;
		contact = new String[noOfContact][noOfContact]; // no. of fields linked to no. of records??
	}
	
	public void setDetails(String name, String tel, String exten)
	{
		if(position == contact.length) // can't resize array, so error
			JOptionPane.showMessageDialog(null, "Storage full", "Error message", JOptionPane.ERROR_MESSAGE);
		else
		{
			contact[position][0] = name;
			contact[position][1] = tel;
			contact[position++][2] = exten;
		}
	}
	
	public void setName( String name, int pos)
	{
		try
		{
			contact[pos][0] = name;
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			JOptionPane.showMessageDialog(null, "array position does not exist", "Error message", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	public void setTelepehone(String tel, int pos) // spelling mistake
	{
		try
		{
			contact[pos][1] = tel;
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			JOptionPane.showMessageDialog(null, "array position does not exist", "Error message", JOptionPane.ERROR_MESSAGE);
		}		
		
	}
	
	public void setExtension(String exten, int pos)
	{
		try
		{
			contact[pos][2] = exten;
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			JOptionPane.showMessageDialog(null, "array position does not exist", "Error message", JOptionPane.ERROR_MESSAGE);
		}		
		
	}
	
	public void setNumbers(String num, int pos)
	{
		try
		{
			contact[pos][3] = num;
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			JOptionPane.showMessageDialog(null, "array position does not exist", "Error message", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	public String getFullName(int pos)
	{
		if(pos < contact.length)
			return contact[pos][0];
		else
			return null;
	}
	public String getTelephone(int pos)
	{
		if(pos < contact.length)
			return contact[pos][1];
		else
			return null;
	}
	
	public String getExtenstion(int pos) // Spelling mistake
	{
		if(pos < contact.length)
			return contact[pos][2];
		else
			return null;
	}
		
	public String[] getDetails(int pos)
	{
		if(pos < contact.length)
			return contact[pos];
		else
			return null;
	}
	
	// Linear search
	// returns -1 if name not found
	public int searchByName(String s)
	{
		int found = -1;
		for(int loop = 0; loop < position; loop++)
			if(s.equals(contact[loop][0]))
				found = loop;
		return found;
	}	

	// Returns -1 if name not found	
	// Not allowed to make new methods so search code repeated here - forced into bad programming
	public int searchByNumber(String num)
	{
		int found = -1;
		for(int loop = 0; loop < position; loop++)
			if(num.equals(contact[loop][1]))
				found = loop;
		return found; 
	}	

	// Returns -1 if name not found	
	// search code repeated again !!
	public int searchByEx(String ex)
	{
		int found = -1;
		for(int loop = 0; loop < position; loop++)
			if(ex.equals(contact[loop][2]))
				found = loop;
		return found;
	}
			
	public String[][] getAllContacts()
	{
		return contact;
	}

	// change int to string and pass to tel no. search	
	public int getPos(int tel)
	{
		return searchByNumber(String.valueOf(tel));	
	}
	
	public boolean save(String file)
	{
		boolean b = false;
		try
		{
			ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
			output.writeInt(position);
			output.writeObject(contact);
			output.close();
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error message", JOptionPane.ERROR_MESSAGE);
			b = true;
		}
		return b;
	}
	
	public boolean load(String file)
	{
		boolean b = false;
		try
		{
			ObjectInputStream input = new ObjectInputStream(new FileInputStream(file));
			position = input.readInt();
			contact = (String[][]) input.readObject();
			input.close();
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "load failure", "Error message", JOptionPane.ERROR_MESSAGE);
			b = true;
		}       		
		return b;
	}
}