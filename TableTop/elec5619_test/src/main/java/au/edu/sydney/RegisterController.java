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
import org.springframework.web.servlet.ModelAndView;

import au.edu.sydney.dao.UserDao;
import au.edu.sydney.domain.User;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Controller
@Transactional
public class RegisterController {
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView showRegister(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("register");
	    mav.addObject("user", new User());
	    return mav;
	}
	
	@Autowired
	UserDao userDao;
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public String submit(@Valid @ModelAttribute("user")User newUser, 
      BindingResult result, ModelMap model, HttpSession session) {
        if (result.hasErrors()) {
            return "error";
        }
        
        //check if input details are valid and username is not taken 
        if (userDao.getUser(newUser.getUsername()) != null) {
        	model.addAttribute("errorMessage", "Username is already taken:(");
        	return "index";
        }
        else if (!newUser.isValid()) {
        	model.addAttribute("errorMessage", "Invalid details entered. Please try again");
        	return "index";
        }
        else if (!newUser.isValidPassword()) {
        	model.addAttribute("errorMessage", "Password should be at least 6 characters");
        	return "index";
        }
        
        
        //hash password
        BCryptPasswordEncoder enc = new BCryptPasswordEncoder();
        newUser.setPassword(enc.encode(newUser.getPassword()));
        
        userDao.saveUser(newUser);
        session.setAttribute("loggedInUser", newUser);
        
        return "redirect:/index";
    }
}
