DROP TABLE IF EXISTS beer;

CREATE TABLE beer (
  id bigint not null PRIMARY KEY,
  name VARCHAR(250) NOT NULL,
  description VARCHAR(2000),
  mean_value decimal
);

-- INSERT INTO beer (id, name, description, mean_value) VALUES
--   (1, 'test1', 'des1', 65),
--   (2, 'test2', 'des2', 66);
