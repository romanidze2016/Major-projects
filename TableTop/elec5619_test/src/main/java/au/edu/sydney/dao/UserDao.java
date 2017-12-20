package au.edu.sydney.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import au.edu.sydney.domain.User;

@Repository(value = "userDao")
public class UserDao {

    @Resource
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void saveUser(User user) {
        sessionFactory.getCurrentSession().save(user);
    }
    
    public User getUser(String username) {
    	return (User) sessionFactory.getCurrentSession().get(User.class, username);
    }
    
    public List<User> getUsers(String caller, String substring) {
    	List<User> users;
    	String hql = "SELECT u FROM User u"
    			+ " WHERE (u.username LIKE CONCAT('%',:substring,'%')) "
    			+ "and (u.username != :caller)";
    	Query query = sessionFactory.getCurrentSession().createQuery(hql);
    	query.setParameter("substring", substring);
    	query.setParameter("caller", caller);
    	users = query.list();
    	return users;
    }
}