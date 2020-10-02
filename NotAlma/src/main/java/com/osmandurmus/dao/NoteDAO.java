package com.osmandurmus.dao;

import java.util.ArrayList;

import javax.persistence.Query;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.osmandurmus.entity.Note;


@Repository
public class NoteDAO {
	
	@Autowired ////servlet-context' den alýyoruz.
	private SessionFactory sessionFactory;
	
	//CRUD
	
	public Long insert(Note note) {
		return (Long) sessionFactory.getCurrentSession().save(note);		
	}
	
	public void update(Note note) {
		sessionFactory.getCurrentSession().update(note);
	}
	
	//Veri varsa (o id'yi sahip olan) update eder yoksa save eder.
	public void persist(Note note) {
		sessionFactory.getCurrentSession().persist(note);
	}
	
	public void delete(Note note) {
		sessionFactory.getCurrentSession().delete(note);
	}
	
	//READ
	
	public Note getFindById(Long id){
		Query query=sessionFactory.getCurrentSession().createQuery("FROM Note WHERE id=:id")
				.setLong("id",id);
		return (Note) query.getSingleResult();
	}
	
	public ArrayList<Note> getAll(){
		Query query=sessionFactory.getCurrentSession().createQuery("FROM Note"); //Note.java
		return (ArrayList<Note>) query.getResultList();
	}
	
	public ArrayList<Note> getAll(Long user_id){
		Query query=sessionFactory.getCurrentSession().createQuery("FROM Note WHERE user_id=:user_id ORDER BY  id desc")
				.setLong("user_id",user_id);
		return (ArrayList<Note>) query.getResultList();
	}
	
	
	
	
	
}