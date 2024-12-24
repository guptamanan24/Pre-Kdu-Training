/*
  Create a utility function findById that takes an array of User objects and a number as arguments and returns a User | undefined (if the user with the given ID exists).
  Extend the function to support a new parameter, which specifies if the returned user should include undefined or throw an error. Use TypeScript's never type to represent the error-throwing case.
  Test the function using a sample array of User objects and handle both scenarios (with and without throwing an error).

  Export the function findById so that it can be used in the test file.
*/

import { User } from './q1';

export function findById(users: User[], id: number, throwIfNotFound: boolean = false): User |
undefined | never {
  const user = users.find(user => user.id === id);
  if (user) {
  return user;
  }
  if (!user && throwIfNotFound) {
  throw new Error(`User with ID ${id} not found`);
  }
  return undefined;
  }
      


      const sampleUsers: User[] = [
        { id: 1, name: "Alice", email: "alice@company.com" },
        { id: 2, name: "Bob", email: "bob@gmail.com" },
        { id: 3, name: "Charlie", email: "charlie@company.com" },
      ];
      
      const foundUser1 = findById(sampleUsers, 2);
      console.log(foundUser1); 
      
      const notFoundUser1 = findById(sampleUsers, 5);
      console.log(notFoundUser1);

      try {
        const foundUser2 = findById(sampleUsers, 5, true);
        console.log(foundUser2);
      } catch (error) {
        console.log(error); 
      }
      