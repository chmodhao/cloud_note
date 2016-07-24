package org.tarena.note.web.controller.note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tarena.note.entity.NoteResult;
import org.tarena.note.service.NoteService;

@Controller
@RequestMapping("/note")
public class LoadNoteBodyController {
	@Autowired
	private NoteService noteService;

	@RequestMapping("/notebody.do")
	@ResponseBody
	public NoteResult excute(String noteId) {
		NoteResult result = noteService.LoadNoteBody(noteId);
		return result;
	}
}
