# JavaCalculator
The calculator implements the basic binary operations using infix style, and with proper error handling feedback to users. 

The features of this state transferring calculator are as follow:

- transferring state between inputting the first number, inputting operator, inputting the second number and calculating.
- basic operations include plus, minus, multiplication and division.
- supporting basic float computing.
- the calculator will change to scientific notion style when input number exceeds digits limitation(14 for integer, 13 for float)
- strict state transition rules.

Illegal input type:

- Double decimal points in a number
- Input equal without an operator
- Leading with equal
- Input without second number (op -> equal)
- Leading with operators
- Operator followed by another operator (op -> op)
- Input operator after inputting the second number 
- Divider is zero

When inputting a number leading with a decimal point, the calculator would see the number as 0.###.

ScreenShot:
