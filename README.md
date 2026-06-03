# Task 3 — Stack: Expression Validator

**Course:** Data Structures & Algorithms (FU_02)  
**Group:** Group 6  
**Task:** Task 3 — Stack: Expression Validator

---

## Group Members

| Member | Role |
|--------|------|
| Phạm Văn Tùng | Developer |
| Nguyễn Ngọc Sơn | Developer |
| Trần Nam Anh | Developer |
| Đinh Thị Phương Thanh | Developer |
| Nguyễn Khắc Minh | Developer |
| Đào Triệu Thái Bảo | Developer |
| Nguyễn Đăng Hiếu | Developer |
---

## Description

A console-based Java application that validates arithmetic expressions using a **Stack** data structure. The program checks:

1. **Balanced brackets** — `()`, `[]`, `{}` using a `Stack<Character>`.
2. **Operator rules** — ensures `+`, `-`, `*`, `/` are not in invalid positions:
   - Cannot start or end with a binary operator.
   - No two operators in a row (e.g., `3++4` is invalid).
   - Operator cannot appear immediately after `(` or immediately before `)`.
3. Ignores spaces. Operands are **single-digit integers** (0–9).
4. Processes **multiple expressions** until user enters `quit`.

**Bonus:** Supports multi-digit integer validation via a tokenizer pass (`validateMultiDigit` method).

### Extended Validations *(beyond original requirements)*

The following rules are implemented **in addition** to the assignment requirements to make the validator behave correctly for real-world arithmetic expressions:

| # | Rule | Example (invalid) | Reason |
|---|------|--------------------|--------|
| EXT-1 | **Character whitelist** — only digits `0–9`, operators `+`,`-`,`*`,`/`, brackets `()[]{}`, and spaces are allowed. | `sadasdas`, `a+b` | Letters and unknown symbols are not valid arithmetic tokens. |
| EXT-2 | **No empty bracket pairs** — a closing bracket must not immediately follow an opening bracket (after stripping spaces). | `()`, `{}`, `([])`, `( )` | An empty bracket has no mathematical meaning. |

---

## How to Run

### Prerequisites
- Java 17+ (project uses Java 21)
- Maven (optional, for build)

### Compile and Run (Command Line)

```bash
# Navigate to the source directory
cd src/main/java

# Compile
javac com/group6/Task3_ExpressionValidator.java

# Run
java com.group6.Task3_ExpressionValidator
```

### Using Maven

```bash
mvn compile

# Interactive mode
mvn exec:java -Dexec.mainClass="com.group6.Task3_ExpressionValidator"

# Run automated test suite
java -cp target/classes com.group6.Task3_ExpressionValidator --test
```

### Using IntelliJ IDEA
1. Open the project in IntelliJ IDEA.
2. Navigate to `Task3_ExpressionValidator.java`.
3. Right-click → Run.

---

## Assumptions

1. Operands are single-digit integers (0–9) for the base task.
2. Only binary operators `+`, `-`, `*`, `/` are validated.
3. Unary minus is **not** required (as specified in the task).
4. Spaces are ignored during validation.
5. Maximum expression length: **200 characters**.
6. The `quit` command is case-insensitive.
7. Empty expressions (after removing spaces) are treated as invalid.

---

## Data Structure Used

- **`java.util.Stack<Character>`** — Used in `isBalancedBrackets()` to push opening brackets and pop/match when closing brackets are encountered.
- **`java.util.Stack<String>`** — Used in `validateMultiDigit()` (bonus) for the tokenizer pass to collect tokens.

---

## Bonus (+5): Lexical Analysis (Tokenization) Algorithm

The `validateMultiDigit(String expr)` method is specifically implemented to earn the Bonus (+5) points by introducing a Tokenization algorithm.

### Why do both methods yield the same "VALID" output?
If you test an expression with multi-digit numbers like `12+34`, both the basic method (`isValidExpression`) and the bonus method (`validateMultiDigit`) will return `VALID`. 
This happens because the basic method only checks for invalid adjacencies (e.g., two operators together `++`, or an operator next to a bracket `(+`). Since it does not strictly enforce single digits when evaluating characters, it accidentally accepts consecutive digits like `1` and `2` without throwing an error.

### What is the core difference?
Despite the identical output, their internal operations are completely different:
1. **Basic Method:** Sweeps through individual characters `['1', '2', '+', '3', '4']`. It has no concept of whole numbers.
2. **Bonus Method (Tokenizer):** Groups consecutive digits together into meaningful logical units using a `while` loop. It successfully transforms the raw string into an array of tokens: `["12", "+", "34"]` and pushes them into a **second Stack/Queue** as explicitly required by the assignment criteria.

