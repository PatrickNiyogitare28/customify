package com.customify.client.services;

import com.customify.client.Keys;
import com.customify.client.SendToServer;

import com.customify.client.data_format.products.ProductFormat;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ProductService {
    private Socket socket;
    private String data;

    InputStream inputStream;
    ObjectInputStream objectInputStream;

    public ProductService(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) throws IOException {
        this.socket = socket;

    }

    public void addNewProduct(ProductFormat product) throws Exception {
        product.setKey(Keys.CREATE_PRODUCT);
        String request = new ObjectMapper().writeValueAsString(product);

        SendToServer sendToServer = new SendToServer(request,this.socket);
        if (sendToServer.send()){
            System.out.println("\n\n\t\t\tREQUEST WAS SENT TO SERVER\n");
            this.handleRegisterProductSuccess();
        }
        else System.out.println("\n\n\t\tSENDING REQUEST TO SERVER FAILED\n");
    }

    /**
     * @description
     * Service to Retrieve Product By Id
     * @author SAUVE Jean-Luc
     * @version 1
     * */
    public void getProductById(Integer productId) throws  Exception{

        String jsonToSend = "{\"key\" : \"GET_PRODUCT_BY_ID\", \"id\" : \""+productId+"\"}";
        SendToServer sendToServer = new SendToServer(jsonToSend, this.socket);

        //if the sending is successful call a method to handle response from server
        if (sendToServer.send()) {
            System.out.println("Reached here 1");
            this.handleGetProductByIdSuccess();
        }
        else{
            System.out.println("\n\nError occurred when trying to send request to server\n");
        }

    }
    //Method Created By Merlyne Iradukunda
    // Due date: 6/2/2020
    public void deleteProduct(String json_data) throws  Exception{
        Long productCode;
//        Request request = new Request(Keys.DELETE_PRODUCT, productCode);
//        Common common = new Common(request, this.socket);
//
//        //if the sending is successful call a method to handle response from server
//        if (common.sendToServer()) {
//            this.handleDeleteProductSuccess();
//        } else {
//            System.out.println("\n\nError occurred when trying to send request to server\n");
//        }
    }

    public void getAllProducts() throws Exception {
        ProductFormat format = new ProductFormat();
        format.setKey(Keys.GET_ALL_PRODUCTS);

        SendToServer sendToServer = new SendToServer(new ObjectMapper().writeValueAsString(format),this.socket);
        if (sendToServer.send()) {
            System.out.println("\n\n\t\t\tREQUEST WAS SENT TO SERVER\n");
            this.handleGetProductListSuccess();
        }
        else System.out.println("\n\n\t\t\tERROR OCCURRED WHEN SENDING REQUEST TO SERVER\n");
    }

    /**
     * @description
     * Service to Update Product By Id
     * @author SAUVE Jean-Luc
     * @version 1
     * */

    public void updateProduct(ProductFormat productFormat) throws  Exception{

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(productFormat);
        SendToServer serverSend = new SendToServer(json, this.socket);
        //if the sending is successful call a method to handle response from server
        if (serverSend.send()) {
            this.handleUpdateProductSuccess();
        }
        else{
            System.out.println("\n\nError occurred when trying to send request to server\n");
        }
    }
    public void handleGetProductListSuccess() throws IOException, ClassNotFoundException {
        try {
            InputStream input = this.socket.getInputStream();
            ObjectInputStream objectInput = new ObjectInputStream(input);
            List<String> res = (List) objectInput.readObject();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode response = objectMapper.readTree(res.get(0));

            if(response.get("status").asInt() == 500){
                System.out.println("\n\n\t\t\t---- INTERNAL SERVER ERROR -----\n");
                return;
            }


            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            System.out.println("\t\t\t\t\t\t\tHere is a list of products registered so far");
            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");

//            List<String> products = (List<String>) new JsonNode().readTreeresponse.get("products").asText()
            for(int i = 0;i<res.size();i++)
            {
                JsonNode jsonNode = objectMapper.readTree(res.get(i));
                System.out.println(String.format("\t\t\t%-25s %-25s %-25s %-25s", jsonNode.get("code").asText(), jsonNode.get("firstName").asText(), jsonNode.get("lastName").asText(), jsonNode.get("email").asText()));


            }

        }catch(Exception e){
            System.out.println("RESPONSE ERROR"+e.getMessage());
        }
//
//        try {
////            List<Response> response = (List<Response>) objectInputStream.readObject();
////            if (response.get(0).getStatusCode() == 200) {
////                List<ProductFormat> products = (List<ProductFormat>) response.get(0).getData();
////
////                if (products.size() == 0) {
////                    System.out.println("\n\nNo products registered so far.\n");
////                    return;
////                }
//////
//                System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//                System.out.println("\t\t\t\t\t\t\tHere is a list of products registered so far");
//                System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
////
////                System.out.println(String.format("%-15s %-30s %-10s %10s %20s %20s", "Code", "name", "quantity", "price", "bounded points", "Created at") + "\n");
////
////                for (int i = 0; i < products.size(); i++) {
////                    ProductFormat product = products.get(i);
////
////                    System.out.println(String.format("%-15s %-30s %-10s %10s %20s %20s", product.getProductCode(), product.getName(), product.getQuantity(), product.getPrice(), product.getBondedPoints(), product.getCreatedAt()));
////                }
////                System.out.println("\n");
////            } else if (response.get(0).getStatusCode() == 400) {
////                System.out.println("\n\nInvalid product format.Please enter product details as required\n\n");
////            } else {
////                System.out.println("\n\nUnknown error occurred.Check your internet connection\n");
////            }
//
//        } catch (IOException e) {
//            System.out.println("\n\nError occurred:" + e.getMessage() + "\n\n");
//        } catch (ClassNotFoundException e) {
//            System.out.println("\n\nError occurred:" + e.getMessage() + "\n\n");
//        }

        return;
    }

    public void handleRegisterProductSuccess() throws IOException, ClassNotFoundException {
        try {
            InputStream input =this.socket.getInputStream();
            ObjectInputStream objectInput = new ObjectInputStream(input);

            List<String> res = (List) objectInput.readObject();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode response = objectMapper.readTree(res.get(0));

            if (response.get("status").asInt() == 201) System.out.println("\n\n\t\tPRODUCT CREATED SUCCESSFULLY\n\n");
            else if(response.get("status").asInt() == 400) System.out.println("\n\n\t\tBAD FORMAT WAS SUPPLIED TO SOME FIELDS\n\n");
            else if(response.get("status").asInt() == 500) System.out.println("\n\n\t\tBACKEND INTERNAL SERVER ERROR.\n\n");
            else System.out.println("\n\n\t\tUNKNOWN ERROR OCCURRED WHEN SENDING AND RECEIVING RESPONSE\n\n");
        }catch(Exception e){
            e.printStackTrace();
        }
        return;
    }

    /**
     * @description
     * Function to Send Response when Product is Retrieved Successfully
     * @author SAUVE Jean-Luc
     * @version 1
     * */

    public void handleGetProductByIdSuccess() throws IOException, ClassNotFoundException{
        System.out.println("Reached here 2");
        inputStream = this.getSocket().getInputStream();
        System.out.println("Reached here 3");
        objectInputStream = new ObjectInputStream(inputStream);
        System.out.println("Reached here 4");
//        try {
//            List<Response> response = (List<Response>) objectInputStream.readObject();
//            if(response.get(0).getStatusCode() == 200){
//                ProductFormat retrievedProduct = (ProductFormat) response.get(0).getData();
//
//                System.out.println("+++++++++++++++++++++++++++++++++++++++++++");
//                System.out.println("Product Code: " + retrievedProduct.getProductCode());
//                System.out.println("Business Id: "+ retrievedProduct.getBusiness_id());
//                System.out.println("Name: " + retrievedProduct.getName());
//                System.out.println("Price: " + retrievedProduct.getPrice() );
//                System.out.println("Quantity: " + retrievedProduct.getQuantity());
//                System.out.println("Description: " + retrievedProduct.getDescription());
//                System.out.println("Bonded Points: " + retrievedProduct.getBondedPoints());
//                System.out.println("Registered By: " + retrievedProduct.getRegistered_by());
//                System.out.println("Created At: " + retrievedProduct.getCreatedAt());
//                System.out.println("+++++++++++++++++++++++++++++++++++++++++++");
//            }
//            else if(response.get(0).getStatusCode() == 400){
//                System.out.println("\n\nInvalid product format.Please enter product details as required\n\n");
//            }
//            else{
//                System.out.println("\n\nUnknown error occurred.Check your internet connection\n");
//            }

//        } catch (IOException e) {
//            System.out.println("\n\nError occurred:" +e.getMessage()+ "\n\n");
//        } catch (ClassNotFoundException e) {
//            System.out.println("\n\nError occurred:" +e.getMessage()+ "\n\n");
//        }

        return;
    }
    public void handleDeleteProductSuccess() throws  Exception, ClassNotFoundException {
        inputStream = this.getSocket().getInputStream();
        objectInputStream = new ObjectInputStream(inputStream);
//        try {
//            List<Response> response = (List<Response>) objectInputStream.readObject();
//
//            if (response.get(0).getStatusCode() == 200) {
//                System.out.println("+++++++++++++++++++++++++++++++++++++++++++");
//                System.out.println("\t\t product deleted successfully");
//                System.out.println("+++++++++++++++++++++++++++++++++++++++++++");
//            } else if (response.get(0).getStatusCode() == 400) {
//                System.out.println("\n\nInvalid product format.Please enter product details as required\n\n");
//            } else if(response.get(0).getStatusCode() == 500){
//                System.out.println("Internal server error!!");
//            }else{
//                System.out.println("\n\nUnknown error occurred.Check your internet connection\n");
//            }
//        } catch (IOException e) {
//            System.out.println("\n\nError occurred:" + e.getMessage() + "\n\n");
//        } catch (ClassNotFoundException e) {
//            System.out.println("\n\nError occurred:" + e.getMessage() + "\n\n");
//        }

        return;
    }

    /**
     * @description
     * Function to Send Response when Product is Updated Successfully
     * @author SAUVE Jean-Luc
     * @version 1
     * */

    public void handleUpdateProductSuccess() throws IOException, ClassNotFoundException {
        inputStream = this.getSocket().getInputStream();
        objectInputStream = new ObjectInputStream(inputStream);
//        try {
//            List<Response> response = (List<Response>) objectInputStream.readObject();
//            System.out.println("Status: "+ response.get(0).getStatusCode());
//            if(response.get(0).getStatusCode() == 200){
//                ProductFormat registeredProduct = (ProductFormat) response.get(0).getData();
//
//                System.out.println("+++++++++++++++++++++++++++++++++++++++++++");
//                System.out.println("\t\t product Updated successfully");
//                System.out.println("+++++++++++++++++++++++++++++++++++++++++++");
//            }
//            else if(response.get(0).getStatusCode() == 400){
//                System.out.println("\n\nInvalid product format.Please enter product details as required\n\n");
//            }
//            else{
//                System.out.println("\n\nUnknown error occurred.Check your internet connection\n");
//            }

//        } catch (IOException e) {
//            System.out.println("\n\nError occurred:" +e.getMessage()+ "\n\n");
//        } catch (ClassNotFoundException e) {
//            System.out.println("\n\nError occurred:" +e.getMessage()+ "\n\n");
//        }

        return;
    }

}