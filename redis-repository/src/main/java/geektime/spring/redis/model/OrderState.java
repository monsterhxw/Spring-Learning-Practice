package geektime.spring.redis.model;

public enum OrderState {
    INIT,
    PAID,
    BREWING,
    BREWED,
    TAKEN,
    CANCELLED
}
