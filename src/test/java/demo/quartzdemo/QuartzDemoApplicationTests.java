package demo.quartzdemo;

import demo.quartzdemo.job.SimpleJob;
import org.junit.jupiter.api.Test;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class QuartzDemoApplicationTests {

    @Autowired
    Scheduler scheduler;

    @Test
    void contextLoads() {
    }


    @Test
    void simpleJobRun() throws SchedulerException, IOException {
        // 创建任务
        JobKey jobKey = new JobKey("simpleJob", "default");
        JobDetail jobDetail = JobBuilder.newJob(SimpleJob.class)
                .withIdentity(jobKey)
                .build();

        // 创建触发器
        TriggerKey triggerKey = new TriggerKey("simpleTrigger", "default");
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerKey)
                .withSchedule(CronScheduleBuilder.cronSchedule("0/20 * * * * ?"))
                .build();


        scheduler.deleteJob(jobKey);

        scheduler.scheduleJob(jobDetail, trigger);


        System.in.read();
    }

}
