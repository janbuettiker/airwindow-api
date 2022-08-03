package ch.hftm.api.logic;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.quartz.DailyTimeIntervalScheduleBuilder;
import org.quartz.DateBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.quartz.TimeOfDay;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.DateBuilder.IntervalUnit;

import ch.hftm.api.models.Task;
import ch.hftm.api.models.Window;
import ch.hftm.api.models.enums.StateType;

@ApplicationScoped
public class ScheduleService {

    @Inject
    org.quartz.Scheduler quartzScheduler;

    public void createTimedEvent(Integer interval, Long wId, StateType st) throws SchedulerException {

        JobDataMap jobData = new JobDataMap();
        jobData.put("windowId", wId);
        jobData.put("state", st);

        JobDetail job = JobBuilder.newJob(MyJob.class)
                .withDescription("timed Event")
                .setJobData(jobData)
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .startAt(DateBuilder.futureDate(interval, IntervalUnit.MINUTE))
                .forJob(job)
                .build();

        quartzScheduler.scheduleJob(job, trigger);
    }

    public void createDailyScheduledEvent(Integer hour, Integer minute, Long wId, StateType st)
            throws SchedulerException {

        JobDataMap jobData = new JobDataMap();
        jobData.put("windowId", wId);
        jobData.put("state", st);

        JobDetail job = JobBuilder.newJob(MyJob.class)
                .withDescription("scheduled Event")
                .setJobData(jobData)
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withSchedule(DailyTimeIntervalScheduleBuilder
                        .dailyTimeIntervalSchedule()
                        .onEveryDay()
                        .startingDailyAt(TimeOfDay.hourAndMinuteOfDay(hour, minute)))
                .forJob(job)
                .build();

        quartzScheduler.scheduleJob(job, trigger);

    }

    @Transactional
    void performTask(Long wId, StateType st, String tt) {

        Task task = new Task(wId, st, tt);
        task.persist();

        // TODO: Logic to update Window State upon Task creation (a bit cleaner than
        // this)
        Window.findWindowById(wId).desiredState = st;

    }

    // A new instance of MyJob is created by Quartz for every job execution
    public static class MyJob implements Job {

        @Inject
        ScheduleService scheduleService;

        public void execute(JobExecutionContext context) throws JobExecutionException {
            scheduleService.performTask(
                    (Long) context.getMergedJobDataMap().get("windowId"),
                    (StateType) context.getMergedJobDataMap().get("state"),
                    context.getJobDetail().getDescription());
        }
    }

}
