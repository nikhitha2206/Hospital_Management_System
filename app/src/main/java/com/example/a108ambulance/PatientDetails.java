package com.example.a108ambulance;

public class PatientDetails  {
    private String number;
    private String address;

    public PatientDetails() {
    }
    public PatientDetails(String number,String address)
    {
        this.number = number;
        this.address = address;
    }
    public String getNumber()
    {
        return number;
    }
    public String getAddress()
    {
        return address;
    }
    public void setNumber(String number)
    {
        this.number=number;
    }
    public void setAddress(String address)
    {
        this.address=address;
    }



}
