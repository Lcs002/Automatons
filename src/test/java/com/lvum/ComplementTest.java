package com.lvum;

import com.lvum.algorithms.Complete;
import com.lvum.algorithms.Equivalency;
import com.lvum.algorithms.properties.Complement;
import com.lvum.algorithms.utility.IsComplete;
import com.lvum.algorithms.utility.IsDFA;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ComplementTest {

    @ParameterizedTest
    @MethodSource("correctArgs")
    void correct(Automaton automaton, Automaton expected) {
        // The two automata must be equivalent
        Automaton result = automaton.run(new Complement());
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
        automaton.addTransition("A", "B", 'b');
        automaton.addTransition("B", "B", 'a');
        automaton.addTransition("B", "A", 'b');
        automaton.setInitialState("A");
        automaton.addFinalState("A");
        return automaton;
    }

    static Automaton expected1() {
        Automaton expected = new Automaton(Set.of('a', 'b'));
        expected.addTransition("A", "B", 'a');
        expected.addTransition("A", "B", 'b');
        expected.addTransition("B", "B", 'a');
        expected.addTransition("B", "A", 'b');
        expected.setInitialState("A");
        expected.addFinalState("B");
        return expected;
    }
}
