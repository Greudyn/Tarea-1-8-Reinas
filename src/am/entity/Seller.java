package am.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Greudyn Velasquez.
 * @FileName: Seller.java
 * @version: 0.1
 * @Description: Entidad para el vendedor
 */
public class Seller implements Serializable {

    private static final long serialVersionUID = 6545245759396182011L;

    private String     name;
    private List<Item> items;
    private float      totalSell;
    private double     totalEarn;

    /**
     * @return obtetiene el parámetro: name.
     */
    public String getName() {
        return name;
    }

    /**
     * @param parámetro: name, para ser seteado.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return obtetiene el parámetro: items.
     */
    public List<Item> getItems() {
        return items;
    }

    /**
     * @param parámetro: items, para ser seteado.
     */
    public void setItems(List<Item> items) {
        this.items = items;
    }

    /**
     * @return obtetiene el parámetro: totalSell.
     */
    public float getTotalSell() {
        return totalSell;
    }

    /**
     * @param parámetro: totalSell, para ser seteado.
     */
    public void setTotalSell(float totalSell) {
        this.totalSell = totalSell;
    }

    /**
     * @return obtetiene el parámetro: totalEarn.
     */
    public double getTotalEarn() {
        return totalEarn;
    }

    /**
     * @param parámetro: totalEarn, para ser seteado.
     */
    public void setTotalEarn(double d) {
        this.totalEarn = d;
    }

}
