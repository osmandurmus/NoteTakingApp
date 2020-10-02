package com.osmandurmus.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.osmandurmus.dao.UserDAO;
import com.osmandurmus.entity.User;

@Service
@Transactional
public class UserService {
	
	
	@Autowired
	private MailService mailService;
	
	
	@Autowired
	private UserDAO userDAO;
	
	public Long insertUser(User user) {
		
		String uuid=UUID.randomUUID().toString();
		user.setKeyreg(uuid);
		
		if(userDAO.insert(user)>0) {
			mailService.registerMail(user.getEmail(),user.getKeyreg());
		}
		return 1l;
	}
	
	public void updateUser(User user) {
		userDAO.update(user);
	}
	
	public User getUserFindByUsernameAndPass(User user){
		return userDAO.getFindByUsernameAndPass(user.getUsername(),user.getPassword());
	}
	
	public User getUserFindByUsername(String username){
		return userDAO.getFindByUsername(username);
	}
	
	public boolean getUserFindByKey(String key){
		User user=userDAO.getFindByKey(key);
		if(user!=null) {
			
			user.setActive(true);
			updateUser(user);
			
			return true;
		}else {
			return false;
		}
	}

	
}
