package com.wavemaker.api.client.constants;

/**
 * This class contains all Database run time related URL's
 * Created by tejaswim on 9/27/2016.
 */
public class DatabaseRuntimeUrlConstants {

    private static final String STUDIO_RUNTIME_COMMON_URL = "/:runtimeProjectId/:projectName/services/:dbName/:tableName";
    private static final String STUDIO_QUERY_RUNTIME_URL = "/:runtimeProjectId/:projectName/services/:dbName/queryExecutor/queries";
    private static final String STUDIO_PROCEDURE_RUNTIME_URL = "/:runtimeProjectId/:projectName/services/:dbName/procedureExecutor/procedure/execute";

    //Runtime Queries Urls
    public static final String QUERY_WITH_PARAMS_URL = STUDIO_QUERY_RUNTIME_URL + "/:queryName?:queryParamsString";
    public static final String QUERY_URL = STUDIO_QUERY_RUNTIME_URL + "/:queryName";
    //Runtime Procedure Urls
    public static final String PROCEDURE_URL = STUDIO_PROCEDURE_RUNTIME_URL+"/:procedureName";
    public static final String PROCEDURE_WITH_PARAMS_URL = STUDIO_PROCEDURE_RUNTIME_URL+"/:procedureName?:procedureParamsString";
    //Runtime urls
    public static final String ALL_RECORDS_URL = STUDIO_RUNTIME_COMMON_URL;
    public static final String EXPORT_URL = STUDIO_RUNTIME_COMMON_URL+"/export/:exportType";
    public static final String RECORDS_BY_QUERY_STRING_URL = STUDIO_RUNTIME_COMMON_URL + "?q=:queryString";
    public static final String RECORDS_COUNT_URL = STUDIO_RUNTIME_COMMON_URL + "/count";
    public static final String RECORDS_COUNT_BY_QUERY_STRING_URL = STUDIO_RUNTIME_COMMON_URL + "/count?q=:queryString";
    public static final String RECORD_BY_UNIQUE_KEY_URL = STUDIO_RUNTIME_COMMON_URL + "/:columnName/:columnValue";
    public static final String RECORD_BY_PK_COLUMN_URL = STUDIO_RUNTIME_COMMON_URL + "/:pkColumnValue";
    public static final String RECORD_BY_RELATED_COLUMN_URL = STUDIO_RUNTIME_COMMON_URL + "/:parentTableColumnValue/:relatedObjectName";
    public static final String RECORD_BY_PAGE_SIZE_SORT_URL = STUDIO_RUNTIME_COMMON_URL + "?q=:queryString&" +
            "page=:pageNumber&size=:pageSize&sort=:sortColumn";
    //School DB run time Urls
    public static final String RECORDS_COMPOSITE_KEY_URL = STUDIO_RUNTIME_COMMON_URL + "/composite-id?:compositeString";

    //filter by query string URLs
    public static final String ALL_RECORDS_URL_BY_FILTER = "/:runtimeProjectId/:projectName/services/:dbName/:tableName/filter?page=:page&size=:size&sort=:sort";
}
