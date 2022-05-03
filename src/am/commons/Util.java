package am.commons;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import am.entity.Buyer;
import am.entity.Item;
import am.entity.Seller;

/**
 * @Author: Greudyn Velasquez.
 * @FileName: Util.java
 * @version: 0.1
 * @Description: Funciones utilitarias
 */
public class Util {

    public static Boolean notNullOrEmpty(String validate) {
        if (validate == null || validate.isEmpty() || validate.trim().isEmpty())
            return Boolean.FALSE;
        else
            return Boolean.TRUE;
    }

    public static List<Seller> setSellers(String[] inArray, List<Seller> sellers) {
        Seller seller = new Seller();
        if (inArray.length >= 2 && Boolean.TRUE.equals(Util.notNullOrEmpty(inArray[1]))) {
            seller.setName(inArray[1]);
        } else {
            LogUtil.inError();
        }

        if (Boolean.TRUE.equals(Util.notNullOrEmpty(seller.getName()))) {
            sellers.add(seller);
        }

        return sellers;
    }

    public static List<Buyer> setBuyers(String[] inArray, List<Buyer> buyers) {
        Buyer buyer = new Buyer();
        if (inArray.length >= 3 && Boolean.TRUE.equals(Util.notNullOrEmpty(inArray[1]))
                && Boolean.TRUE.equals(Util.notNullOrEmpty(inArray[2])) && Boolean.TRUE.equals(Util.notNullOrEmpty(inArray[3]))) {
            buyer.setName(inArray[1]);
            buyer.setBalance(Float.parseFloat(inArray[2]));
            buyer.setMaxPercentage(Float.parseFloat(inArray[3]));
        } else {
            LogUtil.inError();
        }

        if (Boolean.TRUE.equals(Util.notNullOrEmpty(buyer.getName()))
                && Boolean.TRUE.equals(Util.notNullOrEmpty(String.valueOf(buyer.getBalance())))
                && Boolean.TRUE.equals(Util.notNullOrEmpty(String.valueOf(buyer.getMaxPercentage())))) {
            buyers.add(buyer);
        }
        return buyers;
    }

    public static List<Item> setItems(String[] inArray, List<Seller> sellers, List<Item> items) {
        String sellerName = Boolean.TRUE.equals(Util.notNullOrEmpty(inArray[1])) ? inArray[1] : "";
        Item item = new Item();
        if (inArray.length >= 3 && !sellerName.isEmpty()
                && Boolean.TRUE.equals(
                        Objects.nonNull(sellers.stream().filter(x -> sellerName.equalsIgnoreCase(x.getName())).findFirst().orElse(null)))
                && Boolean.TRUE.equals(Util.notNullOrEmpty(inArray[2])) && Boolean.TRUE.equals(Util.notNullOrEmpty(inArray[3]))) {
            item.setOwner(sellerName);
            item.setName(inArray[2]);
            item.setPrice(Float.parseFloat(inArray[3]));
            item.setAvailable(Boolean.TRUE);
        } else {
            LogUtil.inError();
        }

        if (Boolean.TRUE.equals(Util.notNullOrEmpty(item.getName())) && Boolean.TRUE.equals(Util.notNullOrEmpty(item.getOwner()))
                && Boolean.TRUE.equals(Util.notNullOrEmpty(String.valueOf(item.getPrice())))) {
            items.add(item);
        }
        return items;
    }

    public static List<Seller> setSellerItems(List<Item> items, List<Seller> sellers) {
        List<Seller> sellerUpdate = new ArrayList<>();
        for (Seller seller : sellers) {
            List<Item> itemsUpdate = new ArrayList<>();
            for (Item item : items) {
                if (item.getOwner().equalsIgnoreCase(seller.getName())) {
                    itemsUpdate.add(item);
                }
            }
            if (Boolean.FALSE.equals(itemsUpdate.isEmpty())) {
                seller.setItems(itemsUpdate);
            }
            sellerUpdate.add(seller);
        }
        return sellerUpdate;
    }

    public static float validateMaxPercentage(float value, float percentage, float balance) {
        while (percentage != 0) {
            if ((value * (percentage / 100)) < balance) {
                return percentage;
            }
            percentage = percentage - 1;
        }
        return percentage;
    }

    public static int randomGenerator(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    public static void sleep(int min, int max) {
        try {
            Thread.sleep(Util.randomGenerator(min, max));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public static String buyerProducts (Buyer buyer) {
        if (Objects.nonNull(buyer.getItems())) {
            List<String> list = new ArrayList <> ();
            for (Item item : buyer.getItems()) {
                list.add(item.getName());
            }
            return String.join(",", list);
        }
        return "ningun producto comprado,";
    }

    private Util() {

    }
}
