package com.yimin.apache.hadoop;

import org.apache.hadoop.io.Text;

import java.io.Serializable;

/**
 * Created by yimin on 15/4/28.
 */
public class NDCRecordParser implements Serializable {

    private static final long serialVersionUID = 8980411335143779744L;
    private static final int MISSING_TEMPERATURE = 9999;

    private String record;
    private String year;
    private int ariTemperature;
    private String quality;

    public void parse(Text record) {
        parse(record.toString());
    }

    public void parse(String record) {
        this.record = record;

        extract();
    }

    private void extract() {
        year = record.substring(15, 19);

        if (record.charAt(87) == '+') {
            ariTemperature = Integer.parseInt(record.substring(88, 92));
        } else {
            ariTemperature = Integer.parseInt(record.substring(87, 92));
        }
        quality = record.substring(92, 93);
    }

    public String getRecord() {
        return record;
    }

    public String getYear() {
        return year;
    }

    public int getAriTemperature() {
        return ariTemperature;
    }

    public boolean isValidTemperature() {
        return ariTemperature != MISSING_TEMPERATURE && quality.matches("[01459]");
    }
}
