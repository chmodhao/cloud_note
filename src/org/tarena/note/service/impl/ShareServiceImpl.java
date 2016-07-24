package org.tarena.note.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tarena.note.dao.NoteDao;
import org.tarena.note.dao.ShareDao;
import org.tarena.note.entity.Note;
import org.tarena.note.entity.NoteResult;
import org.tarena.note.entity.Share;
import org.tarena.note.service.ShareService;
import org.tarena.note.util.NoteUtil;
@Service
@Transactional
public class ShareServiceImpl implements ShareService {
	@Autowired
	private ShareDao shareDao;
	@Autowired
	private NoteDao noteDao;

	public NoteResult ShareNoteById(String noteId) {
		NoteResult result = new NoteResult();
		Share temp = shareDao.findByNoteId(noteId);
		if (temp != null) {
			result.setStatus(1);
			result.setMsg("笔记已经分享过");
			return result;
		}
		Note tempParam=new Note();
		tempParam.setCn_note_id(noteId);
		Note note = noteDao.find(tempParam).get(0);
		Share share = new Share();
		share.setCn_share_id(NoteUtil.createId());
		share.setCn_share_title(note.getCn_note_title());
		share.setCn_share_body(note.getCn_note_body());
		share.setCn_note_id(noteId);
		int count = shareDao.save(share);
		if (count > 0) {
			result.setStatus(0);
			result.setMsg("分享成功");
		} else {
			result.setStatus(1);
			result.setMsg("分享失败");
		}
		return result;
	}

	public NoteResult search(String title) {
		NoteResult result=new NoteResult();
		String key="%";
		if(title!=null && !title.equals("")){
			key="%"+title+"%";
		}
		List<Share> list=shareDao.findLikeTitle(key);
		result.setStatus(0);
		result.setMsg("查询到结果");
		result.setData(list);
		return result;
	}
}
