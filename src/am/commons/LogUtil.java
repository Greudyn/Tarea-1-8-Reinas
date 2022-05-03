package am.commons;

import am.entity.Buyer;
import am.entity.Seller;

/**
 * @Author: Greudyn Velasquez.
 * @Datecreation: Apr 25, 2022 1:18:40 PM
 * @FileName: LogUtil.java
 * @AuthorCompany: Telefonica
 * @version: 0.1
 * @Description: Util para los mensajes de error
 */
public class LogUtil {

    public static void inError() {
        System.out.print("Error en los datos ingresados\n");
    }

    public static void offers(String seller, String buyer, float price, String item) {
        System.out.print("[" + seller + "] " + buyer + " ofrecio " + price + " por el producto " + item + " \n");
    }

    public static void sell(String seller, String buyer, float price, String item) {
        System.out.print("[" + seller + "] El producto " + item + " fue vendido a " + buyer + " por " + price + "\n");
    }

    public static void buyerProducts(Buyer buyer) {
        System.out.print("El comprador " + buyer.getName() + " compro los siguientes productos: " + Util.buyerProducts(buyer)
                + " a un costo total de: " + buyer.sumItems() + " y el restante de dinero que le quedo fue: " + buyer.getBalance() + "\n");
    }

    public static void sellerProducts(Seller seller) {
        System.out.print("El vendedor " + seller.getName() + " tiene un total de ventas " + seller.getTotalSell()
                + " y un total de ganancias de " + seller.getTotalEarn() + "\n");
    }

    public static void auctionMasterProducst(float total, double earn) {
        System.out.print("Para el Martillero el monto total de las ventas fue " + total + " y sus ganancias fueron " + earn + "\n");
    }

    private LogUtil() {

    }
}
