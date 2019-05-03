package zkxy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import zkxy.model.User;

@Aspect
@Component
public class RoleAspect {

	@Pointcut("@annotation(zkxy.Role)")
	public void checkLogin() {

	}

	@Around("checkLogin() ")
	public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {

		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		HttpSession session = request.getSession();

		Object user = (User) session.getAttribute("user");
//		if (joinPoint.getArgs()!= null ) {
//			joinPoint.getArgs()[0] = user;
//
//		}
		if (user == null) {

			throw new Exception("未登录");
		} else {
			return joinPoint.proceed(joinPoint.getArgs());
		}

	}

	/**
	 * 记录HTTP请求结束时的日志
	 */
	// @After("checkLogin()")
	// public void doAfter() {
	// // ServletRequestAttributes attributes = (ServletRequestAttributes)
	// // RequestContextHolder.getRequestAttributes();
	// // HttpServletRequest request = attributes.getRequest();
	// // logger.info("url = {} end of execution", request.getRequestURL());
	// }

	// /**
	// * 获取返回内容
	// *
	// * @param object
	// */
	// @AfterReturning(returning = "object", pointcut = "checkLogin()")
	// public void doAfterReturn(Object object) {
	// // logger.info("response={}",object.toString());
	// }

	// @AfterThrowing(throwing = "ex", pointcut = "checkLogin()")
	// public void doException(JoinPoint point, Throwable ex) {
	// System.out.println("目标方法中抛出的异常:" + ex);
	// System.out.println("模拟Advice对异常的修复...");
	//
	// }
}
