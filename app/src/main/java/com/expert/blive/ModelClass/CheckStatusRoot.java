package com.expert.blive.ModelClass;

import java.io.Serializable;

public class CheckStatusRoot implements Serializable {
    public String success;
    public String message;
    public String status;
    public String host_status;
    public String panelLink;
    public String agencyCode;
    public String password;

    public String getHost_status() {
        return host_status;
    }

    public void setHost_status(String host_status) {
        this.host_status = host_status;
    }

    public String getPanelLink() {
        return panelLink;
    }

    public void setPanelLink(String panelLink) {
        this.panelLink = panelLink;
    }

    public String getAgencyCode() {
        return agencyCode;
    }

    public void setAgencyCode(String agencyCode) {
        this.agencyCode = agencyCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
