package io.github.lcs002.automatons.rex;

import io.github.lcs002.automatons.automaton.Automaton;
import jakarta.annotation.Nonnull;
import jdk.jshell.spi.ExecutionControl;

/**
 * <h2>Regular Expression</h2>
 */
public class REX {
    private final String expression;

    /**
     * Creates a new Regular Expression.
     * @param expression The regular expression.
     */
    public REX(@Nonnull String expression) {
        this.expression = expression;
    }





    @Override
    public String toString() {
        return expression;
    }
}
