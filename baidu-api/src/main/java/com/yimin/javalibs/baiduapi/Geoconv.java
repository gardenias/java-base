package com.yimin.javalibs.baiduapi;

import com.google.common.base.Joiner;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WuYimin on 2015/3/18.
 */
public class Geoconv implements ServiceAPI, ServiceOut {
    private String ak;
    private String from;
    private String to;
    private String output;
    private String sn;
    private List<String> coords;

    private Geoconv(String ak, From from, To to, String output) {
        ak(ak).from(from).to(to).output(output);
        coords = new ArrayList<String>(10);
    }

    public static Geoconv on(String ak) {
        if (null == ak || ak.length() == 0) throw new IllegalArgumentException("ak invalid");
        return new Geoconv(ak, From.O1, To.F5, "json");
    }

    private Geoconv ak(String ak) {
        this.ak = ak;
        return this;
    }

    public Geoconv sn(String sn) {
        this.sn = sn;
        return this;
    }

    public Geoconv from(From from) {
        this.from = from.getFromCode();
        return this;
    }

    public Geoconv to(To to) {
        this.to = to.getToCode();
        return this;
    }

    public Geoconv xml() {
        return output("xml");
    }

    private Geoconv output(String output) {
        this.output = output;
        return this;
    }

    public ServiceOut request(Request request) throws IOException {
        return request.perform(this.getParams());
    }

    @Override
    public OutputStream getOuter() {
        return System.out;
    }

    @Override
    public String getParams() {
        return "http://api.map.baidu.com/geoconv/v1/?" + Joiner.on("&").join("coords=" + Joiner.on(";").join(this.getCoords()),
                "ak=" + getAk(),
                "from=" + getFrom(), "to=" + getTo(), "output=" + getOutput(), (sn != null ? ("sn=" + getSn()) : ""));
    }

    public Geoconv coord(String longitude, String latitude) {
        coords.add(longitude + "," + latitude);
        return this;
    }

    public String getAk() {
        return ak;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getOutput() {
        return output;
    }

    public String getSn() {
        return sn;
    }

    public List<String> getCoords() {
        return coords;
    }

    /*
        1：GPS设备获取的角度坐标;
        2：GPS获取的米制坐标、sogou地图所用坐标;
        3：google地图、soso地图、aliyun地图、mapabc地图和amap地图所用坐标
        4：3中列表地图坐标对应的米制坐标
        5：百度地图采用的经纬度坐标
        6：百度地图采用的米制坐标
        7：mapbar地图坐标;
        8：51地图坐标
         */
    public enum From {
        O1("1"),
        T2("2"),
        T3("3"),
        F4("4"),
        F5("5"),
        S6("6"),
        S7("7"),
        E8("8");
        String fromCode;

        From(String fromCode) {
            this.fromCode = fromCode;
        }

        public String getFromCode() {
            return fromCode;
        }
    }

    /*
    有两种可供选择：5、6。
    5：bd09ll(百度经纬度坐标),
    6：bd09mc(百度米制经纬度坐标);
     */
    public enum To {
        F5("5"),
        S6("6");
        String toCode;

        To(String toCode) {
            this.toCode = toCode;
        }

        public String getToCode() {
            return toCode;
        }
    }
}
