package com.osmandurmus.endpoint;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.osmandurmus.dto.UserLoginDTO;
import com.osmandurmus.entity.Note;
import com.osmandurmus.service.NoteService;

@RestController
@RequestMapping(value = "/rest")
public class NoteEndPoint {
	
	@Autowired
	private NoteService noteService;
	
	@RequestMapping(value = "/getAll",method = RequestMethod.POST)
	public ResponseEntity<ArrayList<Note>> getNotes(@RequestBody UserLoginDTO login){
		
		return new ResponseEntity<>(noteService.getAll(login),HttpStatus.OK);
	}
}
