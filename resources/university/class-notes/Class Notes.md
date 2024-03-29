# Class Notes
## 1. Basic Concepts
![basic-concepts.png](basic-concepts.png)
## 2. NFA to DFA
![nfa-to-dfa.png](nfa-to-dfa.png)
## 3. Epsilon Closures, Accessibility and Co-Accessibility
![epsilon-closures-accessibility-and-co-accessibility.png](epsilon-closures-accessibility-and-co-accessibility.png)
## 4. Minimization
![minimization.png](minimization.png)
![minimization-examples.png](minimization-examples.png)

<details>
<summary><b>Notes</b></summary>

### 4.1. Equivalency Method
First the Automaton must be:
- **Complete**.
- **Deterministic**.
- **Without Unreachable States**.

Then, the algorithm is as follows:
1. **Create two Partitions**:
    - **C1** : Final States.
    - **C2** : Non-Final States.
2. **For each Partition**:
    - **For each State**: 
      1. Calculate the **Transition** with each **Symbol**.
      2. Its **Destination Partition** will be the **Partition** that contains the **Destination State** of the 
      **Transition**.
    - **Create a new Partition** grouping the **States** by its **Set of Destination Partitions**.
3. **Go to 2** if there are new **Partitions**.
4. The **States** of the minimized automaton are the **Partitions**.

> [!NOTE] A state is **final if X and Y are final** states. We made the task easier as we already separate the final states from 
> non-final states.

> [!NOTE] **Non-Co-Accessible** states and **Error** states are **automatically grouped** together by the algorithm.

### 4.2. Table Method
> _Also called **Myhill-Nerode Theorem**_

The algorithm is as follows:
1. **Create a Table** with the **States** as **Rows** and **Columns**.
   - If the automaton has **n** states, the table will be **n-1 x n-1**. So that no reflexive transitions are present.
2. **Mark** the pairs of states `<p,q>` that satisfies:
   - One is **final** and the other is **non-final**.

   > [!NOTE] **_Pseudocode_**: `q IS FINAL XOR p IS FINAL`

3. **Mark** the pairs of states `<p,q>` not marked that satisfies:
   - The pair `<δ(p,a), δ(q,a)>` is marked. Where `a` is a **symbol** from the **Alphabet**.

   > [!TIP] _If the pair of states cannot be marked, draw an arrow from the pair to the pair that should be marked. Then,_
   > _if target pair is eventually marked, also mark the origin pair. This way we have to make only one iteration._

4. **Repeat 3** if a pair was marked.

> [!WARNING] _Always mark every column then go down one row. Start from the top and go down._

</details>

## 5. Properties and Operations
![operations-with-languages.jpeg](operations-with-languages.png)
![WhatsApp Image 2024-02-21 at 10.42.06.jpeg](WhatsApp%20Image%202024-02-21%20at%2010.42.06.jpeg)
![WhatsApp Image 2024-02-21 at 10.42.10.jpeg](WhatsApp%20Image%202024-02-21%20at%2010.42.10.jpeg)
![WhatsApp Image 2024-02-21 at 10.42.11.jpeg](WhatsApp%20Image%202024-02-21%20at%2010.42.11.jpeg)
## 6. Pumping
![WhatsApp Image 2024-02-21 at 10.42.11.jpeg](WhatsApp%20Image%202024-02-21%20at%2010.42.11.jpeg)
![pumping-examples-and-grammar.png](pumping-examples-and-grammar.png)

<details>
<summary><b>Apuntes</b></summary>

Se da L. Es L regular?

Probar que L es regular
- Si : Escribir un automaton finito que acepta L (No hay otra opciÃ³n)
- No : Â¿? (Lema de Bombeo)

Cumple Lema de Bombeo -> No regular

Regular <-> Automata finito acepta L

> No podemos escribir el automaton porque seria muy grande.
> Con el Lema de Bombeo podemos probar si el automaton no es regular.

```
Pasos:
1) Suponemos L regular.
2) Sea k constante del Lema de Bombeo.
3) [!!] Elegimos una palabra w, que cumple:
- Pertenece al Lenguaje
- Tiene un tamaÃ±o mayorigual a k
4) w tiene una decomposiciÃ³n: w = xyz
- |xy| <= k
- y != lambda
5) [!!] Averiguar donde se encuentra y en w
6) [!!] wi = xy^(i)z en L, para cada i >= 0
- Encontrar un valor para i (0,2) tal que la condicion NO se cumple.
7) Si no se cumple, resulta que L no es regular

El Lema de Bombeo dice que para cada legnguaje regular existe una constante k.
k es una constante que existe, pero no sabemos su valor (en paso 2)
```
---
Ejemplo: L ={a^n, b^n | n >= 1}

	1) Suponemos L regular.
	2) Sea k constante del Lema de Bombeo.
	3) [!!] Elegimos una palabra w, que cumple:
		- Pertenece al Lenguaje
		- Tiene un tamaÃ±o menos igual a k

		Elegimos un w tal que w = a^(k)b^(k)
			- w cumple que pertenece al lenguage 	
			|w| = 2k <= k

	4) w tiene una decomposiciÃ³n: w = xyz
		- |xy| <= k

		|---k---|---k---|
		|-x-|-y-|---z---|

	5) [!!] Averiguar donde se encuentra y en w

		x,y en p ya que |xy| <= k

	6) [!!] wi = xy^(i)z en L, para cada i >= 0
		- Encontrar un valor para i (0,2) tal que la condicion NO se cumple.

		w = a^(m) a^(n) a^(k-m-n) b^(k)
		   |-----|-----|---------------|
		     x     y          z
		w0 = a^(k-n) b^(k)
		w2 = a^(k+n) b^(k)

