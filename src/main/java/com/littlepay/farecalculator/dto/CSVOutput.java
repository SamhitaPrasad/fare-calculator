package com.littlepay.farecalculator.dto;

import com.littlepay.farecalculator.common.LocalDateTimeConverter;
import com.littlepay.farecalculator.enums.TripStatus;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByName;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Validated
public class CSVOutput {

    @CsvCustomBindByName(column = "Started", converter = LocalDateTimeConverter.class)
    @CsvBindByPosition(position = 0)
    @NotNull
    private LocalDateTime started;

    @CsvCustomBindByName(column = "Finished", converter = LocalDateTimeConverter.class)
    @CsvBindByPosition(position = 1)
    @NotNull
    private LocalDateTime finished;

    @CsvBindByName(column = "DurationSecs")
    @CsvBindByPosition(position = 2)
    @NotNull
    private long durationSecs;

    @CsvBindByName(column = "FromStopId")
    @CsvBindByPosition(position = 3)
    @NotNull
    private String fromStopId;

    @CsvBindByName(column = "ToStopId")
    @CsvBindByPosition(position = 4)
    @NotNull
    private String toStopId;

    @CsvBindByName(column = "ChargeAmount")
    @CsvBindByPosition(position = 5)
    @NotNull
    private double chargeAmount;

    @CsvBindByName(column = "CompanyId")
    @CsvBindByPosition(position = 6)
    @NotNull
    private String companyId;

    @CsvBindByName(column = "BusID")
    @CsvBindByPosition(position = 7)
    @NotNull
    private String busID;

    @CsvBindByName(column = "PAN")
    @CsvBindByPosition(position = 8)
    @NotNull
    private String pan;

    @CsvBindByName(column = "Status")
    @CsvBindByPosition(position = 9)
    @NotNull
    private TripStatus status;

    public LocalDateTime getStarted() {
        return started;
    }

    public void setStarted(LocalDateTime started) {
        this.started = started;
    }

    public LocalDateTime getFinished() {
        return finished;
    }

    public void setFinished(LocalDateTime finished) {
        this.finished = finished;
    }

    public long getDurationSecs() {
        return durationSecs;
    }

    public void setDurationSecs(long durationSecs) {
        this.durationSecs = durationSecs;
    }

    public String getFromStopId() {
        return fromStopId;
    }

    public void setFromStopId(String fromStopId) {
        this.fromStopId = fromStopId;
    }

    public String getToStopId() {
        return toStopId;
    }

    public void setToStopId(String toStopId) {
        this.toStopId = toStopId;
    }

    public double getChargeAmount() {
        return chargeAmount;
    }

    public void setChargeAmount(double chargeAmount) {
        this.chargeAmount = chargeAmount;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getBusID() {
        return busID;
    }

    public void setBusID(String busID) {
        this.busID = busID;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public TripStatus getStatus() {
        return status;
    }

    public void setStatus(TripStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CSVOutput{" +
                "started=" + started +
                ", finished=" + finished +
                ", durationSecs=" + durationSecs +
                ", fromStopId='" + fromStopId + '\'' +
                ", toStopId='" + toStopId + '\'' +
                ", chargeAmount=" + chargeAmount +
                ", companyId='" + companyId + '\'' +
                ", busID='" + busID + '\'' +
                ", pan='" + pan + '\'' +
                ", status=" + status +
                '}';
    }
}
