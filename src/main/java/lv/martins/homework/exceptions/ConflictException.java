package lv.martins.homework.exceptions;

public class ConflictException extends CustomException{

    private final Long duplicateId;

    public ConflictException(String message, Long duplicateId) {
        super(message, 4091);
        this.duplicateId = duplicateId;
    }

    public Long getDuplicateId() {
        return duplicateId;
    }
}
