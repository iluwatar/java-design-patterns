USE parrot;
CREATE TABLE OwnedParrot (
    OwnedParrotId INT NOT NULL AUTO_INCREMENT,
    ParrotTypeId INT,
    ParrotName VARCHAR(30) NULL,
    ParrotAge INT NULL,
    Color VARCHAR(30) NULL,
    Tamed BIT,
    PRIMARY KEY (OwnedParrotId),
    FOREIGN KEY(ParrotTypeId)
           REFERENCES ParrotType(ParrotTypeId)
);