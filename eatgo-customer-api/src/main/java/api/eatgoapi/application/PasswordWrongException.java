package api.eatgoapi.application;

public class PasswordWrongException extends RuntimeException{
    PasswordWrongException(){
        super("Password is wrong");
    }
}
