# Polynomial Expansion 


## Important notice:
* It works with any number of brackets
* It works with any number of variables, not only x

## TODO
* validation user's input
* error handling

## Description
Have the function PolynomialExpansion(str) take str which will be a string representing a polynomial containing only (+/-) integers, a letter, parenthesis, and the symbol "^", and return it in expanded form. 

For example: <br>  

    if str is "(2x^2+4)(6x^3+3)",  
    then the output should be "12x^5+24x^3+6x^2+12".  
Both the input and output should contain no spaces. <br>  
The input will only contain one letter, such as "x", "y", "b", etc.  <br>  
There will only be four parenthesis in the input and your output should contain no parenthesis.  <br>  
The output should be returned with the highest exponential element first down to the lowest. <br>  

More generally, the form of str will be: 
    
    ([+/-]{num}[{letter}[{^}[+/-]{num}]]...[[+/-]{num}]...)(copy) 
    
where: 
* "[]" represents optional features
* "{}" represents mandatory features
* "num" represents integers 
* "letter" represents letters such as "x".


Examples

    Input: "(1x)(2x^-2+1)"
    Output: x+2x^-1
    
    Input: "(-1x^3)(3x^3+2)"
    Output: -3x^6-2x^3
