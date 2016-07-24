20150831开始
梁建全：liangjq@tarena.com.cn
1、项目简介
	云笔记系统
	笔记本管理、笔记管理、回收站管理、分享和收藏管理、活动管理等
2、采用的技术体系(Web)
	tomcat(web服务器)
	springmvc(请求接受分发，前端控制)
	SpringIOC,AOP(管理对象及其关系，解耦)
	jquery(JS框架，简化JS+ajax开发)
	fckeditor(js编辑器)
	run_prettify(代码高亮显示)
	ibatis(MyBatis，数据库操作)
	mysql(数据库服务器)
	
	a、所有界面采用HTML
		响应速度快
	b、所有请求使用Ajax处理
		不刷新页面，局部更新，提升用户体验
	c、所有请求采用json结果响应
		/cost/list.do-->DispatcherServlet
		-->CostListController
		-->CostService.find()-->CostDao
		-->将find结果以json方式响应
		1）引入Jackson开发包
		2）将结果从Controller方法返回
		3）在Controller方法前添加@ResponseBody注解
		
		======数据库导入======
		//设置客户端及连接编码
		set names utf8; //本次使用有效
		source sql文件路径; //导入sql文件
		use cloud_note; //进入cloud_note库
		show tables; //显示库里的表信息

		数据库关系结构：参考E-R模型图
		
3、登录功能的实现
	服务器端：SpringMVC，SpringIOC，MyBatis
		a、了解需求和数据库
			--熟悉相关数据表
			--熟悉功能需求
				（用户输入用户名和密码,然后点击登录按钮,服务器检查正确性,正确进入edit.html;错误在log_in.html提示错误信息）
		b、详细设计分析
			/user/login.do(携带用户名密码)
			-->DispatcherServlet
			-->UserLoginController.execute()
			--UserService.checkLogin()
			-->UserDao-->cn_user表
			--返回json结果
		c、
			1)UserDao(Spring+MyBatias)
			2)UserService
			3)UserLoginController
			4)配置请求和响应处理流程
			5)测试
	客户端：js+ajax
		--发送Ajax请求
			由登录按钮单击触发，携带用户名密码
		--响应数据
			返回的json数据结构，成功跳转到edit.html；失败显示错误信息
			
		绑定事件处理函数，发送ajax请求
		编写ajax回调函数，解析json结果
		更新页面显示
			
			
			
		统一服务器返回JSON格式
		{
		atatus:
		msg:
		data:
		}
		
1、采用HTTP Basic Authentication HTTP基础身份认证模式
	a、会将身份信息放入HTTP协议的head
	b、将身份信息采用Base64算法处理
	c、将身份信息采用"信息1:信息2"
2、注册
	
	
spring-test测试工具
	基于junit4使用
	引入spring-test.jar（可以模拟HTTP请求，接收响应结果）
	

1、笔记本列表显示 PowerDesign
	！！！！！！公司里，不允许随意安装盗版软件！！！！！！
	a、了解需求	cn_notebook的结构
	b、设计实现 visio rose
		服务器端：
			/notebook/loadbooks.do
			-->DispatcherServlet
			-->LoadBooksController.execute
			-->BooksService.loadBooksByUserId
			-->NoteBookDao
			-->cn_notebook(查询)
			-->返回NoteResult结构的json结果
		客户端：
			-->发送ajax【一进入页面就自动发送】
				需要用户提交UserId（从Cookie取）
			-->ajax回调处理
				{
					status:0,
					msg:"加载成功",
					data:[{},{},{}]
				}
				解析data数据，以列表形式显示到笔记本区域

2、=====笔记列表显示=====
	/note/loadnotes.do
	-->LoadNotesController.execute
	-->NoteService.loadNotesByBookId
	-->NoteDao-->cn_note(查询)
	-->返回NoteResult结构的json数据

	点击笔记本li时发送ajax请求
	提交笔记本ID
	回调函数解析返回的json结果显示成笔记li列表

	$("#book_list").on("click","li",fn)
	
	笔记本创建按钮处理
/notebook/add.do
-->AddBookController.execute
-->BookService.create
-->NoteBookDao.save
-->返回NoteResult格式的JSON

/note/add.do
-->AddNoteController.execute
-->NoteService
-->NoteDao-->cn_note
--返回NoteResult结构的json

16-07-07
笔记相关的操作
1、笔记删除
2、笔记分享
3、笔记查询
09-10am over

1、笔记本的删除
2、笔记本的修改
0911pm over

//修改需要同步到分享表单


