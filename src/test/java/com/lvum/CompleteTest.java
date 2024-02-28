package com.lvum;

import com.lvum.automaton.Automata;
import com.lvum.automaton.algorithms.Complete;
import com.lvum.automaton.algorithms.Equivalency;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CompleteTest {

    @ParameterizedTest
    @MethodSource("correctArgs")
    void correct(Automata automata, Automata expected) {
        // The two automata must be equivalent
        Automata result = automata.run(new Complete());
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
        automata.addTransition("B", "C", 'b');
        automata.addTransition("C", "A", 'a');
        automata.setInitialState("A");
        automata.addFinalState("C");
        return automata;
    }

    static Automata expected1() {
        Automata expected = new Automata(Set.of('a', 'b'));
        expected.addTransition("A", "B", 'a');
        expected.addTransition("B", "C", 'b');
        expected.addTransition("C", "A", 'a');
        expected.addTransition("A", Automata.EMPTY_STATE, 'b');
        expected.addTransition("B", Automata.EMPTY_STATE, 'a');
        expected.addTransition("C", Automata.EMPTY_STATE, 'b');
        expected.addTransition(Automata.EMPTY_STATE, Automata.EMPTY_STATE, 'a');
        expected.addTransition(Automata.EMPTY_STATE, Automata.EMPTY_STATE, 'b');
        expected.setInitialState("A");
        expected.addFinalState("C");
        return expected;
    }
}
