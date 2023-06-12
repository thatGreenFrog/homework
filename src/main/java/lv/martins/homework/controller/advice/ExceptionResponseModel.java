package lv.martins.homework.controller.advice;

public record ExceptionResponseModel(
        String message,
        int code
) {
}
