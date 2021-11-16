IF NOT EXISTS (
    SELECT  1
    FROM    sys.schemas
    WHERE   name = N'simple'
)
    EXEC('CREATE SCHEMA [simple]');
GO

