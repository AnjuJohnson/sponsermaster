package com.cutesys.sponsormasterfullversionnew.Helperclasses;

/**
 * Created by Kris on 12/9/2016.
 */

public class ProfileItem {


    private String[] file_title,file_name, file_path, profile_title,
            profile_subtitle, advanced_title, advanced_subtitile ;

    public ProfileItem() {
    }

    public String[] getfile_title() {
        return file_title;
    }
    public void setfile_title(String[] file_title) {
        this.file_title = file_title;
    }

    public String[] getfile_name() {
        return file_name;
    }
    public void setfile_name(String[] file_name) {
        this.file_name = file_name;
    }

    public String[] getfile_path() {
        return file_path;
    }
    public void setfile_path(String[] file_path) {
        this.file_path = file_path;
    }

    public String[] getProfile_title() {
        return profile_title;
    }
    public void setProfile_title(String[] profile_title) {
        this.profile_title = profile_title;
    }

    public String[] getProfile_subtitle() {
        return profile_subtitle;
    }
    public void setProfile_subtitle(String[] profile_subtitle) {
        this.profile_subtitle = profile_subtitle;
    }

    public String[] getAdvanced_title() {
        return advanced_title;
    }
    public void setAdvanced_subtitile(String[] advanced_title) {
        this.advanced_title = advanced_title;
    }

    public String[] getAdvanced_subtitile() {
        return advanced_subtitile;
    }
    public void setAdvanced_title(String[] advanced_subtitile) {
        this.advanced_subtitile = advanced_subtitile;
    }
}