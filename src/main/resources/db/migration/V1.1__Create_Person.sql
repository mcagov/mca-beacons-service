CREATE TABLE IF NOT EXISTS person (
    id SERIAL,
    personType VARCHAR,
    name VARCHAR,
    telephone VARCHAR,
    emailAddress VARCHAR,
    addressLine1 VARCHAR,
    addressLine2 VARCHAR,
    addressLine3 VARCHAR,
    addressLine4 VARCHAR,
    addressLine5 VARCHAR,
    addressLine6 VARCHAR,
    addressLine7 VARCHAR,
    PRIMARY KEY (id)
)