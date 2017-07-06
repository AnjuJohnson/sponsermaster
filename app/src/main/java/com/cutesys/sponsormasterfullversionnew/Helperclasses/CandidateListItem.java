package com.cutesys.sponsormasterfullversionnew.Helperclasses;

/**
 * Created by user on 3/30/2017.
 */

public class CandidateListItem {
    private String candidate_id;
    private String candidate_code;
    private String fullname,name;
    private String candidate_email;
    private String candidate_phone;
    private String candidate_job;
    private String application_job_position;
    private String image,id;

    public CandidateListItem(){}
    public String get_id() {
        return id;
    }

    public void set_id(String id) {
        this.id = id;
    }



    public String get_name() {
        return name;
    }

    public void set_name(String name) {
        this.name = name;
    }

    public String getcandidate_id() {
        return candidate_id;
    }

    public void setcandidate_id(String candidate_id) {
        this.candidate_id = candidate_id;
    }

    public String getcandidate_code() {
        return candidate_code;
    }
    public void setcandidate_code(String candidate_code) {
        this.candidate_code = candidate_code;
    }

    public String getfullname() {
        return fullname;
    }
    public void setfullname(String fullname) {
        this.fullname = fullname;
    }

    public String getcandidate_email() {
        return candidate_email;
    }
    public void setcandidate_email(String candidate_email) {
        this.candidate_email = candidate_email;
    }
    public String getcandidate_phone() {
        return candidate_phone;
    }
    public void setcandidate_phone(String candidate_phone) {
        this.candidate_phone = candidate_phone;
    }
    public String getcandidate_job() {
        return candidate_job;
    }
    public void setcandidate_job(String candidate_job) {
        this.candidate_job = candidate_job;
    }
    public String getapplication_job_position() {
        return application_job_position;
    }
    public void setapplication_job_position(String application_job_position) {
        this.application_job_position = application_job_position;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}