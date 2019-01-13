/**
 * 版权：zcc
 * 作者：c0z00k8
 * @data 2018年8月30日
 */
package com.group.wallet.job;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import com.group.wallet.service.wal.HomeService;

/**
 * @author c0z00k8
 *
 */
@Component
@EnableScheduling
public class ScheduledTasks {

	@Autowired
	private HomeService homeService;
	
	/**
	 * 计算pos是否达到激活/刷满返现条件。
	 */
//	@Scheduled(cron = "0 0/5 * * * ?")  //cron接受cron表达式，根据cron表达式确定定时规则
    public void testCron() {
        System.out.println("time:"+new Date());
        try {
			homeService.getCompPos();
		} catch (Exception e) {
			System.out.println("`````````````定时任务执行出错···testCron·········"+e.getMessage());
			e.printStackTrace();
		}
    }

	
}
