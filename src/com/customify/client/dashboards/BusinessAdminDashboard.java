package com.customify.client.dashboards;

import java.net.Socket;
import java.util.Scanner;

public class BusinessAdminDashboard {

    private Socket socket;
    private String userJson;

    public BusinessAdminDashboard(){}

    public BusinessAdminDashboard(Socket socket, String userJson) {
        this.socket = socket;
        this.userJson = userJson;
    }

    public String getUserJson() {
        return userJson;
    }

    public void setUserJson(String userJson) {
        this.userJson = userJson;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }


    public void view(){
        Scanner scan = new Scanner(System.in);
        boolean loggedIn = true;
        do {
            System.out.println("---------------------------------------------");
            System.out.println("--------------CUSTOMIFY HOME-----------------\n");
            System.out.println("           1. EMPLOYEE MANAGEMENT");
            System.out.println("           2. CUSTOMER MANAGEMENT");
            System.out.println("           3. TODAY'S REPORT");
            System.out.println("           4. MY PROFILE");
            System.out.println("           5. PROFILE SETTINGS");
            System.out.println("           6. LOGOUT !!!");
            int choice = scan.nextInt();
            switch (choice) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
//                    loggedIn=false;
                    break;
                    case 5:
//                    loggedIn=false;
                    break;
                case 6:
                    loggedIn=false;
                    break;
                default:
                    System.out.println("INVALID CHOICE");
            }
        }while(loggedIn);
    }

}