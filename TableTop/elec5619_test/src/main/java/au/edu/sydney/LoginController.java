package au.edu.sydney;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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

import au.edu.sydney.dao.UserDao;
import au.edu.sydney.domain.LoginData;
import au.edu.sydney.domain.User;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Controller
@Transactional
public class LoginController {
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String showLogin() {
	    return "login";
	}
	
	@Autowired
	UserDao userDao;
	@RequestMapping(value = "/login", method = RequestMethod.POST)
    public String submit(@RequestParam String username, @RequestParam String password, Model model, HttpSession session) {
 
		BCryptPasswordEncoder enc = new BCryptPasswordEncoder();
		User realUser = userDao.getUser(username);
		
		//authenticate user 
        if (realUser == null || !enc.matches(password, realUser.getPassword())) {
        	model.addAttribute("errorMessage", "Incorrect details entered. Please try again");
        	return "login";
        }
        
        session.setAttribute("loggedInUser", realUser);
        
        return "redirect:/account";
    }
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logOut(HttpSession session) {
		session.invalidate();
		return "index";
    }
}
