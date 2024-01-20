package com.expert.blive.ModelClass.Family;

import java.util.ArrayList;

public class FamilyDetails {
    public String id;
    public String userId;
    public String familyName;
    public String familyDescription;
    public String familyImage;
    public ArrayList<Member> members;
    public int members_count;
    public boolean request_status;
    public boolean family_join_status;
    public String family_id;
    public String admin_id;

    public String getFamily_id() {

        return family_id;
    }

    public void setFamily_id(String family_id) {

        this.family_id = family_id;
    }

    public String getAdmin_id() {

        return admin_id;
    }

    public void setAdmin_id(String admin_id) {

        this.admin_id = admin_id;
    }

    public boolean isRequest_status() {

        return request_status;
    }

    public void setRequest_status(boolean request_status) {

        this.request_status = request_status;
    }

    public boolean isFamily_join_status() {

        return family_join_status;
    }

    public void setFamily_join_status(boolean family_join_status) {

        this.family_join_status = family_join_status;
    }

    public ArrayList<Member> getMembers() {

        return members;
    }

    public void setMembers(ArrayList<Member> members) {

        this.members = members;
    }

    public int getMembers_count() {

        return members_count;
    }

    public void setMembers_count(int members_count) {

        this.members_count = members_count;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {

        this.id = id;
    }

    public String getUserId() {

        return userId;
    }

    public void setUserId(String userId) {

        this.userId = userId;
    }

    public String getFamilyName() {

        return familyName;
    }

    public void setFamilyName(String familyName) {

        this.familyName = familyName;
    }

    public String getFamilyDescription() {

        return familyDescription;
    }

    public void setFamilyDescription(String familyDescription) {

        this.familyDescription = familyDescription;
    }

    public String getFamilyImage() {

        return familyImage;
    }

    public void setFamilyImage(String familyImage) {

        this.familyImage = familyImage;
    }

}
