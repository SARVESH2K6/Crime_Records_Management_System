package CRIME;

class Officer {
    private int officerId;
    private String name;
    private String rank;
    private int age;
    private String department;

    public Officer(int officerId, String name, String rank, int age, String department) {
        this.officerId = officerId;
        this.name = name;
        this.rank = rank;
        this.age=age;
        this.department=department;
    }

    public int getOfficerId() {
        return officerId;
    }

    public String getName() {
        return name;
    }

    public String getRank() {
        return rank;
    }

    public int getAge() {
        return age;
    }

    public String getDepartment() {
        return department;
    }
}

