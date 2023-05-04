package entities;


import java.sql.Date;

public class Subscription {
    private long id;
    private long periodicalsId;
    private long userId;
    private Date subStart;
    private Date subEnd;

    public Subscription(long Id, long PeriodicalsId, long UserId, Date SubStart, Date SubEnd){
        this.id = Id;
        this.periodicalsId = PeriodicalsId;
        this.userId = UserId;
        this.subStart = SubStart;
        this.subEnd = SubEnd;
    }

    public Subscription(long PeriodicalsId, long UserId, Date SubStart, Date SubEnd){
        this.periodicalsId = PeriodicalsId;
        this.userId = UserId;
        this.subStart = SubStart;
        this.subEnd = SubEnd;
    }

    public Subscription(long PeriodicalsId, long UserId, int month){
        long monthMillis = 1000L *60*60*24*30;
        this.periodicalsId = PeriodicalsId;
        this.userId = UserId;
        this.subStart = new Date(System.currentTimeMillis());
        this.subEnd = new Date(System.currentTimeMillis()+month*monthMillis);
    }

    public Subscription(long PeriodicalsId, long UserId){
        long monthMillis = 1000L *60*60*24*30;
        this.periodicalsId = PeriodicalsId;
        this.userId = UserId;
        this.subStart = new Date(System.currentTimeMillis());
        this.subEnd = new Date(System.currentTimeMillis()+monthMillis);
    }

    public Subscription(){}

    public long getId(){return this.id;}
    public void setId(long value){this.id = value;}

    public long getPeriodicalsId(){return this.periodicalsId;}
    public void setPeriodicals_id(long value){this.periodicalsId = value;}

    public long getUserId(){return this.userId;}
    public void setUser_id(long value){this.userId = value;}

    public Date getSubStart(){return this.subStart;}
    public void setSub_start(Date value){this.subStart = value;}

    public Date getSubEnd(){return this.subEnd;}
    public void setSub_end(Date value){this.subEnd = value;}
}
