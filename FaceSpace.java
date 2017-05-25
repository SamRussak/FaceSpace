import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import oracle.jdbc.*;

public class FaceSpace {
	private static Connection connection;
	
	public FaceSpace(Connection c)
	{		
		connection = c;
	}
	
	@SuppressWarnings("resource")
	public static void main (String[] args)
	{
		String URL = "jdbc:oracle:thin:@class3.cs.pitt.edu:1521:dbclass";
		String USERNAME = "dal168";
		String PASSWORD = "4169305";
		Connection conn = null;
		try {			
			//registering driver
			System.out.println("Registering Driver----------");
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			
			//making connection
			System.out.println("Connecting to Database----------");
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			FaceSpace faceSpace = new FaceSpace(conn);
			System.out.println("Welcome to FaceSpace");
			boolean game = true;
			int userA;
			int userB;
			String subject = "";
			String message = "";
			while(game)
			{
			
				System.out.println("-------------------------------");
				System.out.println("Create User - 1");
				System.out.println("Initiate Friendship - 2");
				System.out.println("Establish Friendship - 3");
				System.out.println("Display Friends - 4");
				System.out.println("Create Group - 5");
				System.out.println("Add to Group - 6");
				System.out.println("Send Message to User - 7");
				System.out.println("Send Message to Group - 8");
				System.out.println("Display Messages - 9");
				System.out.println("Display New Messages - 10");
				System.out.println("Search for User - 11");
				System.out.println("Three Degrees Search - 12");
				System.out.println("Top Messengers - 13");
				System.out.println("Drop User - 14");
				System.out.println("Exit Program - 15");			
				System.out.println("-------------------------------");
				Scanner reader = new Scanner(System.in); 	
				int num = reader.nextInt();
				reader.nextLine();
				switch (num)
				{
					case 1:
						System.out.println("Enter First Name: ");
						String fname = reader.nextLine();
						System.out.println("Enter Last Name: ");
						String lname = reader.nextLine();
						System.out.println("Enter email: ");
						String email = reader.nextLine();
						System.out.println("Enter DOB ( Ex. 1993-06-24 00:00:00): ");
						String DOB = reader.nextLine();
						faceSpace.createUser(fname, lname, email, DOB);
						break;
					case 2:
						System.out.println("Enter UserID for sender: ");
						userA = reader.nextInt();
						System.out.println("Enter UserID for recipient: ");
						userB = reader.nextInt();
						faceSpace.initiateFriendship(userA, userB);
						break;
					case 3:
						System.out.println("Enter UserID for sender: ");
						userA = reader.nextInt();
						System.out.println("Enter UserID for recipient: ");
						userB = reader.nextInt();
						faceSpace.establishFriendship(userA, userB);
						break;
					case 4:
						System.out.println("Enter UserID: ");
						userA = reader.nextInt();
						faceSpace.displayFriends(userA);
						break;
					case 5:
						System.out.println("Enter Group Name: ");
						String name = reader.nextLine();
						System.out.println("Enter Group Description: ");
						String desc = reader.nextLine();
						System.out.println("Enter Group limit: ");
						int limit = reader.nextInt();
						faceSpace.createGroup(name, desc, limit);
						break;
					case 6:
						System.out.println("Enter userID: ");
						userA = reader.nextInt();
						System.out.println("Enter Group ID: ");
						userB = reader.nextInt();
						faceSpace.addToGroup(userA, userB);
						break;
					case 7:
						//int sender, int recipient, int replyTo, String subject, String body
						System.out.println("Enter UserID for sender: ");
						userA = reader.nextInt();
						System.out.println("Enter UserID for recipient: ");
						userB = reader.nextInt();
						System.out.println("Enter MsgId of previous message if this is a reply "
								+ "\n(If this is not a reply enter 0): ");
						int reply = reader.nextInt();
						reader.nextLine();
						System.out.println("Enter Subject: ");
						subject = reader.nextLine();
						System.out.println("Enter Message: ");
						message = reader.nextLine();
						faceSpace.sendMessageToUser(userA, userB, reply, subject, message);
						break;
					case 8:
						//int sender, int group, String subject, String body
						System.out.println("Enter UserID for sender: ");
						userA = reader.nextInt();
						System.out.println("Enter GroupID for recipient: ");
						userB = reader.nextInt();
						reader.nextLine();
						System.out.println("Enter Subject: ");
						subject = reader.nextLine();
						System.out.println("Enter Message: ");
						message = reader.nextLine();
						faceSpace.sendMessageToGroup(userA, userB, subject, message);
						break;
					case 9:
						//(int user)
						System.out.println("Enter userID: ");
						userA = reader.nextInt();
						faceSpace.displayMessages(userA);						
						break;
					case 10:
						//(int user)
						System.out.println("Enter userID: ");
						userA = reader.nextInt();
						faceSpace.displayNewMessages(userA);
						break;
					case 11:
						//string search
						System.out.println("What would you like to Search?: ");
						message = reader.nextLine();
						faceSpace.searchForUser(message);
						break;
					case 12:
						//userA userB
						System.out.println("Enter first userID: ");
						userA = reader.nextInt();
						System.out.println("Enter second userID: ");
						userB = reader.nextInt();
						faceSpace.threeDegrees(userA, userB);
						break;
					case 13:
						//num people , num months
						System.out.println("Enter user size: ");
						int ppl = reader.nextInt();
						System.out.println("Enter month range: ");
						int month = reader.nextInt();
						faceSpace.topMessagers(ppl, month);
						break;
					case 14:
						//userID
						System.out.println("Enter userID: ");
						userA = reader.nextInt();
						faceSpace.dropUser(userA);
						break;
					case 15:
						System.out.println("Good-Bye!");
						game = false;
						break;
					default:
						System.out.println("Invalid Input!");
						break;
				}
			}
			
		} catch (SQLException e) {
			System.out.println("Cannot open connection");
			e.printStackTrace();
		} finally {
			try {
				if (conn != null){
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("Cannot close connection");
				e.printStackTrace();
			}
		}
		
	}
	
	public void createUser(String fname, String lname, String email, String DOB)
	{
		Statement stat = null;
		PreparedStatement preStat = null;
		ResultSet result = null;
		
		int maxID = 0; //default 0 because if there is no entry it starts from 1 (added 1 later)
		
		try {
			//get the largest ID integer and increment it by 1
			String command1 = "SELECT MAX(userID) max FROM Users";
			connection.setAutoCommit(false);
			stat = connection.createStatement();
			String lock = "lock table Users, Friendships, Groups, GroupMembers, Messages in exclusive mode";
			stat.executeUpdate(lock);
			
			result = stat.executeQuery(command1);
			while (result.next()){
				maxID = result.getInt("max");
			}
			
			DateFormat format = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
			java.util.Date date = (java.util.Date) format.parse(DOB);
			
			//Insert in Users Table
			String command2 = "INSERT INTO Users VALUES(?, ?, ?, ?, ?, ?)";
			preStat = connection.prepareStatement(command2);
			preStat.setInt(1, maxID + 1);
			preStat.setString(2, fname);
			preStat.setString(3, lname);
			preStat.setString(4, email);
			preStat.setTimestamp(5,  new java.sql.Timestamp(date.getTime()));
			preStat.setTimestamp(6, new java.sql.Timestamp(System.currentTimeMillis()));
			
			preStat.executeUpdate();
			connection.commit();
			System.out.println("User insert Success");
			
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("Exception in creatUser : cannot execute query");
			e.printStackTrace();
		} catch (ParseException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("Parsing issue for DOB");
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (stat != null){
					stat.close();					
				}
				if (result != null){
					result.close();					
				}
				if (preStat != null){
					preStat.close();					
				}
			} catch (SQLException e) {
				System.out.println("connection close issue");
				e.printStackTrace();
			}
		}
	}
	
