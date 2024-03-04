package com.github.lcs002.automatons.automaton.serialize;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.lcs002.automatons.automaton.Automaton;

public interface AutomatonSerializer {
    String serialize(Automaton automaton) throws JsonProcessingException;
    String serializePretty(Automaton automaton) throws JsonProcessingException;
}
