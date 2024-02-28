package com.lvum.machine;

import com.lvum.automaton.Automaton;
import com.lvum.automaton.algorithms.utility.IsDFA;
import jakarta.annotation.Nonnull;

/**
 * <h1>Finite Automaton Machine</h1>
 * <p>Simulates the behavior of an automaton, enabling the user to consume entries and get its current state.</p>
 */
public class AutomatonMachine {
    private final Automaton automaton;
    private String currentState;


    /**
     * Constructor for the AutomatonMachine class
     * @param automaton The automaton to be used by the machine. Must be a DFA.
     */
    public AutomatonMachine(@Nonnull Automaton automaton) {
        // Check if the automaton is a DFA
        if (Boolean.FALSE.equals(automaton.run(new IsDFA())))
            throw new IllegalArgumentException("The automaton must be a DFA");

        this.automaton = automaton;
        this.currentState = automaton.getInitialState();
    }


    // TODO Make this return a COPY of the automaton
    public Automaton getAutomata() {
        return automaton;
    }

    public String getCurrentState() {
        return currentState;
    }

    /**
     * Consumes an entry and returns the next state of the automaton.
     * @param entry The entry to be consumed.
     * @return The next state of the automaton
     */
    public String consume(Character entry) {
        String nextState = automaton.getTransitions().stream()
                .filter(transition -> transition.from().equals(currentState) && transition.entry().equals(entry))
                .map(Automaton.Transition::to)
                .findFirst()
                .orElse(null);
        this.currentState = nextState;
        return nextState;
    }
}
