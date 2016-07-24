package org.tarena.note.junit;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.tarena.note.exception.NoteException;

@SuppressWarnings("deprecation")
public class TestInJunit {
	public void myfrist(){
		System.out.println("--myfrist--");
	}
	@Before//每个@Test方法之前执行
	public void init(){
		System.out.println("--init--");
	}
	@After//每个@Test方法之后执行
	public void destroy(){
		System.out.println("--destroy--");
	}
	@Test
	public void test1(){
		System.out.println("--test1--");
	}
	@Test
	public void test2(){
		System.out.println("--test2--");
		throw new NoteException("模拟异常");
	}
	@Test
	public void test3(){
		System.out.println("--test3--");
		String actual="hello";
		//断言
//		Assert.assertNotNull(actual);
//		Assert.assertFalse(actual.length()>0);
		Assert.assertEquals("Hello", actual);
	}
}
