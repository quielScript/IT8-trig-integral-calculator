# IT8-trig-integral-calculator

## Overview
This document lists all the indefinite integral formulas and rules that the Trigonometric Integral Calculator can handle.

---

## Basic Trigonometric Functions

### 1. Sine Function
**Input:** `sin(x)` or `a*sin(x)` where a is any integer coefficient

**Formula:**
```
∫ sin(x) dx = −cos(x) + C
```

**General Form:**
```
∫ a·sin(x) dx = −a·cos(x) + C
```

**Reasoning:** Because d/dx[cos(x)] = −sin(x)

**Example:**
- Input: `5sin(x)`
- Output: `−5cos(x) + C`

---

### 2. Cosine Function
**Input:** `cos(x)` or `a*cos(x)`

**Formula:**
```
∫ cos(x) dx = sin(x) + C
```

**General Form:**
```
∫ a·cos(x) dx = a·sin(x) + C
```

**Reasoning:** Because d/dx[sin(x)] = cos(x)

**Example:**
- Input: `3cos(x)`
- Output: `3sin(x) + C`

---

### 3. Tangent Function
**Input:** `tan(x)` or `a*tan(x)`

**Formula:**
```
∫ tan(x) dx = −ln|cos(x)| + C
```

**General Form:**
```
∫ a·tan(x) dx = −a·ln|cos(x)| + C
```

**Reasoning:** Because d/dx[−ln|cos(x)|] = tan(x)

**Alternative Form:** Can also be written as `ln|sec(x)| + C`

**Example:**
- Input: `2tan(x)`
- Output: `−2ln|cos(x)| + C`

---

### 4. Cotangent Function
**Input:** `cot(x)` or `a*cot(x)`

**Formula:**
```
∫ cot(x) dx = ln|sin(x)| + C
```

**General Form:**
```
∫ a·cot(x) dx = a·ln|sin(x)| + C
```

**Reasoning:** Because d/dx[ln|sin(x)|] = cot(x)

**Alternative Form:** Can also be written as `−ln|csc(x)| + C`

**Example:**
- Input: `4cot(x)`
- Output: `4ln|sin(x)| + C`

---

### 5. Secant Function
**Input:** `sec(x)` or `a*sec(x)`

**Formula:**
```
∫ sec(x) dx = ln|sec(x) + tan(x)| + C
```

**General Form:**
```
∫ a·sec(x) dx = a·ln|sec(x) + tan(x)| + C
```

**Reasoning:** Because d/dx[ln|sec(x) + tan(x)|] = sec(x)

**Alternative Form:** Can also be written as `ln|tan(x/2 + π/4)| + C`

**Example:**
- Input: `sec(x)`
- Output: `ln|sec(x) + tan(x)| + C`

---

### 6. Cosecant Function
**Input:** `csc(x)` or `a*csc(x)`

**Formula:**
```
∫ csc(x) dx = −ln|csc(x) + cot(x)| + C
```

**General Form:**
```
∫ a·csc(x) dx = −a·ln|csc(x) + cot(x)| + C
```

**Reasoning:** Because d/dx[−ln|csc(x) + cot(x)|] = csc(x)

**Alternative Form:** Can also be written as `ln|tan(x/2)| + C`

**Example:**
- Input: `3csc(x)`
- Output: `−3ln|csc(x) + cot(x)| + C`

---

## Squared Trigonometric Functions

### 7. Secant Squared
**Input:** `sec^2(x)` or `a*sec^2(x)`

**Formula:**
```
∫ sec²(x) dx = tan(x) + C
```

**General Form:**
```
∫ a·sec²(x) dx = a·tan(x) + C
```

**Reasoning:** Because d/dx[tan(x)] = sec²(x)

**Example:**
- Input: `5sec^2(x)`
- Output: `5tan(x) + C`

---

### 8. Cosecant Squared
**Input:** `csc^2(x)` or `a*csc^2(x)`

**Formula:**
```
∫ csc²(x) dx = −cot(x) + C
```

