package com.wavemaker.tests.api.builder;

import java.math.BigDecimal;

import org.apache.commons.lang3.RandomStringUtils;

import com.wavemaker.tests.api.rest.models.database.postgres.AllTypes;

/**
 * Created by Tejaswi Maryala on 11/28/2017.
 */
public class PostgresDBObjectsBuilder {

    public static AllTypes buildAllTypes() {
        AllTypes allTypes = new AllTypes();
        allTypes.setId(Integer.valueOf(RandomStringUtils.randomNumeric(5)));
        allTypes.setIntegerColumn(1233);
        allTypes.setBigDecimalColumn(BigDecimal.valueOf(12341.98));
        allTypes.setBigIntegerColumn((long) 121321312);
        allTypes.setBooleanColumn(true);
        allTypes.setCharacterColumn("?");
        allTypes.setClobColumn("Wavemaker creates waves");
        allTypes.setDoubleColumn(BigDecimal.valueOf(12345.77));
        allTypes.setFloatColumn(123.88);
        allTypes.setLongColumn((long) 131231323);
        allTypes.setStringColumn(RandomStringUtils.randomAlphabetic(5));
        allTypes.setTextColumn("textColumn");
//        allTypes.setDateColumn(new LocalDateTime());
//        allTypes.setDatetimeColumn(new LocalDateTime());
//        allTypes.setTimeColumn(new LocalDateTime());
//        allTypes.setTimestampColumn(new Timestamp(1511269860));
        allTypes.setByteColumn((short) 12);
        allTypes.setShortColumn((short) 1231);
        return allTypes;
    }

    public static AllTypes buildAllTypes(Integer pkColumn) {
        AllTypes allTypes = new AllTypes();
        allTypes.setId(pkColumn);
        allTypes.setIntegerColumn(1233);
        allTypes.setBigDecimalColumn(BigDecimal.valueOf(12341.98));
        allTypes.setBigIntegerColumn((long) 121321312);
        allTypes.setBooleanColumn(true);
        allTypes.setCharacterColumn("?");
        allTypes.setClobColumn("Wavemaker creates waves");
        allTypes.setDoubleColumn(BigDecimal.valueOf(12345.77));
        allTypes.setFloatColumn(123.88);
        allTypes.setLongColumn((long) 131231323);
        allTypes.setStringColumn(RandomStringUtils.randomAlphabetic(5));
        allTypes.setTextColumn("textColumn");
//        allTypes.setDateColumn(new LocalDateTime());
//        allTypes.setDatetimeColumn(new LocalDateTime());
//        allTypes.setTimeColumn(new LocalDateTime());
//        allTypes.setTimestampColumn(new Timestamp(1511269860));
        allTypes.setByteColumn((short) 12);
        allTypes.setShortColumn((short) 1231);
        return allTypes;
    }

}
