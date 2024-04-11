package com.pea.common.manager;

import com.pea.common.utils.SpringUtils;
import com.pea.common.utils.Threads;

import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * AsyncManager 类主要用于管理异步操作任务的调度与执行
 */
public class AsyncManager {

	/**
	 * 定义一个常量，表示所有异步操作默认会有一个10毫秒的延迟
	 */
	private final int OPERATE_DELAY_TIME = 10;

	/**
	 * 使用Spring框架的SpringUtils工具类从IoC容器中获取一个ScheduledExecutorService实例，
	 * 这个线程池将会用来调度和执行异步任务
	 */
	private final ScheduledExecutorService executor = SpringUtils.getBean("scheduledExecutorService");

	/**
	 * 实现单例模式，保证在整个应用中AsyncManager只有一个实例
	 */
	private AsyncManager() {
	}

	/**
	 * 静态内部持有唯一实例，并提供公共静态方法me()来获取这个单例
	 */
	private static final AsyncManager me = new AsyncManager();

	public static AsyncManager me() {
		return me;
	}

	/**
	 * 提供一个execute方法，接受一个TimerTask类型的参数，该方法用于调度任务在OPERATE_DELAY_TIME毫秒后执行
	 * 注意这里实际上是使用ScheduledExecutorService的schedule方法来实现定时任务的功能
	 * @param task
	 */
	public void execute(TimerTask task) {
		executor.schedule(task, OPERATE_DELAY_TIME, TimeUnit.MILLISECONDS);
	}

	/**
	 * 提供一个shutdown方法，用于停止调度器及其相关线程池的运行
	 * 调用Threads工具类的shutdownAndAwaitTermination方法确保线程池被正确关闭并且等待所有任务完成或超时
	 */
	public void shutdown() {
		Threads.shutdownAndAwaitTermination(executor);
	}
}
