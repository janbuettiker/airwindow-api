package ch.hftm.api.logic;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.quartz.DateBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.DateBuilder.IntervalUnit;

import ch.hftm.api.models.Task;

@ApplicationScoped
public class TimedEvent {

    @Inject
    org.quartz.Scheduler quartzScheduler;

    public void createTimedEvent(Integer interval) throws SchedulerException {

        JobDetail job = JobBuilder.newJob(MyJob.class)
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .startAt(DateBuilder.futureDate(interval, IntervalUnit.MINUTE))
                .forJob(job)
                .build();

        quartzScheduler.scheduleJob(job, trigger);
    }

    @Transactional
    void performTask() {
        Task task = new Task();
        task.persist();

    }

    // A new instance of MyJob is created by Quartz for every job execution
    public static class MyJob implements Job {

        @Inject
        TimedEvent timedEvent;

        public void execute(JobExecutionContext context) throws JobExecutionException {
            timedEvent.performTask();
        }

    }

}
