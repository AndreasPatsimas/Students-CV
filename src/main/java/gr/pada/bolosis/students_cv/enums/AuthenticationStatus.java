package gr.pada.bolosis.students_cv.enums;

public enum AuthenticationStatus {

    AUTHENTICATION_SUCCEEDED((short) 0),
    AUTHENTICATION_FAILED((short) 1),
    INACTIVE_USER((short) 2);

    private final short code;

    AuthenticationStatus(short v) {
        code = v;
    }

    public short code() {
        return code;
    }

    public static AuthenticationStatus fromValue(short v) {
        for (AuthenticationStatus c: AuthenticationStatus.values()) {
            if (c.code == v) {
                return c;
            }
        }
        throw new IllegalArgumentException(String.valueOf(v));
    }
}
