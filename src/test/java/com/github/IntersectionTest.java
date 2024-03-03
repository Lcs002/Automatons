package com.github;

import com.github.Lcs002.Automatons.automaton.Automaton;
import com.github.Lcs002.Automatons.automaton.algorithms.Equivalency;
import com.github.Lcs002.Automatons.automaton.algorithms.properties.Intersection;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class IntersectionTest {

    @ParameterizedTest
    @MethodSource("correctArgs")
    void correct(Automaton automaton1, Automaton automaton2, Automaton expected) {
        // The two automaton must be equivalent
        Automaton result = automaton1.run(new Intersection(automaton2));
        assertTrue(result.run(new Equivalency(expected)));
    }


    static Stream<Arguments> correctArgs() {
        return Stream.of(
                Arguments.of(
                        automaton1a(),
                        automaton1b(),
                        expected1()
                )
        );
    }


    static Automaton automaton1a() {
        return new Automaton.Builder()
                .setAlphabet(Set.of('0', '1'))
                .addTransition("q0", "q1", '0')
                .addTransition("q0", "q0", '1')
                .addTransition("q1", "q1", '0')
                .addTransition("q1", "q2", '1')
                .addTransition("q2", "q1", '0')
                .addTransition("q2", "q0", '1')
                .setInitialState("q0")
                .addFinalState("q2")
                .build();
    }

    static Automaton automaton1b() {
        return new Automaton.Builder()
                .setAlphabet(Set.of('0', '1'))
                .addTransition("s0", "s0", '0')
                .addTransition("s0", "s1", '1')
                .addTransition("s1", "s1", '0')
                .addTransition("s1", "s0", '1')
                .setInitialState("s0")
                .addFinalState("s0")
                .build();
    }

    static Automaton expected1() {
        return new Automaton.Builder()
                .setAlphabet(Set.of('0', '1'))
                .addTransition("q0-s0", "q1-s0", '0')
                .addTransition("q0-s0", "q0-s1", '1')
                .addTransition("q0-s1", "q1-s1", '0')
                .addTransition("q0-s1", "q0-s0", '1')
                .addTransition("q1-s0", "q1-s0", '0')
                .addTransition("q1-s0", "q2-s1", '1')
                .addTransition("q1-s1", "q1-s1", '0')
                .addTransition("q1-s1", "q2-s0", '1')
                .addTransition("q2-s1", "q1-s1", '0')
                .addTransition("q2-s1", "q0-s0", '1')
                .addTransition("q2-s0", "q1-s0", '0')
                .addTransition("q2-s0", "q0-s1", '1')
                .setInitialState("q0-s0")
                .addFinalState("q2-s0")
                .build();
    }
}
