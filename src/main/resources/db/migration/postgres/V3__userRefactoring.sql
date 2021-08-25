CREATE TABLE base_user(
    id bigserial NOT NULL,
    first_name character varying(255),
    last_name character varying(255),
    email character varying(255),
    CONSTRAINT base_user_pkey PRIMARY KEY (id)
);


ALTER TABLE reader
    DROP COLUMN first_name,
    DROP COLUMN last_name,
    DROP COLUMN email,
    ADD COLUMN user_id bigint,
    ADD CONSTRAINT fk_base_user FOREIGN KEY (user_id) REFERENCES base_user(id);

ALTER TABLE admin_user
    DROP COLUMN first_name,
    DROP COLUMN last_name,
    DROP COLUMN email,
    ADD COLUMN user_id bigint,
    ADD CONSTRAINT fk_base_user FOREIGN KEY (user_id) REFERENCES base_user(id);