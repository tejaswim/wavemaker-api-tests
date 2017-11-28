package com.wavemaker.api.rest.models.database.postgres;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Objects;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Tejaswi Maryala on 11/28/2017.
 */
public class AllTypes {

    private Integer id;
    private String clobColumn;
    private Boolean booleanColumn;
    private String textColumn;
    private Integer integerColumn;
    private Short shortColumn;
    private Long longColumn;
    private BigDecimal doubleColumn;
    private Double floatColumn;
    private Long bigIntegerColumn;
    private BigDecimal bigDecimalColumn;
    private String stringColumn;
    private String characterColumn;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private byte[] blobColumn;
    private Date dateColumn;
    private Date dateDatetimeColumn;
    private Date dateTimestampColumn;
    private Time timeColumn;
    private Timestamp timestampColumn;
    @Type(type = "DateTime")
    private LocalDateTime datetimeColumn;
    private Short byteColumn;
    @Type(type = "DateTime")
    private LocalDateTime datetimeTimestampColumn;
    private Timestamp timestampDatetimeColumn;


    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getClobColumn() {
        return clobColumn;
    }

    public void setClobColumn(final String clobColumn) {
        this.clobColumn = clobColumn;
    }

    public Boolean getBooleanColumn() {
        return booleanColumn;
    }

    public void setBooleanColumn(final Boolean booleanColumn) {
        this.booleanColumn = booleanColumn;
    }

    public String getTextColumn() {
        return textColumn;
    }

    public void setTextColumn(final String textColumn) {
        this.textColumn = textColumn;
    }

    public Integer getIntegerColumn() {
        return integerColumn;
    }

    public void setIntegerColumn(final Integer integerColumn) {
        this.integerColumn = integerColumn;
    }

    public Short getShortColumn() {
        return shortColumn;
    }

    public void setShortColumn(final Short shortColumn) {
        this.shortColumn = shortColumn;
    }

    public Long getLongColumn() {
        return longColumn;
    }

    public void setLongColumn(final Long longColumn) {
        this.longColumn = longColumn;
    }

    public BigDecimal getDoubleColumn() {
        return doubleColumn;
    }

    public void setDoubleColumn(final BigDecimal doubleColumn) {
        this.doubleColumn = doubleColumn;
    }

    public Double getFloatColumn() {
        return floatColumn;
    }

    public void setFloatColumn(final Double floatColumn) {
        this.floatColumn = floatColumn;
    }

    public Long getBigIntegerColumn() {
        return bigIntegerColumn;
    }

    public void setBigIntegerColumn(final Long bigIntegerColumn) {
        this.bigIntegerColumn = bigIntegerColumn;
    }

    public BigDecimal getBigDecimalColumn() {
        return bigDecimalColumn;
    }

    public void setBigDecimalColumn(final BigDecimal bigDecimalColumn) {
        this.bigDecimalColumn = bigDecimalColumn;
    }

    public String getStringColumn() {
        return stringColumn;
    }

    public void setStringColumn(final String stringColumn) {
        this.stringColumn = stringColumn;
    }

    public String getCharacterColumn() {
        return characterColumn;
    }

    public void setCharacterColumn(final String characterColumn) {
        this.characterColumn = characterColumn;
    }

    public byte[] getBlobColumn() {
        return blobColumn;
    }

    public void setBlobColumn(final byte[] blobColumn) {
        this.blobColumn = blobColumn;
    }

    public Date getDateColumn() {
        return dateColumn;
    }

    public void setDateColumn(final Date dateColumn) {
        this.dateColumn = dateColumn;
    }

    public Date getDateDatetimeColumn() {
        return dateDatetimeColumn;
    }

    public void setDateDatetimeColumn(final Date dateDatetimeColumn) {
        this.dateDatetimeColumn = dateDatetimeColumn;
    }

    public Date getDateTimestampColumn() {
        return dateTimestampColumn;
    }

    public void setDateTimestampColumn(final Date dateTimestampColumn) {
        this.dateTimestampColumn = dateTimestampColumn;
    }

    public Time getTimeColumn() {
        return timeColumn;
    }

    public void setTimeColumn(final Time timeColumn) {
        this.timeColumn = timeColumn;
    }

    public Timestamp getTimestampColumn() {
        return timestampColumn;
    }

    public void setTimestampColumn(final Timestamp timestampColumn) {
        this.timestampColumn = timestampColumn;
    }

    public LocalDateTime getDatetimeColumn() {
        return datetimeColumn;
    }

    public void setDatetimeColumn(final LocalDateTime datetimeColumn) {
        this.datetimeColumn = datetimeColumn;
    }

    public Short getByteColumn() {
        return byteColumn;
    }

    public void setByteColumn(final Short byteColumn) {
        this.byteColumn = byteColumn;
    }

    public LocalDateTime getDatetimeTimestampColumn() {
        return datetimeTimestampColumn;
    }

    public void setDatetimeTimestampColumn(final LocalDateTime datetimeTimestampColumn) {
        this.datetimeTimestampColumn = datetimeTimestampColumn;
    }

    public Timestamp getTimestampDatetimeColumn() {
        return timestampDatetimeColumn;
    }

    public void setTimestampDatetimeColumn(final Timestamp timestampDatetimeColumn) {
        this.timestampDatetimeColumn = timestampDatetimeColumn;
    }

    @Override
    public String toString() {
        return "AllTypes{" +
                "id=" + id +
                ", clobColumn='" + clobColumn + '\'' +
                ", booleanColumn=" + booleanColumn +
                ", textColumn='" + textColumn + '\'' +
                ", integerColumn=" + integerColumn +
                ", shortColumn=" + shortColumn +
                ", longColumn=" + longColumn +
                ", doubleColumn=" + doubleColumn +
                ", floatColumn=" + floatColumn +
                ", bigIntegerColumn=" + bigIntegerColumn +
                ", bigDecimalColumn=" + bigDecimalColumn +
                ", stringColumn='" + stringColumn + '\'' +
                ", characterColumn='" + characterColumn + '\'' +
                ", blobColumn=" + Arrays.toString(blobColumn) +
                ", dateColumn=" + dateColumn +
                ", dateDatetimeColumn=" + dateDatetimeColumn +
                ", dateTimestampColumn=" + dateTimestampColumn +
                ", timeColumn=" + timeColumn +
                ", timestampColumn=" + timestampColumn +
                ", datetimeColumn=" + datetimeColumn +
                ", byteColumn=" + byteColumn +
                ", datetimeTimestampColumn=" + datetimeTimestampColumn +
                ", timestampDatetimeColumn=" + timestampDatetimeColumn +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof AllTypes)) return false;
        final AllTypes allTypes = (AllTypes) o;
        return Objects.equals(getId(), allTypes.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
