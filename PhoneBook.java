// Phone Book - Advanced Programming Assignment
// Michael Russell; 21st December 2005
// BSc Computing with Software Engineering, Bradford College

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class PhoneBook extends JFrame
{
	private final int SIZE = 10;
	private database theDatabase;
	private int dbPos = -1;
	private boolean changed = false;
	private JTextArea lstText;
	private CardLayout mainLayout;
	private JPanel mainPanel;
	private JButton btnDelete, btnChange, btnSearch, btnClear;
	private JTextField txtName, txtTelephone, txtExtension, txtDetails;
	
	public PhoneBook()
	{
		super("Phone Book");
		
		theDatabase = new database(SIZE);
		Container container = getContentPane();
		mainLayout = new CardLayout();
		mainPanel = new JPanel();
		mainPanel.setLayout(mainLayout);

		// *** Menu Panel ***				
		JPanel menu = new JPanel();
		menu.setLayout(new GridLayout(7,1,10,10));
		
		JLabel lblMain = new JLabel("Phone Book", SwingConstants.CENTER);
		lblMain.setFont(new Font("Arial", Font.BOLD, 18));
		menu.add(lblMain);
		
		JButton btnFind = new JButton("Search");
		btnFind.addActionListener(
		new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				dbPos = -1; // No record shown
				textBoxClear();
				textBoxAccess(true);
				mainLayout.show(mainPanel, "search");
			}
		}	
		);		
		menu.add(btnFind);
		
		// Name and telphone are mandatory fields
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(
		new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				String values [] = {"name", "telephone", "extension"};
				String input [] = new String[3];
				for(int loop = 0;loop < input.length; loop++)
				{
					input[loop] = new String("");
					do
					{
						try
						{
							input[loop] = JOptionPane.showInputDialog("Enter " + values[loop]);
							if(input[loop].equals("") && loop < 2)
								JOptionPane.showMessageDialog(null, "Enter " + values[loop]);
						}
						catch(Exception e) // Cancel button pressed
						{
							return;
						}
					}while(input[loop].equals("") && loop < 2);
				}
				theDatabase.setDetails(input[0], input[1], input[2]);
				changed = true;
			}
		}	
		);		
		menu.add(btnAdd);
			
		JButton btnList = new JButton("List");
		btnList.addActionListener(
		new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				String listing = "";
				for(int loop = 0; loop < SIZE; loop++)
				{
					if(theDatabase.getFullName(loop) != null)
					{
						listing = listing.concat(loop + "). ");
						listing = listing.concat(theDatabase.getDetails(loop)[0] + ", ");
						listing = listing.concat(theDatabase.getDetails(loop)[1] + ", ");
						listing = listing.concat(theDatabase.getDetails(loop)[2]);
						if(theDatabase.getDetails(loop)[3] != null)
							listing = listing.concat(", " + theDatabase.getDetails(loop)[3] + "\n");
						else
							listing = listing.concat("\n");
					}
				}
				if(listing.equals(""))
					listing = "No entries in the phone book";
				lstText.setText(listing);				
				mainLayout.show(mainPanel, "list");
			}
		}	
		);		
		menu.add(btnList);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(
		new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				boolean flag;
				String name = "";
				
				name = getFile(true);
				if(name != null)
				{
					flag = theDatabase.save(name);
					changed = false;
				}
			}
		}	
		);		
		menu.add(btnSave);
		
		JButton btnLoad = new JButton("Load");
		btnLoad.addActionListener(
		new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				boolean flag;
				String name;
				
				if(changed == true)
				{
					int answer = JOptionPane.showConfirmDialog(
					    null,
					    "Save changes?",
					    "Save confirmation",
					    JOptionPane.YES_NO_OPTION);
					if(answer == JOptionPane.YES_OPTION)
					{
						name = getFile(true);
						if(name != null)
						{
							flag = theDatabase.save(name);
							changed = false;
						}						
					}	
				}
				name = getFile(false);
				if(name != null)
				{				
					flag = theDatabase.load(name);
					changed = false;
				}
			}
		}	
		);		
		menu.add(btnLoad);				
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(
		new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				if(changed == true)
				{
					int answer = JOptionPane.showConfirmDialog(
					    null,
					    "Save changes?",
					    "Save confirmation",
					    JOptionPane.YES_NO_OPTION);
					if(answer == JOptionPane.YES_OPTION)
					{
						String name = getFile(true);
						if(name != null)
						{
							boolean flag = theDatabase.save(name);
						}						
					}	
				}				
				System.exit(0);
			}
		}	
		);		
		menu.add(btnExit);
		
		// *** Search Panel ***
		GridBagLayout layout = new GridBagLayout();
		JPanel search = new JPanel();
		search.setLayout(layout);
		GridBagConstraints constraints = new GridBagConstraints();
		
		JLabel lblHeading = new JLabel("Search", SwingConstants.CENTER);
		lblHeading.setFont(new Font("Arial", Font.BOLD, 18));
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx=0;
		constraints.gridy=0;
		constraints.gridwidth = 2;
		constraints.gridheight = 1;
		layout.setConstraints(lblHeading, constraints);
		search.add(lblHeading);		
		
		JLabel lblName = new JLabel("Name");
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx=0;
		constraints.gridy=1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		layout.setConstraints(lblName, constraints);
		search.add(lblName);
		
		JLabel lblTelephone = new JLabel("Telephone");
		constraints.gridx=0;
		constraints.gridy=2;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		layout.setConstraints(lblTelephone, constraints);
		search.add(lblTelephone);
		
		JLabel lblExtension = new JLabel("Extension");
		constraints.gridx=0;
		constraints.gridy=3;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		layout.setConstraints(lblExtension, constraints);
		search.add(lblExtension);
		
		JLabel lblDetails = new JLabel("Details");
		constraints.gridx=0;
		constraints.gridy=4;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		layout.setConstraints(lblDetails, constraints);
		search.add(lblDetails);						

		txtName = new JTextField(10);
		constraints.gridx=1;
		constraints.gridy=1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		layout.setConstraints(txtName, constraints);
		search.add(txtName);			

		txtTelephone = new JTextField(10);
		constraints.gridx=1;
		constraints.gridy=2;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		layout.setConstraints(txtTelephone, constraints);
		search.add(txtTelephone);
		
		txtExtension = new JTextField(10);
		constraints.gridx=1;
		constraints.gridy=3;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		layout.setConstraints(txtExtension, constraints);
		search.add(txtExtension);		

		txtDetails = new JTextField(10);
		constraints.gridx=1;
		constraints.gridy=4;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		layout.setConstraints(txtDetails, constraints);
		search.add(txtDetails);
		txtDetails.setEditable(false);

		// Name and telephone are mandatory fields
		btnChange = new JButton("Change");
		btnChange.addActionListener(
		new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				if(dbPos == -1)
					JOptionPane.showMessageDialog(null, "No record present");
				else
				{
					String values [] = {"name", "telephone", "extension", "details"};
					String input [] = new String[4];
					input[0] = txtName.getText();
					input[1] = txtTelephone.getText();
					input[2] = txtExtension.getText();
					input[3] = txtDetails.getText();
					for(int loop = 0;loop < input.length; loop++)
					{
						do
						{
							try
							{
								input[loop] = (String)JOptionPane.showInputDialog(
									null,
									"Enter " + values[loop],
									"Change entry",
									JOptionPane.PLAIN_MESSAGE,
									null,
									null,
									input[loop]
									);
								if(input[loop].equals("") && loop < 2)
									JOptionPane.showMessageDialog(null, "Enter " + values[loop]);
							}
							catch(Exception e) // Cancel button pressed
							{
								return;
							}
						}while(input[loop].equals("") && loop < 2);
					}
					txtName.setText(input[0]);
					txtTelephone.setText(input[1]);
					txtExtension.setText(input[2]);
					txtDetails.setText(input[3]);
					theDatabase.setName(input[0], dbPos);
					theDatabase.setTelepehone(input[1],dbPos);
					theDatabase.setExtension(input[2], dbPos);
					theDatabase.setNumbers(input[3], dbPos);
					changed = true;
				}
			}
		}	
		);	
		constraints.gridx = 0;
		constraints.gridy = 5;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		layout.setConstraints(btnChange, constraints);				
		search.add(btnChange);

		btnClear = new JButton("Clear");
		btnClear.addActionListener(
		new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				dbPos = -1; // No record shown
				textBoxClear();
				textBoxAccess(true);
			}
		}	
		);	
		constraints.gridx = 1;
		constraints.gridy = 6;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		layout.setConstraints(btnClear, constraints);				
		search.add(btnClear);
		
		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(
		new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				if(dbPos == -1)
					JOptionPane.showMessageDialog(null, "No current record");
				else
				{
					int answer = JOptionPane.showConfirmDialog(
					    null,
					    "Are you sure you want to delete this record?",
					    "Deletion confirmation",
					    JOptionPane.YES_NO_OPTION);
					if(answer == JOptionPane.YES_OPTION)
					{
						theDatabase.setName( null, dbPos);
						theDatabase.setTelepehone( null, dbPos);
						theDatabase.setExtension( null, dbPos);
						theDatabase.setNumbers( null, dbPos);
						textBoxClear();
						textBoxAccess(true);
						dbPos = -1;
						changed = true;
					}
				}
			}
		}	
		);
		constraints.gridx = 1;
		constraints.gridy = 5;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		layout.setConstraints(btnDelete, constraints);				
		search.add(btnDelete);		

		btnSearch = new JButton("Search");
		btnSearch.addActionListener(
		new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				// Search;
				if(!txtName.getText().equals(""))
				{
					// search on name
					int result = theDatabase.searchByName(txtName.getText());
					if(result == -1)
						JOptionPane.showMessageDialog(null, "Name not found");
					else
					{
						dbPos = result;
						txtTelephone.setText(theDatabase.getTelephone(dbPos));
						txtExtension.setText(theDatabase.getExtenstion(dbPos));
						textBoxAccess(false);
					}
				}
				else if (!txtTelephone.getText().equals(""))
				{
					// search on telephone
					int result = theDatabase.searchByNumber(txtTelephone.getText());
					if(result == -1)
						JOptionPane.showMessageDialog(null, "Number not found");
					else
					{
						dbPos = result;
						txtName.setText(theDatabase.getFullName(dbPos));
						txtExtension.setText(theDatabase.getExtenstion(dbPos));
						textBoxAccess(false);
					}
				}
				else if (!txtExtension.getText().equals(""))
				{
					// search on extension
					int result = theDatabase.searchByEx(txtExtension.getText());
					if(result == -1)
						JOptionPane.showMessageDialog(null, "Number not found");
					else
					{
						dbPos = result;
						txtName.setText(theDatabase.getFullName(dbPos));
						txtTelephone.setText(theDatabase.getTelephone(dbPos));
						textBoxAccess(false);
					}
				}
				else
					// error no details entered	
					JOptionPane.showMessageDialog(null, "No details entered");			
			}
		}	
		);
		constraints.gridx = 0;
		constraints.gridy = 6;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		layout.setConstraints(btnSearch, constraints);				
		search.add(btnSearch);
				
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(
		new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				mainLayout.show(mainPanel, "menu");
			}
		}	
		);
		constraints.gridx = 0;
		constraints.gridy = 7;
		constraints.gridwidth = 2;
		constraints.gridheight = 1;
		layout.setConstraints(btnBack, constraints);				
		search.add(btnBack);
		
		
		// *** List Panel ***
		JPanel list = new JPanel();
		list.setLayout(new BorderLayout());
		JButton btnReturn = new JButton("Return");
		btnReturn.addActionListener(
		new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				mainLayout.show(mainPanel, "menu");
			}
		}	
		);		
		list.add(btnReturn, BorderLayout.SOUTH);
		
		lstText = new JTextArea(5,5);
		list.add(new JScrollPane(lstText), BorderLayout.CENTER);
		
		mainPanel.add(menu, "menu");
		mainPanel.add(search, "search");
		mainPanel.add(list, "list");
		
		container.add(mainPanel, BorderLayout.CENTER);	
		setSize(200,400);
		UtilGUI.centre(this);
		setVisible(true);
	}
	
	private void textBoxClear()
	{
		txtName.setText("");
		txtTelephone.setText("");
		txtExtension.setText("");
		txtDetails.setText("");		
	}
	
	private void textBoxAccess(boolean state)
	{
		txtName.setEditable(state);
		txtTelephone.setEditable(state);
		txtExtension.setEditable(state);
	}
	
	// save dialog if true passed, else open
	private String getFile(boolean save)
	{
		int result;
		File filename;
		
		do
		{
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			if(save)
				result = fileChooser.showSaveDialog(this);
			else
				result = fileChooser.showOpenDialog(this);
			if(result == JFileChooser.CANCEL_OPTION)
				return null;
			filename = fileChooser.getSelectedFile();
		}while(filename == null || filename.getName().equals(""));
		
		return filename.getPath();	
	}
	
	public static void main(String[] args) 
	{
		PhoneBook program = new PhoneBook();
		
		program.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
}