SpringAOP
1.Spring事务管理(基于AOP)
	a.什么是事务,有什么作用
		事务有提交和回滚,也就是确认或撤销前面执行的insert,update,delete SQL语句
		事务是一组操作，可以保障这组操作的完整性
		
	b.JDBC技术如何控制事务
			//关闭自动提交
			public void f1(){
				dao1.save();
				dao2.update();
			}
			//提交事务
			事务控制的两种情况
			
			（1）、一次请求中访问多条SQL
			try{
				//关闭自动提交
				conn.setAutoCommit(false);
				f1();
				//提交事务
				conn.commit();
			}catch(){
				//回滚事务
				conn.rollback();
			}
			（2）、只操作一次SQL，但是有其他操作
			上传图片事务
			try{
				conn.setAutoCommit(false);
				图片路径写入表->insert
				图片保存到磁盘->文件操作
				conn.commit();
			}catch{
				conn.rollback();
			}
	c.Spring 如何控制事务
		<1>.通过配置控制事务(声明式事务管理)
			需要XML和注解结合配置，简单方便，控制灵活
			JDBC->DataSourceTransactionManager
			Hibernate->HibernateTransactionManager
			(1)--在spring配置文件声明定义DataSourceTransactionManager
			(2)--开启事务注解配置
				<tx:annotation-driven/>
			(3)--在目标组件类前或方法前添加(方法或者类前)
				@Transactional注解
				<!-- 事务管理 -->
				<bean id="tx"
					class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
					<property name="dataSource" ref="dbcp"></property>
				</bean>
				<!-- 开启事务注解@Transactional -->
				<tx:annotation-driven transaction-manager="tx" />
				
		<2>.使用xml配置
			只需要XML配置，复杂度高，使用复杂，控制不灵活
			<!-- 事务管理 -->
			<bean id="tx"
				class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
				<property name="dataSource" ref="dbcp"></property>
			</bean>
			<!-- 遇到哪些方法进行事务管理 -->
			<tx:advice id="txAdvice" transaction-manager="tx">
				<tx:attributes>
					<tx:method name="load" read-only="true"/>
					<tx:method name="add"/>
					<tx:method name="update*"/>
					<tx:method name="*"/>
					<!-- 不写的方法不使用事务管理 -->
				</tx:attributes>
			</tx:advice>
			<!-- 指定tx作用在那些组件上 -->
			<aop:config>
				<aop:pointcut expression="within(org.tarena.note.service..*)" id="target"/>
				<aop:advisor advice-ref="txAdvice" pointcut-ref="target"/>
			</aop:config>
		
	d.事务属性控制
		--readOnly:可读可写
		--rollBackFor:回滚
		--noRollBackFor:不回滚
			默认:运行时异常回滚，检查异常不会滚
		--propagation:事务传播(事务嵌套)
			默认REQUIRED
			Spring中常用事务类型：
				REQUIRED--支持前面事务，如果前面没有事务，就新建一个事务。这是最常见的选择。
				SUPPORTS--支持前面事务，如果前面没有事务，就以非事务方式执行。
				MANDATORY--支持前面事务，如果前面没有事务，就抛出异常。
				REQUIRES_NEW--新建事务，如果前面存在事务，把前面事务挂起。
				NOT_SUPPORTED--以非事务方式执行操作，如果前面存在事务，就把前面事务挂起。
				NEVER--以非事务方式执行，如果前面存在事务，则抛出异常。
				NESTED--如果前面存在事务，则在嵌套事务内执行。如果前面没有事务，则进行与REQUIRED类似的操作。拥有多个可以回滚的保存点，内部回滚不会对外部事务产生影响。只对DataSourceTransactionManager有效
		--isolation:事务隔离级别(并发问题)
			默认:DEFAULT,采用数据库的隔离级别
				READ_UNCOMMITED:查询和增删改查可同时进行，级别最低，安全性最低，处理能力强
				READ_COMMITED:提交后可读
				REPEATABLE_READ:可重复读，两次读之间不许操作
				SERIALIZABLE:级别最高，最安全，不能并发处理
			一般使用级别2、3，然后自己编写锁机制，即隔离级别+模拟锁隔离关键部分代码
