package org.tarena.note.aop.bean;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component//扫描到Spring容器
@Aspect//该组件定义成切面组件
public class MyAopBean1 {
	@Before("within(org.tarena.note.aop.impl.MyServiceImpl)")
	public void log(){
		System.out.println("跟踪日志操作");
	}
}
