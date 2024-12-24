/*
  Define a generic function filterArray<T>(arr: T[], predicate: (item: T) => boolean): T[] that filters an array based on a predicate function.
  Use this function to filter an array of numbers and return only even numbers.
  Use the same function to filter an array of User objects and return users whose email includes "@company.com".

  Export the filterArray function so that the code can be tested in the test file.
*/

import { User } from './q1';

export function filterArray<T>(arr: T[], predicate: (item: T) => boolean): T[]
{
   return arr.filter(predicate);
}

const numbers: number[] = [1,2,3,4,5,6];
const evenNumbers = filterArray(numbers, (num) => num % 2 === 0);
console.log(evenNumbers); 


const Users : User[] = [
  {id:1,name:"Manan",email:"Manan@company "},
  {id:2,name:"Rahul",email:"Rahul@company.com"},
  {id:3,name:"Rohan",email:"Rohan@company.com"},
];

const companyUsers = filterArray(Users,(User) => User.email.includes("@company.com"));
console.log(companyUsers);
