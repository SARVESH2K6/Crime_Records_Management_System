package CRIME;

import java.util.Date;

class Crime {
    private int crimeId;
    private String name;
    private String crimeType;
    private int age;
    private Date arrestDate;

    public Crime(int crimeId, String name, String crimeType, int age, Date arrestDate) {
        this.crimeId = crimeId;
        this.name = name;
        this.crimeType = crimeType;
        this.age = age;
        this.arrestDate = arrestDate;
    }

    public int getCrimeId() {
        return crimeId;
    }

    public String getName() {
        return name;
    }

    public String getCrimeType() {
        return crimeType;
    }

    public int getAge() {
        return age;
    }

    public Date getArrestDate() {
        return arrestDate;
    }
}