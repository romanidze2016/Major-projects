package au.edu.sydney;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Expression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import au.edu.sydney.dao.PersonDao;
import au.edu.sydney.domain.Person;
import au.edu.sydney.domain.User;

/**
 * Handles requests for the application home page.
 */
@Controller
@Transactional
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		logger.info("Oh fuck {}", formattedDate);
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	@RequestMapping(value = "/jdbcAdd", method = RequestMethod.GET)
    public String jdbcAdd(Locale locale, Model model) {
        
        // JDBC driver name and database URL
        String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
        String DB_URL = "jdbc:mysql://localhost/elec5619";

        //  Database credentials
        String USER = "root"; // 
        String PASS = "1995";
        
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try{
           //Register JDBC driver
           Class.forName("com.mysql.jdbc.Driver");

           //Open a connection
           System.out.println("Connecting to database...");
           conn = DriverManager.getConnection(DB_URL,USER,PASS);
           
           String insertTableSQL = "INSERT INTO employee (id, age, first, last) VALUES (?,?,?,?)";
           
           preparedStatement = conn.prepareStatement(insertTableSQL);
           preparedStatement.setInt(1, 1);
           preparedStatement.setInt(2, 25);
           preparedStatement.setString(3, "Test");
           preparedStatement.setString(4, "Name");

           // execute insert SQL statement
           preparedStatement.executeUpdate();
           System.out.println("Record is inserted into employee table!");
           
           //Clean-up environment
           preparedStatement.close();
           conn.close();
        }catch(SQLException se){
           //Handle errors for JDBC
           se.printStackTrace();
        }catch(Exception e){
           //Handle errors for Class.forName
           e.printStackTrace();
        }finally{
           try{
              if(preparedStatement!=null)
                  preparedStatement.close();
           }catch(SQLException se2){
           }
           try{
              if(conn!=null)
                 conn.close();
           }catch(SQLException se){
              se.printStackTrace();
           }
        }

        return "home";
    }
	
	@Autowired
	SessionFactory sessionFactory;
	@RequestMapping(value = "/hibernateAdd", method = RequestMethod.GET)
    public String hibernateAdd(Locale locale, Model model) {
        
        Person p = new Person();
        p.setAge(20);
        p.setFirst("FirstName");
        p.setLast("lastName");
        
        sessionFactory.getCurrentSession().save(p);
        return "home";
    }
	
	@Autowired
	PersonDao personDao;
	@RequestMapping(value = "/hibernateDaoAdd", method = RequestMethod.GET)
    public String hibernateDaoAdd(Locale locale, Model model) {
        Person p = new Person();
        p.setAge(22);
        p.setFirst("Dennis");
        p.setLast("Talic");

        personDao.savePerson(p);
        
        return "home";
    }
	
	@Autowired
	PersonDao pDao;
	@RequestMapping(value = "/hibernateDisplayUser", method = RequestMethod.GET)
    public String hibernateDisplayUser(Locale locale, Model model) {
		if (pDao != null) {
			Person p = pDao.getPerson(1);
	        
	        model.addAttribute("firstName", p.getFirst());
	        model.addAttribute("lastName", p.getLast());
		}
		else {
			model.addAttribute("firstName", "Fail");
	        model.addAttribute("lastName", "Fail");
		}
        
        return "Register";
    }
	
	//New home controller
	@RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(Locale locale, Model model) {
		return "index";
    }
	
	@RequestMapping(value = "/account", method = RequestMethod.GET)
    public String showAccount(Locale locale, Model model, HttpSession session) {
		User realUser = (User) session.getAttribute("loggedInUser");
		if (session.getAttribute("loggedInUser") != null) {
			//push user details into the model
	        model.addAttribute("username", realUser.getUsername());
	        model.addAttribute("first", realUser.getFirst());
	        model.addAttribute("last", realUser.getLast());
	        model.addAttribute("email", realUser.getEmail());
	        
	        return "success";
		}
		else {
			return "redirect:/login";
		}
    }
}
