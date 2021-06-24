ALTER TABLE admin_user
    ADD COLUMN first_name character varying(255),
    ADD COLUMN last_name character varying(255),
    ADD COLUMN email character varying(255);