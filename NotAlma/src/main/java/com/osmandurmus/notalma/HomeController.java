package com.osmandurmus.notalma;

import java.util.ArrayList;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.osmandurmus.entity.Note;
import com.osmandurmus.security.LoginFilter;
import com.osmandurmus.service.MailService;
import com.osmandurmus.service.NoteService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	public static String url="http://localhost:8080/notalma";

	@Autowired
	private NoteService noteService;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String home(Model model,HttpServletRequest req) {
		
		return "redirect:/index"; //otomatikmen "/index" e gidecek.
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String homes(Model model,HttpServletRequest req) {

		return "redirect:/index"; //otomatikmen "/index" e gidecek.
	}
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Model model,HttpServletRequest req) {
		
		//bir tane user objesi getiriyor ve "user" ad�nda de�i�kene y�kleyip modele ekliyoruz.
		model.addAttribute("user",req.getSession().getAttribute("user"));
		
		//Spring sayesinde �rnekleme(new) yapmad�k.
		//Inversion of Control IoC yap�s�nda kulland�k.
		System.out.println(req.getRemoteAddr()); // eri�enin ip adresini verir.
		
		model.addAttribute("baslik","Notlar�n�z� Al�n");
		
		model.addAttribute("serverTime", "/" );
		
		model.addAttribute("notlar", noteService.getAll(1l) );
		
		return "index"; //jsp sayfas�n�n fiziksel yolu index.jsp
	}
	
	@RequestMapping(value = "/detay/{id}", method = RequestMethod.GET)
	public String home(@PathVariable("id") Long id,Model model) {
		
		model.addAttribute("id",id);
		
		return "detail";
	}
	
	@RequestMapping(value = "/ekle", method = RequestMethod.GET)
	public String ekle(Model model) {

		return "addNote";
	}
	
	@RequestMapping(value = "/error_404", method = RequestMethod.GET)
	public String error(Model model) {
		
		return "error_404";
	}
	
	@RequestMapping(value = "/addNote",method = RequestMethod.POST)
	public ResponseEntity<String> addNote(@RequestBody Note note,HttpServletRequest request){
		
		System.out.println(note.toString());
		noteService.createNote(note, request);
		return new ResponseEntity<>("OK",HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/updateNote",method = RequestMethod.POST)
	public ResponseEntity<String> updateNote(@RequestBody Note note,HttpServletRequest request){
		
		Note oldNote=noteService.getNoteFindById(note.getId());
		oldNote.setTitle(note.getTitle());
		oldNote.setContent(note.getContent());
		
		noteService.updateNote(oldNote, request);
		return new ResponseEntity<>("OK",HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/deleteNote",method = RequestMethod.POST)
	public ResponseEntity<String> deleteNote(@RequestBody Note note,HttpServletRequest request){
		
		Note oldNote=noteService.getNoteFindById(note.getId());
		
		noteService.deleteNote(oldNote, request);
		
		return new ResponseEntity<>("OK",HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/getNotes",method = RequestMethod.POST)
	public ResponseEntity<ArrayList<Note>> getNotes(HttpServletRequest request){

		return new ResponseEntity<>(noteService.getAll(LoginFilter.user.getId()),HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/getNote",method = RequestMethod.POST)
	public ResponseEntity<Note> getNotes(@RequestBody String id,HttpServletRequest request){
		
		System.out.println("not id:"+id);
		
		Note note=noteService.getNoteFindById(Long.parseLong(id));
		
		//detail sayfas�nda bir ba�ka kullan�c�ya ait olan note id kullanarak urlden notun i�eri�ine eri�ememeli.
		//note ve session daki user id e�le�mesi i�in g�venlik kontrol�
		if(note.getUser_id().equals(LoginFilter.user.getId()))
			return new ResponseEntity<>(noteService.getNoteFindById(Long.parseLong(id)),HttpStatus.CREATED);
			
		return new ResponseEntity<>(null,HttpStatus.CREATED);
	}
	
}
