package com.iluwatar.saga.orchestration;

public class ChapterResult<K> {
    K value;
    State state;

    public ChapterResult(K value, State state) {
        this.value = value;
        this.state = state;
    }

    public static <K> ChapterResult<K> success(K val) {
        return new ChapterResult<>(val, State.SUCCESS);
    }
    public static <K> ChapterResult<K> failure(K val) {
        return new ChapterResult<>(val, State.FAILURE);
    }

    public enum State {
        SUCCESS, FAILURE
    }
}
