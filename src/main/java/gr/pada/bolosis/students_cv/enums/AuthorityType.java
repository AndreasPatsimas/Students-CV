package gr.pada.bolosis.students_cv.enums;

public enum AuthorityType {

    ROLE_STUDENT(1, "ROLE_STUDENT"),
    ROLE_COMPANY(2, "ROLE_COMPANY");

    private final int code;

    private final String description;

    AuthorityType(int code, String description) {
        this.code = code;

        this.description = description;
    }

    public int code() {
        return code;
    }

    public String description() {
        return description;
    }

    public static AuthorityType fromCode(int v) {
        for (AuthorityType c: AuthorityType.values()) {
            if (c.code == v) {
                return c;
            }
        }
        throw new IllegalArgumentException(String.valueOf(v));
    }

}

