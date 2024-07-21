package com.hijiyam_koubou.taplans;

import java.util.ArrayList;

public class ByDayOfTheWeekStr {

    public String sunday="";
    public String monday="";
    public String tuesday="";
    public String wednesday="";
    public String thursday="";
    public String friday="";
    public String saturday="";
    public String holiday="";

    public ArrayList<String> DOTWPosition= new ArrayList<String>();

    public ByDayOfTheWeekStr(){
        DOTWPosition= new ArrayList<String>();
        DOTWPosition.add("holiday");
        DOTWPosition.add("sunday");
        DOTWPosition.add("monday");
        DOTWPosition.add("tuesday");
        DOTWPosition.add("wednesday");
        DOTWPosition.add("thursday");
        DOTWPosition.add("friday");
        DOTWPosition.add("saturday");
    }

}
