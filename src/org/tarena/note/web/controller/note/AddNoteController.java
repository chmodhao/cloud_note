package org.tarena.note.web.controller.note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tarena.note.entity.Note;
import org.tarena.note.entity.NoteResult;
import org.tarena.note.service.NoteService;
@Controller
@RequestMapping("/note")
public class AddNoteController {
	@Autowired
	private NoteService noteService;
	@RequestMapping("/addnote.do")
	@ResponseBody
	public NoteResult excute(Note note){
		return noteService.addNoteByNoteBookId(note);
	}
}
