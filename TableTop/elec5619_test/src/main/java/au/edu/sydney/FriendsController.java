package au.edu.sydney;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import au.edu.sydney.dao.FriendshipDao;
import au.edu.sydney.dao.UserDao;
import au.edu.sydney.domain.Friendship;
import au.edu.sydney.domain.LoginData;
import au.edu.sydney.domain.User;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Controller
@Transactional
public class FriendsController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value = "/friends", method = RequestMethod.GET)
	public String searchFriends(HttpSession session) {
		if (session.getAttribute("loggedInUser") == null) {
			return "redirect:/login";
		}
		else {
			return "friends";
		}
	}
	
	@Autowired
	UserDao userDao;
	@Autowired
	FriendshipDao friendshipsDao;
	@RequestMapping(value = "/friends", method = RequestMethod.POST)
    public String displayMatchingUsers(@RequestParam String username, Model model, HttpSession session) {
		if (session.getAttribute("loggedInUser") == null) {
			return "redirect:/login";
		}
		
		String sessionOwner = ((User) session.getAttribute("loggedInUser")).getUsername();
		List<User> matchingUsers = userDao.getUsers(sessionOwner, username);
		
		//filter all matching users. 
		//make sure that displayed users are not friends with the session owner yet
		List<User> finalResult = new ArrayList<User>();
		
		for (User u : matchingUsers) {
			if (friendshipsDao.getFriendship(sessionOwner, u.getUsername()).size() == 0 && 
					friendshipsDao.getFriendship(u.getUsername(), sessionOwner).size() == 0) {
				finalResult.add(u);
			}
		}
		
		model.addAttribute("listOfUsers", finalResult);
		return "friends";
    }
	
	@Autowired
	UserDao usersDao;
	@Autowired
	FriendshipDao friendshipDao;
	@RequestMapping(value = "/addFriend", method = RequestMethod.POST)
    public String addFriend(HttpServletRequest request, Model model, HttpSession session) {
		
		User sender = (User) session.getAttribute("loggedInUser");
		User receiver = usersDao.getUser(request.getParameter("friend"));
		
		Friendship f = new Friendship();
		f.setSender(sender.getUsername());
		f.setReceiver(receiver.getUsername());
		f.setStatus(0);
		
		friendshipDao.saveFriendship(f);
		
		model.addAttribute("message", "Friend request sent to");
		model.addAttribute("username", request.getParameter("friend"));
		
		return "addFriend";
    }
	
	@Autowired
	UserDao uDao;
	@Autowired
	FriendshipDao fDao;
	@RequestMapping(value = "/friendlist", method = RequestMethod.GET)
	public String showFriends(Model model, HttpSession session) {
		if (session.getAttribute("loggedInUser") == null) {
			return "redirect:/login";
		}
		
		List<String> friends = fDao.getFriendList(((User) session.getAttribute("loggedInUser")).getUsername());
		List<User> friendsObjects = new ArrayList<User>();
		for (String name : friends) {
			friendsObjects.add(uDao.getUser(name));
		}
		
		model.addAttribute("listOfUsers", friendsObjects);
		
		return "friendlist";
	}
	
	
	@RequestMapping(value = "/friendrequests", method = RequestMethod.GET)
	public String showFriendRequests(Model model, HttpSession session) {
		if (session.getAttribute("loggedInUser") == null) {
			return "redirect:/login";
		}
		
		List<String> friends = fDao.getFriendRequests(((User) session.getAttribute("loggedInUser")).getUsername());
		List<User> friendsObjects = new ArrayList<User>();
		for (String name : friends) {
			friendsObjects.add(uDao.getUser(name));
		}
		
		model.addAttribute("listOfUsers", friendsObjects);
		
		return "friendrequests";
	}
	
	@RequestMapping(value = "/acceptFriend", method = RequestMethod.POST)
	public String acceptFriend(HttpServletRequest request, Model model, HttpSession session) {
		if (session.getAttribute("loggedInUser") == null) {
			return "redirect:/login";
		}
		
		List<Friendship> f = fDao.getFriendship(request.getParameter("friend"), ((User) session.getAttribute("loggedInUser")).getUsername());
		Friendship current = f.get(0);
		current.setStatus(1);
		fDao.saveFriendship(current);
		
		model.addAttribute("message", "Friend request accepted from");
		model.addAttribute("username", request.getParameter("friend"));
		
		return "addFriend";
	}
	
	@RequestMapping(value = "/removeFriend", method = RequestMethod.POST)
	public String removeFriend(HttpServletRequest request, Model model, HttpSession session) {
		if (session.getAttribute("loggedInUser") == null) {
			return "redirect:/login";
		}
		
		List<Friendship> f = fDao.getFriendship(request.getParameter("friend"), ((User) session.getAttribute("loggedInUser")).getUsername());
		if (f.size() == 0) {
			f = fDao.getFriendship(((User) session.getAttribute("loggedInUser")).getUsername(), request.getParameter("friend"));
		}
		fDao.deleteFriendship(f.get(0));
		
		model.addAttribute("message", "Removed friend with username");
		model.addAttribute("username", request.getParameter("friend"));
		
		return "addFriend";
	}
	
	@RequestMapping(value = "/rejectRequest", method = RequestMethod.POST)
	public String rejectRequest(HttpServletRequest request, Model model, HttpSession session) {
		if (session.getAttribute("loggedInUser") == null) {
			return "redirect:/login";
		}
		
		List<Friendship> f = fDao.getFriendship(request.getParameter("friend"), ((User) session.getAttribute("loggedInUser")).getUsername());
		if (f.size() == 0) {
			f = fDao.getFriendship(((User) session.getAttribute("loggedInUser")).getUsername(), request.getParameter("friend"));
		}
		fDao.deleteFriendship(f.get(0));
		
		return "redirect:/friendrequests";
	}
}
