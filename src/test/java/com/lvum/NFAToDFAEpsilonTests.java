package com.lvum;

import com.lvum.Automaton;
import com.lvum.algorithms.Equivalency;
import com.lvum.algorithms.NFAToDFA;
import com.lvum.algorithms.NFAToDFAEpsilon;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NFAToDFAEpsilonTests {
    @Test
    void sameLanguage() {
        // Test 1
        // The language of the automaton must not change after the conversion
        Set<Character> language = new HashSet<>(Arrays.asList('0', '1'));
        Automaton automaton = new Automaton(language);

        automaton.addTransition("S1", "S2", '0');
        automaton.addTransition("S1", "S1", '0');
        automaton.addTransition("S1", "S1", '1');
        automaton.addTransition("S2", "S1", '0');
        automaton.addTransition("S2", "S2", '1');
        automaton.setInitialState("S1");
        automaton.addFinalState("S1");

        Automaton result = automaton.run(new NFAToDFAEpsilon());

        assertEquals(result.getLanguage(), language);
    }

    @Test
    void deterministic() {
        // Test 2
        // The resulting automaton must not have any state with the same entry more than once
        Set<Character> language = new HashSet<>(Arrays.asList('0', '1'));
        Automaton automaton = new Automaton(language);

        automaton.addTransition("S1", "S2", '0');
        automaton.addTransition("S1", "S1", '0');
        automaton.addTransition("S1", "S1", '1');
        automaton.addTransition("S2", "S1", '0');
        automaton.addTransition("S2", "S2", '1');
        automaton.setInitialState("S1");
        automaton.addFinalState("S1");

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
        Set<Character> language = new HashSet<>(Arrays.asList('0', '1'));
        Automaton original = new Automaton(language);

        original.addTransition("S1", "S2", '0');
        original.addTransition("S1", "S1", '0');
        original.addTransition("S1", "S1", '1');
        original.addTransition("S2", "S1", '0');
        original.addTransition("S2", "S2", '1');
        original.setInitialState("S1");
        original.addFinalState("S1");

        Automaton result = original.run(new NFAToDFA());

        assertTrue(original.run(new Equivalency(result)));
    }
}
