package entities;


import jakarta.persistence.*;

import java.sql.Date;

@Table(name = "subscription")
@Entity
@NamedQuery(name = "Subscription.findAll",
        query = "select a from Subscription a")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;
    @Column(name = "periodicals_id")
    private long periodicalsId;
    @Column(name = "user_id")
    private long userId;
    @Column(name = "sub_start")
    private Date subStart;
    @Column(name = "sub_end")
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
    public void setPeriodicalsId(long value){this.periodicalsId = value;}

    public long getUserId(){return this.userId;}
    public void setUserId(long value){this.userId = value;}

    public Date getSubStart(){return this.subStart;}
    public void setSubStart(Date value){this.subStart = value;}

    public Date getSubEnd(){return this.subEnd;}
    public void setSubEnd(Date value){this.subEnd = value;}
}