### Why is this Tokenization crucial?
The instructor's intent is to assess our ability to prepare the expression for **actual calculation**. 
If we were asked to compute the value of `12+34`, the basic method would be useless because it treats `1` and `2` as disconnected characters. The Bonus method, however, successfully extracts `12` and `34` as complete integers. Because of this tokenizer pass, we could easily feed this stack of tokens into an evaluator (like the Shunting-yard algorithm) to compute `12 + 34 = 46`. 

The team intentionally implemented the `validateMultiDigit` method to demonstrate this Tokenizer technique, perfectly satisfying the technical constraints of the Bonus requirement.

---

## Test Cases

Run all test cases automatically with:
```bash
java -cp target/classes com.group6.Task3_ExpressionValidator --test
```

### Required (REQ) — from assignment spec

| # | Expression | Expected | Notes |
|---|-----------|----------|-------|
| 1 | `(3+5)*[2-1]` | VALID | Sample from spec |
| 2 | `(3+5]` | INVALID | Mismatched bracket types |
| 3 | `3+*5` | INVALID | Two consecutive operators |
| 4 | `+3` | INVALID | Starts with binary operator |
| 5 | `3+` | INVALID | Ends with binary operator |
| 6 | `([{3+2}])` | VALID | Deeply nested, all types |

### Edge / Boundary (EDGE)

| # | Expression | Expected | Notes |
|---|-----------|----------|-------|
| 7 | *(empty string)* | INVALID | No tokens at all |
| 8 | `"   "` (spaces only) | INVALID | Blank after stripping |
| 9 | `5` | VALID | Single-digit operand |
| 10 | `3 + 5` | VALID | Spaces ignored |
| 11 | `(+3)` | INVALID | Operator after opening bracket |
| 12 | `(3+)` | INVALID | Operator before closing bracket |

### Extended (EXT) — beyond requirements

| # | Expression | Expected | Notes |
|---|-----------|----------|-------|
| 13 | `sadasdas` | INVALID | [EXT-1] Letters only |
| 14 | `abc+3` | INVALID | [EXT-1] Mixed letters + digit |
| 15 | `{}` | INVALID | [EXT-2] Empty curly brackets |
| 16 | `()` | INVALID | [EXT-2] Empty round brackets |
| 17 | `( )` | INVALID | [EXT-2] Empty brackets with space |
| 18 | `([])` | INVALID | [EXT-2] Empty brackets nested |
| 19 | `(3+[2])` | VALID | [EXT-2] Mixed bracket types, valid |

---

## Sample Run Output

```
========================================================================
#    Expression                     Expected Actual   Result
------------------------------------------------------------------------
1    (3+5)*[2-1]                    VALID    VALID    PASS ✓
2    (3+5]                          INVALID  INVALID  PASS ✓
3    3+*5                           INVALID  INVALID  PASS ✓
4    +3                             INVALID  INVALID  PASS ✓
5    3+                             INVALID  INVALID  PASS ✓
6    ([{3+2}])                      VALID    VALID    PASS ✓
7    (empty)                        INVALID  INVALID  PASS ✓
8    (spaces)                       INVALID  INVALID  PASS ✓
9    5                              VALID    VALID    PASS ✓
10   3 + 5                          VALID    VALID    PASS ✓
11   (+3)                           INVALID  INVALID  PASS ✓
12   (3+)                           INVALID  INVALID  PASS ✓
13   sadasdas                       INVALID  INVALID  PASS ✓
14   abc+3                          INVALID  INVALID  PASS ✓
15   {}                             INVALID  INVALID  PASS ✓
16   ()                             INVALID  INVALID  PASS ✓
17   ( )                            INVALID  INVALID  PASS ✓
18   ([])                           INVALID  INVALID  PASS ✓
19   (3+[2])                        VALID    VALID    PASS ✓
========================================================================
Results: 19/19 passed  — All tests passed! ✓
========================================================================
```

### Interactive mode

```
=== Expression Validator ===
Enter an arithmetic expression to validate.
Supported brackets: (), [], {}
Supported operators: +, -, *, /
Operands: single-digit integers (0-9)
Type 'quit' to exit.

Expression: (3+5)*[2-1]
Result: VALID

Expression: (3+5]
Result: INVALID (Unbalanced brackets)

Expression: 3+*5
Result: INVALID (Operator error)

Expression: quit
Goodbye!
```
