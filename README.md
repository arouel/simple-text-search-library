# Simple Text Search Library

A very small and simple text search library.

## Search Operators

| Operator | What it does | Example |
| --- | --- | --- |
| Keywords separated by spaces | Act as an **AND** operator | **Yellow snake** matches that have **yellow** in the text and have **snake** in the text. |
| Keywords separated by commas | Act as an **OR** operator | **Yellow, snake** matches that have **yellow** in the text or have **snake** in the text. |
| Minus sign (-) | Act as a **NOT** operator | **Yellow -snake** matches that have **yellow** in the text and do not have **snake** in the text. |

## Example

```Java
String text = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore...";
Pattern.compile("lorem").matches(text)                                            // true
Pattern.compile("lorem -sed").matches(text)                                       // false
Pattern.compile("(lorem -sad), (ipsum -amot)").matches(text)                      // true
Pattern.compile("(lorem -sed), (ipsum -amet)").matches(text)                      // false
Pattern.compile("(lorem -sed), (ipsum -amet), (sed eirmod dolor)").matches(text)  // true
```

## Useful Gradle Tasks

* Prepare project for Eclipse IDE: `gradle compileJava eclipse`
* Executing tests:                 `gradle test`
* Create JAR:                      `gradle jar`
* Create a fat-JAR:                `gradle shadowJar`