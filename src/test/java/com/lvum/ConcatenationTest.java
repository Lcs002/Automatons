package com.lvum;

import com.lvum.automaton.Automata;
import com.lvum.automaton.algorithms.Equivalency;
import com.lvum.automaton.algorithms.NFAToDFAEpsilon;
import com.lvum.automaton.algorithms.properties.Concatenation;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConcatenationTest {

    @ParameterizedTest
    @MethodSource("correctArgs")
    void correct(Automata automata1, Automata automata2, Automata expected) {
        // The two automata must be equivalent
        Automata resultDFA = automata1.run(new Concatenation(automata2)).run(new NFAToDFAEpsilon());
        assertTrue(resultDFA.run(new Equivalency(expected)));
    }

    static Stream<Arguments> correctArgs() {
        return Stream.of(
                // https://www.geeksforgeeks.org/concatenation-process-in-dfa/
                Arguments.of(
                        automaton1a(),
                        automaton1b(),
                        expected1()
                ),
                // https://www.tutorialspoint.com/explain-the-concatenation-process-in-dfa
                Arguments.of(
                        automaton2a(),
                        automaton2b(),
                        expected2()
                ),
                Arguments.of(
                        automaton4a(),
                        automaton4b(),
                        expected4()
                )
        );
    }


    private static Object automaton1a() {
        Set<Character> alphabet = Set.of('a', 'b');
        Automata automata = new Automata(alphabet);
        automata.addTransition("A", "B", 'b');
        automata.addTransition("A", "C", 'a');
        automata.addTransition("B", "B", 'a');
        automata.addTransition("B", "B", 'b');
        automata.addTransition("C", "C", 'a');
        automata.addTransition("C", "C", 'b');
        automata.setInitialState("A");
        automata.addFinalState("C");
        return automata;
    }

    private static Object automaton1b() {
        Set<Character> alphabet = Set.of('a', 'b');
        Automata automata = new Automata(alphabet);
        automata.addTransition("A", "B", 'b');
        automata.addTransition("A", "A", 'a');
        automata.addTransition("B", "A", 'a');
        automata.addTransition("B", "B", 'b');
        automata.setInitialState("A");
        automata.addFinalState("B");
        return automata;
    }

    private static Object expected1() {
        Set<Character> alphabet = Set.of('a', 'b');
        Automata automata = new Automata(alphabet);
        automata.addTransition("A", "B", 'a');
        automata.addTransition("A", "D", 'b');
        automata.addTransition("B", "B", 'a');
        automata.addTransition("B", "C", 'b');
        automata.addTransition("C", "B", 'a');
        automata.addTransition("C", "C", 'b');
        automata.addTransition("D", "D", 'a');
        automata.addTransition("D", "D", 'b');
        automata.setInitialState("A");
        automata.addFinalState("C");
        return automata;
    }


    private static Object automaton2a() {
        Set<Character> alphabet = Set.of('a', 'b');
        Automata automata = new Automata(alphabet);
        automata.addTransition("q0", "q1", 'a');
        automata.addTransition("q1", "q2", 'b');
        automata.setInitialState("q0");
        automata.addFinalState("q2");
        return automata;
    }

    private static Object automaton2b() {
        Set<Character> alphabet = Set.of('a', 'b');
        Automata automata = new Automata(alphabet);
        automata.addTransition("p0", "p1", 'a');
        automata.setInitialState("p0");
        automata.addFinalState("p1");
        return automata;
    }

    private static Object expected2() {
        Set<Character> alphabet = Set.of('a', 'b');
        Automata automata = new Automata(alphabet);
        automata.addTransition("q0", "q1", 'a');
        automata.addTransition("q1", "q2-p0", 'b');
        automata.addTransition("q2-p0", "p1", 'a');
        automata.setInitialState("q0");
        automata.addFinalState("p1");
        return automata;
    }


    private static Object automaton3a() {
        Set<Character> alphabet = Set.of('a', 'b', 'c');
        Automata automata = new Automata(alphabet);
        automata.addTransition("q0", "q1", 'a');
        automata.addTransition("q0", "q3", 'b');
        automata.addTransition("q0", "q2", 'c');
        automata.addTransition("q1", "q3", 'a');
        automata.addTransition("q1", "q3", 'b');
        automata.addTransition("q1", "q3", 'c');
        automata.addTransition("q2", "q3", 'a');
        automata.addTransition("q2", "q1", 'b');
        automata.addTransition("q2", "q3", 'c');
        automata.addTransition("q3", "q3", 'a');
        automata.addTransition("q3", "q3", 'b');
        automata.addTransition("q3", "q3", 'c');
        automata.setInitialState("q0");
        automata.addFinalState("q1");
        automata.addFinalState("q2");
        return automata;
    }

    private static Object automaton3b() {
        Set<Character> alphabet = Set.of('a', 'b', 'c');
        Automata automata = new Automata(alphabet);
        automata.addTransition("p0", "p3", 'a');
        automata.addTransition("p0", "p4", 'b');
        automata.addTransition("p0", "p4", 'c');
        automata.addTransition("p3", "p4", 'a');
        automata.addTransition("p3", "p2", 'b');
        automata.addTransition("p3", "p4", 'c');
        automata.addTransition("p4", "p4", 'a');
        automata.addTransition("p4", "p4", 'b');
        automata.addTransition("p4", "p4", 'c');
        automata.addTransition("p2", "p4", 'a');
        automata.addTransition("p2", "p4", 'b');
        automata.addTransition("p2", "p1", 'c');
        automata.addTransition("p1", "p4", 'a');
        automata.addTransition("p1", "p4", 'b');
        automata.addTransition("p1", "p4", 'c');
        automata.setInitialState("p0");
        automata.addFinalState("p1");
        automata.addFinalState("p0");
        return automata;
    }

    private static Object expected3() {
        Set<Character> alphabet =Set.of('a', 'b', 'c');
        Automata automata = new Automata(alphabet);
        automata.addTransition("q0", "q1-p0", 'a');
        automata.addTransition("q0", "q3", 'b');
        automata.addTransition("q0", "q2-p0", 'c');

        automata.addTransition("q1-p0", "q3-p3", 'a');
        automata.addTransition("q1-p0", "q3-p4", 'b');
        automata.addTransition("q1-p0", "q3-p4", 'c');

        automata.addTransition("q3-p3", "q3-p4", 'a');
        automata.addTransition("q3-p3", "q3-p2", 'b');
        automata.addTransition("q3-p3", "q3-p4", 'c');

        automata.addTransition("q3-p4", "q3-p4", 'a');
        automata.addTransition("q3-p4", "q3-p4", 'b');
        automata.addTransition("q3-p4", "q3-p4", 'c');

        automata.addTransition("q3-p2", "q3-p4", 'a');
        automata.addTransition("q3-p2", "q3-p4", 'b');
        automata.addTransition("q3-p2", "q3-p1", 'c');

        automata.addTransition("q3-p1", "q3-p4", 'a');
        automata.addTransition("q3-p1", "q3-p4", 'b');
        automata.addTransition("q3-p1", "q3-p4", 'c');

        automata.addTransition("q2-p0", "q3-p3", 'a');
        automata.addTransition("q2-p0", "q1-p4", 'b');
        automata.addTransition("q2-p0", "q3-p4", 'c');

        automata.addTransition("q1-p4", "q3-p4", 'a');
        automata.addTransition("q1-p4", "q3-p4", 'b');
        automata.addTransition("q1-p4", "q3-p4", 'c');

        automata.addTransition("q3", "q3", 'a');
        automata.addTransition("q3", "q3", 'b');
        automata.addTransition("q3", "q3", 'c');
        automata.setInitialState("q0");
        automata.addFinalState("q1-p0");
        automata.addFinalState("q2-p0");
        automata.addFinalState("q3-p1");
        return automata;
    }


    static Automata automaton4a() {
        Set<Character> alphabet = Set.of('a', 'b');
        Automata automata = new Automata(alphabet);
        automata.addTransition("A", "B", 'a');
        automata.addTransition("B", "B", 'b');
        automata.setInitialState("A");
        automata.addFinalState("A");
        automata.addFinalState("B");
        return automata;
    }

    static Automata automaton4b() {
        Set<Character> alphabet = Set.of('a', 'b');
        Automata automata = new Automata(alphabet);
        automata.addTransition("A", "A", 'a');
        automata.addTransition("A", "B", 'b');
        automata.addTransition("B", "B", 'a');
        automata.addTransition("B", "B", 'b');
        automata.setInitialState("A");
        automata.addFinalState("A");
        return automata;
    }

    static Automata expected4() {
        Set<Character> alphabet = Set.of('a', 'b');
        Automata automata = new Automata(alphabet);
        automata.addTransition("A1-A2", "B1-A2", 'a');
        automata.addTransition("A1-A2", "B2", 'b');
        automata.addTransition("B1-A2", "A2", 'a');
        automata.addTransition("B1-A2", "B1-A2-B2", 'b');
        automata.addTransition("B1-A2-B2", "A2-B2", 'a');
        automata.addTransition("B1-A2-B2", "B1-A2-B2", 'b');
        automata.addTransition("A2-B2", "A2-B2", 'a');
        automata.addTransition("A2-B2", "B2", 'b');
        automata.addTransition("B2", "B2", 'a');
        automata.addTransition("B2", "B2", 'b');
        automata.addTransition("A2", "A2", 'a');
        automata.addTransition("A2", "B2", 'b');
        automata.setInitialState("A1-A2");
        automata.addFinalState("B1-A2");
        automata.addFinalState("A1-A2");
        automata.addFinalState("A2-B2");
        automata.addFinalState("B1-A2-B2");
        automata.addFinalState("A2");
        return automata;
    }
}
