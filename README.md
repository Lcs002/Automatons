# Automatons
[![Java CI with Maven and JUnit](https://github.com/Lcs002/Automatons/actions/workflows/maven.yml/badge.svg?branch=master)](https://github.com/Lcs002/Automatons/actions/workflows/maven.yml)

Automaton's Algorithms implemented in Java.

> [!TIP]
> **[Project Documentation](https://lcs002.github.io/Automatons/)**
## Feature Wishlist

|                                            **Functionalities**                                            | **Done** | **Tested** |
|:---------------------------------------------------------------------------------------------------------:|:--------:|:----------:|
|                 _[NFA to DFA](src/main/java/com/lvum/automata/algorithms/NFAToDFA.java)_                 |  **X**   |   **X**    |
|            _[NFA-ε to DFA](src/main/java/com/lvum/automata/algorithms/NFAToDFAEpsilon.java)_             |  **X**   |   **X**    |
| _[Remove Unreachable States](src/main/java/com/lvum/automata/algorithms/utility/RemoveUnreachable.java)_ |  **X**   |            |
|         _[ε-Closure](src/main/java/com/lvum/automata/algorithms/utility/GetEpsilonClosure.java)_         |  **X**   |            |
|                                      _Minimize - Equivalency Method_                                      |          |            |
|                                         _Minimize - Table Method_                                         |          |            |
|               _[Equivalency](src/main/java/com/lvum/automata/algorithms/Equivalency.java)_               |  **X**   |   **X**    |
|                 _[Completion](src/main/java/com/lvum/automata/algorithms/Complete.java)_                 |  **X**   |   **X**    |
|          _[Complement](src/main/java/com/lvum/automata/algorithms/properties/Complement.java)_           |  **X**   |   **X**    |
|       _[Concatenation](src/main/java/com/lvum/automata/algorithms/properties/Concatenation.java)_        |  **X**   |   **X**    |
|        _[Intersection](src/main/java/com/lvum/automata/algorithms/properties/Intersection.java)_         |  **X**   |   **X**    |
|               _[Union](src/main/java/com/lvum/automata/algorithms/properties/Union.java)_                |  **X**   |   **X**    |
|                                                _Inversion_                                                |          |            |
|                                             _Language to DFA_                                             |          |            |
|                                             _Language to NFA_                                             |          |            |
|                                            _Language to NFA-ε_                                            |          |            |
|                  _[Automaton to Json](src/main/java/com/lvum/automata/serialize/json)_                   |  **X**   |            |
|                                           _Load/Save Automaton_                                           |          |            |
|                                          _Functional Automaton_                                           |          |            |
|                                         _Step by Step Algorithm_                                          |          |            |
## Usage
### Creating Automatons
First an **alphabet** must be defined:
```java
Set<Character> alphabet = new HashSet<>(Set.of('a', 'b'));
```

Then, an instance of Automata must be created using the alphabet defined:
```java
Automata automata = new Automata(alphabet);
```

**States** are added using:
```java
automata.addState("q0");
```

**Transitions** are added by using:
```java
// For normal transitions
automata.addTransition("q0", "q1", "a");

// For epsilon transitions
automata.addTransition("q0", "q1", Automata.EPSILON);
```

> [!NOTE]
> If any of the states does not exist, they will be created and added to the automata.

> [!WARNING] 
> All entries must be symbols from the automata's alphabet.

**Initial State** is set by using:
```java
automata.setInitialState("q0");
```

> [!WARNING]
> Be sure an Initial State is set before running any algorithm.

> [!NOTE] 
> Only one initial state is allowed per automata.

**Final States** are added by using:
```java
automata.addFinalState("q1");
```

> [!WARNING]
> Be sure at least one Final State is set before running any algorithm.

> [!TIP]
> You can visualize the automata by simply printing it or using the method `toString()`.
> ```java
> System.out.println(automata);
> ```

### Running Algorithms
Call the method `run(T algorithm)` passing the algorithm as parameter:
```java
// Define the alphabet
Set<Character> alphabet = new HashSet<>(Set.of('a', 'b'));
// Create a new instance of Automata
Automata automata = new Automata(alphabet);
// Call the method 'run' passing the algorithm as parameter
Automata result = automata.run(new NFAToDFAEpsilon());

System.out.println(automata);
System.out.println(result);
```
### De/Serializing Automatons
Simply call the method `serialize` from the class `AutomatonJsonSerializer`:
Or call the method `deserialize` from the class `AutomatonJsonDeserializer`:
```java
String json = new AutomatonJsonSerializer().serialize(automata);
Automata automata = new AutomatonJsonDeserializer().deserialize(json);
```