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
import org.springframework.web.bind.annotation.RequestParam;

import au.edu.sydney.dao.FriendshipDao;
import au.edu.sydney.dao.MemberDao;
import au.edu.sydney.dao.RoomDao;
import au.edu.sydney.dao.UserDao;
import au.edu.sydney.domain.Friendship;
import au.edu.sydney.domain.Member;
import au.edu.sydney.domain.Room;
import au.edu.sydney.domain.User;

@Controller
@Transactional
public class CreateController {

	@Autowired
	UserDao userDao;
	@Autowired
	FriendshipDao friendshipDao;
	@Autowired
	RoomDao roomDao;
	@Autowired
	MemberDao memberDao;
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String searchFriends(Model model, HttpSession session) {
		if (session.getAttribute("loggedInUser") == null) {
			return "redirect:/login";
		}
		else {
			String sessionOwner = ((User) session.getAttribute("loggedInUser")).getUsername();
			Room room = roomDao.getRoom(sessionOwner);
			List<String> friends = friendshipDao.getFriendList(sessionOwner);
			List<User> friendsObjects = new ArrayList<User>();
			for (String name : friends) {
				if (room == null || memberDao.getMember(room.getId(), name) == null) {
					friendsObjects.add(userDao.getUser(name));
				}
			}
			
			model.addAttribute("listOfFriends", friendsObjects);
			
			List<User> invitedUsers = new ArrayList<User>();
			
			
			if (room != null) {
				List<String> members = memberDao.getMembers(room.getId());
				for (String member : members) {
					invitedUsers.add(userDao.getUser(member));
				}
			}
			else {
				room = new Room();
				room.setOwner(sessionOwner);
				room.setStatus(0);
				roomDao.saveRoom(room);
			}
			
			model.addAttribute("listOfMembers", invitedUsers);
			
			return "create";
		}
	}
	
	
	@Autowired
	UserDao usersDao;
	@Autowired
	FriendshipDao friendshipsDao;
	@Autowired
	MemberDao membersDao;
	@RequestMapping(value = "/inviteFriend", method = RequestMethod.POST)
    public String inviteFriend(HttpServletRequest request, Model model, HttpSession session) {
		if (session.getAttribute("loggedInUser") == null) {
			return "redirect:/login";
		}
		
		String sessionOwner = ((User) session.getAttribute("loggedInUser")).getUsername();
		Room room = roomDao.getRoom(sessionOwner);
		String friend = request.getParameter("invite");
		
		Member m = new Member();
		m.setJoined(0);
		m.setRoomId(room.getId());
		m.setUsername(friend);
		
		membersDao.saveMember(m);
		
		return "redirect:/create";
    }
	
	@Autowired
	UserDao uDao;
	@Autowired
	FriendshipDao fDao;
	@Autowired
	MemberDao mDao;
	@RequestMapping(value = "/removeMember", method = RequestMethod.POST)
    public String removeMember(HttpServletRequest request, Model model, HttpSession session) {
		if (session.getAttribute("loggedInUser") == null) {
			return "redirect:/login";
		}
		
		String sessionOwner = ((User) session.getAttribute("loggedInUser")).getUsername();
		Room room = roomDao.getRoom(sessionOwner);
		String friend = request.getParameter("remove");
		
		Member m = mDao.getMember(room.getId(), friend);
		mDao.deleteMember(m);
		
		return "redirect:/create";
    }
	
	@RequestMapping(value = "/removeRoom", method = RequestMethod.GET)
	public String removeRoom(Model model, HttpSession session) {
		if (session.getAttribute("loggedInUser") == null) {
			return "redirect:/login";
		}
		else {
			String sessionOwner = ((User) session.getAttribute("loggedInUser")).getUsername();
			Room room = roomDao.getRoom(sessionOwner);
			
			memberDao.deleteInvites(room.getId());
			roomDao.deleteRoom(room);
			
			return "removedRoom";
		}
	}
	
}
