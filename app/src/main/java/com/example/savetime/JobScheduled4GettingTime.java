package com.example.savetime;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class JobScheduled4GettingTime extends JobService {

    private static final String TAG = "JobScheduled4GettingTime";
    private static boolean jobCancelled = false;
    private static int isRun;


    @Override
    public boolean onStartJob(JobParameters jobParameters) {
//        Log.d(TAG, "onStartJob run");
        doBackgroundWork(jobParameters);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        jobCancelled = true;
        return true;
    }

    public void doBackgroundWork(JobParameters jobParameters) {

//        Log.d(TAG, "isRan is " + isRan);
        while ((isRun < 1)) {
            String path = getExternalCacheDir().getAbsolutePath();
//            Log.d(TAG , "path is : " + path);
            File file = new File(path + "/sample.txt");
            isRun = 1;

            new Thread(new Runnable() {
                @Override
                public void run() {

                    for (int i = 0; ; i++) {

//                        Log.d("jobScheduler4Loop" , "run : " + i);

                        Calendar calendar = Calendar.getInstance();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                        String time = simpleDateFormat.format(calendar.getTime());

                        try {
                            try (Writer writer = new FileWriter(file, true);
                                 BufferedWriter bw = new BufferedWriter(writer)) {
                                String content = time + "\n";
                                bw.write(content);
                            } catch (IOException e) {
                                Log.e("Append methode", e.getMessage());
                            }
                            Thread.sleep(10000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
    }

}
