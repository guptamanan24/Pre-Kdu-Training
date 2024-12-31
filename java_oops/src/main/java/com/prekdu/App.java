package com.prekdu;

import java.util.ArrayList;
import java.util.List;

// Abstract class for library resources
enum ResourceStatus {
  /** Indicates that the resource is currently borrowed by a member. */
  BORROWED,

  /** Indicates that the resource is available for use. */
  AVAILABLE
}

// Enums
enum MembershipType {
  /** Represents a standard membership with basic privileges. */
  STANDARD,

  /** Represents a premium membership with additional privileges. */
  PREMIUM
}

enum Frequency {
  /** Indicates that the publication is issued on a weekly basis. */
  WEEKLY,

  /** Indicates that the publication is issued on a monthly basis. */
  MONTHLY
}

enum ContentFormat {
  /** Represents content in Portable Document Format (PDF). */
  PDF,

  /** Represents content in ePub format, commonly used for eBooks. */
  EPUB
}

// Interfaces
interface Renewable {
  boolean renewLoan(LibraryMember member);
}

interface Reservable {
  void reserve(LibraryMember member);

  void cancelReservation(LibraryMember member);
}

abstract class LibraryResource {
  /** Represents the unique identifier for the resource. */
  private String resourceID;

  /** Represents the title of the resource. */
  private String title;

  /** Indicates whether the resource is currently available. */
  private boolean availableStatus;

  /** Represents the current status of the resource. */
  private ResourceStatus status;

  LibraryResource(final String rID, final String t, final boolean a) {
    this.resourceID = rID;
    this.title = t;
    this.availableStatus = a;
    this.status = a ? ResourceStatus.AVAILABLE : ResourceStatus.BORROWED;
  }

  public abstract double calculateLateFee(int daysLate);

  public abstract long getMaxLoanPeriod();

  public boolean isAvailable() {
    return availableStatus;
  }

  public void setAvailableStatus(final boolean s) {
    this.availableStatus = s;
    this.status = s ? ResourceStatus.AVAILABLE : ResourceStatus.BORROWED;
  }

  public String getResourceID() {
    return resourceID;
  }

  public ResourceStatus getStatus() {
    return status;
  }

  public String getTitle() {
    return title;
  }
}

// Resource Classes
class Book extends LibraryResource implements Reservable, Renewable {
  /** Represents the author of the book or publication. */
  private final String author;

  /** Represents the International Standard Book Number (ISBN) of the book. */
  private final String isbn;

  /** Indicates whether the book is reserved. Defaults to {@code false}. */
  private Boolean reserved = false;

  /** Represents the library member who has reserved the book. */
  private LibraryMember reservedBY;

  public String getAuthor() {
    return author;
  }

  public String getISBN() {
    return isbn;
  }

  Book(final String rID, final String t, final String auth, final String bn) {
    super(rID, t, true);
    this.author = auth;
    this.isbn = bn;
  }

  @Override
  public double calculateLateFee(final int daysLate) {
    final double val = 0.5;
    return daysLate * val;
  }

  @Override
  public long getMaxLoanPeriod() {
    final int val = 14;
    return val * 1;
  }

  @Override
  public boolean renewLoan(final LibraryMember member) {
    if ((member.getBorrowedResources().contains(this) && !reserved)
        || (member.getBorrowedResources().contains(this)
            && reserved
            && reservedBY.equals(member))) {
      System.out.println("Book renewed by member: " + member.getMemberID());
      return true;
    } else if (reserved && !reservedBY.equals(member)) {
      System.out.println("BOOK is Reserved by Another member");
      return false;
    } else if (!isAvailable()) {
      System.out.println("Book is not available for renewal");
      return false;
    }
    System.out.println("Book renewed by member: " + member.getMemberID());
    return true;
  }

  @Override
  public void reserve(final LibraryMember member) {
    if (reserved && !reservedBY.equals(member)) {
      throw new IllegalStateException(
          "Cannot reserve a Book that is already Reserved by another member.");
    } else {
      reserved = true;
      reservedBY = member;
      System.out.println("Book reserved by member: " + member.getMemberID());
    }
  }

  @Override
  public void cancelReservation(final LibraryMember member) {
    reserved = false;
    System.out.println("Book reservation canceled by: " + member.getMemberID());
  }
}

class DigitalContent extends LibraryResource implements Renewable {
  /** Represents the size of the file in bytes. */
  private final double fileSize;

  /** Specifies the format of the content, such as PDF, EPUB, or DOCX. */
  private final ContentFormat format;

  //  DigitalContent() {
  //    super(r, t, true);
  //    this.fileSize = s;
  //    this.format = f;
  //  }
  DigitalContent(
      final String resourceID,
      final String title,
      final double newFilesize,
      final ContentFormat newformat) {
    super(resourceID, title, true);
    this.fileSize = newFilesize;
    this.format = newformat;
  }

  public double getFileSize() {
    return fileSize;
  }

  public ContentFormat getFormat() {
    return format;
  }

  @Override
  public double calculateLateFee(final int daysLate) {
    final double val = 0.25;
    return daysLate * val;
  }

  @Override
  public long getMaxLoanPeriod() {
    final int val = 7;
    return val * 1;
  }

  @Override
  public boolean renewLoan(final LibraryMember member) {
    if (member.getBorrowedResources().contains(this)) {
      System.out.println("Periodical renewed by: " + member.getMemberID());
      return true;
    } else if (!isAvailable()) {
      System.out.println("Periodical is not available for renewal");
      return false;
    }
    System.out.println("Periodical renewed by member: " + member.getMemberID());
    return true;
  }
}

