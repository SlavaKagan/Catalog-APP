package catalog.utils;

/**
 * Final Strings to use in messages
 */
public interface FinalStrings {

    /******************************************************************
                            CODES
     ******************************************************************/

    /**************
     200-299
     **************/
    String OK= "OK";                        // code 200 - Ok
    String CREATED= "CREATED";              // code 201 - Created

    /**************
     400-499
     **************/
    String FORBIDDEN = "Accessing the resource is forbidden";                // code 403 - Forbidden
    String NOT_FOUND = "Resource not found in stock";                                 // code 404 - Not Found
    String CONFLICT = "Resource already exists with code: ";                 // code 409 - Conflict

    /**************
     500-599
     **************/
    String SERVER_ERROR = "Internal server error";                          // code 500 - Internal Server Error

    /**************
     General
     **************/
    String WITHDRAW_PROBLEM = "Can't withdraw this amount from stock";
}
