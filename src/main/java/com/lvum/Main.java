package com.lvum;

import com.lvum.algorithms.utility.RemoveUnreachable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        new Main().example();
    }


    private void example() {
        Set<Character> language = new HashSet<>(Arrays.asList('0', '1'));
        Automaton automaton = new Automaton(language);

        automaton.addTransition("S1", "S2", '0');
        automaton.addTransition("S1", "S1", '1');
        automaton.addTransition("S2", "S1", '0');
        automaton.addTransition("S2", "S2", '1');
        automaton.addTransition("S3", "S2", '1');
        automaton.addTransition("S3", "S1", '0');
        automaton.setInitialState("S1");

        System.out.println(automaton);
        System.out.println(automaton.run(new RemoveUnreachable()));
    }
}