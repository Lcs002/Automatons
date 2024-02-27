package com.lvum.automaton.serialize.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lvum.automaton.Automaton;
import com.lvum.automaton.serialize.AutomatonSerializer;

/**
 * <h1>Automaton Serializer</h1>
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
