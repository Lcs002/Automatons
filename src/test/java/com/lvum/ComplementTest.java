package com.lvum;

import com.lvum.automaton.Automata;
import com.lvum.automaton.algorithms.Equivalency;
import com.lvum.automaton.algorithms.properties.Complement;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ComplementTest {

    @ParameterizedTest
    @MethodSource("correctArgs")
    void correct(Automata automata, Automata expected) {
        // The two automata must be equivalent
        Automata result = automata.run(new Complement());
        assertTrue(result.run(new Equivalency(expected)));
    }


    static Stream<Arguments> correctArgs() {
        return Stream.of(
                Arguments.of(
                        automaton1(),
                        expected1()
                )
        );
    }


    static Automata automaton1() {
        Automata automata = new Automata(Set.of('a', 'b'));
        automata.addTransition("A", "B", 'a');
        automata.addTransition("A", "B", 'b');
        automata.addTransition("B", "B", 'a');
        automata.addTransition("B", "A", 'b');
        automata.setInitialState("A");
        automata.addFinalState("A");
        return automata;
    }

    static Automata expected1() {
        Automata expected = new Automata(Set.of('a', 'b'));
        expected.addTransition("A", "B", 'a');
        expected.addTransition("A", "B", 'b');
        expected.addTransition("B", "B", 'a');
        expected.addTransition("B", "A", 'b');
        expected.setInitialState("A");
        expected.addFinalState("B");
        return expected;
    }
}
