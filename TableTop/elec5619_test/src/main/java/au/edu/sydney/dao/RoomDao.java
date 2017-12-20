package au.edu.sydney.dao;

import static org.hamcrest.CoreMatchers.nullValue;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import au.edu.sydney.domain.Friendship;
import au.edu.sydney.domain.Room;
import au.edu.sydney.domain.User;

@Repository(value = "roomDao")
public class RoomDao {

    @Resource
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void saveRoom(Room room) {
        sessionFactory.getCurrentSession().save(room);
    }
    
    public Room getRoom(String owner) {
    	String hql = "SELECT r FROM Room r WHERE r.owner = :name";
    	Query query = sessionFactory.getCurrentSession().createQuery(hql);
    	query.setParameter("name", owner);
    	List<Room> rooms = query.list();
    	
    	if (rooms.size() != 0) {
    		return rooms.get(0);
    	}
    	else {
    		return null;
    	}
    }
    
    public Room getRoom(int id) {
    	return (Room) sessionFactory.getCurrentSession().get(Room.class, id);
    }
    
    public void deleteRoom(Room room) {
    	sessionFactory.getCurrentSession().delete(room);
    }
}