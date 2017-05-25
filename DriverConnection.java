import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import oracle.jdbc.*;


public class DriverConnection {
	private static final String URL = "jdbc:oracle:thin:@class3.cs.pitt.edu:1521:dbclass";
	private static final String USERNAME = "dal168";
	private static final String PASSWORD = "4169305";
	
	public static void main(String[] args) {
		Connection conn = null;
		try {
			//registering driver
			System.out.println("Registering Driver----------");
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			
			//making connection
			System.out.println("Connecting to Database----------");
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			FaceSpace faceSpace = new FaceSpace(conn);
			for(int i = 0; i < 10; i++){
				faceSpace.createUser("Daniel", "Lee", "wow@gmail.com", "1993-06-24 00:00:00");
				faceSpace.initiateFriendship(i, i+1);
				faceSpace.establishFriendship(i, i+1);
				faceSpace.displayFriends(i);
				faceSpace.createGroup("how", "eat", 2);
				faceSpace.addToGroup(i, i);
				faceSpace.sendMessageToUser(i, i+1, 0, "e", "ewe");
				faceSpace.sendMessageToGroup(i, i, "w", "we");
				faceSpace.displayMessages(i);
				faceSpace.displayNewMessages(i);
				faceSpace.searchForUser("daniel");
				faceSpace.topMessagers(i, 20);
				faceSpace.threeDegrees(i, i+1);
			}
			faceSpace.dropUser(1);
			
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
}