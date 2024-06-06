package com.crud.tasks.domain;

import lombok.*;

@Getter
@Builder
public class Mail {
    @NonNull
    private final String mailTo;
    @NonNull
    private final String subject;
    @NonNull
    private final String message;
    private String toCc;
}
