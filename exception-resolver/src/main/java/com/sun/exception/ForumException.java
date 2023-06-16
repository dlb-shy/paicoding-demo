package com.sun.exception;

import com.sun.dto.Status;
import lombok.Getter;

/**
 * @author sunshine
 * @date 2023/4/23 下午6:19
 */
public class ForumException extends RuntimeException {
    @Getter
    private Status status;
    public ForumException(Status status) {
        this.status = status;
    }
    public ForumException(int code, String msg) {
        this.status = Status.newStatus(code, msg);
    }
}
