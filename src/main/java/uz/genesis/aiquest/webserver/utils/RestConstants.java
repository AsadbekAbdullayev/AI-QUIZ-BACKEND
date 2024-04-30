package uz.genesis.aiquest.webserver.utils;

public interface RestConstants {

    String TM_BASE_URL = "/api";

    String TM_BASE_TALENT_URL = TM_BASE_URL + "/talent";

//    String TM_BASE_HR_URL = TM_BASE_URL + "/hr";
    //for temporary access we need put /admin for first part of request path, in that case we can access by admin token
    String TM_BASE_HR_URL = TM_BASE_URL + "/hr";

    String TM_BASE_ADMIN_URL = TM_BASE_URL + "/admin";

    String EMAIL_FROM = "aiquestuz@gmail.com";

    String EMAIL_FROM_METADATA = "aiquestuz@gmail.com";
    String BEARER = "Bearer ";
    String USER_TYPE = "user_type";
    String EMAIL_KEY = "email";
}
