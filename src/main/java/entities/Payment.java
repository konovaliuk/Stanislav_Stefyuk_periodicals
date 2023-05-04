package entities;

public class Payment {
    private long id;
    private float amount;
    private long subscriptionId;
    private String status;


    public Payment(long Id, float Amount, long SubscriptionId, String Status){
        this.id = Id;
        this.amount = Amount;
        this.subscriptionId = SubscriptionId;
        this.status = Status;
    }

    public Payment(long Id, float Amount, long SubscriptionId){
        this.id = Id;
        this.amount = Amount;
        this.subscriptionId = SubscriptionId;
        this.status = "pending";
    }

    public Payment(float Amount, long SubscriptionId, String Status){
        this.amount = Amount;
        this.subscriptionId = SubscriptionId;
        this.status = Status;
    }

    public Payment(float Amount, long SubscriptionId){
        this.amount = Amount;
        this.subscriptionId = SubscriptionId;
        this.status = "pending";
    }
    public Payment(){}

    public long getId(){return this.id;}
    public void setId(long value){this.id = value;}

    public float getAmount(){return this.amount;}
    public void setAmount(float value){this.amount = value;}

    public long getSubscriptionId(){return this.subscriptionId;}
    public void setSubscriptionId(long value){this.subscriptionId = value;}

    public String getStatus(){return this.status;}
    public void setStatus(String value){this.status = value;}
}
