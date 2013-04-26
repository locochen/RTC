package endpointExchange;

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import collabrationFeaturesSupport.RoomManager;

import peersConnection.InBoundPool;

import net.sf.json.JSONObject;



@WebServlet(urlPatterns = {"/message"})
public class MessageServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		super.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String r = request.getParameter("r");//room number
		String u = request.getParameter("u");//the connected people
	    BufferedReader br = request.getReader();
        String line = null;
        StringBuilder sb = new StringBuilder();
        while((line = br.readLine())!=null){
            sb.append(line); //get stream. 
        }
		
		String message = sb.toString();
		JSONObject json = JSONObject.fromObject(message);
		if (json != null) {
			String type = json.getString("type");
			if ("bye".equals(type)) {
				System.out.println("User :" + u + " Exit..");
				RoomManager.removeUser(r, u);
			}
		}
		String otherUser = RoomManager.getOtherUser(r, u);
		if (u.equals(otherUser)) {
			message = message.replace("\"offer\"", "\"answer\"");
			message = message.replace("a=crypto:0 AES_CM_128_HMAC_SHA1_32",
					"a=xrypto:0 AES_CM_128_HMAC_SHA1_32");
			message = message.replace("a=ice-options:google-ice\\r\\n", "");
		}
		InBoundPool.sendMsg(otherUser, message);
	}
}
