package com.check.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gaoxueyong
 */
@Component
public class CheckTxtScheduled {

    private final Logger log = LoggerFactory.getLogger(CheckTxtScheduled.class);

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    //要查询的路径
    private String filePath = "D:\\export\\txt";
    //要查询字符串
    private String splitStr = "\\$";


    // 1000 = 1s
    // 1000 * 60 * 5 = 5min
//    @Scheduled(cron = "0 0/2 * * * ?")
    @Scheduled(cron = "50 */2 * * * ?")
//    @Scheduled(cron = "* * * * * ?")
    public void run() throws Exception {
        log.info("The time is now {}", dateFormat.format(new Date()));
        File file = new File(filePath);
        if (file.isDirectory()) {
            File[] list = file.listFiles();
            for (File fe : list) {
                boolean flag = true;
                if (fe.isFile()) {
                    InputStreamReader reader = new InputStreamReader(new FileInputStream(fe));
                    BufferedReader bf = new BufferedReader(reader);
                    String line = bf.readLine();
                    while (line != null) {
                        String[] tempAr = line.split(splitStr);
                        if (tempAr.length != 10) {
                            flag = false;
                            break;
                        }
//

                        line = bf.readLine();
                    }
                    bf.close();
                    reader.close();
                }
//                删除匹配不到的文件
//                if (flag) {
////                   System.out.println(fe.getName());
//                    fe.delete();
//                }

                if(!flag){
                    //匹配到的文件
                    System.out.println(fe.getName());
                }
            }
        }
    }


}
