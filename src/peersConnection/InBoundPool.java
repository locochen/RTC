package peersConnection;

import java.io.IOException;
import java.nio.CharBuffer;
import java.util.HashMap;
import java.util.Map;


public class InBoundPool {

	private static final Map<String,MessageInBound > connector = new HashMap<String,MessageInBound>();
	
	public static void addMsgInBound(MessageInBound messageInBound){
		//add connection
		System.out.println("User : " + messageInBound.getUser() + " Join..");
		connector.put(messageInBound.getUser(), messageInBound);
	}
	
	public static void removeMsgInBound(MessageInBound messageInBound){
		//remove connection
		System.out.println("User : " + messageInBound.getUser() + " Exit..");
		connector.remove(messageInBound.getUser());
	}
	
	public static void sendMsg(String user,String message){
		try {
			//send data to a specific user.
			System.out.println("Send message to user : " + user + " ,message content : " + message);
			MessageInBound inbound = connector.get(user);
			if(inbound != null){
				inbound.getWsOutbound().writeTextMessage(CharBuffer.wrap(message));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
