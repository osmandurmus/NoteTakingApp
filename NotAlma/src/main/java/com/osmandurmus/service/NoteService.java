package com.osmandurmus.service;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.osmandurmus.dao.NoteDAO;
import com.osmandurmus.dto.UserLoginDTO;
import com.osmandurmus.entity.Note;
import com.osmandurmus.entity.User;
import com.osmandurmus.security.LoginFilter;

@Service
@Transactional
public class NoteService {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private NoteDAO noteDAO;
	
	public Long createNote(Note note,HttpServletRequest request) {
		
		note.setUser_id(LoginFilter.user.getId()); //giriþ yapan kullanýcýn user id'si alýnýr.
		return noteDAO.insert(note);
	}
	
	public Long updateNote(Note note,HttpServletRequest request) {
		noteDAO.update(note);
		return 1l;
	}
	
	
	public Long deleteNote(Note note,HttpServletRequest request) {
		noteDAO.delete(note);
		return 1l;
	}
	
	public Note getNoteFindById(Long id){
		return noteDAO.getFindById(id);
	}
	
	public ArrayList<Note> getAll(){
		return noteDAO.getAll();
	}
	
	public ArrayList<Note> getAll(Long user_id){
		return noteDAO.getAll(user_id);
	}
	
	//rest //for web service
	public ArrayList<Note> getAll(UserLoginDTO login){
		User userm=new User();
		userm.setUsername(login.getUsername());
		userm.setPassword(login.getPassword());
		
		User user=userService.getUserFindByUsernameAndPass(userm);
		return noteDAO.getAll(user.getId());
	}
	

	
	
}
