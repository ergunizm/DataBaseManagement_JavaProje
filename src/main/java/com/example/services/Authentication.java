package com.example.services;

import com.example.utils.ColoredOutput;
import com.example.utils.DBUtil;
import java.util.List;

import com.example.models.User;

public class Authentication {
    public static boolean authenticate(String ssn, String password) {
        List<User> users = DBUtil.selectAllFromDB("users");

        ColoredOutput.print("Authenticating user with SSN: " + ssn + " and password: " + password,
                ColoredOutput.Color.YELLOW_BOLD_BRIGHT);

        for (User user : users) {
            if (user.getSsn().equals(ssn) && user.getPassword().equals(password)) {
                return true;
            }
        }

        return false;
    }

    public static void register(String ssn, String fname, String lname, String password) {
        User user = new User();
        user.setSsn(ssn);
        user.setFirstName(fname);
        user.setLastName(lname);
        user.setPassword(password);

        DBUtil.insertToDB(user);
    }
}
