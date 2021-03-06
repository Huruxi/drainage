package com.drainage.config;

import com.drainage.service.IActiveCodeService;
import com.drainage.service.IRebateFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *  定时任务
 * @author hrd <br/>
 * @date 2020/12/8
 */
@Component
public class ScheduleJob {

    @Autowired
    private IRebateFormService rebateFormService;

    @Autowired
    private IActiveCodeService activeCodeService;

    /**
     * 每分钟返利
     */
    @Scheduled(cron = "0 */1 * * * ?")
    public void perMinuteRebate(){
        rebateFormService.loginRebate();
    }


    /**
     * 检测激活码是否离线
     */
    @Scheduled(cron = "*/58 * * * * ?")
    public void detectActiveCodeOffline(){
        activeCodeService.detectActiveCodeOffline();
    }

}