> [!NOTE]
> Si queremos probar que w es capicua, con un algoritmo la idea es que puedo tener una variable i de izquierda y d de derecha y siempre recorremos simultaneamente con esas dos variables la entrada y comprobamos si las letras son iguales.
> Que pasa si queremos implementar este automata con una AF, no podemos recorrer desde la derecha hacia la izquierda solamente desde izquierda a derecha y despues de recorrer una parte no puedo recuperarla una vez consumida.
> Resulta que lo mas probable es que este lenguaje no sea regular, y si el regular no es regular, la unica opcion es aplicar el lema de bombeo.
> En nuestro caso que, Palabra podemos elegir? w = 1^p01^p

</details>

## 7. Grammar
![pumping-examples-and-grammar.png](pumping-examples-and-grammar.png)

<details>
<summary><b>Apuntes</b></summary>

Una gramatica genera frases, en este caso podemos asimilar, la gramatica con nuestro modo de pensar cuando construimos una frase.
Cuando recibimos una frase, solo comprobamos si es correcta su semantaica pero cuando queremos transmitir una frase, necesitamos un generador (cadena, palabra) -> Gramatica

Se categorizan en 4 niveles:
- **Regulares** : Producciones en la parte izq no terminal y derecha 3 posibilidades: lambda o terminal y nada mas o terminal seguido de no terminal
  - **_Formato_**: `A -> lambda | a | aB`
- **Contexto Libre** : Unica cond es el la parte izquierda tenga solo 1 terminal, no interesa derecha.+
  - **_Formato_**: `A -> *`


### 7.1. Definicion Formal de Gramatica
**_G = (Sig, N, S, P)_**
- **Sig** : Alfabeto terminal o de constantes.
- **N** : Alfabeto de no terminales (variables)
- **S** : Simbolo inicial de start (axioma de la gramatica)
- **P** : Conjunto de producciones, tiene dos miembros (el izquierdo siempre contiene al menos un terminal)

> [!NOTE]
>  Sig y N son siempre disjuntos.

### 7.2. Paso de Derivación
Si tenemos una **cadena** y cada cadena que **interviene en las derivaciones** se llama **forma sentencial** y tengo una produccion `a -> b`
como se aplica esta produccion a esta forma sentencial?
- Busco si la **forma sentencial** contiene la subcadena `a`.
  - **No**. Entonces no se puede aplciar
  - **Si**. Entonces el resultado es : (z1) prefijo cte, alpha -> beta, sufijo cte (z2)
    
	> [!NOTE] Que pasa si contiene más de una aparición de alpha?
	> Cuando aplicamos la produccion podemos elegir de manera no determinista una aparicion.

  	> [!NOTE] Convenio: **Terminales** -> Minusculas, **No Terminales** -> Mayusculas
	

**Que significa una derivacion de mas pasos?**

Ej: aYYbYXa

- En el primer paso aplicao regla 5 y obtengo aYaSvYXa
- Continuo la derivacion con aplicar la regla 2 aYabXavYXa
- Puedo continuar con una regla 4 por ejemplo aYababYXa

Si quiero mostrar que esta forma setencial se peude llegar a otra con derivaciones de mas pasos, se escribe directamente `aYYbYXa ->* aYababYXa`.

> [!NOTE] El **lenguaje generado de una gramatica** es formado de **todas palabras w** pero solamente palabras de **terminales** tal
> que desde el simbolo inicial puedo con una derivacion arbitraria llegar a w.
> Es decir: **Contiene todas pero solo terminales**.
> 
> Cada palabra se puede obtener del simbolo inicial con una derivacion con cierto numero de pasos
> `L(G) = {w IN Z*|S->* w}`.

### 7.3. Ejemplos Gramatica `->` Lenguage

- `A->aBa`
  - **NO REGULAR**
- `A->aa`
  - **NO REGULAR**
- `A-B`
  - **NO REGULAR**
- `a->bB`
  - **NO REGULAR**, **NO ES NADA**

### 7.4. Ejemplos Lenguaje `->` Gramatica

- L = {xbby|x,y IN {a,b}}

~~Todas las cadenas con ¿? contiene la subcadena ???~~

> [!NOTE] Intentamos **asignar** a cada **no terminal** un **significado**, una semantica.

