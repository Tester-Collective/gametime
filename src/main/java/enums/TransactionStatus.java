package enums;

import lombok.Getter;

@Getter
public enum TransactionStatus {
    FAILED("FAILED"),
    SUCCESS("SUCCESS");

    private final String value;

    private TransactionStatus(String value) {
        this.value = value;
    }

    public static boolean contains(String param) {
        for (TransactionStatus transactionStatus : TransactionStatus.values()) {
            if (transactionStatus.name().equals(param)) {
                return true;
            }
        }
        return false;
    }
}
