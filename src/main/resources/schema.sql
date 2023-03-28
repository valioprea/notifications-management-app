CREATE TABLE notifications_table (
  notification_id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL,
  time_stamp DATE NOT NULL,
  message VARCHAR(255) NOT NULL,
  has_been_read BOOLEAN NOT NULL
);
