package repository;

public class RepoException extends RuntimeException {
    public RepoException(Exception e) {
        super(e);
    }

    public RepoException(String message) {
        super(message);
    }
}
