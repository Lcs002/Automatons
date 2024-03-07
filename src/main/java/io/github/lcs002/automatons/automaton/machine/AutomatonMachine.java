package io.github.lcs002.automatons.automaton.machine;

import io.github.lcs002.automatons.automaton.Automaton;
import io.github.lcs002.automatons.automaton.algorithms.utility.IsDFA;
import io.github.lcs002.automatons.automaton.algorithms.utility.IsComplete;
import jakarta.annotation.Nonnull;

/**
 * <h2>Finite Automaton Machine</h2>
 * <p>Simulates the behavior of an automaton, enabling the user to consume entries and get its current state.</p>
 * <p>The automaton used must <b>{@link IsDFA be a DFA}</b> and <b>{@link IsComplete be complete}</b>.</p>
 */
public class AutomatonMachine {
    /**
     * The automaton used by the machine.
     */
    private final Automaton automaton;

    /**
     * The current state of the automaton.
     */
    private String currentState;


    /**
     * Constructor for the AutomatonMachine class
     * @param automaton The automaton to be used by the machine. Must be a DFA.
     */
    public AutomatonMachine(@Nonnull Automaton automaton) {
        // Check if the automaton is a DFA
        if (Boolean.FALSE.equals(automaton.isDfa()))
            throw new IllegalArgumentException("The automaton must be a DFA");
        // Check if the automaton is complete
        if (Boolean.FALSE.equals(automaton.isComplete()))
            throw new IllegalArgumentException("The automaton must be complete");

        this.automaton = automaton;
        this.currentState = automaton.getInitialState();
    }


    /**
     * Returns the automaton used by the machine.
     * @return The automaton used by the machine.
     */
    // TODO Make this return a COPY of the automaton
    public Automaton getAutomata() {
        return automaton;
    }

    /**
     * Returns the current state of the automaton.
     * @return The current state of the automaton.
     */
    public String getCurrentState() {
        return currentState;
    }


    /**
     * Consumes a series of entries and returns the resultant state of the automaton.
     * @param entries The entries to be consumed. Must be in the automaton's alphabet.
     * @return The resultant state of the automaton.
     */
    public String consume(String entries) {
        for (Character entry : entries.toCharArray()) consume(entry);
        return currentState;
    }

    /**
     * Consumes an entry and returns the next state of the automaton.
     * @param entry The entry to be consumed. Must be in the automaton's alphabet.
     * @return The next state of the automaton
     */
    public String consume(Character entry) {
        // The entry must be in the automaton's alphabet
        if (!automaton.getAlphabet().contains(entry))
            throw new IllegalArgumentException("The entry must be in the automaton's alphabet");

        // Get the next state given the current state and the entry
        String nextState = automaton.getTransitions().stream()
                // Get the transitions where the current state and the entry match with the transition's from and entry
                .filter(transition -> transition.from().equals(currentState) && transition.entry().equals(entry))
                // Get the next states
                .map(Automaton.Transition::to)
                // Get the first next state
                .findFirst()
                // If there is no next state, return null
                .orElse(null);
        // Update the current state
        this.currentState = nextState;
        // Return the next state
        return nextState;
    }
}
