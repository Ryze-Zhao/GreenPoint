package com.zhaolearn.green;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class UpdateSystemTime {

    public static void main(String[] args) {
        updateSysDateTime();
    }

    /**
     * 自动将系统时间往前设置一天
     */
    public static void updateSysDateTime() {
        try {
            SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
            Date beginDate = new Date();
            Calendar date = Calendar.getInstance();
            date.setTime(beginDate);
            date.set(Calendar.DATE, date.get(Calendar.DATE) - 1);
            String dataStr_ = dft.format(date.getTime());

            String osName = System.getProperty("os.name");
            // Window 系统
            if (osName.matches("^(?i)Windows.*$")) {
                String cmd;
                // 格式：yyyy-MM-dd
                cmd = " cmd /c date " + dataStr_;
                Runtime.getRuntime().exec(cmd);
                // 格式 HH:mm:ss
//                cmd = " cmd /c time " + timeStr_;
//                Runtime.getRuntime().exec(cmd);
//                System.out.println("windows 时间修改");
            } else if (osName.matches("^(?i)Linux.*$")) {
                // Linux 系统 格式：yyyy-MM-dd HH:mm:ss   date -s "2017-11-11 11:11:11"
                FileWriter excutefw = new FileWriter("/usr/updateSysTime.sh");
                BufferedWriter excutebw = new BufferedWriter(excutefw);
//                excutebw.write("date -s \"" + dataStr_ +" "+ timeStr_ +"\"\r\n");
                excutebw.close();
                excutefw.close();
                String cmd_date = "sh /usr/updateSysTime.sh";
                Runtime.getRuntime().exec(cmd_date);
//                System.out.println("cmd :" + cmd_date + " date :" + dataStr_ +" time :" + timeStr_);
                System.out.println("linux 时间修改");
            } else {
                System.out.println("操作系统无法识别");
            }
        } catch (IOException e) {
            e.getMessage();
        }
    }

}

