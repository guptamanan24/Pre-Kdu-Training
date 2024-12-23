const countervalue = document.getElementById('counter-value');
const increment = document.getElementById('increment');
const decrement = document.getElementById('decrement');

let count = 0;
increment.onclick = () => {
    count++;
    countervalue.innerHTML = count;
};

decrement.addEventListener('click', () => {
    count--;
    countervalue.innerHTML = count;
  });

