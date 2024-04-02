package np.com.bigbracktes.jobhive.entities.users.enums;

public enum Role {
    JOB_SEEKER("ROLE_JOB_SEEKER"), EMPLOYER("ROLE_EMPLOYER"), ADMIN("ROLE_ADMIN");

    private String value;

    Role(String value) {
        this.value =value;
    }

    public String getValue() {
        return this.value;
    }
}
