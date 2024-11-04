package evs.electronicvotingsystem.Constants;

public class AppConstants {

    public static final String NOT_NULL = "Can't be null";
    public static final String NOT_Blank = "Can't be blank";
    public static final String MIN_VALUE = "Can't be zero or negative";
    public static final String FUTURE_DATE = "Date should be future";

    public static final String GENDER_PATTERN = "[MmFfTt]";
    public static final String GENDER_VALIDATION_MESSAGE = "Gender must be 'M', 'F', or 'T'";

    public static final String NOT_IN_FORMAT = "Not in format";

    public static final String PAST_DATE = "Must be past date";

    public static final String MOBILE_REGEX_PATTERN = "^[0-9]{10}$";
    public static final String MOBILE_VALIDATION_MESSAGE = "Mobile number must be exactly 10 digits and contain only numbers 0-9";

    public static final String DATE_PATTERN = "yyyy-MM-dd";

    public static final String ADMIN = "ADMIN";
    public static final String VOTER = "VOTER";
    public static final String ELECTORIAL_OFFICER = "ELECTORIAL-OFFICER";

}
