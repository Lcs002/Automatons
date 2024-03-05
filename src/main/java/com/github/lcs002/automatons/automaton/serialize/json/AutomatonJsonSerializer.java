package com.github.lcs002.automatons.automaton.serialize.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.lcs002.automatons.automaton.Automaton;
import com.github.lcs002.automatons.automaton.serialize.AutomatonSerializer;

/**
 * <h2>Automaton Serializer</h2>
 * <p>Class that serializes an Automaton object into a JSON string.</p>
 */
public class AutomatonJsonSerializer implements AutomatonSerializer {

    @Override
    public String serialize(Automaton automaton) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(automaton);
    }

    @Override
    public String serializePretty(Automaton automaton) throws JsonProcessingException {
        return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(automaton);
    }
}
