package com.lvum;

import com.lvum.algorithms.Equivalency;
import com.lvum.algorithms.NFAToDFA;
import com.lvum.algorithms.NFAToDFAEpsilon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class NFAToDFAEpsilonTest {
    private Set<Character> language;
    private Automaton automaton;

    @BeforeEach
    void beforeEach() {
        language = new HashSet<>(Arrays.asList('0', '1', Automaton.EPSILON));
        automaton = new Automaton(language);
        automaton.addTransition("q0", "q1", Automaton.EPSILON);
        automaton.addTransition("q0", "q2", Automaton.EPSILON);
        automaton.addTransition("q1", "q3", '0');
        automaton.addTransition("q2", "q3", '1');
        automaton.addTransition("q3", "q4", '1');
        automaton.setInitialState("q0");
        automaton.addFinalState("q4");
    }

    @Test
    void deterministic() {
        // Test 2
        // The resulting automaton must not have any state with the same entry more than once
        Automaton result = automaton.run(new NFAToDFAEpsilon());

        for (Automaton.Transition transition : result.getTransitions()) {
            assertEquals(1, result.getTransitions().stream()
                    .filter(t -> t.from().equals(transition.from()))
                    .filter(t -> t.entry().equals(transition.entry()))
                    .count());
        }
    }

    @Test
    void correct() {
        Set<Character> expectedAlphabet = new HashSet<>(Arrays.asList('0', '1'));
        Automaton expected = new Automaton(expectedAlphabet);
        expected.addTransition("A", "B", '0');
        expected.addTransition("A", "B", '1');
        expected.addTransition("B", "C", '1');
        expected.setInitialState("A");
        expected.addFinalState("C");

        Automaton result = automaton.run(new NFAToDFAEpsilon());
        assertTrue(result.run(new Equivalency(expected)));
    }
}
