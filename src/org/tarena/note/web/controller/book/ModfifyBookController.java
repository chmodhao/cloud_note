package org.tarena.note.web.controller.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tarena.note.entity.NoteBook;
import org.tarena.note.entity.NoteResult;
import org.tarena.note.service.BookService;

@Controller
@RequestMapping("/notebook")
public class ModfifyBookController {
	@Autowired
	private BookService bookService;
	@ResponseBody
	@RequestMapping("/modfify.do")
	public NoteResult excute(NoteBook notebook){
		return bookService.modfifyByBookId(notebook);
	}
}
