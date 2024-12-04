package toby.springboot.learn.exception;

public class MalformedApiRequestException extends RuntimeException
{
    public MalformedApiRequestException(final String message)
    {
        super(message);
    }
}
