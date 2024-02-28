package com.lvum;

import com.lvum.automaton.Automata;
import com.lvum.automaton.algorithms.Equivalency;
import com.lvum.automaton.algorithms.NFAToDFAEpsilon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class NFAToDFAEpsilonTest {
    private Set<Character> language;
    private Automata automata;

    @BeforeEach
    void beforeEach() {
        language = new HashSet<>(Arrays.asList('0', '1', Automata.EPSILON));
        automata = new Automata(language);
        automata.addTransition("q0", "q1", Automata.EPSILON);
        automata.addTransition("q0", "q2", Automata.EPSILON);
        automata.addTransition("q1", "q3", '0');
        automata.addTransition("q2", "q3", '1');
        automata.addTransition("q3", "q4", '1');
        automata.setInitialState("q0");
        automata.addFinalState("q4");
    }

    @Test
    void deterministic() {
        // Test 2
        // The resulting automata must not have any state with the same entry more than once
        Automata result = automata.run(new NFAToDFAEpsilon());

        for (Automata.Transition transition : result.getTransitions()) {
            assertEquals(1, result.getTransitions().stream()
                    .filter(t -> t.from().equals(transition.from()))
                    .filter(t -> t.entry().equals(transition.entry()))
                    .count());
        }
    }

    @ParameterizedTest
    @MethodSource("correctArgs")
    void correct(Automata automata, Automata expected) {
        Automata result = automata.run(new NFAToDFAEpsilon());
        assertTrue(result.run(new Equivalency(expected)));
    }

    static Stream<Arguments> correctArgs() {
        return Stream.of(
                Arguments.of(
                        automaton1(),
                        expected1()
                ),
                Arguments.of(
                        automaton2(),
                        expected2()
                )
        );
    }

    static Automata automaton1() {
        Set<Character> language = new HashSet<>(Arrays.asList('0', '1', Automata.EPSILON));
        Automata automata = new Automata(language);
        automata.addTransition("q0", "q1", Automata.EPSILON);
        automata.addTransition("q0", "q2", Automata.EPSILON);
        automata.addTransition("q1", "q3", '0');
        automata.addTransition("q2", "q3", '1');
        automata.addTransition("q3", "q4", '1');
        automata.setInitialState("q0");
        automata.addFinalState("q4");
        return automata;
    }

    static Automata expected1() {
        Set<Character> language = new HashSet<>(Arrays.asList('0', '1'));
        Automata automata = new Automata(language);
        automata.addTransition("A", "B", '0');
        automata.addTransition("A", "B", '1');
        automata.addTransition("B", "C", '1');
        automata.setInitialState("A");
        automata.addFinalState("C");
        return automata;
    }


    static Automata automaton2() {
        Set<Character> language = new HashSet<>(Arrays.asList('a', 'b', Automata.EPSILON));
        Automata automata = new Automata(language);
        automata.addTransition("q0", "q1", Automata.EPSILON);
        automata.addTransition("q0", "q2", Automata.EPSILON);
        automata.addTransition("q1", "q3", 'a');
        automata.addTransition("q1", "q1", 'b');
        automata.addTransition("q3", "q3", 'a');
        automata.addTransition("q3", "q0", 'b');
        automata.addTransition("q2", "q3", Automata.EPSILON);
        automata.addTransition("q2", "q2", 'b');
        automata.setInitialState("q0");
        automata.addFinalState("q3");
        return automata;
    }

    static Automata expected2() {
        Set<Character> language = new HashSet<>(Arrays.asList('a', 'b'));
        Automata automata = new Automata(language);
        automata.addTransition("A", "A", 'a');
        automata.addTransition("A", "B", 'b');
        automata.addTransition("B", "B", 'a');
        automata.addTransition("B", "A", 'b');
        automata.setInitialState("A");
        automata.addFinalState("B");
        automata.addFinalState("A");
        return automata;
    }
}