- Por ejemplo S va a generar la primera parte x, que puede que sea cualquier palabra tal que si genero una a minuscula puedo continuar con la generacion
cuando he generado una b voy a cambiar el no terminal, por ejemplo A. 
- Cuando tengo A, mi gramatica ha generado ya ultima vez una b.

El significado de A es: cuando he generado A significa que la ultima letra generado fue una B y que a partir de este A puedo generar otra b o a

Resulta que la B dice: has generado las dos b o puedo volver al inicio.

Con B puedo generar cualquier letra aB o bB o termino a o b

- S -> aS|bA
- A -> bB|aS|b
- B -> aB|bB|a|b

### 7.5. Gramatica a Automata
Cuando haya una transicion A -> lambda, A es final

Tenemos dos algoritmos para pasar de una Gramatica a un Automata.

### 7.6. Nueva forma de verificar si un lenguaje es regular
Si volvemos a la pregunta, **_¿L es regular?_**. 
1. Para la respuesta **Es Regular**, podremos además de escribir un **_Automata_**, también escribir una **_Gramatica_**.
2. Para la respuesta **No es Regular**, seguimos solo teniendo **_Lema de Bombeo_**.
</details>

## 8. ER a Automata
### 8.1. Thomson (NFA-ε)
![er-automata.jpeg](er-automata.jpeg)
### 8.2. M'Naghten Yamada (NFA)
![er-automata-2.jpeg](er-automata-2.jpeg)
### 8.3 Brzozowski (DFA)
![er-automata-1.jpeg](er-automata-1.jpeg)

<details>
<summary><b>Apuntes</b></summary>

> [!NOTE] Otra forma de generar el automata es por intuición.



</details>

## 9. Automata a ER
### 9.1. Mediante Sistema de Ecuaciones
![automata-er1.jpeg](automata-er1.jpeg)

<details>
<summary><b>Apuntes</b></summary>

Ahora quiero sacar la expresion regular de un automata dado. 

Para **cualquier tipo de Automata** pero es aconsejable reducir el automata -> menos ecuaciones.

> [!NOTE] **La idea**:
> Tengo un automata y construyo un sistema de ecuaciones.


**Nos interesa la incognita asociada al estado inicial.**

La **solucion de cada una** de la incognitas va a ser una **expresion regular**. 

> ![NOTE]
> La expresion regular del estado inicial va a ser la expresion regular del automata.

#### 9.1.1. Propiedades
##### Solución para Recursiva
Formato de una recursiva: `X = rX + s`

**Ecuación general** `X = rX + s -> X = r*s`

##### Factor Común
> [!NOTE] Siempre a la derecha.
> 
Normalmente `ab+cb = b(a+c) = (a+c)b` por la propiedad **Conmutativa**.

Para estes calculos: `ab+cb = (a+c)b`, **No Conmutativa**.

##### Otras (Aunque no haga falta):
- `X + X = X`
- `X.X* = X*`
- `X*+lambda = X*`
- `X + lambda != X* + lambda`
- `X + lambda = X + lambda`
- `X* + lambda = X*`

> [!NOTE] Se pueden sacar intuitivamente.

Los ejercicios d este tipo piden exp reg, no se puede pedir un enunciado así. Se da un automata y se pida la exp reg minima, ya que no existe definicion que es una exp reg mas simple que otra.
-> Mas Corta -> Más Complicada -> Menos Letras? (No se puede).
No hay ninguna expresion para una exp reg mas simple.

</details>

### 9.2. Mediante Eliminación de Estados
![automata-er2.jpeg](automata-er2.jpeg)

<details>
<summary><b>Apuntes</b></summary>

Ahora, empezamos con un automata y sacamos la exp reg con un metodo intuitivo parecido al primer.

> [!NOTE] **La idea**:
> Eliminar estados tal que finalmente me quedo con un inicial, un final y una transicion entre ellos etiquetada con la transición puesta.

Antes, necesitamos cumplir con _ciertas condiciones_, si no cumple, veremos que no funciona:
1. **Ninguna transición entra en el estado inicial**.
2. **Ninguna transición sale del estado final**.
3. **Hay un unico estado final**.

**Si algun automata no cumple** con alguna de las condiciones:
1. Hacer un **nuevo estado inicial** con una **transicion vacia (e) hacia el original**.
2. Ir a **3**.
3. Con **transiciones vacias (e)**, unimos a un **nuevo estado final**, los **estados finales originales** ya no serán finales.

**_El Algoritmo_**:
1. **Eliminar un estado X** (Se aconseja para trabajar menos eliminar el estado con el numero minimo de transiciones).
2. Construir 2 conjuntos **In** y **Out**.
   - **In**: Estados que **llegan a X**.
   - **Out**: Estados que se **llegan desde X** (excepto si mismo).
3. **Eliminamos X**.
4. **Calculamos las expresiones para** `In --expresion--> Out` para cada estado afectado por la eliminacion de X.
5. **Creamos un nuevo automata**. el nuevo no es un atuomata normal , sino uno normalizado con expresiones regulares. Ir a **1** hasta que se eliminen todos.

</details>

## 10. ER a Gramatica
