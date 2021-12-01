package com.littlepay.farecalculator.dto;

import com.littlepay.farecalculator.common.LocalDateTimeConverter;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Validated
public class Taps {

    @CsvBindByName(column = "ID")
    @NotEmpty
    public String id;

    @CsvCustomBindByName(column = "DateTimeUTC", converter = LocalDateTimeConverter.class)
    @NotEmpty
    public LocalDateTime dateTimeUTC;

    @CsvBindByName(column = "TapType")
    @NotEmpty
    public String tapType;

    @CsvBindByName(column = "StopId")
    @NotEmpty
    public String stopId;

    @CsvBindByName(column = "CompanyId")
    @NotEmpty
    public String companyId;

    @CsvBindByName(column = "BusID")
    @NotEmpty
    public String busId;

    @CsvBindByName(column = "PAN")
    @NotEmpty
    public String pan;

    public Taps() {
    }

    public Taps(String id, LocalDateTime dateTimeUTC, String tapType, String stopId, String companyId, String busId, String pan) {
        this.id = id;
        this.dateTimeUTC = dateTimeUTC;
        this.tapType = tapType;
        this.stopId = stopId;
        this.companyId = companyId;
        this.busId = busId;
        this.pan = pan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id.trim();
    }

    public LocalDateTime getDateTimeUTC() {
        return dateTimeUTC;
    }

    public void setDateTimeUTC(LocalDateTime dateTimeUTC) {
        this.dateTimeUTC = dateTimeUTC;
    }

    public String getTapType() {
        return tapType;
    }

    public void setTapType(String tapType) {
        this.tapType = tapType.trim();
    }

    public String getStopId() {
        return stopId;
    }

    public void setStopId(String stopId) {
        this.stopId = stopId.trim();
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId.trim();
    }

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId.trim();
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan.trim();
    }


    @Override
    public String toString() {
        return "Taps{" +
                "ID='" + id + '\'' +
                ", DateTimeUTC='" + dateTimeUTC + '\'' +
                ", TapType='" + tapType + '\'' +
                ", StopId='" + stopId + '\'' +
                ", CompanyId='" + companyId + '\'' +
                ", BusID='" + busId + '\'' +
                ", PAN='" + pan + '\'' +
                '}';
    }
}

