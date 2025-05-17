package com._7.hr.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDetails {
    private LocalDateTime timeStamp;
    private int status;
    private String path;
    private String message;
}
