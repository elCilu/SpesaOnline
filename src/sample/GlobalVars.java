package sample;

import models.ProductModel;

import java.util.SortedMap;
import java.util.TreeMap;

public class GlobalVars {
    public static int USER_ID;
    public static String OS = System.getProperty("os.name").toLowerCase();
    public static SortedMap<ProductModel, Integer> cart = new TreeMap<>();

}
