SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [simple].[SimpleObject](
    [id]      [bigint]        IDENTITY(1,1) NOT NULL,
    [name]    [varchar](40)                 NOT NULL,
    [notes]   [varchar](4000)               NULL,
    [version] [datetime2](7)                NOT NULL,

    CONSTRAINT [SimpleObject_PK] PRIMARY KEY CLUSTERED
        ([id] ASC)
        WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
        ON [PRIMARY],

    CONSTRAINT [SimpleObject_name_UNQ] UNIQUE NONCLUSTERED
        ([name] ASC)
        WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
        ON [PRIMARY]
)
ON [PRIMARY]
GO

