package lv.martins.homework.exceptions;

public class NotFoundException extends CustomException{

    public NotFoundException(String message) {
        super(message, 4041);
    }

}
