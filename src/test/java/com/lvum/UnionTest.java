package com.lvum;

import com.lvum.automaton.Automaton;
import com.lvum.automaton.algorithms.Equivalency;
import com.lvum.automaton.algorithms.conversion.NFAToDFAEpsilon;
import com.lvum.automaton.algorithms.properties.Union;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UnionTest {
    Automaton automaton1;
    Automaton automaton2;

    @BeforeEach
    void beforeEach() {
        automaton1 = new Automaton.Builder()
                .setAlphabet(Set.of('a', 'b'))
                .addTransition("A", "B", 'a')
                .addTransition("A", "D", 'b')
                .addTransition("B", "B", 'a')
                .addTransition("B", "C", 'b')
                .addTransition("C", "B", 'a')
                .addTransition("C", "C", 'b')
                .addTransition("D", "D", 'a')
                .addTransition("D", "D", 'b')
                .setInitialState("A")
                .addFinalState("C")
                .build();

        automaton2 = new Automaton.Builder()
                .setAlphabet(Set.of('a', 'b'))
                .addTransition("A", "D", 'a')
                .addTransition("A", "B", 'b')
                .addTransition("B", "C", 'a')
                .addTransition("B", "B", 'b')
                .addTransition("C", "C", 'a')
                .addTransition("C", "B", 'b')
                .addTransition("D", "D", 'a')
                .addTransition("D", "D", 'b')
                .setInitialState("A")
                .addFinalState("C")
                .build();
    }

    @Test
    void correct() {
        Automaton expected = new Automaton.Builder()
                .setAlphabet(Set.of('a', 'b'))
                .addTransition("A²-A¹-A¹-A²", "B¹-D²", 'a')
                .addTransition("A²-A¹-A¹-A²", "B²-D¹", 'b')
                .addTransition("B¹-D²", "B¹-D²", 'a')
                .addTransition("B¹-D²", "C¹-D²", 'b')
                .addTransition("B²-D¹", "D¹-C²", 'a')
                .addTransition("B²-D¹", "B²-D¹", 'b')
                .addTransition("C¹-D²", "B¹-D²", 'a')
                .addTransition("C¹-D²", "C¹-D²", 'b')
                .addTransition("D¹-C²", "D¹-C²", 'a')
                .addTransition("D¹-C²", "B²-D¹", 'b')
                .setInitialState("A²-A¹-A¹-A²")
                .addFinalState("C¹-D²")
                .addFinalState("D¹-C²")
                .build();

        Automaton result = automaton1.run(new Union(automaton2)).run(new NFAToDFAEpsilon());
        assertTrue(result.run(new Equivalency(expected)));
    }
}
