package com.example.nextword;

public class Info {

    private String heading, description;

    public Info(String a, String b) {
        heading = a;
        description = b;
    }


    public String getDescription() {
        return description;
    }

    public String getHeading() {
        return heading;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }
}

