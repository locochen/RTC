package endpointExchange;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import collabrationFeaturesSupport.RoomManager;




@WebServlet(urlPatterns = {"/room"})
public class RoomServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String room = request.getParameter("r");
		System.out.println("-------------------");
		System.out.print("Do Post....");
		if(StringUtils.isEmpty(room)){
			room = String.valueOf(System.currentTimeMillis());//if a room is empty, create a new number of room.
			response.sendRedirect("room?r=" + room);
			System.out.println("Room Num: "+room +";");
		}else{
			Integer initiator = 1;
			String user = UUID.randomUUID().toString().replace("-", "");//create a user id
			if(!RoomManager.checkUser(room)){//wait people enter into the room, if there is no person, don't create a connection request.
				initiator = 0;//if there is no person in the room, not send the connection request.
			}
			RoomManager.addUser(room, user);//add a user into the room
			//chat hyperlink. 
			String roomLink = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort() +  request.getContextPath() +"/room?r=" + room;
			String roomKey = room;
			request.setAttribute("initiator", initiator);
			request.setAttribute("roomLink", roomLink);
			request.setAttribute("roomKey", roomKey);
			request.setAttribute("user", user);
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}
	}
}
