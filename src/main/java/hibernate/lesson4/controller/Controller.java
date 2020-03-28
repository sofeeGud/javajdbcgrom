package hibernate.lesson4.controller;

import hibernate.lesson4.model.Authorization;
import hibernate.lesson4.model.UserType;

public class Controller {


    protected Authorization authorization;

    protected boolean isAccess(UserType userType) {
        if (authorization.checkIn()) {

            return true;
        }
        System.err.println("You must be logged");
        return false;

    }
}
