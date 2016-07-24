package org.tarena.note.web.controller.note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tarena.note.entity.NoteResult;
import org.tarena.note.service.ShareService;
@Controller
@RequestMapping("note")
public class ShareNoteController {
	@Autowired
	private ShareService shareService;
	@ResponseBody
	@RequestMapping("/shareNote.do")
	public NoteResult excute(String noteId){
		NoteResult result=shareService.ShareNoteById(noteId);
		return result;
	}
}
