package com.example.sparklingclean.DB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class PaymentDetails extends DatabaseObject {

    private static String tableName = "Payment";

    private int cardNumber;
    private Date expiryDate;
    private int securityCode;

    public int getCardNumber() {
        ResultSet rs = getSingleField("card_number", tableName);
        try {
            cardNumber = rs.getInt(1);
            return cardNumber;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }


    public Date getExpiryDate() {
        ResultSet rs = getSingleField("expiry_date", tableName);
        try {
            expiryDate = rs.getDate(1);
            return expiryDate;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public int getSecurityCode() {
        ResultSet rs = getSingleField("security_code", tableName);
        try {
            securityCode = rs.getInt(1);
            return securityCode;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void setSecurityCode(int securityCode){
        this.securityCode = securityCode;
    }
}
