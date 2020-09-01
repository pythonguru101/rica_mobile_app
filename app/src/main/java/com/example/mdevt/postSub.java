package com.example.mdevt;

import android.os.Build;

public class postSub {
    String mobile_number;
    String name;
    String surname;
    String id_passport_number;
    String id_passport_expiry;
    String address1;
    String address2;
    String address3;
    String postal_Code;

    postSub(String mobile_number, String name, String surname, String id_passport_number, String id_passport_expiry,
            String address1, String address2, String address3, String postal_Code) {
        this.mobile_number = mobile_number;
        this.name = name;
        this.surname = surname;
        this.id_passport_expiry = id_passport_expiry;
        this.id_passport_number  = id_passport_number;
        this. address1 = address1;
        this.address2 = address2;
        this.address3 = address3;
        this.postal_Code = postal_Code;

    }

    public String getMobile_number() {
        return mobile_number;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getId_passport_number() {
        return id_passport_number;
    }

    public String getId_passport_expiry() {
        return id_passport_expiry;
    }

    public String getAddress1() {
        return address1;
    }

    public String getAddress2() {
        return address2;
    }

    public String getAddress3() {
        return address3;
    }

    public String getPostal_Code() {
        return postal_Code;
    }
}
