package com.example.sashok.clienttask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by sashok on 7.4.17.
 */

public class Customer {
    private String firstName;
    private String lastName;
    private String site;
    private String email;
    private Boolean vip;
    private Boolean bad;
    private int customerId;
    private int managerId;
    private Date createDate;
    private List<String> phones;

    public Customer() {
        phones = new ArrayList<>();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getVip() {
        return vip;
    }

    public void setVip(Boolean vip) {
        this.vip = vip;
    }

    public Boolean getBad() {
        return bad;
    }

    public void setBad(Boolean bad) {
        this.bad = bad;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public List<String> getPhones() {

        return phones;
    }

    public void setPhones(List<String> phones) {
        this.phones = phones;
    }

    public String showInformation() {
        String costumer = "";
        costumer += (getFirstName() != null ? "firstName " + getFirstName() + "\n" : "");
        costumer += (getCustomerId() != 0 ? "id " + getCustomerId() + "\n" : "");
        costumer += (getLastName() != null ? "lastName " + getLastName() + "\n" : "");
        costumer += (getSite() != null ? "site " + getSite() + "\n" : "");
        costumer += (getEmail() != null ? "email " + getEmail() + "\n" : "");
        costumer += (getBad() != null ? "bad " + getBad() + "\n" : "");
        costumer += (getVip() != null ? "vip " + getVip() + "\n" : "");
        String phones = "";
        if (getPhones().size() != 0) {
            for (int i = 0; i < getPhones().size() - 1; i++) {
                phones += getPhones().get(i) + ",";


            }
            phones += getPhones().get(getPhones().size() - 1);
            costumer += "phones " + phones + "\n";
        }
        if (getCreateDate() != null) {

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String reportDate = df.format(getCreateDate());
            costumer += "createDate " + reportDate + "\n";
        }
        costumer += (getManagerId() != 0 ? "managerId " + getManagerId() : "");
        return costumer;

    }

    @Override
    public String toString() {
        return super.toString();
    }
}
