package com.github.Lcs002.Automatons.automaton.serialize;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.Lcs002.Automatons.automaton.Automaton;

public interface AutomatonSerializer {
    String serialize(Automaton automaton) throws JsonProcessingException;
    String serializePretty(Automaton automaton) throws JsonProcessingException;
}
