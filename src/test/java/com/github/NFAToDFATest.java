package com.github;

import com.github.lcs002.automatons.automaton.Automaton;
import com.github.lcs002.automatons.automaton.algorithms.Equivalency;
import com.github.lcs002.automatons.automaton.algorithms.conversion.NFAToDFA;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class NFAToDFATest {
    protected Automaton automaton;

    @BeforeEach
    void beforeEach() {
        automaton = new Automaton.Builder()
                .setAlphabet(Set.of('a', 'b'))
                .addTransition("q0", "q0", 'a')
                .addTransition("q0", "q1", 'a')
                .addTransition("q0", "q0", 'b')
                .addTransition("q1", "q2", 'b')
                .setInitialState("q0")
                .addFinalState("q2")
                .build();
    }

    @Test
    public void deterministic() {
        // The resulting automaton must not have any state with the same entry more than once
        Automaton result = automaton.run(new NFAToDFA());

        for (Automaton.Transition transition : result.getTransitions()) {
            assertEquals(1, result.getTransitions().stream()
                    .filter(t -> t.from().equals(transition.from()))
                    .filter(t -> t.entry().equals(transition.entry()))
                    .count());
        }
    }

    @Test
    public void correct() {
        // Given the NFA automaton:
        // Language: {a, b}
        // | State | a      | b  |
        // | ->q0  | q0, q1 | q0 |
        // | q1    |        | q2 |
        // | *q2   |        |    |
        // The resulting DFA automaton must be:
        // | State  | a     | b     |
        // | ->q0   | q0-q1 | q0    |
        // | q0-q1  | q0-q1 | q0-q2 |
        // | *q0-q2 | q0-q1 | q0    |
        // Example from: https://www.geeksforgeeks.org/conversion-from-nfa-to-dfa/

        Automaton expected = new Automaton.Builder()
                .setAlphabet(Set.of('a', 'b'))
                .addTransition("q0", "q0-q1", 'a')
                .addTransition("q0", "q0", 'b')
                .addTransition("q0-q1", "q0-q1", 'a')
                .addTransition("q0-q1", "q0-q2", 'b')
                .addTransition("q0-q2", "q0-q1", 'a')
                .addTransition("q0-q2", "q0", 'b')
                .setInitialState("q0")
                .addFinalState("q0-q2")
                .build();

        Automaton result = automaton.run(new NFAToDFA());

        assertTrue(expected.run(new Equivalency(result)));
    }
}
