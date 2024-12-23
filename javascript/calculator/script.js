const num1 = document.querySelector('#num1');
const num2 = document.querySelector('#num2');
const add = document.querySelector('#add');
const subtract = document.querySelector('#subtract');
const multiply = document.querySelector('#multiply');
const divide = document.querySelector('#divide');

function calculate(operation)
{
    const n1 = parseFloat(num1.value);
    const n2 = parseFloat(num2.value);

    if(isNaN(n1) || isNaN(n2))
    {
        alert("please enter valid number");
        return;
    }
    
    let result = 0;
    if(operation == 'add') result = n1 + n2;
    else if(operation == 'subtract') result = n1 - n2;
    else if(operation == 'multiply') result = n1 * n2;
    else if(operation == 'divide')
    {
        if(n2 == 0) alert("Cannot divide by zero.");
        else result = n1 / n2;
    }
    alert("The result is: " + result);
}

add.addEventListener('click', () => calculate('add'));
subtract.addEventListener('click', () => calculate('subtract'));
multiply.addEventListener('click', () => calculate('multiply'));
divide.addEventListener('click', () => calculate('divide'));
