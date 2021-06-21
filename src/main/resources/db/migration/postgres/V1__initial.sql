CREATE TABLE credentials(
    id bigserial NOT NULL,
    email character varying(255),
    password character varying(255),
    role character varying(255),
    creation_date date,
    CONSTRAINT credentials_pkey PRIMARY KEY (id)
);

CREATE TABLE reader(
    id bigserial NOT NULL,
    first_name character varying(255),
    last_name character varying(255),
    email character varying(255),
    about text,
    date_of_birth date,
    credentials_id bigint,
    CONSTRAINT reader_pkey PRIMARY KEY (id),
    CONSTRAINT fk_credentials FOREIGN KEY (credentials_id) REFERENCES credentials(id)
);

CREATE TABLE admin_user(
    id bigserial NOT NULL,
    credentials_id bigint,
    CONSTRAINT admin_user_pkey PRIMARY KEY (id),
    CONSTRAINT fk_credentials FOREIGN KEY (credentials_id) REFERENCES credentials(id)
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
