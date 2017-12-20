package au.edu.sydney.dao;

import static org.hamcrest.CoreMatchers.nullValue;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import au.edu.sydney.domain.Friendship;
import au.edu.sydney.domain.Member;
import au.edu.sydney.domain.Room;
import au.edu.sydney.domain.User;

@Repository(value = "memberDao")
public class MemberDao {

    @Resource
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public void saveMember(Member member) {
        sessionFactory.getCurrentSession().save(member);
    }
    
    public List<String> getMembers(int roomId) {
    	String hql = "SELECT m.username FROM Member m WHERE m.roomId = :room";
    	Query query = sessionFactory.getCurrentSession().createQuery(hql);
    	query.setParameter("room", roomId);
    	return query.list();
    }
    
    public Member getMember(int roomId, String username) {
    	String hql = "SELECT m FROM Member m WHERE (m.roomId = :room) and (m.username = :user)";
    	Query query = sessionFactory.getCurrentSession().createQuery(hql);
    	query.setParameter("room", roomId);
    	query.setParameter("user", username);
    	List<Member> members = query.list();
    	
    	if (members.size() != 0) {
    		return members.get(0);
    	}
    	else {
    		return null;
    	}
    }
    
    public Member getJoinedMember(String username) {
    	String hql = "SELECT m FROM Member m WHERE (m.username = :user)"
    			+ "and (m.joined = 1)";
    	Query query = sessionFactory.getCurrentSession().createQuery(hql);
    	query.setParameter("user", username);
    	List<Member> members = query.list();
    	
    	if (members.size() != 0) {
    		return members.get(0);
    	}
    	else {
    		return null;
    	}
    }
    
    public List<Member> getRoomInvites(String username) {
    	String hql = "SELECT m FROM Member m WHERE m.username = :name";
    	Query query = sessionFactory.getCurrentSession().createQuery(hql);
    	query.setParameter("name", username);
    	return query.list();
    }
    
    public void deleteMember(Member member) {
    	sessionFactory.getCurrentSession().delete(member);
    }
    
    public void deleteInvites(int roomId) {
    	String hql = "SELECT m FROM Member m WHERE m.roomId = :id";
    	Query query = sessionFactory.getCurrentSession().createQuery(hql);
    	query.setParameter("id", roomId);
    	List<Member> members = query.list();
    	for (Member m : members) {
    		deleteMember(m);
    	}
    }
}