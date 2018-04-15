package io.github.isubham.myapplication.utility;


/**
 * Created by suraj on 2/3/18.
 */

public class data_wrapper {


    //////////////////////////////
    // TEMPORARY VARIABLES
    //////////////////////////
    public static String TEMP_ADMIN_ID = "10";
    public static String TEMP_SUPERVISOR_ID = "31";
    public static String TEMP_CONTRACTER_ID = "31";

    public static String TEMP_PROJECT_ID = "31";


    // account_type
    //=============

    public final static String AC_ADMIN = "1";
    public final static String AC_CONTRACTOR = "2";
    public final static String AC_SUPERVISOR = "3";

    // worker_type
    //=============


    public static String W_TYPE_PERM_SKILLED = "1";
    public static String W_TYPE_TEMP_SKILLED = "2";
    public static String W_TYPE_PERM_SEMI_SKILLED = "3";
    public static String W_TYPE_TEMP_SEMI_SKILLED  = "4";
    public static String W_TYPE_PERM_UNSKILLED = "5";
    public static String W_TYPE_TEMP_UNSKILLED = "6";



    // query String
    //=============

    // sleek
    //
    // public static String BASE_URL_LOCAL = "https://trinetra.000webhostapp.com/test/design.php";
    //
    //public static String BASE_URL_LOCAL = "http://192.168.31.20/trinetra/design.php";
    // my android public static String BASE_URL_LOCAL = "http://192.168.43.122/trinetra/design.php";

    /* TODO : add base url */
    public static String BASE_URL_LOCAL  = "https://trinetra.000webhostapp.com/test/design.php";
    // TODO add your deploying server
    public static String BASE_URL_DEPLOY = "https://trinetra.000webhostapp.com/test/design.php";

    public static String QTYPE_I = "i";
    public static String QTYPE_O = "o";

    public static String QMODULE_USER = "user";
    public static String QMODULE_PROJECT = "project";
    public static String QMODULE_USER_PROJECT = "user_project";
    public static String QMODULE_PACKAGE = "package";
    public static String QMODULE_USER_PACKAGE = "user_package";
    public static String QMODULE_SHIFT = "shift";
    public static String QMODULE_ATTENDENCE = "attendence";
    public static String QMODULE_AUTHENTICATION = "authentication";
    public static String QMODULE_WORKER = "manpower";

    public static String QMODULE_USER_WORKER = "user_worker";





    // params

    public static String MODUL = "module";
    public static String QTYPE = "query_type";
    public static String QUERY = "query";



    // user table operations and schema for making map
    // ===========
    public static String Q_SIGN_IN = "sign_in";

    public static String Q_PARAM_USER_EMAIL = "email";
    public static String Q_PARAM_USER_PASSWORD = "password";
    public static String Q_PARAM_USER_AADHAR_ID = "aadhar_id";
    public static String Q_PARAM_USER_NAME = "name";
    public static String Q_PARAM_USER_USER_TYPE = "user_type";

    public static final String Q_ADD_USER = "add_user";

    // query create account in user table
    public static String Q_CREATE_ACCOUNT = "create_account";

    // response

    public final static String RESPONSE_EMAIL_EXISTS = "-101";
    public final static String RESPONSE_AADHAR_EXISTS = "-102";
    public final static String RESPONSE_EMAIL_AND_PASSWORD_EXISTS = "-103";





    // project table

    public static String Q_READ_PROJECTS = "read_projects";
    public static String Q_READS_PROJECT = "read_project";

    public static String Q_CREATE_PROJECT = "create_project";

    public static final String Q_ADD_USER_PROJECT = "add_user_project";
    public static final String Q_READ_USER_PROJECT = "read_user_project";

    // user project table
    public static final String EMAIL_NOT_IN_USER = "-1001";
    public static final String USER_ALREADY_IN_PROJECT = "-1002";




    // package
     public static String Q_READ_PACKAGES = "read_packages";
    public static String Q_READS_PACKAGE = "reads_package";

    public static String Q_CREATE_PACKAGE = "create_package";

     // user pacakage
    public static String Q_ADD_USER_PACKAGE = "add_user_package";

    public static String Q_C_ADD_USER_PACKAGE = "c_add_user_package";

    // raturn status


    public final static String RETURN_EMAIL_NOT_IN_USER = "-1001";
    public final static String RETURN_USER_ALREADY_IN_PROJECT = "-1002";

    public final static String RETURN_USER_ALREADY_IN_PACKAGE = "-1003";

    public final static String RETURN_USER_ADDED_IN_PACKAGE = "-1004";

    // manpower
    public static String Q_CREATE_WORKER = "create_worker";

    public final static String RETURN_WORKER_ADDED = "-2001";
    public final static String RETURN_WORKER_ALREADY_PRESENT = "-2002";


    // worker_package
    public final static String RETURN_WORKER_NOT_AVAIlABLE = "-3001";
    public final static String RETURN_WORKER_ALREADY_PRESENT_IN_PACKAGE = "-3004";
    public final static String RETURN_WORKER_ADDED_TO_PACKAGE = "-3003";


    public static String Q_READ_USER_WORKER = "read_user_worker";


    public static String Q_CREATE_SHIFT = "create_shift";
    public static String Q_READ_SHIFT = "read_shift";


    public static String Q_CREATE_ATTENDENCE = "create_attendence";
    public static String Q_READ_ATTENDENCE = "read_attendence";


    public static String Q_CREATE_AUTHENTICATION = "create_authentication";
    public static String Q_READ_AUTHENTICATION = "read_authentication";


}
