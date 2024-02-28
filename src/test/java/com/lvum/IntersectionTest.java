package com.lvum;

import com.lvum.automaton.Automaton;
import com.lvum.automaton.algorithms.Equivalency;
import com.lvum.automaton.algorithms.properties.Intersection;
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
        Automaton automaton = new Automaton(Set.of('0', '1'));
        automaton.addTransition("q0", "q1", '0');
        automaton.addTransition("q0", "q0", '1');
        automaton.addTransition("q1", "q1", '0');
        automaton.addTransition("q1", "q2", '1');
        automaton.addTransition("q2", "q1", '0');
        automaton.addTransition("q2", "q0", '1');
        automaton.setInitialState("q0");
        automaton.addFinalState("q2");
        return automaton;
    }

    static Automaton automaton1b() {
        Automaton automaton = new Automaton(Set.of('0', '1'));
        automaton.addTransition("s0", "s0", '0');
        automaton.addTransition("s0", "s1", '1');
        automaton.addTransition("s1", "s1", '0');
        automaton.addTransition("s1", "s0", '1');
        automaton.setInitialState("s0");
        automaton.addFinalState("s0");
        return automaton;
    }

    static Automaton expected1() {
        Automaton expected = new Automaton(Set.of('0', '1'));
        expected.addTransition("q0-s0", "q1-s0", '0');
        expected.addTransition("q0-s0", "q0-s1", '1');

        expected.addTransition("q0-s1", "q1-s1", '0');
        expected.addTransition("q0-s1", "q0-s0", '1');

        expected.addTransition("q1-s0", "q1-s0", '0');
        expected.addTransition("q1-s0", "q2-s1", '1');

        expected.addTransition("q1-s1", "q1-s1", '0');
        expected.addTransition("q1-s1", "q2-s0", '1');

        expected.addTransition("q2-s1", "q1-s1", '0');
        expected.addTransition("q2-s1", "q0-s0", '1');

        expected.addTransition("q2-s0", "q1-s0", '0');
        expected.addTransition("q2-s0", "q0-s1", '1');

        expected.setInitialState("q0-s0");
        expected.addFinalState("q2-s0");
        return expected;
    }
}
