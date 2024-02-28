# Automatons
[![Java CI with Maven and JUnit](https://github.com/Lcs002/Automatons/actions/workflows/maven.yml/badge.svg?branch=master)](https://github.com/Lcs002/Automatons/actions/workflows/maven.yml)

_Automaton's Algorithms implemented in Java._

> [!TIP]
> **[Project Documentation](https://lcs002.github.io/Automatons/)**

- **[Feature Wishlist](#feature-wishlist)**
- **[Usage](#usage)**
  - **[Creating Automatons](#creating-automatons)**
  - **[Running Algorithms](#running-algorithms)**
  - **[De/Serializing Automatons](#deserializing-automatons)**

## Feature Wishlist

|                                            **Functionalities**                                            | **Done** | **Tested** |
|:---------------------------------------------------------------------------------------------------------:|:--------:|:----------:|
|                 _[NFA to DFA](src/main/java/com/lvum/automaton/algorithms/NFAToDFA.java)_                 |  **X**   |   **X**    |
|            _[NFA-ε to DFA](src/main/java/com/lvum/automaton/algorithms/NFAToDFAEpsilon.java)_             |  **X**   |   **X**    |
| _[Remove Unreachable States](src/main/java/com/lvum/automaton/algorithms/utility/RemoveUnreachable.java)_ |  **X**   |            |
|         _[ε-Closure](src/main/java/com/lvum/automaton/algorithms/utility/GetEpsilonClosure.java)_         |  **X**   |            |
|                                      _Minimize - Equivalency Method_                                      |          |            |
|                                         _Minimize - Table Method_                                         |          |            |
|               _[Equivalency](src/main/java/com/lvum/automaton/algorithms/Equivalency.java)_               |  **X**   |   **X**    |
|                 _[Completion](src/main/java/com/lvum/automaton/algorithms/Complete.java)_                 |  **X**   |   **X**    |
|          _[Complement](src/main/java/com/lvum/automaton/algorithms/properties/Complement.java)_           |  **X**   |   **X**    |
|       _[Concatenation](src/main/java/com/lvum/automaton/algorithms/properties/Concatenation.java)_        |  **X**   |   **X**    |
|        _[Intersection](src/main/java/com/lvum/automaton/algorithms/properties/Intersection.java)_         |  **X**   |   **X**    |
|               _[Union](src/main/java/com/lvum/automaton/algorithms/properties/Union.java)_                |  **X**   |   **X**    |
|           _[Reversion](src/main/java/com/lvum/automaton/algorithms/properties/Reversion.java)_            |  **X**   |   **X**    |
|                                            _Reg. Expr. to DFA_                                            |          |            |
|                                            _Reg. Expr. to NFA_                                            |          |            |
|                                           _Reg. Expr. to NFA-ε_                                           |          |            |
|                  _[Automaton to Json](src/main/java/com/lvum/automaton/serialize/json)_                   |  **X**   |            |
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
Automata automaton = new Automata(alphabet);
```

**States** are added using `addState` with the name of the state as parameter:
```java
automaton.addState("q0");
```

**Transitions** are added by using `addTransition` with the source state, destination state and symbol as parameters:
```java
// For normal transitions
automaton.addTransition("q0", "q1", "a");

// For epsilon transitions
automaton.addTransition("q0", "q1", Automata.EPSILON);
```

> [!NOTE]
> _If any of the states does not exist, they will be created and added to the automaton._

> [!WARNING] 
> _All entries must be symbols from the automaton's alphabet._

**Initial State** is set by using `setInitialState` with the name of the state as parameter:
```java
automaton.setInitialState("q0");
```

> [!WARNING]
> _Be sure an Initial State is set before running any algorithm._

> [!NOTE] 
> _Only one initial state is allowed per automaton._

**Final States** are added by using `addFinalState` with the name of the state as parameter
```java
automaton.addFinalState("q1");
```

> [!WARNING]
> _Be sure at least one Final State is set before running any algorithm._

> [!TIP]
> _You can visualize the automaton by simply printing it or using the method `toString()`._
> ```java
> System.out.println(automaton);
> ```

### Running Algorithms
To run an algorithm on a automaton, call the method `run(T algorithm)` passing the algorithm as parameter:
```java
// Define the alphabet
Set<Character> alphabet = new HashSet<>(Set.of('a', 'b'));
// Create a new instance of Automaton
Automata automaton = new Automata(alphabet);
// Call the method 'run' passing the algorithm as parameter
Automata result = automaton.run(new NFAToDFAEpsilon());

System.out.println(automaton);
System.out.println(result);
```

### De/Serializing Automatons
To convert an **Automaton** to a **JSON** string, call the method `serialize` from the class `AutomatonJsonSerializer`:
```java
String json = new AutomatonJsonSerializer().serialize(automaton);
```
To convert a **JSON** string to an **Automaton**, call the method `deserialize` from the class `AutomatonJsonDeserializer`:
```java
Automata automaton = new AutomatonJsonDeserializer().deserialize(json);
```