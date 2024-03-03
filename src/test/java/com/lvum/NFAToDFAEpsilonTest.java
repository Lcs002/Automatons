package com.lvum;

import com.lvum.automaton.Automaton;
import com.lvum.automaton.algorithms.Equivalency;
import com.lvum.automaton.algorithms.conversion.NFAToDFAEpsilon;
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

    @ParameterizedTest
    @MethodSource("correctArgs")
    void correct(Automaton automaton, Automaton expected) {
        Automaton result = automaton.run(new NFAToDFAEpsilon());
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

    static Automaton automaton1() {
        Set<Character> language = new HashSet<>(Arrays.asList('0', '1', Automaton.EPSILON));
        Automaton automaton = new Automaton(language);
        automaton.addTransition("q0", "q1", Automaton.EPSILON);
        automaton.addTransition("q0", "q2", Automaton.EPSILON);
        automaton.addTransition("q1", "q3", '0');
        automaton.addTransition("q2", "q3", '1');
        automaton.addTransition("q3", "q4", '1');
        automaton.setInitialState("q0");
        automaton.addFinalState("q4");
        return automaton;
    }

    static Automaton expected1() {
        Set<Character> language = new HashSet<>(Arrays.asList('0', '1'));
        Automaton automaton = new Automaton(language);
        automaton.addTransition("A", "B", '0');
        automaton.addTransition("A", "B", '1');
        automaton.addTransition("B", "C", '1');
        automaton.setInitialState("A");
        automaton.addFinalState("C");
        return automaton;
    }


    static Automaton automaton2() {
        Set<Character> language = new HashSet<>(Arrays.asList('a', 'b', Automaton.EPSILON));
        Automaton automaton = new Automaton(language);
        automaton.addTransition("q0", "q1", Automaton.EPSILON);
        automaton.addTransition("q0", "q2", Automaton.EPSILON);
        automaton.addTransition("q1", "q3", 'a');
        automaton.addTransition("q1", "q1", 'b');
        automaton.addTransition("q3", "q3", 'a');
        automaton.addTransition("q3", "q0", 'b');
        automaton.addTransition("q2", "q3", Automaton.EPSILON);
        automaton.addTransition("q2", "q2", 'b');
        automaton.setInitialState("q0");
        automaton.addFinalState("q3");
        return automaton;
    }

    static Automaton expected2() {
        Set<Character> language = new HashSet<>(Arrays.asList('a', 'b'));
        Automaton automaton = new Automaton(language);
        automaton.addTransition("A", "A", 'a');
        automaton.addTransition("A", "B", 'b');
        automaton.addTransition("B", "B", 'a');
        automaton.addTransition("B", "A", 'b');
        automaton.setInitialState("A");
        automaton.addFinalState("B");
        automaton.addFinalState("A");
        return automaton;
    }
}
