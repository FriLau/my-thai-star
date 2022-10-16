CREATE SEQUENCE HIBERNATE_SEQUENCE START WITH 100000;

-- *** Table ***
CREATE TABLE TableEntity (
  id BIGINT NOT NULL,
  modificationCounter INTEGER NOT NULL,
  seatsNumber INTEGER NOT NULL,
  CONSTRAINT PK_TableEntity PRIMARY KEY(id)
);

-- *** Booking ***
CREATE TABLE Booking (
  id BIGINT NOT NULL,
  modificationCounter INTEGER NOT NULL,
  userId BIGINT,
  name VARCHAR (255) NOT NULL,
  bookingToken VARCHAR (255),
  comment VARCHAR (4000),
  email VARCHAR(255) NOT NULL,
  bookingDate TIMESTAMP NOT NULL,
  expirationDate TIMESTAMP,
  creationDate TIMESTAMP,
  canceled BOOLEAN NOT NULL DEFAULT ((false)) ,
  bookingType INTEGER,
  tableId BIGINT,
  orderId BIGINT,
  assistants INTEGER,
  CONSTRAINT PK_Booking PRIMARY KEY(id),
  CONSTRAINT FK_Booking_tableId FOREIGN KEY(tableId) REFERENCES TableEntity(id)
);

-- *** InvitedGuest ***
CREATE TABLE InvitedGuest (
  id BIGINT NOT NULL,
  modificationCounter INTEGER NOT NULL,
  bookingId BIGINT NOT NULL,
  guestToken VARCHAR (255),
  email VARCHAR (60),
  accepted BOOLEAN,
  modificationDate TIMESTAMP,
  orderId BIGINT,
  CONSTRAINT PK_InvitedGuest PRIMARY KEY(id),
  CONSTRAINT FK_InvitedGuest_bookingId FOREIGN KEY(bookingId) REFERENCES Booking(id)
);
