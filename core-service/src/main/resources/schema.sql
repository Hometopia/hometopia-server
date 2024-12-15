CREATE TABLE province
(
    code       INTEGER                     NOT NULL,
    version    SMALLINT                    NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    created_by VARCHAR(255)                NOT NULL,
    updated_by VARCHAR(255)                NOT NULL,
    CONSTRAINT pk_province PRIMARY KEY (code)
);

CREATE TABLE province_lan
(
    version      SMALLINT                    NOT NULL,
    created_at   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    created_by VARCHAR(255)                  NOT NULL,
    updated_by VARCHAR(255)                  NOT NULL,
    name         VARCHAR(255)                NOT NULL,
    province_id  INTEGER                     NOT NULL,
    country_code CHAR(2)                     NOT NULL,
    CONSTRAINT pk_province_lan PRIMARY KEY (province_id, country_code)
);

ALTER TABLE province_lan
    ADD CONSTRAINT FK_PROVINCE_LAN_ON_PROVINCE FOREIGN KEY (province_id) REFERENCES province (code);

CREATE TABLE district
(
    code        INTEGER                     NOT NULL,
    version     SMALLINT                    NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    created_by VARCHAR(255)                 NOT NULL,
    updated_by VARCHAR(255)                 NOT NULL,
    province_id INTEGER,
    CONSTRAINT pk_district PRIMARY KEY (code)
);

ALTER TABLE district
    ADD CONSTRAINT FK_DISTRICT_ON_PROVINCE FOREIGN KEY (province_id) REFERENCES province (code);

CREATE TABLE district_lan
(
    version      SMALLINT                    NOT NULL,
    created_at   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    created_by   VARCHAR(255)                NOT NULL,
    updated_by   VARCHAR(255)                NOT NULL,
    name         VARCHAR(255)                NOT NULL,
    district_id  INTEGER                     NOT NULL,
    country_code CHAR(2)                     NOT NULL,
    CONSTRAINT pk_district_lan PRIMARY KEY (district_id, country_code)
);

ALTER TABLE district_lan
    ADD CONSTRAINT FK_DISTRICT_LAN_ON_DISTRICT FOREIGN KEY (district_id) REFERENCES district (code);

CREATE TABLE ward
(
    code        INTEGER                     NOT NULL,
    version     SMALLINT                    NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    created_by VARCHAR(255)                 NOT NULL,
    updated_by VARCHAR(255)                 NOT NULL,
    district_id INTEGER                     NOT NULL,
    CONSTRAINT pk_ward PRIMARY KEY (code)
);

ALTER TABLE ward
    ADD CONSTRAINT FK_WARD_ON_DISTRICT FOREIGN KEY (district_id) REFERENCES district (code);

CREATE TABLE ward_lan
(
    version      SMALLINT                    NOT NULL,
    created_at   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    created_by   VARCHAR(255)                NOT NULL,
    updated_by   VARCHAR(255)                NOT NULL,
    name         VARCHAR(255)                NOT NULL,
    ward_id      INTEGER                     NOT NULL,
    country_code CHAR(2)                     NOT NULL,
    CONSTRAINT pk_ward_lan PRIMARY KEY (ward_id, country_code)
);

ALTER TABLE ward_lan
    ADD CONSTRAINT FK_WARD_LAN_ON_WARD FOREIGN KEY (ward_id) REFERENCES ward (code);

CREATE TABLE address
(
    id          VARCHAR(255)                NOT NULL,
    version     SMALLINT                    NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    created_by VARCHAR(255)                 NOT NULL,
    updated_by VARCHAR(255)                 NOT NULL,
    line        VARCHAR(255)                NOT NULL,
    province_id INTEGER                     NOT NULL,
    district_id INTEGER                     NOT NULL,
    ward_id     INTEGER                     NOT NULL,
    CONSTRAINT pk_address PRIMARY KEY (id)
);

ALTER TABLE address
    ADD CONSTRAINT FK_ADDRESS_ON_DISTRICT FOREIGN KEY (district_id) REFERENCES district (code);

ALTER TABLE address
    ADD CONSTRAINT FK_ADDRESS_ON_PROVINCE FOREIGN KEY (province_id) REFERENCES province (code);

ALTER TABLE address
    ADD CONSTRAINT FK_ADDRESS_ON_WARD FOREIGN KEY (ward_id) REFERENCES ward (code);

