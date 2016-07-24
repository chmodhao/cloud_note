package org.tarena.note.aop.impl;

import org.springframework.stereotype.Service;
import org.tarena.note.aop.MyService;
@Service("myservice")
public class MyServiceImpl implements MyService {

	public void f1() {
		System.out.println("执行业务f1处理");
	}

	public void f2() {
		System.out.println("执行业务f2处理");
	}

}
