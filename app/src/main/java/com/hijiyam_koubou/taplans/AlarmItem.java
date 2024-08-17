package com.hijiyam_koubou.taplans;

/**日ごとのアラーム時刻**/
public class AlarmItem {

    public String dateStr ="";
    public String timeStr ="";
    public Boolean isTarget = false;
    public int DayOfTheWeek;
    public String comment ="";
    /**
     * 変更不可
     * */
    public Boolean isNotChangeable = false;

    public String[] dowDisplay= {};

}