	public void initiateFriendship(int sender, int recipient)
	{
		Statement stat = null;
		PreparedStatement preStat = null;
		ResultSet result = null;
		
		try {
			//checking userIDs
			if ((!validUser(sender)) || (!validUser(recipient))){
				System.out.println("Invalid userID");
				return;
			}
			
			//check see if two Ids are already friends (regardless of states)
			String command1 = "SELECT * FROM Friendships WHERE " + 
							  "((senderID = " + sender + " AND " + "recipientID = " + recipient + ")" + " OR " + 
							  "(senderID = " + recipient + " AND " + "recipientID = " + sender + "))";
			connection.setAutoCommit(false);
			stat = connection.createStatement();
			String lock = "lock table Users, Friendships, Groups, GroupMembers, Messages in exclusive mode";
			stat.executeUpdate(lock);
			
			result = stat.executeQuery(command1);
			
			//Already friends or request sent
			while (result.next()){
				System.out.println(sender + " and " + recipient + " are already Friends (or request pending)");
				return;
			}
			
			//Insert in Friendships Table
			String command2 = "INSERT INTO Friendships VALUES(?, ?, ?, null)";
			preStat = connection.prepareStatement(command2);
			preStat.setInt(1, sender);
			preStat.setInt(2, recipient);
			preStat.setString(3, "pending");
			preStat.executeUpdate();
			connection.commit();
			System.out.println("Friendship insert Success");
			
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("Exception in initiateFriendship : cannot execute query");
			e.printStackTrace();
		} finally {
			try {
				if (stat != null){
					stat.close();					
				}
				if (result != null){
					result.close();					
				}
				if (preStat != null){
					preStat.close();					
				}
			} catch (SQLException e) {
				System.out.println("Cannot close");
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Establish friendship only in terms of recipient perspective. Sender and recipient are distinguishable.
	 * If there is a pending friendship request from recipient to sender, the friendship will not be established.
	 * Friendship is established only if there is a pending request from sender to recipient. 
	 */
	public void establishFriendship(int sender, int recipient)
	{
		Statement stat = null;
		PreparedStatement preStat = null;
		ResultSet result = null;
		
		try {
			//checking userIDs
			if ((!validUser(sender)) || (!validUser(recipient))){
				System.out.println("Invalid userID");
				return;
			}
			
			//check see if there is a pending request from sender to recipient
			String command1 = "SELECT * FROM Friendships WHERE " + 
							  "(senderID = " + sender + " AND " + 
							  "recipientID = " + recipient + " AND " + 
							  "states = 'pending')";
			connection.setAutoCommit(false);
			stat = connection.createStatement();
			String lock = "lock table Users, Friendships, Groups, GroupMembers, Messages in exclusive mode";
			stat.executeUpdate(lock);
			result = stat.executeQuery(command1);
			
			if (!result.next()){
				System.out.println("There is no pending friendship request from " + sender + " to " + recipient);
				return;
			}

			//Update states and timestamp in Friendships Table
			String command2 = "UPDATE Friendships " + 
							  "SET states = 'established', friendship_date = ?" + 
							  "WHERE senderID = ? AND recipientID = ?";
			preStat = connection.prepareStatement(command2);
			preStat.setTimestamp(1, new java.sql.Timestamp(System.currentTimeMillis()));
			preStat.setInt(2, sender);
			preStat.setInt(3, recipient);
			preStat.executeUpdate();
			connection.commit();
			
			System.out.println("Friendship establish Success");
			
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("Exception in establishFriendship : cannot execute query");
			e.printStackTrace();
		} finally {
			try {
				if (stat != null){
					stat.close();					
				}
				if (result != null){
					result.close();					
				}
				if (preStat != null){
					preStat.close();					
				}
			} catch (SQLException e) {
				System.out.println("Cannot close");
				e.printStackTrace();
			}
		}
	}
	
	public void displayFriends(int user)
	{
		Statement stat = null;
		ResultSet result = null;
		
		try {
			//checking userIDs
			if (!validUser(user)){
				System.out.println("Invalid userID");
				return;
			}
			
			String command1 = "SELECT * FROM Friendships WHERE " + 
							  "(senderID = " + user + " OR " + 
							  "recipientID = " + user + ")";
			connection.setAutoCommit(false);
			stat = connection.createStatement();
			String lock = "lock table Users, Friendships, Groups, GroupMembers, Messages in exclusive mode";
			stat.executeUpdate(lock);
			result = stat.executeQuery(command1);
			
			
			int count = 1;
			System.out.println("------------Displaying " + user + "'s friends------------");
			while (result.next()){
				System.out.print("Friend_" + count + " : ");
				if (result.getInt("senderID") != user){
					System.out.print(result.getInt("senderID")); 
				} else {
					System.out.print(result.getInt("recipientID"));
				}
				System.out.println(", state = " + result.getString("states"));
				count++;
			}
			
			if (count == 1){
				System.out.println("No friends to display");
			}
			connection.commit();

		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("Exception in displayFriends : cannot execute query");
			e.printStackTrace();
		} finally {
			try {
				if (stat != null){
					stat.close();					
				}
				if (result != null){
					result.close();					
				}
			} catch (SQLException e) {
				System.out.println("Cannot close");
				e.printStackTrace();
			}
		}
	}
	
	public void createGroup(String name, String description, int limit)
	{
		Statement stat = null;
		PreparedStatement preStat = null;
		ResultSet result = null;
		int maxID = 0; 
		
		try {
			//get the largest ID integer and increment it by 1
			String command1 = "SELECT MAX(groupID) max FROM Groups";
			connection.setAutoCommit(false);
			stat = connection.createStatement();
			String lock = "lock table Users, Friendships, Groups, GroupMembers, Messages in exclusive mode";
			stat.executeUpdate(lock);
			result = stat.executeQuery(command1);
			while (result.next()){
				maxID = result.getInt("max");
			}
			
			//Insert in Groups Table
			String command2 = "INSERT INTO Groups VALUES(?, ?, ?, ?)";
			preStat = connection.prepareStatement(command2);
			preStat.setInt(1, maxID + 1);
			preStat.setString(2, name);
			preStat.setString(3, description);
			preStat.setInt(4, limit);
			
			preStat.executeUpdate();
			connection.commit();
			System.out.println("Group insert Success");
			
			
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("Exception in creatGroup : cannot execute query");
			e.printStackTrace();
		} finally {
			try {
				if (stat != null){
					stat.close();					
				}
				if (result != null){
					result.close();					
				}
				if (preStat != null){
					preStat.close();					
				}
			} catch (SQLException e) {
				System.out.println("connection close issue");
				e.printStackTrace();
			}
		}
	}
	
	public void addToGroup(int userID, int groupID)
	{
		Statement stat = null;
		PreparedStatement preStat = null;
		ResultSet result1 = null;
		ResultSet result2 = null;
		int maxLimit = 0; 
		int numMember = 0; 

		try {
			//checking userIDs
			if (!validUser(userID)){
				System.out.println("Invalid userID");
				return;
			}

			//checking groupID
			if (!validGroup(groupID)){
				System.out.println("Invalid groupID");
				return;
			}
			
			if(InGroup(userID, groupID))
			{
				System.out.println("User already in this group");
				return;
			}
			
			//checking max limit
			String command1 = "SELECT memberLimit FROM Groups where groupID = " + groupID;
			connection.setAutoCommit(false);
			stat = connection.createStatement();
			String lock = "lock table Users, Friendships, Groups, GroupMembers, Messages in exclusive mode";
			stat.executeUpdate(lock);
			result1 = stat.executeQuery(command1);
			while (result1.next()){
				maxLimit = result1.getInt("memberLimit");
			}
			
			String command2 = "SELECT COUNT(userID) Count FROM GroupMembers WHERE groupID = " + groupID;
			result2 = stat.executeQuery(command2);
			while (result2.next()){
				numMember = result2.getInt("Count");
			}
			
			if (numMember >= maxLimit){
				System.out.println("No room for additional user in this group.");
				return;
			}
			
			//Insert in GroupMembers Table
			String command = "INSERT INTO GroupMembers VALUES(?, ?)";
			preStat = connection.prepareStatement(command);
			preStat.setInt(1, userID);
			preStat.setInt(2, groupID);
			
			preStat.executeUpdate();
			connection.commit();
			System.out.println("Group Member insert Success");
			
			
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("Exception in addToGroup : cannot execute query");
			e.printStackTrace();
		} finally {
			try {
				if (stat != null){
					stat.close();					
				}
				if (result1 != null){
					result1.close();					
				}
				if (result2 != null){
					result2.close();					
				}
				if (preStat != null){
					preStat.close();					
				}
			} catch (SQLException e) {
				System.out.println("connection close issue");
				e.printStackTrace();
			}
		}
	}
	
	public void sendMessageToUser(int sender, int recipient, int replyTo, String subject, String body)
	{
		Statement stat = null;
		PreparedStatement preStat = null;
		ResultSet result1 = null;
		ResultSet result2 = null;
		int maxID = 0;
		
		try {
			//checking userIDs
			if ((!validUser(sender)) || (!validUser(recipient))){
				System.out.println("Invalid userID");
				return;
			}
			
			//chekcing length of message
			if (body.length() > 100){
				System.out.println("Message too long.");
				return;
			}

			connection.setAutoCommit(false);
			stat = connection.createStatement();
			String lock = "lock table Users, Friendships, Groups, GroupMembers, Messages in exclusive mode";
			stat.executeUpdate(lock);
			//checking valid replyTo. Let's make it default 0 when NULL 
			if (replyTo != 0){
				String command1 = "SELECT * FROM Messages WHERE msgID = " + replyTo + " AND senderID = " + recipient;
				result1 = stat.executeQuery(command1);
				if (!result1.next()){
					System.out.println("ReplyTo is not valid.");
					return;
				}
				
			}
			
			//get the largest msgID integer and increment it by 1
			String command2 = "SELECT MAX(msgID) max FROM Messages";
			result2 = stat.executeQuery(command2);
			while (result2.next()){
				maxID = result2.getInt("max");
			}
			
			//Insert in Messages Table
			String command3 = "INSERT INTO Messages VALUES(?, ?, ?, null, ?, ?, ?, ?)";
			preStat = connection.prepareStatement(command3);
			preStat.setInt(1, maxID + 1);
			preStat.setInt(2, sender);
			preStat.setInt(3, recipient);
			if (replyTo == 0){
				preStat.setNull(4, java.sql.Types.INTEGER);	
			} else {
				preStat.setInt(4, replyTo);
			}
			preStat.setString(5, subject);
			preStat.setString(6, body);
			preStat.setTimestamp(7, new java.sql.Timestamp(System.currentTimeMillis()));
			preStat.executeUpdate();
			connection.commit();
			System.out.println("Message sent : Success");
			
			
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("Exception in sendMessageToUser : cannot execute query");
			e.printStackTrace();
		} finally {
			try {
				if (stat != null){
					stat.close();					
				}
				if (result1 != null){
					result1.close();					
				}
				if (result2 != null){
					result2.close();					
				}
				if (preStat != null){
					preStat.close();					
				}
			} catch (SQLException e) {
				System.out.println("connection close issue");
				e.printStackTrace();
			}
		}
	}
	
	public void sendMessageToGroup(int sender, int group, String subject, String body)
	{
		Statement stat = null;
		PreparedStatement preStat = null;
		ResultSet result1 = null;
		ResultSet result2 = null;
		int maxID = 0;
		
		try {
			//checking userID of sender
			if (!validUser(sender)){
				System.out.println("Invalid userID");
				return;
			}
			
			//checking message length
			if (body.length() > 100){
				System.out.println("Message too long.");
				return;
			}


			//get the largest msgID integer and increment it by 1
			String command1 = "SELECT MAX(msgID) max FROM Messages";
			connection.setAutoCommit(false);
			stat = connection.createStatement();
			String lock = "lock table Users, Friendships, Groups, GroupMembers, Messages in exclusive mode";
			stat.executeUpdate(lock);
			result1 = stat.executeQuery(command1);
			while (result1.next()){
				maxID = result1.getInt("max");
			}
			
			//Insert in Messages Table. replyTo is NULL when sending message to a group.
			String command2 = "INSERT INTO Messages VALUES(?, ?, null, ?, null, ?, ?, ?)";
			preStat = connection.prepareStatement(command2);
			preStat.setInt(1, maxID + 1);
			preStat.setInt(2, sender);
			preStat.setInt(3, group);
			preStat.setString(4, subject);
			preStat.setString(5, body);
			preStat.setTimestamp(6, new java.sql.Timestamp(System.currentTimeMillis()));
			preStat.executeUpdate();
			connection.commit();
			System.out.println("Message sent to Group: Success");
			
			// Call sendMessageToUser for each user in the group
			String command3 = "SELECT userID FROM GroupMembers WHERE groupID = " + group;
			result2 = stat.executeQuery(command3);
			int userID = 0;
			while (result2.next()){
				userID = result2.getInt("userID");
				sendMessageToUser(sender, userID, 0, subject, body);
			}
			System.out.println("Message sent to members in the Group: Success");
			
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("Exception in sendMessageToGroup : cannot execute query");
			e.printStackTrace();
		} finally {
			try {
				if (stat != null){
					stat.close();					
				}
				if (result1 != null){
					result1.close();					
				}
				if (result2 != null){
					result2.close();					
				}
				if (preStat != null){
					preStat.close();					
				}
			} catch (SQLException e) {
				System.out.println("connection close issue");
				e.printStackTrace();
			}
		}
	}
	
	//display RECEIVED Messages
	public void displayMessages(int user)
	{
		Statement stat = null;
		ResultSet result = null;
		
		try {
			//checking userID
			if (!validUser(user)){
				System.out.println("Invalid userID");
				return;
			}
			
			String command1 = "SELECT * FROM Messages WHERE recipientID = " + user;
			connection.setAutoCommit(false);
			stat = connection.createStatement();
			String lock = "lock table Users, Friendships, Groups, GroupMembers, Messages in exclusive mode";
			stat.executeUpdate(lock);
			result = stat.executeQuery(command1);
			
			int count = 1;
			System.out.println("--------------------Displaying " + user + "'s Received Messages--------------------");
			System.out.printf("%-10s    %-10s    %-25s    %-25s    %-7s\n", "msgID", "Sender", "Timesent", "Subject", "msgText");
			while (result.next()){
				System.out.printf("%-10d    %-10d    %-25s    %-25s    %-100s\n", 
								  result.getInt("msgID"),
								  result.getInt("senderID"),
								  result.getTimestamp("dateSent"),
								  result.getString("subject"),
								  result.getString("msgText"));
				count++;
			}
			
			if (count == 1){
				System.out.println("No Messages to display");
			}
			connection.commit();
			System.out.println("Display received messages : Success");
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("Exception in displayMessages : cannot execute query");
			e.printStackTrace();
		} finally {
			try {
				if (stat != null){
					stat.close();					
				}
				if (result != null){
					result.close();					
				}
			} catch (SQLException e) {
				System.out.println("Cannot close");
				e.printStackTrace();
			}
		}
	}
	
	public void displayNewMessages(int user)
	{
		Statement stat = null;
		ResultSet result1 = null;
		ResultSet result2 = null;
		java.sql.Timestamp timeStamp = null;
		
		try {
			//checking userID
			if (!validUser(user)){
				System.out.println("Invalid userID");
				return;
			}
			
			//Retrieve last login from Users
			String command1 = "SELECT last_login from Users where userId = " + user;
			connection.setAutoCommit(false);
			stat = connection.createStatement();
			String lock = "lock table Users, Friendships, Groups, GroupMembers, Messages in exclusive mode";
			stat.executeUpdate(lock);
			result1 = stat.executeQuery(command1);
			
			String lastLogin = null;
			if (result1.next()){
				timeStamp = result1.getTimestamp("last_login");
				lastLogin = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(timeStamp.getTime()));
			}
			
			//Select messages after last login
			String command2 = "SELECT * FROM Messages WHERE recipientID = " + user + 
							  " AND dateSent > TO_TIMESTAMP('" + lastLogin + "', 'YYYY-MM-DD HH24:MI:SS')";
			result2 = stat.executeQuery(command2);
			
			int count = 1;
			System.out.println("--------------------Displaying " + user + "'s Received Messages--------------------");
			System.out.printf("%-10s    %-10s    %-25s    %-25s    %-7s\n", "msgID", "Sender", "Timesent", "Subject", "msgText");
			while (result2.next()){
				System.out.printf("%-10d    %-10d    %-25s    %-25s    %-100s\n", 
								  result2.getInt("msgID"),
								  result2.getInt("senderID"),
								  result2.getTimestamp("dateSent"),
								  result2.getString("subject"),
								  result2.getString("msgText"));
				count++;
			}
			
			if (count == 1){
				System.out.println("No Messages to display");
			}
			connection.commit();
			System.out.println("Display received new messages : Success");
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("Exception in displayNewMessages : cannot execute query");
			e.printStackTrace();
		} finally {
			try {
				if (stat != null){
					stat.close();					
				}
				if (result1 != null){
					result1.close();					
				}
				if (result2 != null){
					result2.close();					
				}
			} catch (SQLException e) {
				System.out.println("Cannot close");
				e.printStackTrace();
			}
		}
	}
	
	public void searchForUser(String search){
		Statement stat = null;
		PreparedStatement preStat = null;
		ResultSet result1 = null;		
		ResultSet result2 = null;		
		String command1 = null;

		try {
			String[] strArr = search.split(" ");
			for(int i = 0; i < strArr.length; i++)
			{
				command1 = "SELECT * from Users U where "
							+ "(instr(U.userID, ?) != 0) OR"
							+ "(instr(U.fname, ?) != 0) OR"
							+ "(instr(U.lname, ?) != 0) OR"
							+ "(instr(U.email, ?) != 0) OR"
							+ "(instr(TO_CHAR(U.DOB, 'YYYY-MM-DD HH24:MI:SS'), ?) != 0)";
				
				connection.setAutoCommit(false);
				stat = connection.createStatement();
				String lock = "lock table Users, Friendships, Groups, GroupMembers, Messages in exclusive mode";
				stat.executeUpdate(lock);
				
				preStat = connection.prepareStatement(command1);
				preStat.setString(1, strArr[i]);
				preStat.setString(2, strArr[i]);
				preStat.setString(3, strArr[i]);
				preStat.setString(4, strArr[i]);
				preStat.setString(5, strArr[i]);
				result1 = preStat.executeQuery();
				
				//print out names
				System.out.println("----------------Displaying Users Search Results : " 
									+ strArr[i] 
									+ " --------------------");

				while(result1.next()) {
					command1 = "SELECT userID, fname, lname from Users where userID = " + result1.getInt("userID");					
					result2 = stat.executeQuery(command1);
					if (result2.next()){
						System.out.println(result2.getString("fname") + " " + result2.getString("lname"));
					}
				}
			}	
			
			connection.commit();
			System.out.println("Display Search for Users : Success");
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("Exception in searchForUsers : cannot execute query");
			e.printStackTrace();
		} finally {		
			try {
				if (stat != null){
					stat.close();					
				}
				if (preStat != null){
					preStat.close();					
				}
				if (result1 != null){
					result1.close();					
				}
				if (result2 != null){
					result2.close();					
				}
			} catch (SQLException e) {
				System.out.println("Cannot close");
				e.printStackTrace();
			}
		}
		
		
	}

	public void threeDegrees(int userA, int userB){
		Statement stat = null;
		ResultSet result = null;
		Map<Integer, Set<Integer>> map = new HashMap<Integer, Set<Integer>>();
		List<Integer> toReturn = new ArrayList<Integer>();
		try {
			//checking userID
			if (!validUser(userA) || !validUser(userB)){
				System.out.println("Invalid userID");
				return;
			}
			
			if(userA == userB){
				System.out.println("userA and userB are the same person");
				return;
			}
			
			connection.setAutoCommit(false);
			stat = connection.createStatement();
			String lock = "lock table Users, Friendships, Groups, GroupMembers, Messages in exclusive mode";
			stat.executeUpdate(lock);
			
			String command = "SELECT senderID, recipientID from Friendships";
			result = stat.executeQuery(command);
			
			//make a map
			while(result.next()){
				int key = result.getInt("senderID");
				int value = result.getInt("recipientID");
				
				//key, value
				if (!map.containsKey(key)){
					Set<Integer> newSet = new HashSet<Integer>();
					newSet.add(value);
					map.put(key, newSet);
				} else {
					map.get(key).add(value);
				}
				
				//value, key
				if (!map.containsKey(value)){
					Set<Integer> newSet = new HashSet<Integer>();
					newSet.add(key);
					map.put(value, newSet);
				} else {
					map.get(value).add(key);
				}
			}
			
			List<Integer> resultPath = findPath(0, new HashSet<Integer>(), map.get(userA), toReturn, userB, map);
			if (!resultPath.contains(userB) || resultPath.size() >= 4){
				System.out.println("No path between " + userA + " and " + userB);
			} else {
				System.out.print("Path is : " + userA);
				for(int element : resultPath){
					System.out.print(" --> " + element);
				}
				System.out.println("");
			}
			
			connection.commit();
			System.out.println("Display Three Degrees : Success");
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("Exception in threeDegrees : cannot execute query");
			e.printStackTrace();
		} finally {
			try {
				if (stat != null){
					stat.close();					
				}
				if (result != null){
					result.close();					
				}
			} catch (SQLException e) {
				System.out.println("Cannot close");
				e.printStackTrace();
			}
		}
		
		
	}

	private List<Integer> findPath(int count, Set<Integer> used, Set<Integer> toExamine, List<Integer> toReturn, int userB, Map<Integer, Set<Integer>> map){
		if (count >= 3){
			return toReturn;
		} 
		used.addAll(toExamine);
		for(int element : toExamine){
			if (element == userB){
				toReturn.add(element);
				return toReturn;
			}
			List<Integer> toReturnNew = new ArrayList<Integer>();
			toReturnNew.addAll(toReturn);
			toReturnNew.add(element);
			
			//create a new examine set to pass on
			Set<Integer> toExamineNew = new HashSet<Integer>();
			toExamineNew.addAll(map.get(element));
			toExamineNew.removeAll(used);
			
			List<Integer> toTest = findPath(count++, used, toExamineNew, toReturnNew, userB, map);
			if (toTest.contains(userB)){
				return toTest;
			}
		}
		return toReturn;
	}
	
	public void topMessagers(int numPeople, int numMonths){
		Statement stat = null;
		ResultSet result1 = null;
		ResultSet result2 = null;
		java.sql.Timestamp timeStamp = null;
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		try {			
			Calendar cal = Calendar.getInstance(); 
			cal.setTime(new java.sql.Timestamp(System.currentTimeMillis()));
			cal.add(Calendar.MONTH, -numMonths);
			java.util.Date result = cal.getTime();
			timeStamp = new java.sql.Timestamp(result.getTime());
			String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(timeStamp.getTime()));
			String command1 = "select senderID, COUNT(*) from Messages where dateSent >= TO_TIMESTAMP('" + time + "', 'YYYY-MM-DD HH24:MI:SS') group by senderID order by COUNT(*) DESC";
			String command2 = "select recipientID, COUNT(*) from Messages where dateSent >= TO_TIMESTAMP('" + time + "', 'YYYY-MM-DD HH24:MI:SS') group by recipientID order by COUNT(*) DESC";
			connection.setAutoCommit(false);
			stat = connection.createStatement();
			String lock = "lock table Users, Friendships, Groups, GroupMembers, Messages in exclusive mode";
			stat.executeUpdate(lock);
			result1 = stat.executeQuery(command1);
			result2 = stat.executeQuery(command2);
			
			while (result1.next()){
				int key = result1.getInt("senderID");
				int val = result1.getInt("COUNT(*)");
				map.put(key, val);
			}
			
			while (result2.next()){
				int key = result2.getInt("recipientID");
				int val = result2.getInt("COUNT(*)");
				if (map.containsKey(key)){
					int originalVal = map.get(key);
					map.put(key, val + originalVal);
				} else {
					map.put(key, val);
				}
			}

			List<Integer> comm = new ArrayList<Integer>();
			for(int i = 0; i < numPeople; i++){
				int id = 0;
				int count = 0;
				for (int key : map.keySet()) {
					if (count < map.get(key)){
						id = key;
					}
				}
				if (id != 0){
					map.remove(id);
					comm.add(id);
				}
			}			
			
			System.out.println("--------------------Displaying Top Messengers--------------------");
			for (int i = 0; i < comm.size(); i++) {
				command1 = "SELECT userID, fname, lname from Users where userID = " + comm.get(i);				
				result1 = stat.executeQuery(command1);
				while (result1.next()){
					System.out.println((i+1) + ". " + result1.getString("fname") + " " + result1.getString("lname"));
				}
			}
			connection.commit();
			System.out.println("Display Top Messangers : Success");
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("Exception in Top Messengers : cannot execute query");
			e.printStackTrace();
		} finally {
			try {
				if (stat != null){
					stat.close();					
				}
				if (result1 != null){
					result1.close();					
				}
				if (result2 != null){
					result2.close();					
				}
			} catch (SQLException e) {
				System.out.println("Cannot close");
				e.printStackTrace();
			}
		}
	}

	public void dropUser(int userID){
		Statement stat = null;
		ResultSet result1 = null;
		try {
			//checking userID
			if (!validUser(userID)){
				System.out.println("Invalid userID");
				return;
			}
			//trigger in sql file will handle other tables
			String command1 = "DELETE FROM Users WHERE userID = " + userID;
			connection.setAutoCommit(false);
			stat = connection.createStatement();
			String lock = "lock table Users, Friendships, Groups, GroupMembers, Messages in exclusive mode";
			stat.executeUpdate(lock);
			result1 = stat.executeQuery(command1);			
			
			connection.commit();
			System.out.println("Drop User : Success");
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("Exception DropUser : cannot execute query");
			e.printStackTrace();
		} finally {
			try {
				if (stat != null){
					stat.close();					
				}
				if (result1 != null){
					result1.close();					

				}}catch (SQLException e) {
				System.out.println("Cannot close");
				e.printStackTrace();
			}
		}
	}
	
	
	private boolean validUser(int user){
		Statement stat = null;
		ResultSet result = null;
		try {
			String command = "SELECT * FROM Users WHERE userID = " + user;
			stat = connection.createStatement();
			result = stat.executeQuery(command);
			if (result.next()){
				return true;
			}
		} catch (SQLException e) {
			System.out.println("Exception in validUser : cannot execute query");
			e.printStackTrace();
		} finally {
			try {
				if (stat != null){
					stat.close();					
				}
				if (result != null){
					result.close();					
				}
			} catch (SQLException e) {
				System.out.println("connection close issue");
				e.printStackTrace();
			}
		}
		return false;
	}
	
	private boolean validGroup(int group){
		Statement stat = null;
		ResultSet result = null;
		try {
			String command = "SELECT * FROM Groups WHERE groupID = " + group;
			stat = connection.createStatement();
			result = stat.executeQuery(command);
			if (result.next()){
				return true;
			}
		} catch (SQLException e) {
			System.out.println("Exception in validGroup : cannot execute query");
			e.printStackTrace();
		} finally {
			try {
				if (stat != null){
					stat.close();					
				}
				if (result != null){
					result.close();					
				}
			} catch (SQLException e) {
				System.out.println("connection close issue");
				e.printStackTrace();
			}
		}
		return false;
	}
	
	private boolean InGroup(int userID, int groupID)
	{
		Statement stat = null;
		ResultSet result = null;
		try {
			String command = "SELECT * FROM GroupMembers WHERE userID = " + userID + " AND groupID = " + groupID;
			stat = connection.createStatement();
			result = stat.executeQuery(command);
			if (result.next()){
				return true;
			}
		} catch (SQLException e) {
			System.out.println("Exception in validGroup : cannot execute query");
			e.printStackTrace();
		} finally {
			try {
				if (stat != null){
					stat.close();					
				}
				if (result != null){
					result.close();					
				}
			} catch (SQLException e) {
				System.out.println("connection close issue");
				e.printStackTrace();
			}
		}
		return false;
		
	}
		
}