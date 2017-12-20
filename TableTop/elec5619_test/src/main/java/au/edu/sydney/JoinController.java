package au.edu.sydney;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import au.edu.sydney.dao.FriendshipDao;
import au.edu.sydney.dao.MemberDao;
import au.edu.sydney.dao.RoomDao;
import au.edu.sydney.dao.UserDao;
import au.edu.sydney.domain.Room;
import au.edu.sydney.domain.DisplayData;
import au.edu.sydney.domain.User;
import au.edu.sydney.domain.Member;

@Controller
@Transactional
public class JoinController {

	@Autowired
	UserDao userDao;
	@Autowired
	FriendshipDao friendshipDao;
	@Autowired
	RoomDao roomDao;
	@Autowired
	MemberDao memberDao;
	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public String showRooms(Model model, HttpSession session) {
		if (session.getAttribute("loggedInUser") == null) {
			return "redirect:/login";
		}
		else {
			String sessionOwner = ((User) session.getAttribute("loggedInUser")).getUsername();
			 
			List<Member> roomInvites = memberDao.getRoomInvites(sessionOwner);
			
			List<DisplayData> inviteData = new ArrayList<DisplayData>();
			
			for (Member m : roomInvites) {
				Room r = roomDao.getRoom(m.getRoomId());
				User roomCreator = userDao.getUser(r.getOwner());
				
				DisplayData a = new DisplayData();
				a.setUsername(roomCreator.getUsername());
				a.setFirst(roomCreator.getFirst());
				a.setLast(roomCreator.getLast());
				a.setString(memberDao.getMembers(r.getId()).size() + "");
				inviteData.add(a);
			}
			
			model.addAttribute("inviteData", inviteData);
			
			return "join";
		}
	}	
	
	
	@RequestMapping(value = "/joinRoom", method = RequestMethod.POST)
    public String joinRoom(HttpServletRequest request, Model model, HttpSession session) {
		if (session.getAttribute("loggedInUser") == null) {
			return "redirect:/login";
		}
		
		String sessionOwner = ((User) session.getAttribute("loggedInUser")).getUsername();
		String roomCreator = request.getParameter("join");
		
		Room room = roomDao.getRoom(roomCreator);
		Member m = memberDao.getMember(room.getId(), sessionOwner);
		m.setJoined(1);
		memberDao.saveMember(m);
		
		return "redirect:/lobby";
    }
	
}
