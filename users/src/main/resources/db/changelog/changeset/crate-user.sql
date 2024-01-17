CREATE TABLE "user_table" (
user_id BIGSERIAL PRIMARY KEY,
telegram_id BIGINT UNIQUE,
u_name VARCHAR,
password VARCHAR,
birth_day DATE
)