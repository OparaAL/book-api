CREATE TABLE base_user(
    id bigserial NOT NULL,
    first_name character varying(255),
    last_name character varying(255),
    email character varying(255),
    account_type character varying(255),
    CONSTRAINT base_user_pkey PRIMARY KEY (id)
);

CREATE TABLE application_account_credentials(
    id bigserial NOT NULL,
    email character varying(255),
    password character varying(255),
    role character varying(255),
    creation_date date,
    user_id bigint,
    CONSTRAINT application_account_credentials_pkey PRIMARY KEY (id)
);

CREATE TABLE google_account_credentials(
    id bigserial NOT NULL,
    email character varying(255),
    password character varying(255) DEFAULT NULL,
    role character varying(255),
    creation_date date,
    user_id bigint,
    CONSTRAINT google_account_credentials_pkey PRIMARY KEY (id)
);

ALTER TABLE application_account_credentials
    ADD CONSTRAINT fk_base_user FOREIGN KEY (user_id) REFERENCES base_user (id);

ALTER TABLE google_account_credentials
    ADD CONSTRAINT fk_base_user FOREIGN KEY (user_id) REFERENCES base_user (id);

CREATE TABLE reader(
    id bigserial NOT NULL,
    about text,
    date_of_birth date,
    user_id bigint,
    CONSTRAINT reader_pkey PRIMARY KEY (id),
    CONSTRAINT fk_base_user FOREIGN KEY (user_id) REFERENCES base_user(id)
);

CREATE TABLE admin_user(
    id bigserial NOT NULL,
    user_id bigint,
    CONSTRAINT admin_user_pkey PRIMARY KEY (id),
    CONSTRAINT fk_base_user FOREIGN KEY (user_id) REFERENCES base_user(id)
);

CREATE TABLE book(
    id bigserial NOT NULL,
    name character varying(255),
    author character varying(255),
    publisher character varying(255),
    description text,
    language character varying(255),
    page_count bigserial,
    year_of_publish bigserial,
    CONSTRAINT book_pkey PRIMARY KEY (id)
);
