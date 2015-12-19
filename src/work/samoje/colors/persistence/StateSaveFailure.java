package work.samoje.colors.persistence;

import com.fasterxml.jackson.core.JsonProcessingException;

public class StateSaveFailure extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public StateSaveFailure(final String string, final JsonProcessingException e) {
        super(string, e);
    }
}