**General Form:**
```
∫ a·csc²(x) dx = −a·cot(x) + C
```

**Reasoning:** Because d/dx[−cot(x)] = csc²(x)

**Example:**
- Input: `2csc^2(x)`
- Output: `−2cot(x) + C`

---

### 9. Sine Squared
**Input:** `sin^2(x)` or `a*sin^2(x)`

**Formula:**
```
∫ sin²(x) dx = x/2 − sin(2x)/4 + C
```

**General Form:**
```
∫ a·sin²(x) dx = a(x/2 − sin(2x)/4) + C
```

**Reasoning:** Using the power-reduction identity: sin²(x) = (1 − cos(2x))/2

**Derivation:**
```
∫ sin²(x) dx = ∫ (1 − cos(2x))/2 dx
             = (1/2)∫ 1 dx − (1/2)∫ cos(2x) dx
             = x/2 − sin(2x)/4 + C
```

**Example:**
- Input: `3sin^2(x)`
- Output: `3(x/2 − sin(2x)/4) + C`

---

### 10. Cosine Squared
**Input:** `cos^2(x)` or `a*cos^2(x)`

**Formula:**
```
∫ cos²(x) dx = x/2 + sin(2x)/4 + C
```

**General Form:**
```
∫ a·cos²(x) dx = a(x/2 + sin(2x)/4) + C
```

**Reasoning:** Using the power-reduction identity: cos²(x) = (1 + cos(2x))/2

**Derivation:**
```
∫ cos²(x) dx = ∫ (1 + cos(2x))/2 dx
             = (1/2)∫ 1 dx + (1/2)∫ cos(2x) dx
             = x/2 + sin(2x)/4 + C
```

**Example:**
- Input: `2cos^2(x)`
- Output: `2(x/2 + sin(2x)/4) + C`

---

### 11. Tangent Squared
**Input:** `tan^2(x)` or `a*tan^2(x)`

**Formula:**
```
∫ tan²(x) dx = tan(x) − x + C
```

**General Form:**
```
∫ a·tan²(x) dx = a(tan(x) − x) + C
```

**Reasoning:** Using the Pythagorean identity: tan²(x) = sec²(x) − 1

**Derivation:**
```
∫ tan²(x) dx = ∫ (sec²(x) − 1) dx
             = ∫ sec²(x) dx − ∫ 1 dx
             = tan(x) − x + C
```

**Example:**
- Input: `4tan^2(x)`
- Output: `4(tan(x) − x) + C`

---

### 12. Cotangent Squared
**Input:** `cot^2(x)` or `a*cot^2(x)`

**Formula:**
```
∫ cot²(x) dx = −cot(x) − x + C
```

**General Form:**
```
∫ a·cot²(x) dx = a(−cot(x) − x) + C
```

**Reasoning:** Using the Pythagorean identity: cot²(x) = csc²(x) − 1

**Derivation:**
```
∫ cot²(x) dx = ∫ (csc²(x) − 1) dx
             = ∫ csc²(x) dx − ∫ 1 dx
             = −cot(x) − x + C
```

**Example:**
- Input: `cot^2(x)`
- Output: `−cot(x) − x + C`

---

## Integration Rules Applied

### 1. Sum/Difference Rule
```
∫ [f(x) ± g(x)] dx = ∫ f(x) dx ± ∫ g(x) dx
```

**Example:**
- Input: `sin(x) + cos(x)`
- Process: `∫ sin(x) dx + ∫ cos(x) dx`
- Output: `−cos(x) + sin(x) + C`

---

### 2. Constant Multiple Rule
```
∫ k·f(x) dx = k·∫ f(x) dx
```
where k is any constant

**Example:**
- Input: `5sin(x)`
- Process: `5·∫ sin(x) dx`
- Output: `5·(−cos(x)) + C = −5cos(x) + C`

---

### 3. Linearity of Integration
The calculator combines both rules above to handle expressions like:
```
∫ [a·f(x) + b·g(x) − c·h(x)] dx = a·∫ f(x) dx + b·∫ g(x) dx − c·∫ h(x) dx
```

