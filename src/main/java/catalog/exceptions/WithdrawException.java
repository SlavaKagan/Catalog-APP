package catalog.exceptions;

public class WithdrawException extends RuntimeException {
    public WithdrawException() {
            super();
        }
        public WithdrawException(String message) {
            super(message);
        }
        public WithdrawException(Throwable cause) {
            super(cause);
        }
        public WithdrawException(String message, Throwable cause) {
            super(message, cause);
        }
}
