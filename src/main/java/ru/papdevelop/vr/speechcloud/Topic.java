package ru.papdevelop.vr.speechcloud;

public enum Topic {

    QUERIES("queries"),
    MAPS("maps"),
    DATES("dates"),
    NAMES("names"),
    NUMBERS("numbers"),
    MUSIC("music"),
    BUYING("bying");

    Topic(String topic) {
        this.topic = topic;
    }

    public String toString() {
        return topic;
    }

    public static Topic fromString(String text) {
        for(Topic t : Topic.values()) {
            if (t.toString().equalsIgnoreCase(text)) {
                return t;
            }
        }
        return null;
    }

    private String topic;
}
