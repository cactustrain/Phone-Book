// Driver class to test all Phone Book methods. M.Russell 21/12/2005
public class PhoneTest 
{
	public static void main(String[] args) 
	{
		final int SIZE = 4;
		database phoneBook = new database(SIZE);
		
		phoneBook.setDetails("Michael","433115", "123");
		phoneBook.setDetails("Police","999", "999");
		phoneBook.setDetails("Mary","987", "321");
		phoneBook.setDetails("Ahmed","578", "989");
		phoneBook.setDetails("Sarah","345", "478");

		
		System.out.println("Details for position zero: ");
		System.out.println("Name : " + phoneBook.getFullName(0));
		System.out.println("Telephone : " + phoneBook.getTelephone(0));
		System.out.println("Extension : " + phoneBook.getExtenstion(0));
		
		phoneBook.setName("Fred",11); // Induce error
		phoneBook.setName("Fred",0);
		phoneBook.setTelepehone("123456",0);
		phoneBook.setExtension("1234",0);
		phoneBook.setNumbers("mobile 08956 253689",0);
		
		System.out.println("\nDetails for position zero: ");
		String details [] = new String [SIZE];
		details = phoneBook.getDetails(0);
		for(int loop = 0; loop <SIZE; loop++)
			System.out.print(details[loop] + ", ");
		
		int found = phoneBook.searchByName("Ahmed");
		System.out.println("\nAhmed found at " + found);
		
		found = phoneBook.searchByNumber("999");
		System.out.println("999 found at " + found);
		
		found = phoneBook.searchByEx("321");
		System.out.println("321 found at " + found);
		
		found = phoneBook.getPos(123456);
		System.out.println("123456 found at " + found);
		
		found = phoneBook.searchByName("Steve");
		if(found == -1)
			System.out.println("Steve not found");
		
		if(phoneBook.save("test.tmp"))
			System.out.println("save failed");
		else
			System.out.println("save successful");
		
		String [][] records = phoneBook.getAllContacts();
		
		for(int tmp = 0; tmp<records.length; tmp++)
		{
			records[0][0]="";
			records[0][1]="";
			records[0][2]="";
			records[0][3]="";
		}

		System.out.println("Details for position zero: ");
		System.out.println("Name : " + phoneBook.getFullName(0));
		System.out.println("Telephone : " + phoneBook.getTelephone(0));
		System.out.println("Extension : " + phoneBook.getExtenstion(0));
			
		if(phoneBook.load("test.tmp"))
			System.out.println("load failed");
		else
			System.out.println("load successful");
		
		System.out.println("\nDetails for position zero: ");
		details = phoneBook.getDetails(0);
		for(int loop = 0; loop <SIZE; loop++)
			System.out.print(details[loop] + ", ");
		System.out.println();
		System.exit(0);
	}	
}
