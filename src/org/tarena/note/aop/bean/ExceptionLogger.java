package org.tarena.note.aop.bean;

import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ExceptionLogger {
	private Logger logger=Logger.getLogger(ExceptionLogger.class);
	@AfterThrowing(throwing="ex",pointcut="within(org.tarena.note.web.controller..*)")
	public void log(Exception ex){
		//记录异常信息
//		try {
//			FileWriter out=new FileWriter("E:\\error.log");
//			PrintWriter pw=new PrintWriter(out,true);
//			pw.print("---------------------异常信息如下---------------------"+'\n');
//			ex.printStackTrace(pw);
//			pw.print("------------------------------------------------------"+'\n');
//			pw.flush();
//			pw.close();
//			out.close();
//		} catch (IOException e) {
//			
//		}
		logger.error("发生异常:"+ex);
		StackTraceElement[] st=ex.getStackTrace();
		for(StackTraceElement e:st){			
			logger.error(e.toString());
		}
	}
}
