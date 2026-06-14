-- Schema referensi H2/JPA. Aplikasi membuat tabel otomatis memakai Hibernate (ddl-auto=update).
CREATE TABLE IF NOT EXISTS users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  created_at TIMESTAMP,
  username VARCHAR(80) UNIQUE NOT NULL,
  display_name VARCHAR(120) NOT NULL,
  password VARCHAR(255) NOT NULL,
  role VARCHAR(30),
  favorite_game VARCHAR(120) NOT NULL,
  game_rank VARCHAR(80) NOT NULL,
  preferred_role VARCHAR(80) NOT NULL,
  region VARCHAR(80),
  online_status VARCHAR(80),
  rating DOUBLE,
  bio VARCHAR(700),
  gender VARCHAR(40),
  followers INT,
  following INT,
  friends INT
);

CREATE TABLE IF NOT EXISTS party_rooms (
  id INT AUTO_INCREMENT PRIMARY KEY,
  created_at TIMESTAMP,
  name VARCHAR(160) NOT NULL,
  game VARCHAR(120) NOT NULL,
  rank VARCHAR(80) NOT NULL,
  missing_role VARCHAR(80) NOT NULL,
  privacy VARCHAR(40),
  owner VARCHAR(120) NOT NULL,
  max_slots INT
);

CREATE TABLE IF NOT EXISTS party_members (
  room_id INT NOT NULL,
  member_name VARCHAR(120) NOT NULL
);
