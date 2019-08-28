package geektime.spring.jedis.model;

public enum OrderState {
    INIT,
    PAID,
    BREWING,
    BREWED,
    TAKEN,
    CANCELLED
}
