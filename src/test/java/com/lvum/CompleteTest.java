package com.lvum;

import com.lvum.automaton.Automaton;
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
    void correct(Automaton automaton, Automaton expected) {
        // The two automaton must be equivalent
        Automaton result = automaton.run(new Complete());
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


    static Automaton automaton1() {
        Automaton automaton = new Automaton(Set.of('a', 'b'));
        automaton.addTransition("A", "B", 'a');
        automaton.addTransition("B", "C", 'b');
        automaton.addTransition("C", "A", 'a');
        automaton.setInitialState("A");
        automaton.addFinalState("C");
        return automaton;
    }

    static Automaton expected1() {
        Automaton expected = new Automaton(Set.of('a', 'b'));
        expected.addTransition("A", "B", 'a');
        expected.addTransition("B", "C", 'b');
        expected.addTransition("C", "A", 'a');
        expected.addTransition("A", Automaton.EMPTY_STATE, 'b');
        expected.addTransition("B", Automaton.EMPTY_STATE, 'a');
        expected.addTransition("C", Automaton.EMPTY_STATE, 'b');
        expected.addTransition(Automaton.EMPTY_STATE, Automaton.EMPTY_STATE, 'a');
        expected.addTransition(Automaton.EMPTY_STATE, Automaton.EMPTY_STATE, 'b');
        expected.setInitialState("A");
        expected.addFinalState("C");
        return expected;
    }
}