2.Spring AOP应用
	事务管理组件
	try{
		//取消自动提交
		//调用组件
		//提交事务	
	}catch(){
		//回滚事务
	}
	a.AOP概念、为什么用AOP
		Aspect Oriented Programming面向方面/切面编程
			OOP:Object Oriented Programming面向对象编程
		将共通的处理逻辑从传统的业务逻辑中剥离出来，形成独立的组件，然后以配置的方式结合，这样可以将共通处理和传统处理解耦
		
		好处：解耦，重复利用，配置关系灵活
		
		事务管理，权限控制，日志跟踪，异常记录等功能都适合采用AOP处理
	b.AOP的使用方法
		分析需求，找出一下3个关键点
		<1>.切面(Aspect)，切面bean
			切面是封装共通业务，可以动态的作用到其他目标组件方法上
		<2>.切入点(PointCut)
			切入点是用于指定哪些组件是目标，在Spring中用一个表达式指定切入点
			
			(1).方法限定表达式
				execution(修饰符? 返回类型 方法名(参数列表) 抛出异常?)
				execution(public void f1(String s1) throws NumberFormatException)
				修饰符和异常可以省略
				返回类型:*;void;具体类型
				方法名:f1;load*;org.service.MyService.f1
				参数列表:具体类型，(..)表示零到多个参数
					execution(* add*(..))//以add开头的方法
					execution(* service.MyService.*(..))//service包下MyService组件中的所有方法
					execution(* service.*.*(..))//匹配service包下所有组件的所有方法
					execution(* service..*.*(..))//匹配service包中及所有子包中所有组件的所有方法
			(2).类型限定表达式
				within(组件类型)
					within(service.MyService)//service包下MyService组件中的所有方法
					within(service.*)//匹配service包下所有组件所有方法
					within(service..*)//匹配service包下及所有子包下所有组件所有方法
			(3)名称限定表达式
				bean(组将类型)
					bean("myservice")//匹配id为myservice的组件的所有方法
					bean("*service")//匹配id以service结尾的组件的所有方法
		<3>.通知(Advice)
			通知是指切面功能切入目标组件方法的时机
				try{
					前置通知->切面组件处理 <aop:before> @Before
					执行目标组件方法
					后置通知->切面组件处理 <aop:after-returning> @AfterReturning
				}catch(){
					异常通知->切面组件处理 <aop:after-throwing> @AfterRhrowing
				}finally{
					最终通知->切面组件处理 <aop:after> @After
				}
				环绕通知:功能为前置+后置通知 <aop:around> @Around
			
		例：为所有的Service追加事务管理功能
				事务管理->切面
				所有Service->目标->用切入点表达式
				开始+结束+抛异常作用->通知->作用时机
			
		例：切面：实现跟踪操作
				切入点：MyService方法
				通知:在执行MyService方法前插入功能
		(1).使用xml方式
			<!-- 将MyAopBean1作用到MyService上 -->
			<bean id="myservice" class="org.tarena.note.aop.impl.MyServiceImpl"></bean>
			<bean id="myaopbean1" class="org.tarena.note.aop.bean.MyAopBean1"></bean>
			<aop:config>
				<!-- 将myaopbean1作为切面 -->
				<aop:aspect ref="myaopbean1">
					<!-- 采用前置通知将切面切入到MyServiceImpl上 -->
					<aop:befor method="log" pointcut="within(org.tarena.note.aop.impl.MyServiceImpl)"/>
					<!-- 只作用在f1上 -->
					<!-- <aop:after method="log" pointcut="execution(* f1(..))"/> -->
				</aop:aspect>
			</aop:config>
			public class MyServiceImpl implements MyService {
				public void f1() {
					System.out.println("执行业务f1处理");
				}
				public void f2() {
					System.out.println("执行业务f2处理");
				}
			}
			public class MyAopBean1 {
				public void log(){
					System.out.println("跟踪日志操作");
				}
			}
		
		(2).使用注解方式
			<!-- 开启组件扫描 -->
			<context:component-scan base-package="org.tarena.note.aop"></context:component-scan>
			<!-- 开启AOP注解 -->
			<aop:aspectj-autoproxy></aop:aspectj-autoproxy>
			@Service("myservice")
			public class MyServiceImpl implements MyService {
				public void f1() {
					System.out.println("执行业务f1处理");
				}
				public void f2() {
					System.out.println("执行业务f2处理");
				}
			}
			@Component//扫描到Spring容器
			@Aspect//该组件定义成切面组件
			public class MyAopBean1 {
				@Before("within(org.tarena.note.aop.impl.MyServiceImpl)")
				public void log(){
					System.out.println("跟踪日志操作");
				}
			}
			

	1.Spring事务管理使用方法
	  --编程式事务管理(Java)
	  --*声明式事务管理(配置)
	  a.注解配置应用
	   --定义一个DataSourceTransactionManager组件
	   --开启事务注解@Transactional
	   <tx:annotation-driven transaction-manager="xx"/> 

	2.Spring AOP使用（注解）
		面向方面编程
		解决：共通业务与普通业务隔离，将共通业务封装成独立组件,然后用配置方式作用到普通业务组件方法上。
		a.通知(决定在哪个位置切入)
		b.方面(要切入的功能)
		c.切入点(要切哪些组件)
