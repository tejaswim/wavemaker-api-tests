package com.wavemaker.api.tests.builder;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.joda.time.LocalDateTime;

import com.wavemaker.api.rest.models.database.wmstudio.AllTypes;
import com.wavemaker.api.utils.ApiUtils;

/**
 * Created by Tejaswi Maryala on 11/21/2017.
 */
public class DBObjectsBuilder {

    public static AllTypes buildAllTypes() {
        AllTypes allTypes = new AllTypes();
        allTypes.setPkId(Integer.valueOf(RandomStringUtils.randomNumeric(5)));
        allTypes.setIntegerColumn(1233);
        allTypes.setBigdecimalColumn(BigDecimal.valueOf(12341.98));
        allTypes.setBigintegerColumn(BigInteger.valueOf(121321312));
//        allTypes.setBlobColumn(getBlobData());
        allTypes.setBooleanColumn("Y");
        allTypes.setCharacterColumn("?");
        allTypes.setClobColumn("Wavemaker creates waves");
//        allTypes.setDateColumn(new LocalDateTime());
//        allTypes.setDatetimeColumn(new LocalDateTime());
        allTypes.setDoubleColumn(BigDecimal.valueOf(12345.77));
        allTypes.setFloatColumn((float) 123.88);
        allTypes.setLongColumn((long) 131231323);
        allTypes.setStringColumn(RandomStringUtils.randomAlphabetic(5));
        allTypes.setTextColumn("textColumn");
//        allTypes.setTimeColumn(new LocalDateTime());
        allTypes.setTimestampColumn(new Timestamp(1511269860));
        allTypes.setByteColumn((byte) 12);
        allTypes.setShortColumn((short) 1231);
        return allTypes;
    }

    public static AllTypes buildAllTypes(Integer pkColumn) {
        AllTypes allTypes = new AllTypes();
        allTypes.setPkId(pkColumn);
        allTypes.setIntegerColumn(1233);
        allTypes.setBigdecimalColumn(BigDecimal.valueOf(12341.98));
        allTypes.setBigintegerColumn(BigInteger.valueOf(121321312));
//        allTypes.setBlobColumn(getBlobData());
        allTypes.setBooleanColumn("Y");
        allTypes.setCharacterColumn("?");
        allTypes.setClobColumn("Wavemaker creates waves");
//        allTypes.setDateColumn(new LocalDateTime());
//        allTypes.setDatetimeColumn(new LocalDateTime());
        allTypes.setDoubleColumn(BigDecimal.valueOf(12345.77));
        allTypes.setFloatColumn((float) 123.88);
        allTypes.setLongColumn((long) 131231323);
        allTypes.setStringColumn(RandomStringUtils.randomAlphabetic(5));
        allTypes.setTextColumn("textColumn");
//        allTypes.setTimeColumn(new LocalDateTime());
        allTypes.setTimestampColumn(new Timestamp(1511269860));
        allTypes.setByteColumn((byte) 12);
        allTypes.setShortColumn((short) 1231);
        return allTypes;
    }

    public static byte[] getBlobData(){
        File file = ApiUtils.getFile("images/wmlogo.png");
        byte[] byteArray = new byte[(int) file.length()];
        try {
            byteArray = FileUtils.readFileToByteArray(file);
        }catch(Exception e){
            e.printStackTrace();
        }
        return byteArray;
    }
}
