package exception;

import enumeration.RpcError;

/**
 * @author yooyep
 * @create 2021-06-15 21:11
 */
public class RpcException extends RuntimeException {
    public RpcException(RpcError error, String detail) {
        super(error.getMessage() + ": " + detail);
    }

    public RpcException(String message, Throwable cause) {
        super(message, cause);
    }

    public RpcException(RpcError error) {
        super(error.getMessage());
    }
}
