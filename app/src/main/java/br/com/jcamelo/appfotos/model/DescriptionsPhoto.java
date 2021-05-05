package br.com.jcamelo.appfotos.model;

import java.io.File;
import java.util.List;

public class DescriptionsPhoto {

    private String user;
    private String equip;
    private String os;
    private String initials;
    private List<String> partsEquip;
    private List<File> fileList;

    public void setPartsEquip(List<String> partsEquip) {
        this.partsEquip = partsEquip;
    }

    public List<String> getPartsEquip() {
        return partsEquip;
    }

    public void setFileList(List<File> fileList) {
        this.fileList = fileList;
    }

    public List<File> getFileList() {
        return fileList;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getInitials() {
        return initials;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getEquip() {
        return equip;
    }

    public void setEquip(String equip) {
        this.equip = equip;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = "OS-" + os;
    }

    @Override
    public String toString() {
        return getEquip() + " | " + getOs();
    }
}
