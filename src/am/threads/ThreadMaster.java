package am.threads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import am.commons.Constant;
import am.commons.LogUtil;
import am.commons.Util;
import am.entity.Buyer;
import am.entity.Item;
import am.entity.Seller;

/**
 * @Author: Greudyn Velasquez.
 * @FileName: ThreadMaster.java
 * @version: 0.1
 * @Description: description details
 */
public class ThreadMaster {
    private List<Item>   items   = new ArrayList<>();
    private List<Buyer>  buyers  = new ArrayList<>();
    private List<Seller> sellers = new ArrayList<>();
    private String       inValues;

    /**
     * Metodo para setear nuevas ofertas por productos
     * 
     * @param buyer
     * @param item
     * @return
     */
    public float setOffer(Buyer buyer, Item item) {
        float maxOfferPercentage = Util.validateMaxPercentage(item.getPrice(), buyer.getMaxPercentage(), buyer.getBalance());
        if (maxOfferPercentage != 0) {
            float offer = item.getPrice() + (Math.round(item.getPrice() * (Util.randomGenerator(1, Math.round(maxOfferPercentage))) / 100));
            LogUtil.offers(item.getOwner(), buyer.getName(), offer, item.getName());
            synchronized (this) {
                item.setPrice(offer);
                item.setBuyer(buyer.getName());
                item.setAuctionInProcess(Boolean.TRUE);
            }

            synchronized (this) {
                buyer.setBalance(buyer.getBalance() - offer);
            }

            return offer;
        }
        return 0;
    }

    /**
     * Metodo para setear las transaciones ya realizadas
     * 
     * @param buyer
     * @param item
     * @param offer
     */

    public void setTransaction(Buyer buyer, Item item, float offer) {
        if (item.getBuyer().equalsIgnoreCase(buyer.getName()) && item.getPrice() == offer) {
            synchronized (this) {
                item.setAuctionInProcess(Boolean.FALSE);
                item.setAvailable(Boolean.FALSE);
                item.setSellPrice(offer);
                buyer.addItem(item);
                LogUtil.sell(item.getName(), buyer.getName(), offer, item.getName());
            }
        } else {
            synchronized (this) {
                buyer.setBalance(buyer.getBalance() + offer);
            }
        }
    }

    /**
     * Metodo para mostrar las gananacias del vendedor
     * 
     * @param seller
     */

    public void setSellerEarns(Seller seller) {
        double totalSell = 0;
        double totalEarn = 0;

        for (Item item : seller.getItems()) {
            if (Boolean.FALSE.equals(item.getAvailable())) {
                totalSell = totalSell + item.getSellPrice();
                totalEarn = totalEarn + (item.getSellPrice() * 0.10);
            }
        }

        LogUtil.sellerProducts(seller);
    }

    /**
     * Metodo para setear las ganancias del Martillero
     * 
     * @param items
     */

    public void setAuctionMasterEarns(List<Item> items) {
        float totalSell = 0;
        double totalEarn = 0;

        for (Item item : items) {
            if (Boolean.FALSE.equals(item.getAvailable())) {
                totalSell = totalSell + item.getSellPrice();
            }
        }

        if (totalSell != 0) {
            totalEarn = totalSell * 0.15;
            LogUtil.auctionMasterProducst(totalSell, totalEarn);
        }
    }

    public void setBuyerEarns() {
        for (Buyer buyer : buyers) {
            LogUtil.buyerProducts(buyer);
        }
    }

    /**
     * Metodo para verificar el fin de la subasta
     * 
     * @param bufferedReader
     * @return
     */
    public Boolean endAuction(BufferedReader bufferedReader) {
        String reader;
        try {
            reader = bufferedReader.readLine();
            while (Constant.FIN.equalsIgnoreCase(reader)) {
                reader = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Boolean.TRUE;
    }

    /**
     * Metodo para validar que la subasta sigue teniendo items y compradores con balance suficiente
     * 
     * @param items
     * @param buyers
     * @return
     */
    public Boolean auctionValidation(List<Item> items, List<Buyer> buyers) {
        Buyer validBuyer = new Buyer();

        synchronized (this) {
            Item validItem = items.stream().filter(x -> Boolean.TRUE.equals(x.getAvailable())).findFirst().orElse(null);

            if (!(Objects.nonNull(validItem) && Boolean.TRUE.equals(Util.notNullOrEmpty(validItem.getName())))) {
                return Boolean.TRUE;
            }

            for (Item item : items) {
                if (Boolean.TRUE.equals(item.getAvailable())) {
                    validBuyer = buyers.stream()
                            .filter(x -> x.getBalance() > item.getPrice() + (item.getPrice() * 0.10))
                            .findFirst()
                            .orElse(null);
                }

                if (Objects.nonNull(validBuyer) && Boolean.TRUE.equals(Util.notNullOrEmpty(validBuyer.getName()))) {
                    return Boolean.TRUE;
                }
            }
        }

        return Boolean.FALSE;

    }

    /**
     * 
     * @Author: Greudyn Velasquez.
     * @FileName: ThreadMaster.java
     * @version: 0.1
     * @Description: Clase para la creacion de subastas
     */

    class BuyerAuction {
        public void buyerOffer(Buyer buyer) {
            float offer = 0;
            synchronized (this) {
                while (offer < buyer.getBalance()) {
                    for (Item item : items) {
                        offer += setOffer(buyer, item);
                    }
                }
            }
        }
    }

    public void main() {
        String[] inArray;
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader bufferedReader = new BufferedReader(reader);
        BuyerAuction buyerAuction = new BuyerAuction();

        try {

            inValues = bufferedReader.readLine();
            while (!Constant.INICIO.equalsIgnoreCase(inValues)) {

                if (Constant.V == inValues.charAt(0)) {

                    inArray = inValues.split(" ");
                    sellers = Util.setSellers(inArray, sellers);

                } else if (Constant.C == inValues.charAt(0)) {

                    inArray = inValues.split(" ");
                    buyers = Util.setBuyers(inArray, buyers);

                } else if (Constant.S == inValues.charAt(0)) {

                    inArray = inValues.split(" ");
                    items = Util.setItems(inArray, sellers, items);
                    sellers = Util.setSellerItems(items, sellers);

                } else if (!Constant.INICIO.equalsIgnoreCase(inValues)) {

                    LogUtil.inError();
                }

                inValues = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Thread auctionMaster = new Thread(new Runnable() {
            public void run() {
                try {
                    inValues = bufferedReader.readLine();
                    while (!Constant.FIN.equalsIgnoreCase(inValues)) {
                        if (Boolean.TRUE.equals(auctionValidation(items, buyers))) {
                            break;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread auctionBuyers = new Thread(new Runnable() {

            public void run() {
                while (auctionMaster.isAlive()) {
                    synchronized (buyers) {
                        for (Buyer buyer : buyers) {
                            buyerAuction.buyerOffer(buyer);
                        }
                    }
                }
            }
        });

        auctionMaster.start();
        auctionBuyers.start();

        try {
            auctionBuyers.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (Seller seller : sellers) {
            setSellerEarns(seller);
        }

        setAuctionMasterEarns(items);

        setBuyerEarns();
    }
}
