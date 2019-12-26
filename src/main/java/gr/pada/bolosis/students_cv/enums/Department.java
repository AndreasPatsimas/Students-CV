package gr.pada.bolosis.students_cv.enums;

public enum Department {

    DEPARTMENT_OF_PUBLIC_HEALTH((short) 1),
    DEPARTMENT_OF_MANAGEMENT_ECONOMICS_SOCIAL_SCIENCES((short) 2),
    DEPARTMENT_OF_FOOD_SCIENCES((short) 3),
    DEPARTMENT_OF_HEALTH_AND_WELFARE_SCIENCES((short) 4),
    DEPARTMENT_OF_APPLIED_ARTS_AND_CULTURE((short) 5),
    DEPARTMENT_OF_ENGINEERING((short) 6);

    private final short code;

    Department(short v) {
        code = v;
    }

    public short code() {
        return code;
    }

    public static Department fromValue(short v) {
        for (Department c: Department.values()) {
            if (c.code == v) {
                return c;
            }
        }
        throw new IllegalArgumentException(String.valueOf(v));
    }
}
