package com.example.vocabularioapp;

public class Word {
    private String mdefaultTranslation;
    private String mspanishTranslation;
    private int mimageId;
    private int maudioId;
    private boolean isImage = false;

    public Word(String defaultTranslation, String spanishTranslation, int imageId, int audioId) {
        mdefaultTranslation = defaultTranslation;
        mspanishTranslation = spanishTranslation;
        mimageId = imageId;
        maudioId = audioId;
        isImage = true;
    }

    public Word(String defaultTranslation, String spanishTranslation, int audioId) {
        mdefaultTranslation = defaultTranslation;
        mspanishTranslation = spanishTranslation;
        maudioId = audioId;

    }

    public String getDefaultTranslation() {
        return mdefaultTranslation;
    }

    public String getMiwokTranslation() {
        return mspanishTranslation;
    }

    public int getImageId() {
        return mimageId;
    }

    public int getAudioId() {
        return  maudioId;
    }
    public boolean getIsImage () {
        return isImage;
    }
}