CREATE TABLE "user"
(
    id         VARCHAR(255)                NOT NULL,
    version    SMALLINT                    NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    created_by VARCHAR(255)                NOT NULL,
    updated_by VARCHAR(255)                NOT NULL,
    username   VARCHAR(255)                NOT NULL,
    email      VARCHAR(255)                NOT NULL,
    first_name VARCHAR(255)                NOT NULL,
    last_name  VARCHAR(255)                NOT NULL,
    address_id VARCHAR(255)                NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

ALTER TABLE "user"
    ADD CONSTRAINT uc_user_address UNIQUE (address_id);

ALTER TABLE "user"
    ADD CONSTRAINT uc_user_username UNIQUE (username);

ALTER TABLE "user"
    ADD CONSTRAINT FK_USER_ON_ADDRESS FOREIGN KEY (address_id) REFERENCES address (id);

CREATE TABLE category
(
    id          VARCHAR(255)                NOT NULL,
    version     SMALLINT                    NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    created_by  VARCHAR(255)                NOT NULL,
    updated_by  VARCHAR(255)                NOT NULL,
    name        VARCHAR(255)                NOT NULL,
    description VARCHAR(255),
    user_id     VARCHAR(255)                NOT NULL,
    parent_id   VARCHAR(255),
    CONSTRAINT pk_category PRIMARY KEY (id)
);

ALTER TABLE category
    ADD CONSTRAINT FK_CATEGORY_ON_PARENT FOREIGN KEY (parent_id) REFERENCES category (id);

ALTER TABLE category
    ADD CONSTRAINT FK_CATEGORY_ON_USER FOREIGN KEY (user_id) REFERENCES "user" (id);

CREATE TABLE asset
(
    id                   VARCHAR(255)                NOT NULL,
    version              SMALLINT                    NOT NULL,
    created_at           TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at           TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    created_by           VARCHAR(255)                NOT NULL,
    updated_by           VARCHAR(255)                NOT NULL,
    name                 VARCHAR(255)                NOT NULL,
    description          TEXT,
    images               JSONB,
    purchase_date        date                        NOT NULL,
    purchase_place       VARCHAR(255)                NOT NULL,
    purchase_price       DECIMAL                     NOT NULL,
    vendor               VARCHAR(255)                NOT NULL,
    serial_number        VARCHAR(255)                NOT NULL,
    location             VARCHAR(255)                NOT NULL,
    warranty_expiry_date date                        NOT NULL,
    documents            JSONB,
    status               VARCHAR(255)                NOT NULL,
    maintenance_cycle    INTEGER,
    category_id          VARCHAR(255)                NOT NULL,
    user_id              VARCHAR(255)                NOT NULL,
    CONSTRAINT pk_asset PRIMARY KEY (id)
);

ALTER TABLE asset
    ADD CONSTRAINT FK_ASSET_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES category (id);

ALTER TABLE asset
    ADD CONSTRAINT FK_ASSET_ON_USER FOREIGN KEY (user_id) REFERENCES "user" (id);

CREATE TABLE asset_life_cycle
(
    id          VARCHAR(255)                NOT NULL,
    version     SMALLINT                    NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    created_by  VARCHAR(255)                NOT NULL,
    updated_by  VARCHAR(255)                NOT NULL,
    description VARCHAR(255)                NOT NULL,
    asset_id    VARCHAR(255)                NOT NULL,
    CONSTRAINT pk_asset_life_cycle PRIMARY KEY (id)
);

ALTER TABLE asset_life_cycle
    ADD CONSTRAINT FK_ASSET_LIFE_CYCLE_ON_ASSET FOREIGN KEY (asset_id) REFERENCES asset (id);

CREATE TABLE schedule
(
    id         VARCHAR(255)                NOT NULL,
    version    SMALLINT                    NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    created_by VARCHAR(255)                NOT NULL,
    updated_by VARCHAR(255)                NOT NULL,
    title      VARCHAR(255)                NOT NULL,
    start      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    "end"      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    vendor     VARCHAR(255)                NOT NULL,
    cost       DECIMAL,
    documents  JSONB,
    type       VARCHAR(255)                NOT NULL,
    asset_id   VARCHAR(255)                NOT NULL,
    CONSTRAINT pk_schedule PRIMARY KEY (id)
);

ALTER TABLE schedule
    ADD CONSTRAINT FK_SCHEDULE_ON_ASSET FOREIGN KEY (asset_id) REFERENCES asset (id);