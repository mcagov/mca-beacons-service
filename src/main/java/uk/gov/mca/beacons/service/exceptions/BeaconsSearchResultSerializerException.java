package uk.gov.mca.beacons.service.exceptions;

public class BeaconsSearchResultSerializerException extends RuntimeException {

  public BeaconsSearchResultSerializerException(
    String message,
    Throwable cause
  ) {
    super(message, cause);
  }
}
