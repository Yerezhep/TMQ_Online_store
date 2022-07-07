package kz.tmq.tmq_online_store.constant;

public class AuthConstant {

    private static final String DOMAIN_URL = "http://localhost:8080/api/v1/auth";

    public static final String ACTIVATE_URL = DOMAIN_URL + "/register/activate/";
    public static final String ACTIVATE_ATTRIBUTE = "activationUrl";
    public static final String ACTIVATE_SUBJECT = "Activate account";
    public static final String ACTIVATE_TEMPLATE_NAME = "activate-user";

    public static final String RESET_PASSWORD_URL = DOMAIN_URL + "/reset-password/";
    public static final String RESET_PASSWORD_ATTRIBUTE = "resetPasswordUrl";
    public static final String RESET_PASSWORD_SUBJECT = "Reset password";
    public static final String RESET_PASSWORD_TEMPLATE_NAME = "reset-password";

    public static final String ACCOUNT_SUCCESSFULLY_ACTIVATED_MESSAGE = "Account was successfully activated: %s";
    public static final String PASSWORDS_DO_NOT_MATCH_MESSAGE = "Passwords do not match";
    public static final String FIELD_IS_NOT_VALID_MESSAGE = "%s field is not valid";
    public static final String USER_NOT_ACTIVATED_MESSAGE = "User not activated: %s";
    public static final String RESET_PASSWORD_LINK_SEND_MESSAGE = "Reset password link was sent to %s";
    public static final String PASSWORD_SUCCESSFULLY_CHANGED_MESSAGE = "Password successfully changed for user: %s";

    public static final String USER = "User";
    public static final String ROLE = "Role";
    public static final String ID = "id";
    public static final String USERNAME = "username";
    public static final String EMAIL = "email";
    public static final String EMAIL_USERNAME = "email/username";
    public static final String PASSWORD_RESET_CODE = "Password Reset Code";
    public static final String ACTIVATION_CODE = "Activation Code";
    public static final String NAME = "name";

    public static final String BAD_CREDENTIALS_MESSAGE = "Invalid Email/Username/Password. Please, try again";

}
