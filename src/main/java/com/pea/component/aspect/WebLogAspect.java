package com.pea.component.aspect;

import cn.hutool.json.JSONUtil;
import com.pea.business.sys.domain.SysOperationLog;
import com.pea.business.sys.domain.SysUser;
import com.pea.common.annotation.SysLogInterface;
import com.pea.common.enums.DelStatusEnums;
import com.pea.common.enums.StatusEnums;
import com.pea.common.manager.AsyncManager;
import com.pea.common.manager.factory.AsyncFactory;
import com.pea.common.utils.IpUtil;
import com.pea.common.utils.SecurityUtil;
import com.pea.common.utils.ServletUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import java.lang.reflect.Method;
import java.util.Map;

@Slf4j
@Aspect
@Component
public class WebLogAspect {

	/**
	 * 配置织入点
	 */
	@Pointcut("@annotation(com.pea.common.annotation.SysLogInterface)")
	public void logPointCut() {

	}

	/**
	 * 处理完请求后执行
	 * @param joinPoint 切点
	 */
	@AfterReturning(pointcut = "logPointCut()", returning = "jsonResult")
	public void doAfterReturning(JoinPoint joinPoint, Object jsonResult) {
		handleLog(joinPoint, null, jsonResult);
	}

	/**
	 * 拦截异常操作
	 * @param joinPoint 切点
	 * @param e 异常
	 */
	@AfterThrowing(value = "logPointCut()", throwing = "e")
	public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
		handleLog(joinPoint, e, null);
	}

	protected void handleLog(final JoinPoint joinPoint, final Exception e,
			Object jsonResult) {
		try {
			// 获得注解
			SysLogInterface controllerLog = getAnnotationLog(joinPoint);
			if (controllerLog == null) {
				return;
			}

			// 获取当前的用户
			SysUser loginUser = SecurityUtil.getSysUser();

			// *========数据库日志=========*//
			SysOperationLog operLog = new SysOperationLog();
			operLog.setStatus(StatusEnums.ENABLE.getCode());
			operLog.setIsDeleted(DelStatusEnums.DISABLE.getCode());
			// 请求的地址
			String ip = IpUtil.getIp(ServletUtils.getRequest());
			operLog.setReqIp(ip);
			// 返回参数
			operLog.setResp(JSONUtil.toJsonStr(jsonResult));

			if (loginUser != null) {
				operLog.setOperName(loginUser.getUserName());
			}

			if (e != null) {
				operLog.setStatus(StatusEnums.DISABLE.getCode());
				operLog.setErrorMsg(StringUtils.substring(e.getMessage(), 0, 2000));
			}
			// 设置方法名称
			String className = joinPoint.getTarget().getClass().getName();
			String methodName = joinPoint.getSignature().getName();
			operLog.setMethod(className + "." + methodName + "()");
			// 设置请求方式
			operLog.setRequestMethod(ServletUtils.getRequest().getMethod());
			// 处理设置注解上的参数
			getControllerMethodDescription(joinPoint, controllerLog, operLog);
			// 保存数据库
			AsyncManager.me().execute(AsyncFactory.recordOper(operLog));
		}
		catch (Exception exp) {
			// 记录本地异常日志
			log.error("==前置通知异常==");
			log.error("异常信息:{}", exp.getMessage());
		}
	}

	/**
	 * 获取注解中对方法的描述信息 用于Controller层注解
	 * @param log 日志
	 * @param sysOperationLog 操作日志
	 */
	public void getControllerMethodDescription(JoinPoint joinPoint, SysLogInterface log,
			SysOperationLog sysOperationLog) {
		// 设置action动作
		sysOperationLog.setBusinessType(log.businessType().getCode());
		// 设置标题
		sysOperationLog.setDescription(log.title());
		// 是否需要保存request，参数和值
		if (log.isSaveRequestData()) {
			// 获取参数的信息，传入到数据库中。
			setRequestValue(joinPoint, sysOperationLog);
		}
	}

	/**
	 * 获取请求的参数，放到log中
	 * @param sysOperationLog 操作日志
	 */
	private void setRequestValue(JoinPoint joinPoint, SysOperationLog sysOperationLog) {
		String requestMethod = sysOperationLog.getRequestMethod();
		if (HttpMethod.PUT.name().equals(requestMethod)
				|| HttpMethod.POST.name().equals(requestMethod)) {
			String params = argsArrayToString(joinPoint.getArgs());
			sysOperationLog.setReqParam(StringUtils.substring(params, 0, 2000));
		}
		else {
			Map<?, ?> paramsMap = (Map<?, ?>) ServletUtils.getRequest()
					.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
			sysOperationLog.setReqParam(StringUtils.substring(paramsMap.toString(), 0, 2000));
		}
	}

	/**
	 * 是否存在注解，如果存在就获取
	 */
	private SysLogInterface getAnnotationLog(JoinPoint joinPoint) {
		Signature signature = joinPoint.getSignature();
		MethodSignature methodSignature = (MethodSignature) signature;
		Method method = methodSignature.getMethod();

		if (method != null) {
			return method.getAnnotation(SysLogInterface.class);
		}
		return null;
	}

	/**
	 * 参数拼装
	 */
	private String argsArrayToString(Object[] paramsArray) {
		StringBuilder params = new StringBuilder();
		if (paramsArray != null) {
			for (Object o : paramsArray) {
				if (!isFilterObject(o)) {
					Object jsonObj = JSONUtil.toJsonStr(o);
					params.append(jsonObj.toString()).append(" ");
				}
			}
		}
		return params.toString().trim();
	}

	/**
	 * 判断是否需要过滤的对象。
	 *
	 * @param o 对象信息。
	 * @return 如果是需要过滤的对象，则返回true；否则返回false。
	 */
	public boolean isFilterObject(final Object o) {
		return o instanceof MultipartFile || o instanceof HttpServletRequest
				|| o instanceof HttpServletResponse;
	}

}
