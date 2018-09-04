package ru.papdevelop.vr.speechcloud;

public enum Lang {
    RU("ru-RU"),
    US("en-US"),
    UK("uk-UK"),
    TR("tr-TR");

    Lang(String lang) {
        this.lang = lang;
    }

    public String toString() {
        return lang;
    }

    public static Lang fromString(String text) {
        for (Lang l : Lang.values()) {
            if (l.toString().equalsIgnoreCase(text)) {
                return l;
            }
        }
        return null;
    }

    private String lang;
}
