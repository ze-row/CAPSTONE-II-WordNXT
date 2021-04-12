package com.example.nextword;

public class Info {

    private String heading, description, timestamp;

    public Info(String a, String b, String c) {
        heading = a;
        description = b;
        timestamp = c;
    }

    public String getDescription() {
        return description;
    }

    public String getHeading() {
        return heading;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}