------------------------------------------
AOP应用[事务管理、日志记录]
1、要求：等程序出异常后，将异常信息写入日志文件
通知:出现异常时执行(异常通知)
方面:将异常对象写入文件
切入点:Controller within(org.tarena.note.web.controller..*)
-------------------------------------------
Log4j工具:日志工具
	static Logger logger=Logger.getLogger(TestLog4j.class);
	public static void main(String[] args) {
		logger.debug("调试信息");
		logger.info("普通信息");
		logger.warn("警告信息");
		logger.error("错误信息");
		logger.fatal("致命信息");		
	}
	 0914am over
	 
AOP实现原理
	AOP:Spring采用了动态代理技术生成一个新的组件，新组件重写原有组件方法，在新组件方法中调用方面组件和原目标组件功能
	优点:
	
	Spring框架内部有两种动态代理机制【为了保证不出错，一般采用CGLIB方式】
	--只适用于有接口组件
		采用JDK Proxy API生成代理类 java.lang.reflect.Proxy
		实现接口public class Proxyxxx implements 目标组件接口
	--适用于没有接口组件
		采用CGLIB技术生成动态代理类
		继承类方式 public class xxx extends 目标组件{}
		注意:如果一个类实现了接口，在代理时要采用CGLIB技术，否则会出错
		<aop:aspectj-autoproxy proxy-target-class="true"></aop:aspectj-autoproxy>
		
MyBatis动态SQL标签
	if,choose,for,where,set
	1.<if test="">xxx</if>
			<!-- 动态SQL，不定条件查询 -->
			<select id="find" parameterType="org.tarena.note.entity.Note"
				resultType="org.tarena.note.entity.Note">
				select * from cn_note
				<where><!-- 可以自动添加where字段也可以去除and/or字段 -->
					<if test="cn_note_id != null">
						cn_note_id=#{cn_note_id}
					</if>
					<if test="cn_notebook_id != null">
						and cn_notebook_id=#{cn_notebook_id}
					</if>
					<if test="cn_user_id != null">
						and cn_user_id=#{cn_user_id}
					</if>
					<if test="cn_note_status_id != null">
						and cn_note_status_id=#{cn_note_status_id}
					</if>
					<if test="cn_note_type_id != null">
						and cn_note_type_id=#{cn_note_type_id}
					</if>
					<if test="cn_note_title != null">
						and cn_note_title=#{cn_note_title}
					</if>
					<if test="cn_note_body != null">
						and cn_note_body=#{cn_note_body}
					</if>
					<if test="cn_note_create_time != null">
						and cn_note_create_time=#{cn_note_create_time}
					</if>
					<if test="cn_note_last_modify_time != null">
						and cn_note_last_modify_time=#{cn_note_last_modify_time}
					</if>
				</where>
				order by cn_note_last_modify_time desc
			</select>
			
			<!-- 动态更新，参数属性不为null就更新 -->
			<update id="update" parameterType="org.tarena.note.entity.Note">
				update cn_note
				<set>
					<if test="cn_notebook_id != null">
						cn_notebook_id=#{cn_notebook_id},
					</if>
					<if test="cn_user_id != null">
						cn_user_id=#{cn_user_id},
					</if>
					<if test="cn_note_status_id != null">
						cn_note_status_id=#{cn_note_status_id},
					</if>
					<if test="cn_note_type_id != null">
						cn_note_type_id=#{cn_note_type_id},
					</if>
					<if test="cn_note_title != null">
						cn_note_title=#{cn_note_title},
					</if>
					<if test="cn_note_body != null">
						cn_note_body=#{cn_note_body},
					</if>
					<if test="cn_note_create_time != null">
						cn_note_create_time=#{cn_note_create_time},
					</if>
					<if test="cn_note_last_modify_time != null">
						cn_note_last_modify_time=#{cn_note_last_modify_time},
					</if>
				</set>
				where cn_note_id=#{cn_note_id}
			</update>
	2.<choose>
				<when test=""></when>
				<when test=""></when>
				<otherwise></otherwise>
		 </choose>
	3、<foreach></foreach>
		<!-- 批量删除，使用foreach生成in条件 -->
		<delete id="delete">
			delete from cn_note where cn_note_id in
			<foreach collection="array" item="id" separator="," open="(" close=")" >
				<!-- array代表参数是数组 -->
				#{id}
			</foreach>
		</delete>
	
