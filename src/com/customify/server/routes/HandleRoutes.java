package com.customify.server.routes;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

import com.customify.shared.Keys;

import static com.customify.shared.Keys.CREATE_PRODUCT;

public class HandleRoutes {
    private final Keys key;
    Socket socket;
    AuthRoute authRoute;
    PointsRoutes pointsRoutes;
    ProductRoute productRoute;

    public HandleRoutes(Keys key, Socket socket) throws IOException, SQLException {
        this.socket = socket;
        this.authRoute = new AuthRoute(socket);
        this.pointsRoutes = new PointsRoutes(socket);
        this.productRoute = new ProductRoute(socket);
        this.key = key;
        this.switchRoutes();
    }

    public void switchRoutes() throws IOException, SQLException {
        if (this.key == Keys.LOGIN) {
            authRoute.loginRoute();
        }
        if (this.key ==Keys.CREATE_PRODUCT){
            productRoute.registerProduct();
        }
        if (this.key ==Keys.GET_PRODUT_BY_ID){
            productRoute.registerProduct();
        }
        if (this.key ==Keys.POINTS_BY_CUSTOMER_EMAIL){
            pointsRoutes.getPointsByCustomer();
        }
        if(this.key==Keys.UPDATE_PRODUCT){
            productRoute.updateProduct();
        }
        if(this.key==Keys.DELETE_PRODUCT){
            productRoute.deleteProduct();
        }
//        authRoute.loginError();
    }
}
