DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (user_id, datetime, calories, description) VALUES
  (100000, '2015-05-30 10:00:00', 500, 'Завтрак'),
  (100000, '2015-05-30 13:00:00', 1000, 'Обед'),
  (100000, '2015-05-30 20:00:00', 500, 'Ужин'),
  (100000, '2015-05-31 10:00:00', 1000, 'Завтрак'),
  (100000, '2015-05-31 13:00:00', 500, 'Обед'),
  (100000, '2015-05-31 20:00:00', 510, 'Ужин'),
  (100001, '2015-06-01 14:00:00', 510, 'Админ ланч'),
  (100001, '2015-06-01 21:00:00', 1500, 'Админ ланч');