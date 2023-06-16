package com.sun.exception;

import com.sun.dto.Status;
import com.sun.dto.StatusEnum;
import lombok.Getter;

/**
 * @author sunshine
 * @date 2023/4/24 下午3:16
 */
public class ForumAdviceException extends RuntimeException{
    @Getter
    private Status status;

    public ForumAdviceException(Status status) {
        this.status = status;
    }
    public ForumAdviceException(int code, String msg) {
        this.status = Status.newStatus(code, msg);
    }
    public ForumAdviceException(StatusEnum statusEnum, Object... args){
        this.status = Status.newStatus(statusEnum, args);
    }

}