class Periodical extends LibraryResource implements Reservable, Renewable {
  /** Represents the issue number of the publication. */
  private final int issueNumber;

  /** Specifies the frequency of the publication. */
  private final Frequency frequency;

  /** Indicates publication is reserved. Defaults to {@code false}. */
  private Boolean reserved = false;

  /** Represents the library member who reserved the publication. */
  private LibraryMember reservedBY;

  Periodical(
      final String resourceID,
      final String title,
      final int newissueNumber,
      final Frequency newfrequency) {
    super(resourceID, title, true);
    this.issueNumber = newissueNumber;
    this.frequency = newfrequency;
  }

  public int getIssueNumber() {
    return issueNumber;
  }

  public Frequency getFrequency() {
    return frequency;
  }

  @Override
  public double calculateLateFee(final int daysLate) {
    final double val1 = 0.75;
    return daysLate * val1;
  }

  @Override
  public long getMaxLoanPeriod() {
    final int val1 = 7;
    return val1 * 1;
  }

  @Override
  public boolean renewLoan(final LibraryMember member) {
    if ((member.getBorrowedResources().contains(this) && !reserved)
        || (member.getBorrowedResources().contains(this)
            && reserved
            && reservedBY.equals(member))) {
      System.out.println("Periodical renewed by: " + member.getMemberID());
      return true;
    } else if (reserved && !reservedBY.equals(member)) {
      System.out.println("Periodical is Reserved by Another member");
      return false;
    } else if (!isAvailable()) {
      System.out.println("Periodical is not available for renewal");
      return false;
    }
    System.out.println("Periodical renewed by member: " + member.getMemberID());
    return true;
  }

  @Override
  public void reserve(final LibraryMember member) {
    if (reserved && !reservedBY.equals(member)) {
      throw new IllegalStateException("Already Reserved.");
    } else {
      reserved = true;
      reservedBY = member;
      System.out.println("Periodical reserved by: " + member.getMemberID());
    }
  }

  @Override
  public void cancelReservation(final LibraryMember member) {
    reserved = false;
    System.out.println("Reservation canceled by: " + member.getMemberID());
  }
}

// Custom Exceptions
class ResourceNotAvailableException extends Exception {
  ResourceNotAvailableException(final String message) {
    super(message);
  }
}

class MaximumLoanExceededException extends Exception {
  MaximumLoanExceededException(final String message) {
    super(message);
  }
}

// Library Member
class LibraryMember {
  /** The unique identifier for the library member. */
  private final String memberID;

  /** The type of membership for the library member. */
  private final MembershipType membershipType;

  /** The list of resources currently borrowed by the library member. */
  private final List<LibraryResource> borrowedResources;

  LibraryMember(final String mID, final MembershipType inputmembershipType) {
    this.memberID = mID;
    this.membershipType = inputmembershipType;
    this.borrowedResources = new ArrayList<>();
  }

  public String getMemberID() {
    return memberID;
  }

  public int getMaxLoanLimit() {
    final int k1 = 5;
    final int k2 = 10;
    return membershipType == MembershipType.PREMIUM ? k2 : k1;
  }

  public List<LibraryResource> getBorrowedResources() {
    return borrowedResources;
  }

  public synchronized void borrowResource(final LibraryResource resource)
      throws ResourceNotAvailableException, MaximumLoanExceededException {
    if (resource == null) {
      throw new IllegalArgumentException("Resource cannot be null.");
    }
    if (!resource.isAvailable()) {
      throw new ResourceNotAvailableException(
          "Resource " + resource.getResourceID() + " is not available.");
    }
    if (borrowedResources.size() >= getMaxLoanLimit()) {
      throw new MaximumLoanExceededException("loan limit exceeded:" + memberID);
    }
    borrowedResources.add(resource);
    resource.setAvailableStatus(false);
    System.out.println(resource.getResourceID() + " borrowed by: " + memberID);
  }

  public synchronized void returnResource(final LibraryResource resource) {
    if (borrowedResources.remove(resource)) {
      resource.setAvailableStatus(true);
      System.out.println(resource.getResourceID() + "returned by: " + memberID);
    } else {
      System.out.println("Resource not found in borrowed resources.");
    }
  }
}

// Main Application
public final class App {
  private App() {
    /*this is comment*/
  }

  /**
   * The entry point of the application.
   *
   * @param args the input arguments
   */
  public static void main(final String[] args) {
    try {
      Book book =
          new Book(
              "B001", "Clean Code",
              "Robert Martin", "978-0132350884");
      final double val1 = 15.5;
      final int val2 = 12;
      DigitalContent digitalContent =
          new DigitalContent("D001", "Digital Design", val1, ContentFormat.PDF);
      Periodical p = new Periodical("P001", "WEEK", val2, Frequency.WEEKLY);

      LibraryMember sM = new LibraryMember("STD001", MembershipType.STANDARD);
      LibraryMember pM = new LibraryMember("PRE001", MembershipType.PREMIUM);

      sM.borrowResource(book);
      pM.borrowResource(digitalContent);
      pM.borrowResource(p);

      sM.returnResource(book);

      try {
        sM.borrowResource(book);
      } catch (ResourceNotAvailableException | MaximumLoanExceededException e) {
        System.err.println(e.getMessage());
      }

    } catch (ResourceNotAvailableException | MaximumLoanExceededException e) {
      System.err.println(e.getMessage());
    }
  }
}
