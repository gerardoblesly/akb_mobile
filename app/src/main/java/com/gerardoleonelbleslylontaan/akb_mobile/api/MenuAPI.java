package com.gerardoleonelbleslylontaan.akb_mobile.api;

public class MenuAPI {
    public static final String ROOT_URL   = "http://192.168.18.6:8000/";
    public static final String ROOT_API   = ROOT_URL+ "api/";

    public static final String URL_SELECT_EVENT = ROOT_API + "menu";
    public static final String URL_ADD_ORDER = ROOT_API + "detailTransaksi";
    public static final String URL_GET_ORDER = ROOT_API + "getAllItem/";
    public static final String URL_END_ORDER = ROOT_API + "endTransaction/";
    public static final String URL_QR = ROOT_API + "afterScan/";
}
