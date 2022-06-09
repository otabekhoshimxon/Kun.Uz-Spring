package uz.kun.exception;

//User :Lenovo
//Date :09.06.2022
//Time :6:30
//Project Name :Kun.uzWithThymleaf
public class ItemNotFoundException extends RuntimeException {

    public ItemNotFoundException(String message) {
        super(message);
    }
}