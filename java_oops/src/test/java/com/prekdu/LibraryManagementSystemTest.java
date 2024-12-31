package com.prekdu;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LibraryManagementSystemTest {
  private LibraryMember standardMember;
  private LibraryMember premiumMember;
  private Book book;
  private DigitalContent digitalContent;

  @BeforeEach
  void setUp() {
    standardMember = new LibraryMember("STD001", MembershipType.STANDARD);
    premiumMember = new LibraryMember("PRE001", MembershipType.PREMIUM);
    book = new Book("B001", "Clean Code", "Robert Martin", "978-0132350884");
    digitalContent = new DigitalContent("D001", "Digital Design", 15.5, ContentFormat.PDF);
  }

  @Test
  void testBookLateFeeCalculation() {
    assertEquals(5.0, book.calculateLateFee(10), 0.01);
  }

  @Test
  void testDigitalContentLateFeeCalculation() {
    assertEquals(2.5, digitalContent.calculateLateFee(10), 0.01);
  }

  @Test
  void testStandardMemberBorrowLimit() {
    for (int i = 0; i < 5; i++) {
      Book newBook = new Book("B00" + i, "Test Book " + i, "Author", "ISBN");
      try {
        standardMember.borrowResource(newBook);
      } catch (ResourceNotAvailableException | MaximumLoanExceededException e) {
        fail("Unexpected exception: " + e.getMessage());
      }
    }

    Book extraBook = new Book("B006", "Extra Book", "Author", "ISBN");
    assertThrows(
        MaximumLoanExceededException.class, () -> standardMember.borrowResource(extraBook));
  }

  @Test
  void testPremiumMemberBorrowLimit() {
    for (int i = 0; i < 10; i++) {
      Book newBook = new Book("B00" + i, "Test Book " + i, "Author", "ISBN");
      try {
        premiumMember.borrowResource(newBook);
      } catch (ResourceNotAvailableException | MaximumLoanExceededException e) {
        fail("Unexpected exception: " + e.getMessage());
      }
    }
    assertEquals(10, premiumMember.getBorrowedResources().size());
  }

  @Test
  void testBorrowAndReturn() {
    try {
      standardMember.borrowResource(book); // Handle exception here
    } catch (ResourceNotAvailableException | MaximumLoanExceededException e) {
      fail("Unexpected exception: " + e.getMessage());
    }
    assertEquals(ResourceStatus.BORROWED, book.getStatus());
    assertEquals(1, standardMember.getBorrowedResources().size());

    standardMember.returnResource(book);
    assertEquals(ResourceStatus.AVAILABLE, book.getStatus());
    assertEquals(0, standardMember.getBorrowedResources().size());
  }

  @Test
  void testBookReservation() {
    try {
      standardMember.borrowResource(book);
    } catch (ResourceNotAvailableException | MaximumLoanExceededException e) {
      fail("Unexpected exception: " + e.getMessage());
    }
    LibraryMember anotherMember = new LibraryMember("STD002", MembershipType.STANDARD);
    book.reserve(anotherMember);
    assertFalse(book.renewLoan(standardMember));
  }

  @Test
  void testResourceAvailability() {
    try {
      standardMember.borrowResource(book); // Handle exception here
    } catch (ResourceNotAvailableException | MaximumLoanExceededException e) {
      fail("Unexpected exception: " + e.getMessage());
    }
    LibraryMember anotherMember = new LibraryMember("STD002", MembershipType.STANDARD);
    assertThrows(ResourceNotAvailableException.class, () -> anotherMember.borrowResource(book));
  }

  @Test
  void testDigitalContentRenewal() {
    try {
      standardMember.borrowResource(digitalContent); // Handle exception here
    } catch (ResourceNotAvailableException | MaximumLoanExceededException e) {
      fail("Unexpected exception: " + e.getMessage());
    }
    assertTrue(digitalContent.renewLoan(standardMember));
  }

  @Test
  void testInvalidReservation() {
    LibraryMember anotherMember = new LibraryMember("STD002", MembershipType.STANDARD);
    book.reserve(anotherMember);
    assertThrows(IllegalStateException.class, () -> book.reserve(standardMember));
  }
}
