package pe.utp.marcodesarrolloweb.dto;

/**
 * @author Alonso
 */
public record RegisterRequest(String firstName, String lastName,
                              String email, String password) {}
