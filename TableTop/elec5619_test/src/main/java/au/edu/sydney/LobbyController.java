package au.edu.sydney;

import java.util.ArrayList;
import java.util.List;

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
import au.edu.sydney.domain.DisplayData;
import au.edu.sydney.domain.Member;
import au.edu.sydney.domain.Room;
import au.edu.sydney.domain.User;

@Controller
@Transactional
public class LobbyController {
	
	@Autowired
	UserDao userDao;
	@Autowired
	FriendshipDao friendshipDao;
	@Autowired
	RoomDao roomDao;
	@Autowired
	MemberDao memberDao;
	@RequestMapping(value = "/lobby", method = RequestMethod.GET)
	public String showLobby(Model model, HttpSession session) {
		if (session.getAttribute("loggedInUser") == null) {
			return "redirect:/login";
		}
		else {
			String sessionOwner = ((User) session.getAttribute("loggedInUser")).getUsername();
			
			Room room = roomDao.getRoom(sessionOwner);
			List<DisplayData> data = new ArrayList<DisplayData>();
			
			//player entered the lobby
			if (room == null) {
				Member m = memberDao.getJoinedMember(sessionOwner);
				room = roomDao.getRoom(m.getRoomId());
				model.addAttribute("player", 1);
			}
			else {
				room.setStatus(1);
				roomDao.saveRoom(room);
			}
			
			
			List<String> roomMembers = memberDao.getMembers(room.getId());
			//get list of invited players and corresponding data
			for (String username : roomMembers) {
				User member = userDao.getUser(username);
					
				DisplayData memberData = new DisplayData();
				memberData.setUsername(username);
				memberData.setFirst(member.getFirst());
				memberData.setLast(member.getLast());
				if (memberDao.getMember(room.getId(), username).getJoined() == 1) {
					memberData.setString("Joined");
				}
				else {
					memberData.setString("Invited");
				}
				data.add(memberData);
			}
			
			//push data to display on the page
			model.addAttribute("roomCreator", userDao.getUser(room.getOwner()));
			model.addAttribute("players", data);
			
			return "lobby";
		}
	}
	
	
	@RequestMapping(value = "/cancel", method = RequestMethod.GET)
	public String cancelRoom(Model model, HttpSession session) {
		if (session.getAttribute("loggedInUser") == null) {
			return "redirect:/login";
		}
		else {
			String sessionOwner = ((User) session.getAttribute("loggedInUser")).getUsername();
			
			Room room = roomDao.getRoom(sessionOwner);
			room.setStatus(0);
			memberDao.deleteInvites(room.getId());
			
			return "redirect:/create";
		}
	}
	
	@RequestMapping(value = "/exit", method = RequestMethod.GET)
	public String exitRoom(Model model, HttpSession session) {
		if (session.getAttribute("loggedInUser") == null) {
			return "redirect:/login";
		}
		else {
			String sessionOwner = ((User) session.getAttribute("loggedInUser")).getUsername();
			
			memberDao.getJoinedMember(sessionOwner).setJoined(0);
			
			return "redirect:/join";
		}
	}
}
