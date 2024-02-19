package com.lvum;

import com.lvum.algorithms.NFAToDFA;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Automaton automaton = new Automaton(Arrays.asList('0', '1'));
        automaton.addTransition("S0", "S1", '0');
        automaton.addTransition("S0", "S0", '1');
        automaton.addTransition("S1", "S0", '1');
        automaton.addTransition("S1", "S1", '0');
        automaton.addTransition("S0", "S0", '0');
        automaton.setInitialState("S0");

        System.out.println(automaton);

        Automaton other = automaton.run(new NFAToDFA());

        System.out.println(other);
    }
}