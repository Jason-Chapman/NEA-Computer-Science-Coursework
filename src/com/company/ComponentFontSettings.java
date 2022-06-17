package com.company;

public class ComponentFontSettings {
    protected String font;
    protected int fontSize;

    public ComponentFontSettings(String font, int fontSize) {
        this.font = font;
        this.fontSize = fontSize;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }
}
