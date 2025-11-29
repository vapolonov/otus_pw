package exceptions;

public class PageCreateException extends RuntimeException {
  public PageCreateException(String message, Throwable cause) {
    super(message, cause);
  }
}
