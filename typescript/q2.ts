/*
  Create an enum Status with the following members:
  Active
  Inactive
  Suspended

  Define a tuple type UserStatus that contains:
  A User object (from Question 1)
  A Status value

  Write a function printUserStatus that accepts a UserStatus tuple as a parameter and logs a message in the format:
  "[UserName] is currently [Status]."
  
  Create a sample tuple and call the function with it.

  Export the enum Status, the UserStatus tuple type, and the printUserStatus function.
  So that the code can be tested in the test file.
 */

import { User } from "./q1";
 export enum Status {
  Active = "Active",
  Inactive = "Inactive",
  Suspended = "Suspended"
}

// export type User = {
//   id: number;
//   name: string;
//   email: string;
//   role?:string;
// };


export type UserStatus = [User, Status];

export function printUserStatus(userStatus: UserStatus):void {
  const [user, status] = userStatus;
  console.log(`${user.name} is currently ${status}.`);
}

const sampleUser: User = {
  id: 1,
  name: "Alice",
  email: "alice@example.com",
  role: "Admin",
};

const sampleStatus: UserStatus = [sampleUser, Status.Active];

printUserStatus(sampleStatus); 
