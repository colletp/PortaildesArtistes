package com.unamur.portaildesartistes.wsartiste;

import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class MyRoutingDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return "Test";
    }

    public void initDataSources(DataSource ds1) {
        Map<Object, Object> dsMap = new HashMap<>();
        dsMap.put("Test", ds1);
        this.setTargetDataSources(dsMap);
    }

}