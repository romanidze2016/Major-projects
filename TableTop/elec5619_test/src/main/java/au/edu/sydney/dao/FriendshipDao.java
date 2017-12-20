package au.edu.sydney.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import au.edu.sydney.domain.Friendship;
import au.edu.sydney.domain.Person;
import au.edu.sydney.domain.User;

@Repository(value = "friendshipDao")
public class FriendshipDao {

    @Resource
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void saveFriendship(Friendship friendship) {
        sessionFactory.getCurrentSession().save(friendship);
    }
    
    public void deleteFriendship(Friendship friendship) {
    	sessionFactory.getCurrentSession().delete(friendship);
    }
    
    public Friendship getFriendship(int id) {
    	return (Friendship) sessionFactory.getCurrentSession().get(Friendship.class, id);
    }
    
    public List<Friendship> getFriendship(String sender, String receiver) {
    	List<Friendship> friendships;
    	String hql = "SELECT f FROM Friendship f"
    			+ " WHERE (f.sender = :s ) "
    			+ "and (f.receiver = :r)";
    	Query query = sessionFactory.getCurrentSession().createQuery(hql);
    	query.setParameter("s", sender);
    	query.setParameter("r", receiver);
    	friendships = query.list();
    	return friendships;
    }
    
    public List<String> getFriendList(String username) {
    	List<String> friends;
    	String hql = "SELECT f.receiver FROM Friendship f"
    			+ " WHERE (f.sender = :s ) and (f.status = 1)";
    	Query query = sessionFactory.getCurrentSession().createQuery(hql);
    	query.setParameter("s", username);
    	friends = query.list();
    	
    	hql = "SELECT f.sender FROM Friendship f"
    			+ " WHERE (f.receiver = :s ) and (f.status = 1)";
    	query = sessionFactory.getCurrentSession().createQuery(hql);
    	query.setParameter("s", username);
    	friends.addAll(query.list());
    	return friends;
    }
    
    public List<String> getFriendRequests(String username) {
    	List<String> friends;
    	String hql = "SELECT f.sender FROM Friendship f"
    			+ " WHERE (f.receiver = :s ) and (f.status = 0)";
    	Query query = sessionFactory.getCurrentSession().createQuery(hql);
    	query.setParameter("s", username);
    	friends = query.list();
    	return friends;
    }
    
}