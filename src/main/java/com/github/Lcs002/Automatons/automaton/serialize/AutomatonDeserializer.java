package com.github.Lcs002.Automatons.automaton.serialize;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.Lcs002.Automatons.automaton.Automaton;

public interface AutomatonDeserializer {
    Automaton deserialize(String json) throws JsonProcessingException;
}
