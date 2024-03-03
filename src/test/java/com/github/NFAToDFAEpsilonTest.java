package com.github;

import com.github.Lcs002.Automatons.automaton.Automaton;
import com.github.Lcs002.Automatons.automaton.algorithms.Equivalency;
import com.github.Lcs002.Automatons.automaton.algorithms.conversion.NFAToDFAEpsilon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class NFAToDFAEpsilonTest {
    private Automaton automaton;

    @BeforeEach
    void beforeEach() {
        automaton = new Automaton.Builder()
                .setAlphabet(Set.of('0', '1', Automaton.EPSILON))
                .addTransition("q0", "q1", Automaton.EPSILON)
                .addTransition("q0", "q2", Automaton.EPSILON)
                .addTransition("q1", "q3", '0')
                .addTransition("q2", "q3", '1')
                .addTransition("q3", "q4", '1')
                .setInitialState("q0")
                .addFinalState("q4")
                .build();
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
        return new Automaton.Builder()
                .setAlphabet(Set.of('0', '1', Automaton.EPSILON))
                .addTransition("q0", "q1", Automaton.EPSILON)
                .addTransition("q0", "q2", Automaton.EPSILON)
                .addTransition("q1", "q3", '0')
                .addTransition("q2", "q3", '1')
                .addTransition("q3", "q4", '1')
                .setInitialState("q0")
                .addFinalState("q4")
                .build();
    }

    static Automaton expected1() {
        return new Automaton.Builder()
                .setAlphabet(Set.of('0', '1'))
                .addTransition("A", "B", '0')
                .addTransition("A", "B", '1')
                .addTransition("B", "C", '1')
                .setInitialState("A")
                .addFinalState("C")
                .build();
    }


    static Automaton automaton2() {
        return new Automaton.Builder()
                .setAlphabet(Set.of('a', 'b', Automaton.EPSILON))
                .addTransition("q0", "q1", Automaton.EPSILON)
                .addTransition("q0", "q2", Automaton.EPSILON)
                .addTransition("q1", "q3", 'a')
                .addTransition("q1", "q1", 'b')
                .addTransition("q3", "q3", 'a')
                .addTransition("q3", "q0", 'b')
                .addTransition("q2", "q3", Automaton.EPSILON)
                .addTransition("q2", "q2", 'b')
                .setInitialState("q0")
                .addFinalState("q3")
                .build();
    }

    static Automaton expected2() {
        return new Automaton.Builder()
                .setAlphabet(Set.of('a', 'b'))
                .addTransition("A", "A", 'a')
                .addTransition("A", "B", 'b')
                .addTransition("B", "B", 'a')
                .addTransition("B", "A", 'b')
                .setInitialState("A")
                .addFinalState("B")
                .addFinalState("A")
                .build();
    }
}
