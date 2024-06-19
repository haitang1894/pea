package com.pea.common.manager.factory;

import com.pea.business.sys.domain.SysOperationLog;
import com.pea.business.sys.service.SysOperationLogService;
import com.pea.common.utils.IpUtil;
import com.pea.common.utils.SpringUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.TimerTask;

@Slf4j
public class AsyncFactory {

	/**
	 * 操作日志记录
	 * @param operLog 操作日志信息
	 * @return 任务task
	 */
	public static TimerTask recordOper(final SysOperationLog operLog) {
		return new TimerTask() {
			@Override
			public void run() {
				String address = IpUtil.getAddress(operLog.getReqIp());
				// 远程查询操作地点
				operLog.setOperLocation(address);
				SysOperationLogService operationLogService = SpringUtils.getBean(SysOperationLogService.class);
				operationLogService.save(operLog);
			}
		};
	}

}