package io.github.lcs002.automatons.rex;

import io.github.lcs002.automatons.automaton.Automaton;

public class AutomatonToREX implements io.github.lcs002.automatons.Method<REX> {
    private final Automaton automaton;


    public AutomatonToREX(Automaton automaton) {
        this.automaton = automaton;
    }

    @Override
    public REX call() {
        return null;
    }
}
