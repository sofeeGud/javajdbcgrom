package hibernate.lesson4.controller;

import hibernate.lesson4.model.Authorization;
import hibernate.lesson4.model.UserType;

public class Controller {


    protected Authorization authorization;

    protected boolean isAccess(UserType userType) {
        if (authorization.checkIn()) {
            if (userType == UserType.ADMIN && !authorization.isAdmin()) {
                System.err.println("You can not using this function");
                return false;
            }

            return true;
        }
        System.err.println("You must be logged");
        return false;

    }
}
