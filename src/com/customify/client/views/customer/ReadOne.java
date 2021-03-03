package com.customify.client.views.customer;

/**
 * @author Nshimiye Emmy
 * @role
 * this is the class to implement the update customer view to show interface of update customer
 * */

import com.customify.client.Keys;
import com.customify.client.data_format.CreateCustomerFormat;
import com.customify.client.data_format.GetCustomer;
import com.customify.client.services.CustomerService;
import com.customify.client.data_format.UpdateCustomerFormat;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReadOne {
    private Socket socket;

    public ReadOne(){ }

    public ReadOne(Socket socket) {
        this.socket = socket;
    }

    public void view() throws IOException, ClassNotFoundException {
        boolean customerView = true;
        Scanner scan = new Scanner(System.in);
        search:do{
            System.out.println("\t\t\t------------------HOME >> CUSTOMER MANAGEMENT >> SEARCH-CUSTOMER---------------------");
            System.out.println("\n       00. Return ");
            System.out.println("        Enter  Customer Code:");
            String customer_code = scan.nextLine();


            if (customer_code.equals("00"))
                break search;

            System.out.println(String.format("\t\t\t%-25s %-25s %-25s %-25s", "CODE","FIRST-NAME","LAST-NAME", "EMAIL" ));
            System.out.println("\t\t\t-------------------------------------------------------------------------------------------------------");
            GetCustomer format = new GetCustomer(Keys.GET_CUSTOMER, customer_code);


            CustomerService customerService = new CustomerService(this.socket);
            List<String> res = new ArrayList<>();
            res = customerService.get(format);
            if (res != null)
            {
                System.out.println(res.get(0));
                for(int i = 0;i<res.size();i++)
                {
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode jsonNode = objectMapper.readTree(res.get(i));
                    System.out.println(String.format("\t\t\t%-25s %-25s %-25s %-25s", jsonNode.get("code").asText(), jsonNode.get("firstName").asText(), jsonNode.get("lastName").asText(), jsonNode.get("email").asText()));


                }
            }
        }while(true);
    }


}
