package org.tarena.note.web.controller.note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tarena.note.entity.NoteResult;
import org.tarena.note.service.NoteService;

@Controller
@RequestMapping("/note")
public class LoadNotesController {
	@Autowired
	private NoteService noteService;
	@RequestMapping("/notes.do")
	@ResponseBody
	public NoteResult excute(String notebookId){
		NoteResult result=noteService.loadNotesByBookId(notebookId);
		return result;
	}
}
