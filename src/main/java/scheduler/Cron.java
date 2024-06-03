/* scheduler */

package scheduler;

import bitget.App;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Cron {

    private static final Logger log = LoggerFactory.getLogger(Cron.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    public static void main(String[] args) {
        Cron tasks = new Cron();
        tasks.startScheduler();
    }

    public void startScheduler() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        Runnable task = new Runnable() {
            @Override
            public void run() {
                reportCurrentTime();
                try {
                    App app = new App();
                    app.run();
                } catch (Exception e) {
                    log.error("Error running the Bitget App", e);
                }
            }
        };

        scheduler.scheduleAtFixedRate(task, 0, 300, TimeUnit.SECONDS);
    }

    public void reportCurrentTime() {
        log.info("The time is now {}", dateFormat.format(new Date()));

        // Measure RAM used
        Runtime runtime = Runtime.getRuntime();
        runtime.gc();
        long memory = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Used memory in bytes: " + memory);
        System.out.println("Used memory in megabytes: " + memory / (1024L * 1024L));
        System.out.println("reported time from cronBitGet");
    }
}