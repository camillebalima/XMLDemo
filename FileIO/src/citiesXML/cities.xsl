<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:template match="/Cities">
<html>
<head>
  <head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous"/>
    <link rel="stylesheet" href="styles.css"/>
    <title>Display Cities</title>
  </head>
</head> 
<body>
  <h2>Display Cities</h2>
  <table border="1">
    <tr bgcolor="#9acd32">
      <th>Country Code</th>
      <th>City Name</th>
      <th>Population N.</th>
    </tr>
    <xsl:for-each select="City">
    <tr>
      <td><xsl:value-of select="CountryCode"/></td>
      <td><xsl:value-of select="Name"/></td>
      <td><xsl:value-of select="Population"/></td>
    </tr>
    </xsl:for-each>
  </table>
</body>
</html>
</xsl:template>
</xsl:stylesheet>