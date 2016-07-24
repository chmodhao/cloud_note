package org.tarena.note.web.controller.note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tarena.note.entity.NoteResult;
import org.tarena.note.service.NoteService;

@Controller
@RequestMapping("/note")
public class AbandonNoteController {
	@Autowired
	private NoteService noteService;

	@RequestMapping("/abandon.do")
	@ResponseBody
	public NoteResult excute(String noteId) {
		NoteResult result = noteService.abandonNoteByNoteId(noteId);
		return result;
	}
}
//扫描，注入，业务方法，返回