**Example:**
- Input: `9sin(x) - 3cos(x) + 2tan(x)`
- Output: `−9cos(x) − 3sin(x) − 2ln|cos(x)| + C`

---

## Complex Expression Examples

### Example 1: Mixed Basic Functions
**Input:** `3sin(x) + 2cos(x) - tan(x)`

**Solution:**
```
∫ [3sin(x) + 2cos(x) − tan(x)] dx
= 3∫ sin(x) dx + 2∫ cos(x) dx − ∫ tan(x) dx
= 3(−cos(x)) + 2(sin(x)) − (−ln|cos(x)|)
= −3cos(x) + 2sin(x) + ln|cos(x)| + C
```

---

### Example 2: Squared Functions
**Input:** `sin^2(x) + cos^2(x)`

**Solution:**
```
∫ [sin²(x) + cos²(x)] dx
= ∫ sin²(x) dx + ∫ cos²(x) dx
= (x/2 − sin(2x)/4) + (x/2 + sin(2x)/4)
= x + C
```

**Note:** This demonstrates that sin²(x) + cos²(x) = 1 (Pythagorean identity)

---

### Example 3: Pythagorean Identities
**Input:** `sec^2(x) - tan^2(x)`

**Solution:**
```
∫ [sec²(x) − tan²(x)] dx
= ∫ sec²(x) dx − ∫ tan²(x) dx
= tan(x) − (tan(x) − x)
= x + C
```

**Note:** This demonstrates that sec²(x) − tan²(x) = 1

---

### Example 4: Multiple Terms with Coefficients
**Input:** `4sin(x) - 2cos(x) + 3sec^2(x) - tan(x)`

**Solution:**
```
∫ [4sin(x) − 2cos(x) + 3sec²(x) − tan(x)] dx
= 4∫ sin(x) dx − 2∫ cos(x) dx + 3∫ sec²(x) dx − ∫ tan(x) dx
= 4(−cos(x)) − 2(sin(x)) + 3(tan(x)) − (−ln|cos(x)|)
= −4cos(x) − 2sin(x) + 3tan(x) + ln|cos(x)| + C
```

---

## Input Format Guidelines

### Supported Formats:
1. **Function alone:** `sin(x)`, `cos(x)`, `tan(x)`
2. **With coefficient:** `5sin(x)`, `3cos(x)`, `2tan(x)`
3. **Negative terms:** `-sin(x)`, `-4cos(x)`
4. **Squared functions:** `sin^2(x)`, `cos^2(x)`, `sec^2(x)`, etc.
5. **Addition:** `sin(x) + cos(x)`, `3sin(x) + 2tan(x)`
6. **Subtraction:** `sin(x) - cos(x)`, `5sin(x) - 3cos(x)`
7. **Double negatives:** `sin(x) - -cos(x)` (becomes `sin(x) + cos(x)`)
8. **Mixed operations:** `9sin(x) - 3cos(x) + 2sec^2(x) - tan^2(x)`

### Important Notes:
- Always include `(x)` after the function name
- Use `^2` for squared functions (e.g., `sin^2(x)` not `sin²(x)`)
- Spaces are optional and will be ignored
- Coefficients must be integers
- The constant of integration `+ C` is automatically added

---

## Limitations

### Currently NOT Supported:
1. **Higher powers:** `sin^3(x)`, `cos^4(x)`, etc.
2. **Products:** `sin(x)cos(x)`, `sin(x)tan(x)`
3. **Quotients:** `sin(x)/cos(x)` (use `tan(x)` instead)
4. **Compositions:** `sin(2x)`, `cos(3x)`, `tan(x/2)`
5. **Inverse functions:** `arcsin(x)`, `arccos(x)`, `arctan(x)`
6. **Decimal coefficients:** `2.5sin(x)`, `1.3cos(x)`
7. **Variable coefficients:** `xsin(x)`, `x²cos(x)`
8. **Exponential/logarithmic mixed:** `e^x·sin(x)`, `ln(x)·cos(x)`

---
