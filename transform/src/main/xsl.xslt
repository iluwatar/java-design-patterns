<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0"  xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:template match="/">
       <html>
            <head>
                <title>YuCong's Demo</title>
            </head>
            <body>
            <xsl:choose>
                <xsl:when test="members/member">
                    <table border="1">
                        <thead>
                            <tr>
                                <td>id</td>
                                <td>Name</td>
								<td>Age</td>
                                <td>Book</td>
                            </tr>
                        </thead>
                            <tbody>
                                <xsl:for-each select="members/member">
                                    <tr>
                                        <td>
                                            <xsl:value-of select="@id"/>
                                        </td>

                                        <td>
                                            <xsl:value-of select="name"/>
                                        </td>
										<td>
										    <xsl:value-of select="age"/>
										</td>
                                        <td>
                                            <xsl:value-of select="book"/>
                                        </td>
                                    </tr>
                                </xsl:for-each>
                            </tbody>
                        </table>
                    </xsl:when>
                </xsl:choose>
            </body>
       </html>
</xsl:template>
</xsl:stylesheet>



