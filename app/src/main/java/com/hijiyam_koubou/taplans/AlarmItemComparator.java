package com.hijiyam_koubou.taplans;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

public class AlarmItemComparator  implements Comparator<AlarmItem> {
    @Override
    public int compare(AlarmItem o1, AlarmItem o2) {
        String[] dayStr = o1.dateStr .split("/");
        int val1 = Integer.parseInt(dayStr[0])*10000+Integer.parseInt(dayStr[1])*100+Integer.parseInt(dayStr[2]);
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.YEAR, Integer.parseInt(dayStr[0])); //年を設定
//        calendar.set(Calendar.MONTH, Integer.parseInt(dayStr[1])); //月を設定(※MONTHは0始まり 0→Jan、1→Feb、...になる)
//        calendar.set(Calendar.DATE, Integer.parseInt(dayStr[2]));//日を設定
        dayStr = o2.dateStr .split("/");
        int val2 = Integer.parseInt(dayStr[0])*10000+Integer.parseInt(dayStr[1])*100+Integer.parseInt(dayStr[2]);

//        Date stringToDate1 = null;
//        Date stringToDate2 = null;
//        SimpleDateFormat sdf = new SimpleDateFormat(o1.dateStr);
//        try {
//            stringToDate1 = sdf.parse(o1.dateStr);
//            stringToDate1.
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
//        try {
//            stringToDate2 = sdf.parse(o2.dateStr);
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
//        return val1 < val2 ? -1 : 1;
        if(val1 < val2) {
            return -1;
        } else if(val1 > val2) {
            return 1;
        } else {
            return o1.dateStr.compareTo(o2.dateStr );
        }
   }
}


// 配列やListをソートする方法      https://www.sejuku.net/blog/14155
