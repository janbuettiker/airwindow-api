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

        /**
         * Creates a one timed task which will execute the "performTask" function.
         * For example, "open window 1 in 15 minutes".
         * 
         * @param interval - How many minutes in the future should the task be
         *                 performed.
         * @param wId      - Window id
         * @param st       - StateType, so whether you want to open or close the window.
         * @throws SchedulerException
         */
        public void createTimedEvent(Integer interval, Long wId, StateType st) throws SchedulerException {

                // JobDataMap is needed to send data with the job for later use.
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

        /**
         * Creates a sheduled task. As of now, this task will run
         * each day of the week during the configured time slot.
         * Hour and minute will define the timeslot.
         * For example, "close windows 1 at 12:30 each day"
         * 
         * @param hour   - At what hour should the task run.
         * @param minute - At which minute.
         * @param wId    - Window id
         * @param st     - StateType, so whether you want to open or close the window.
         * @throws SchedulerException
         */
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

        /**
         * The actual task that should be performed when it gets triggered.
         * We will persist the task to have a log and also change the window state to
         * the configured StateType.
         * 
         * @param wId - Window id
         * @param st  - StateType
         * @param tt  - TaskType, one-time or scheduled
         */
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

                // Here we extract the data in the JobDataMap to perform our task
                public void execute(JobExecutionContext context) throws JobExecutionException {
                        scheduleService.performTask(
                                        (Long) context.getMergedJobDataMap().get("windowId"),
                                        (StateType) context.getMergedJobDataMap().get("state"),
                                        context.getJobDetail().getDescription());
                }
        }

}
