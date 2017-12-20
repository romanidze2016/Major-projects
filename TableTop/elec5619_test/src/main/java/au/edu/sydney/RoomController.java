package au.edu.sydney;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@Transactional
public class RoomController {

	@RequestMapping(value = "/room", method = RequestMethod.GET)
	public String getRoom(HttpSession session) {
		if (session.getAttribute("loggedInUser") == null) {
			return "redirect:/login";
		}
		else {
			return "room";
		}
	}
}
