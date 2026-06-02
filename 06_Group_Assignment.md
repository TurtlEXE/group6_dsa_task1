# DSA Assignment Tasks — Intermediate Level

**Course:** Data Structures & Algorithms (FU_02)  
**Format:** 6 tasks — assign each task to a student group when ready  
**Language:** Java (console application)  
**Duration suggestion:** 3–5 class sessions (in-class + homework)

---

## General Rules (All Tasks)

1. Use **Java 17+** and a single `main` class unless the schema asks for extra classes.
2. Follow the **method signatures** in your task schema. You may add private helper methods.
3. Handle **invalid input** with clear messages (do not crash silently).
4. Print results exactly in the **output format** shown in your task (spacing and labels matter for grading).
5. Submit:
   - Source code (`.java` files)
   - Short `README.md` (how to run, assumptions, task number, group members)
   - Sample run screenshot or text output for **at least 3 test cases**
6. **No GUI required** — console only.
7. You may use `java.util` collections where allowed in your task. **Do not** use external libraries.

### Grading Overview (100 points per task)

| Criteria | Points |
|----------|--------|
| Correctness (sample + hidden tests) | 40 |
| Required data structures used correctly | 25 |
| Code structure (classes, methods, naming) | 15 |
| Input validation & edge cases | 10 |
| README + demo output | 10 |

---

## Task 3 — Stack: Expression Validator  
**Topics:** `stack`

### Scenario
Build a **bracket and operator validator** for simple arithmetic expressions using a **stack** (`java.util.Stack` or your own stack class).

### Requirements
1. Check whether an expression has **balanced brackets**: `()`, `[]`, `{}`.
2. After brackets are balanced, validate that **operators** (`+`, `-`, `*`, `/`) are not in invalid positions:
   - Cannot start or end with a binary operator (except unary minus is **not** required in this task).
   - No two operators in a row (e.g. `3++4` is invalid).
3. Ignore spaces. Operands are **single-digit integers** only (0–9) for this task.
4. Process **multiple expressions** until user enters `quit`.

### Class Schema

```java
public class Task3_ExpressionValidator {

    public static boolean isBalancedBrackets(String expr);

    public static boolean isValidExpression(String expr); // brackets + operator rules

    public static void main(String[] args);
}
```

### Input / Output Examples
```
Expression: (3+5)*[2-1]
Result: VALID

Expression: (3+5]
Result: INVALID (Unbalanced brackets)

Expression: 3+*5
Result: INVALID (Operator error)
```

### Constraints
- Must use a **stack** for bracket matching (grading will check usage).
- Maximum expression length: **200** characters.

### Bonus (+5)
Extend to support **multi-digit integers** using a second stack or queue for tokens (tokenizer pass).

---

## Suggested Test Cases (Instructors)

| Task | Must-test edge case |
|------|---------------------|
| 1 | ID not in grid; ID in multiple cells |
| 2 | Tie scores; duplicate ID rejected |
| 3 | Empty string; nested brackets `([{}])` |
| 4 | Process empty queue; many VIP jobs in a row |
| 5 | Enroll same student twice; drop from empty course |
| 6 | Merge when one list empty; duplicate insert rejected |

---

## Submission Checklist (Students)

- [ ] Correct **task number** in class name / README title
- [ ] All required methods from schema implemented
- [ ] Required data structure used (not replaced by a simpler one)
- [ ] At least 3 test cases documented in README
- [ ] Code compiles with `javac` and runs with `java`
- [ ] No hard-coded output only — program must react to user input

---

## Topic Coverage Map

| Task | Array | ArrayList | Stack | Queue | HashSet | HashMap | LinkedList | Search | Sort |
|------|:-----:|:---------:|:-----:|:-----:|:-------:|:-------:|:----------:|:------:|:----:|
| 1 | ✓ | | | | | | | ✓ | |
| 2 | | ✓ | | | | | | | ✓ |
| 3 | | | ✓ | | | | | | |
| 4 | | | | ✓ | | | | | |
| 5 | | | | | ✓ | ✓ | | | ✓* |
| 6 | | | | | | | ✓ | ✓ | ✓ |

\* Task 5: sort student **names** for display only.

---

## Task Assignment (Instructor)

Use this table when you assign tasks to groups:

| Student group | Assigned task |
|---------------|---------------|
| Group 1 | Task 5 |
| Group 2 | Task 6 |
| Group 3 | Task 2 |
| Group 4 | Task 4 |
| Group 5 | Task 1 |
| Group 6 | Task 3 |

---

*Good luck — focus on clear structure, correct use of each data structure, and readable output.*