MyBatis关联映射
	将用关系的表的数据映射成对象关系
	
	1.对象关联属性Note-->Note.book
		(1)--与主对象一起加载(建议)
			<!-- 关联查询 -->
			<!-- 主对象一起加载 -->
			<select id="find1" resultMap="noteMap1">
				select * from cn_note note join
				cn_notebook book on(note.cn_notebook_id=book.cn_notebook_id)
			</select>
			<!-- 自定义映射关系 -->
			<resultMap id="noteMap1" type="org.tarena.note.entity.Note">
				<id property="cn_note_id" column="cn_note_id" />
				<result property="cn_notebook_id" column="cn_notebook_id" />
				<result property="cn_user_id" column="cn_user_id" />
				<result property="cn_note_status_id" column="cn_note_status_id" />
				<result property="cn_note_type_id" column="cn_note_type_id" />
				<result property="cn_note_title" column="cn_note_title" />
				<result property="cn_note_body" column="cn_note_body" />
				<result property="cn_note_create_time" column="cn_note_create_time" />
				<result property="cn_note_last_modify_time" column="cn_note_last_modify_time" />
				<!-- 其他属性省略 -->
				<!-- 关联属性book装载 -->
				<association property="book" javaType="org.tarena.note.entity.NoteBook"><!-- 对象关联 -->
					<id property="cn_notebook_id" column="cn_notebook_id" />
					<result property="cn_user_id" column="cn_user_id" />
					<result property="cn_notebook_type_id" column="cn_notebook_type_id" />
					<result property="cn_notebook_name" column="cn_notebook_name" />
					<result property="cn_notebook_desc" column="cn_notebook_desc" />
					<result property="cn_notebook_createtime" column="cn_notebook_createtime" />
					<!-- 其他属性省略 -->
				</association>
			</resultMap>
		(2)--单独发送SQL加载
			<!-- 单独的SQL加载 -->
			<select id="find2" resultMap="noteMap2">
				select * from cn_note
			</select>
			<resultMap type="org.tarena.note.entity.Note" id="noteMap2">
				<association property="book" javaType="org.tarena.note.entity.NoteBook"
					column="cn_notebook_id" select="load"><!-- column是下一级SQL的条件 -->
				</association>
			</resultMap>
			<select id="load" parameterType="string"
				resultType="org.tarena.note.entity.NoteBook">
				select * from cn_notebook where
				cn_notebook_id=#{cn_notebook_id};
			</select>
	2.集合关联属性NoteBook-->NoteBook.notes<List>
		(1)--与主对象一起加载(建议)
			<!-- 集合关系映射 -->
			<select id="find3" parameterType="string" resultMap="bookMap1">
				select * from cn_notebook book join cn_note note on (book.cn_notebook_id=note.cn_notebook_id) where book.cn_notebook_id=#{bookid}
			</select>
			<resultMap type="org.tarena.note.entity.NoteBook" id="bookMap1">
				<id property="cn_notebook_id" column="cn_notebook_id" />
				<result property="cn_user_id" column="cn_user_id" />
				<result property="cn_notebook_type_id" column="cn_notebook_type_id" />
				<result property="cn_notebook_name" column="cn_notebook_name" />
				<result property="cn_notebook_desc" column="cn_notebook_desc" />
				<result property="cn_notebook_createtime" column="cn_notebook_createtime" />
				<collection property="notes" javaType="java.util.List" ofType="org.tarena.note.entity.Note">
					<id property="cn_note_id" column="cn_note_id" />
					<result property="cn_notebook_id" column="cn_notebook_id" />
					<result property="cn_user_id" column="cn_user_id" />
					<result property="cn_note_status_id" column="cn_note_status_id" />
					<result property="cn_note_type_id" column="cn_note_type_id" />
					<result property="cn_note_title" column="cn_note_title" />
					<result property="cn_note_body" column="cn_note_body" />
					<result property="cn_note_create_time" column="cn_note_create_time" />
					<result property="cn_note_last_modify_time" column="cn_note_last_modify_time" />
				</collection>
			</resultMap>
		(2)--单独发送SQL加载
			<!-- 单独SQL加载 -->
			<select id="find4" parameterType="string" resultMap="bookMap2">
				select * from cn_notebook where cn_notebook_id=#{bookid}
			</select>
			<resultMap type="org.tarena.note.entity.NoteBook" id="bookMap2">
				<collection property="notes" javaType="java.util.List"
					ofType="org.tarena.note.entity.Note" column="cn_notebook_id" select="load"></collection>
			</resultMap>
			<select id="load" parameterType="string" resultType="org.tarena.note.entity.Note">
				select * from cn_note where cn_notebook_id=#{cn_notebook_id}		
			</select>
0915pm over