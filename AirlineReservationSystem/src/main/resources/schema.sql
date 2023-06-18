CREATE DATABASE IF NOT EXISTS airline_ticket_booking;

USE airline_ticket_booking;

CREATE TABLE users (
  user_id VARCHAR(20) NOT NULL UNIQUE,
  user_name VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  user_role ENUM('MANAGER', 'CUSTOMER') NOT NULL,
  PRIMARY KEY (user_id)
);
CREATE TABLE flight (
  flight_id VARCHAR(20) NOT NULL UNIQUE,
  airline_name ENUM('DELTA', 'UNITED', 'AMERICAN', 'SOUTHWEST') NOT NULL,
  departure VARCHAR(255) NOT NULL,
  arrival VARCHAR(255) NOT NULL,
  duration VARCHAR(20) NOT NULL,
  price DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (flight_id)
);
CREATE TABLE booking (
  booking_id VARCHAR(20) NOT NULL UNIQUE,
  user_id VARCHAR(20) NOT NULL,
  flight_id VARCHAR(20) NOT NULL,
  airline_name VARCHAR(255) NOT NULL,
  passenger_name VARCHAR(50) NOT NULL,
  price DECIMAL(10,2) NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users(user_id),
  FOREIGN KEY (flight_id) REFERENCES flight(flight_id),
  INDEX (user_id),
  INDEX (flight_id)
);
CREATE TABLE bank_account (
  user_id VARCHAR(20) NOT NULL,
  account_number VARCHAR(20) NOT NULL,
  bank_name VARCHAR(255) NOT NULL,
  balance DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (user_id, account_number),
  FOREIGN KEY (user_id) REFERENCES users(user_id)
);






