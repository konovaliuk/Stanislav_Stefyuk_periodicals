package entities;

public class Periodicals {
    private long id;
    private String name;
    private String pubHouse;
    private String info;
    private float price;


    public Periodicals(long Id, String Name, String Pub, String Info, float Price){
        this.id = Id;
        this.name = Name;
        this.pubHouse = Pub;
        this.info = Info;
        this.price = Price;
    }

    public Periodicals(String Name, String Pub, String Info, float Price){
        this.name = Name;
        this.pubHouse = Pub;
        this.info = Info;
        this.price = Price;
    }

    public Periodicals(){}

    public long getId(){return this.id;}
    public void setId(long value){this.id = value;}

    public String getName(){return this.name;}
    public void setName(String value){this.name = value;}

    public String getPub(){return this.pubHouse;}
    public void setPub(String value){this.pubHouse = value;}

    public String getInfo(){return this.info;}
    public void setInfo(String value){this.info = value;}

    public float getPrice(){return this.price;}
    public void setPrice(float value){this.price = value;}

}
