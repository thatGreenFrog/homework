package lv.martins.homework.exceptions;

public class CustomException extends Exception{

    private final Integer code;

    public CustomException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
