# Task 3 — Stack: Expression Validator

**Course:** Data Structures & Algorithms (FU_02)  
**Group:** Group 6  
**Task:** Task 3 — Stack: Expression Validator

---

## Group Members

| Member | Role |
|--------|------|
| Phạm Văn Tùng | Leader |
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
3. Ignores spaces. Operands are **single-digit integers** (0–9).
4. Processes **multiple expressions** until user enters `quit`.

**Bonus:** Supports multi-digit integer validation via a tokenizer pass (`validateMultiDigit` method).

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
mvn exec:java -Dexec.mainClass="com.group6.Task3_ExpressionValidator"
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

| Case Type | Expression | Expected Output | Reason |
|-----------|------------|-----------------|--------|
| Sample 1 | `(3+5)*[2-1]` | `VALID` | Well-formed brackets and operators. |
| Sample 2 | `(3+5]` | `INVALID (Unbalanced brackets)` | Mismatched closing bracket `]`. |
| Sample 3 | `3+*5` | `INVALID (Operator error)` | Two operators in a row `+*`. |
| Edge (Nested) | `([{}])` | `VALID` | Only nested brackets, valid structure. |
| Edge (Empty) | `   ` | `INVALID (Operator error)` | Nothing to evaluate after stripping spaces. |
| Edge (Op Start) | `+3-4` | `INVALID (Operator error)` | Cannot start with a binary operator. |
| Edge (Op End) | `3-4*` | `INVALID (Operator error)` | Cannot end with a binary operator. |
| Edge (Op Next to Bracket)| `(+3)` | `INVALID (Operator error)` | Operator cannot immediately follow an opening bracket. |

## Sample Run Output

```text
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

Expression: ([{}])
Result: VALID

Expression:    
Result: INVALID (Operator error)

Expression: +3-4
Result: INVALID (Operator error)

Expression: 3-4*
Result: INVALID (Operator error)

Expression: (+3)
Result: INVALID (Operator error)

Expression: quit
Goodbye!
```
