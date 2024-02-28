package com.lvum;

import com.lvum.automaton.Automata;
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
    void correct(Automata automata1, Automata automata2, Automata expected) {
        // The two automata must be equivalent
        Automata result = automata1.run(new Intersection(automata2));
        System.out.println(result);
        System.out.println(expected);
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


    static Automata automaton1a() {
        Automata automata = new Automata(Set.of('0', '1'));
        automata.addTransition("q0", "q1", '0');
        automata.addTransition("q0", "q0", '1');
        automata.addTransition("q1", "q1", '0');
        automata.addTransition("q1", "q2", '1');
        automata.addTransition("q2", "q1", '0');
        automata.addTransition("q2", "q0", '1');
        automata.setInitialState("q0");
        automata.addFinalState("q2");
        return automata;
    }

    static Automata automaton1b() {
        Automata automata = new Automata(Set.of('0', '1'));
        automata.addTransition("s0", "s0", '0');
        automata.addTransition("s0", "s1", '1');
        automata.addTransition("s1", "s1", '0');
        automata.addTransition("s1", "s0", '1');
        automata.setInitialState("s0");
        automata.addFinalState("s0");
        return automata;
    }

    static Automata expected1() {
        Automata expected = new Automata(Set.of('0', '1'));
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
