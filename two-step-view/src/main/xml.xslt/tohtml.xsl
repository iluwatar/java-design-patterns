<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <html>
            <head>
                <style>
                    table {
                    font-family: arial, sans-serif;
                    border-collapse: collapse;
                    width:
                    100%;
                    }
                    td, th {
                    border: 1px solid #dddddd;
                    text-align: left;
                    padding: 8px;
                    }
                    tr:nth-child(even) {
                    background-color: #dddddd;
                    }
                </style>
            </head>
            <body>
                <h2>Students</h2>
                <table>
                    <tr>
                        <th>ID</th>
                        <th>Firstname</th>
                        <th>Lastname</th>
                        <th>Username</th>
                        <th>Date of Birth</th>
                    </tr>
                    <xsl:for-each select="students/student">
                        <tr>
                            <td>
                                <xsl:value-of select="@id" />
                            </td>
                            <td>
                                <xsl:value-of select="firstName" />
                            </td>
                            <td>
                                <xsl:value-of select="lastName" />
                            </td>
                            <td>
                                <xsl:value-of select="userName" />
                            </td>
                            <td>
                                <xsl:value-of select="DOB" />
                            </td>
                        </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>