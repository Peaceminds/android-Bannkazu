package com.drolma.bannkazu.transwarp;

/**
 * Created by Drolma on 16/5/19.
 */
public class modelTool {

    public int _id;
    public String dateTime;
    public String pScore1;
    public String pScore2;
    public String pScore3;
    public String pScore4;
    public String pScore5;
    public int isLLWin;
    public int isAutoInsert;
    public int llt1;
    public int llt2;
    public int llt3;
    public int llt4;
    public int llt5;
    public int wit1;
    public int wit2;
    public int wit3;
    public int wit4;
    public int wit5;


    public modelTool() {

    }

    public modelTool(String dateTime, String pScore1, String pScore2, String pScore3, int isLLWin, int isAutoInsert, int[] llTimes, int[] winTimes) {
        this.dateTime = dateTime;
        this.pScore1 = pScore1;
        this.pScore2 = pScore2;
        this.pScore3 = pScore3;
        this.isLLWin = isLLWin;
        this.isAutoInsert = isAutoInsert;
        this.llt1 = llTimes[0];
        this.llt2 = llTimes[1];
        this.llt3 = llTimes[2];
        this.wit1 = winTimes[0];
        this.wit2 = winTimes[1];
        this.wit3 = winTimes[2];
    }


    public modelTool(String dateTime, String pScore1, String pScore2, String pScore3, String pScore4, String pScore5, int isLLWin, int isAutoInsert, int[] llTimes, int[] winTimes) {
        this.dateTime = dateTime;
        this.pScore1 = pScore1;
        this.pScore2 = pScore2;
        this.pScore3 = pScore3;
        this.pScore4 = pScore4;
        this.pScore5 = pScore5;
        this.isLLWin = isLLWin;
        this.isAutoInsert = isAutoInsert;
        this.llt1 = llTimes[0];
        this.llt2 = llTimes[1];
        this.llt3 = llTimes[2];
        this.llt4 = llTimes[3];
        this.llt5 = llTimes[4];
        this.wit1 = winTimes[0];
        this.wit2 = winTimes[1];
        this.wit3 = winTimes[2];
        this.wit4 = winTimes[3];
        this.wit5 = winTimes[4];
    }

}
