package org.tarena.note.web.controller.note.test;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.tarena.note.entity.NoteResult;
import org.tarena.note.web.controller.note.AddNoteController;

import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class TestAddNoteController {
	@Autowired//注入要测试的Controller
	private AddNoteController controller;
	//发送测试请求
	private MockMvc mockMvc;
	@Before
	public void init(){
		mockMvc=MockMvcBuilders.standaloneSetup(controller).build();
	}
	@After
	public void destroy(){
		
	}
	
	@Test
	public void test1() throws Exception{
		//select * from cn_note where cn_notebook_id='0037215c-09fe-4eaa-aeb5-25a340c6b39b' order by cn_note_create_time;
		
		//发送执行一个HTTP请求
		RequestBuilder request=MockMvcRequestBuilders
		.post("/note/addnote.do")
		.param("cn_user_id", "52f9b276-38ee-447f-a3aa-0d54e7a736e4")//传数据
		.param("cn_notebook_id", "0037215c-09fe-4eaa-aeb5-25a340c6b39b")
		.param("cn_note_title", "java daliy4");
		
		MvcResult result=mockMvc.perform(request)
		.andDo(MockMvcResultHandlers.print())//将请求头和响应头打印
		.andExpect(MockMvcResultMatchers.status().isOk())//期望返回状态码200
		.andReturn();//用来返回结果
		
		//提取响应中的json字符串
		String jsonStr=result.getResponse().getContentAsString();
		System.out.println("----------------------------"+'\n'+jsonStr+'\n'+"----------------------------");
		//将json字符串转成java对象
		ObjectMapper mapper=new ObjectMapper();
		NoteResult noteResult=mapper.readValue(jsonStr, NoteResult.class);
		//断言
		Assert.assertEquals(0, noteResult.getStatus());
	}
}
