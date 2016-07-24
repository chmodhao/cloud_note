package org.tarena.note.web.controller.note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tarena.note.entity.NoteResult;
import org.tarena.note.service.ShareService;

@Controller
@RequestMapping("/note")
public class SearchShareController {
	@Autowired
	ShareService shareService;
	@ResponseBody
	@RequestMapping("/search.do")
	public NoteResult excute(String title){
		NoteResult result=shareService.search(title);
		return result;
	}
}
