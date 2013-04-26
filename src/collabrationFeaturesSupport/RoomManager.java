package collabrationFeaturesSupport;

import java.util.HashMap;
import java.util.Map;


public class RoomManager {

	private static final Map<String,Room> provider = new HashMap<String,Room>();
	
	public static void addUser(String roomKey,String user){
		Room room = provider.get(roomKey);
		if(room == null){
			System.out.println("Add room : " + roomKey);
			room = new Room(roomKey,user);
			provider.put(roomKey, room);
		}else{
			room.addUser(user);
		}
	}
	
	public static void removeUser(String roomKey,String user){
		Room room = provider.get(roomKey);
		if(room != null){
			System.out.println("Remove user : " + user);
			room.removeUser(user);
		}
		if(!room.hasUser()){
			provider.remove(roomKey);
			System.out.println("Remove room : " + roomKey);
		}
	}
	
	public static boolean checkUser(String key){
		Room room = provider.get(key);
		if(room != null){
			return room.hasUser();
		}else{
			return false;
		}
	}
	
	public static String getOtherUser(String roomKey,String user){
		Room room = provider.get(roomKey);
		return room.getOtherUser(user);
	}
}
