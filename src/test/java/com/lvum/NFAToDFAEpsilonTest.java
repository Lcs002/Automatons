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
        automaton.addTransition("A", "B", '0');
        automaton.addTransition("A", "C", '0');
        automaton.addTransition("A", "A", '1');
        automaton.addTransition("A", "B", Automaton.EPSILON);
        automaton.addTransition("B", "B", '1');
        automaton.addTransition("B", "C", Automaton.EPSILON);
        automaton.addTransition("C", "C", '0');
        automaton.addTransition("C", "C", '1');
        automaton.setInitialState("A");
        automaton.addFinalState("C");
    }

    @Test
    void sameLanguage() {
        // Test 1
        // The language of the automaton must not change after the conversion
        Automaton result = automaton.run(new NFAToDFAEpsilon());
        assertEquals(result.getAlphabet(), language);
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
    void equivalent() {
        // Test 3
        // The resulting automaton must be equivalent to the original one
        Automaton result = automaton.run(new NFAToDFAEpsilon());
        System.out.println(result);
        assertTrue(automaton.run(new Equivalency(result)));
    }

    @Test
    void correct() {
        Automaton expected = new Automaton(language);
        expected.addTransition("A-B-C", "B-C", '0');
        expected.addTransition("A-B-C", "A-B-C", '1');
    }
}
