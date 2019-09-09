package geektime.spring.hystrix.model;

public enum OrderState {
    INIT,
    PAID,
    BREWING,
    BREWED,
    TAKEN,
    CANCELLED
}