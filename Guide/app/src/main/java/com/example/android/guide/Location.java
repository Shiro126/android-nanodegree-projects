package com.example.android.guide;

public class Location {
    private int nameStringId;
    private int imgResourceId = NO_IMAGE_PROVIDED;
    private int localizationStringId;
    private int descriptionStringId;

    public int getDescriptionStringId() {
        return descriptionStringId;
    }

    public int getImgResourceId() {
        return imgResourceId;
    }

    public int getLocalizationStringId() {
        return localizationStringId;
    }

    public int getNameStringId() {
        return nameStringId;
    }
    private static final int NO_IMAGE_PROVIDED = -1;
    public Location(int nameStringId, int localizationStringId, int descriptionStringId, int imgResourceId)
    {
        this.nameStringId = nameStringId;
        this.localizationStringId = localizationStringId;
        this.descriptionStringId = descriptionStringId;
        this.imgResourceId = imgResourceId;
    }
}
