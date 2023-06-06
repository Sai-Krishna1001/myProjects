create database weatherapp;

use weatherapp;

CREATE TABLE `users` (
  `userid` varchar(50) NOT NULL,
  `uname` varchar(50) NOT NULL,
  `country` varchar(50) NOT NULL,
  `city` varchar(50) NOT NULL,
  `pwd` varchar(20) NOT NULL,
  `role` varchar(20) NOT NULL
);


INSERT INTO `users` (`userid`, `uname`, `country`, `city`, `pwd`, `role`) VALUES
('admin', 'Sai Krishna', 'India', 'Visakhapatnam', 'admin', 'admin');