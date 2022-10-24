package NWeek_Mission.Week_Mission.member.exception;

public class SignupUsernameDuplicatedException extends RuntimeException {
    public SignupUsernameDuplicatedException(String message) {
        super(message);
    }
}
