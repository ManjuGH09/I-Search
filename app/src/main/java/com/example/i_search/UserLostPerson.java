package com.example.i_search;

public class UserLostPerson {

    public String lpname;
    public String lpage;
    public String lpaddress;
    public String lpphonenum;
    public String lpcolour;
    public String lpheight;
    public String lpdate;
    public String lptime;
    public String lplostplace;
    public String lpgender;
    public String lpmentallystrong;
    public String lpmissedbefore;
    public String lpstatus;

    public UserLostPerson()
    {

    }

    public UserLostPerson(String lpname, String lpage, String lpaddress, String lpphonenum, String lpcolour, String lpheight, String lpdate, String lptime, String lplostplace, String lpgender, String lpmentallystrong, String lpmissedbefore, String lpstatus) {
        this.lpname = lpname;
        this.lpage = lpage;
        this.lpaddress = lpaddress;
        this.lpphonenum = lpphonenum;
        this.lpcolour = lpcolour;
        this.lpheight = lpheight;
        this.lpdate = lpdate;
        this.lptime = lptime;
        this.lplostplace = lplostplace;
        this.lpgender = lpgender;
        this.lpmentallystrong = lpmentallystrong;
        this.lpmissedbefore = lpmissedbefore;
        this.lpstatus = lpstatus;
    }

    public String getLpname() {
        return lpname;
    }

    public void setLpname(String lpname) {
        this.lpname = lpname;
    }

    public String getLpage() {
        return lpage;
    }

    public void setLpage(String lpage) {
        this.lpage = lpage;
    }

    public String getLpaddress() {
        return lpaddress;
    }

    public void setLpaddress(String lpaddress) {
        this.lpaddress = lpaddress;
    }

    public String getLpphonenum() {
        return lpphonenum;
    }

    public void setLpphonenum(String lpphonenum) {
        this.lpphonenum = lpphonenum;
    }

    public String getLpcolour() {
        return lpcolour;
    }

    public void setLpcolour(String lpcolour) {
        this.lpcolour = lpcolour;
    }

    public String getLpheight() {
        return lpheight;
    }

    public void setLpheight(String lpheight) {
        this.lpheight = lpheight;
    }

    public String getLpdate() {
        return lpdate;
    }

    public void setLpdate(String lpdate) {
        this.lpdate = lpdate;
    }

    public String getLptime() {
        return lptime;
    }

    public void setLptime(String lptime) {
        this.lptime = lptime;
    }

    public String getLplostplace() {
        return lplostplace;
    }

    public void setLplostplace(String lplostplace) {
        this.lplostplace = lplostplace;
    }

    public String getLpgender() {
        return lpgender;
    }

    public void setLpgender(String lpgender) {
        this.lpgender = lpgender;
    }

    public String getLpmentallystrong() {
        return lpmentallystrong;
    }

    public void setLpmentallystrong(String lpmentallystrong) {
        this.lpmentallystrong = lpmentallystrong;
    }

    public String getLpmissedbefore() {
        return lpmissedbefore;
    }

    public void setLpmissedbefore(String lpmissedbefore) {
        this.lpmissedbefore = lpmissedbefore;
    }

    public String getLpstatus() {
        return lpstatus;
    }

    public void setLpstatus(String lpstatus) {
        this.lpstatus = lpstatus;
    }

    /*public UserLostPerson(String lpname,String lpage,String lpgender)
    {
        this.lpname=lpname;
        this.lpage=lpage;
        this.lpgender=lpgender;
    }

    public String toString()
    {
        return this.lpname + "\n" +lpage+ "\t" +lpgender;
    }*/
}
