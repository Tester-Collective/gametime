package enums;

import lombok.Getter;

@Getter
public enum PaymentMethod {
    GOPAY("GOPAY"),
    OVO("OVO"),
    DANA("DANA"),
    BANKTRANSFER("BANK_TRANSFER");

    private final String value;
    private PaymentMethod(String value) {
        this.value = value;
    }

    public static boolean contains(String param) {
        for (PaymentMethod paymentMethod : PaymentMethod.values()) {
            if (paymentMethod.name().equals(param)) {
                return true;
            }
        }
        return false;
    }
}