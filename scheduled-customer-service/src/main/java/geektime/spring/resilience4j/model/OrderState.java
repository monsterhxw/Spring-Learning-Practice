package geektime.spring.resilience4j.model;

public enum OrderState {
    INIT,
    PAID,
    BREWING,
    BREWED,
    TAKEN,
    CANCELLED
}