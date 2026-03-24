package org.example.be_dimuadi.Exception;

public class AppException extends RuntimeException {
    private ErrorCode errorCode;
    public AppException(ErrorCode errorCode, String messages)
    {
        super(messages);
        this.errorCode=errorCode;
    }

    public ErrorCode getErrorCode()
    {
        return errorCode;
    }

}
