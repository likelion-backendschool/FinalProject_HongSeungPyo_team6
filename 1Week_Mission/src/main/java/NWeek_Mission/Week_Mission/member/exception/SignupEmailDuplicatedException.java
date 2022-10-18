package NWeek_Mission.Week_Mission.member.exception;

public class SignupEmailDuplicatedException extends RuntimeException {
    public SignupEmailDuplicatedException(String message) {
        super(message);
    }
}
