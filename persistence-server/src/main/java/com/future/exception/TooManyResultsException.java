package com.future.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 功能描述:
 *
 * @author future
 * @date 2021-07-31 16:26
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TooManyResultsException extends RuntimeException {

    public TooManyResultsException() {

    }

    public TooManyResultsException(String message) {
        super(message);
    }
}
