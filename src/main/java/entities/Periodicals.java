package entities;


import jakarta.persistence.*;

@Table(name = "periodicals")
@Entity
@NamedQuery(name = "Periodicals.findAll",
        query = "select a from Periodicals a")
public class Periodicals {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "pub_house")
    private String pubHouse;
    @Column(name = "info")
    private String info;
    @Column(name = "price")
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
