package com.sistema.blog.utils;

/**
 * Application-wide constants used as default values for pagination
 * and sorting query parameters.
 */
public class AppConstants {

    /** Default page index (zero-based). Used when {@code pageNo} is not specified. */
    public static final String PAGE_NUMBER = "0";

    /** Default number of items per page. Used when {@code pageSize} is not specified. */
    public static final String PAGE_SIZE   = "10";

    /** Default field to sort results by. Used when {@code sortBy} is not specified. */
    public static final String SORT_BY     = "id";

    /** Default sort direction. Used when {@code sortDir} is not specified. */
    public static final String SORT_DIR    = "asc";

}
