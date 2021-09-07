CREATE TABLE github_account_credentials(
    id bigserial NOT NULL,
    email character varying(255),
    password character varying(255) DEFAULT NULL,
    role character varying(255),
    creation_date date,
    client_id character varying (255),
    user_id bigint,
    CONSTRAINT github_account_credentials_pkey PRIMARY KEY (id)
);

CREATE TABLE facebook_account_credentials(
    id bigserial NOT NULL,
    email character varying(255),
    password character varying(255) DEFAULT NULL,
    role character varying(255),
    creation_date date,
    client_id character varying (255),
    user_id bigint,
    CONSTRAINT facebook_account_credentials_pkey PRIMARY KEY (id)
);

ALTER TABLE github_account_credentials
    ADD CONSTRAINT fk_base_user FOREIGN KEY (user_id) REFERENCES base_user (id);

ALTER TABLE facebook_account_credentials
    ADD CONSTRAINT fk_base_user FOREIGN KEY (user_id) REFERENCES base_user (id);

ALTER TABLE google_account_credentials
    ADD COLUMN client_id character varying (255);