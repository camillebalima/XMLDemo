# Introduction

CitiesXML is a small demo using XSL to render XML.
The CitiesXML_Copy file is a copy of the original one located [HERE](https://github.com/camillebalima/XMLDemo/tree/main/FileIO/src/citiesXML/).
The XML file as been generated using the Java FileIO project. It contains all the Cities, Population and Country code from the built in world database from MySQL.


# What I learned about XSL

* XSLT pulls data from XML and displays according to the way I want to output it. (HTL, PDF, Another XML)

* To estaclish a communitaction between the two, I need to do the following:
   * Add this line to the XML file in orther to refer the appropriate XSL file.
   ```<?xml-stylesheet href="cities.xsl" type="text/xsl"?>```
   * Add the followings to the XSL file in other to get the data from the XML file
   ```
   <xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">   
   <xsl:template match="/Cities">
   //Add the code here
   </xsl:template>
   </xsl:stylesheet>
   ```
   
   * XML must be coded hierarchically. It is not possible to access child without accessing the parent element before.
   * Some elements such as XSL template, value-of, sort, choose
   
 # Images
 
   ### Cities From World DB
   ![Cities World DB](https://github.com/camillebalima/XMLDemo/blob/main/img/SelectCity_FromWorldDatabase.PNG)
 
   ### Generated XML Before formatting
   ![XML Before formatting](https://github.com/camillebalima/XMLDemo/blob/main/img/CitiesXML_BeforeFormating.PNG)
   
   ### XML Formatter
   ![XML Formatter](https://github.com/camillebalima/XMLDemo/blob/main/img/XML_Formatter.PNG)
   

   ### XML After formatting
   ![XML After formatting](https://github.com/camillebalima/XMLDemo/blob/main/img/XML_AfterFormatting.PNG)
   
   ### XSL Code
   ![XSL Code](https://github.com/camillebalima/XMLDemo/blob/main/img/XSL_Code.PNG)
   
   ### Cities From World DB
   ![Final Result](https://github.com/camillebalima/XMLDemo/blob/main/img/CitiesXML_WithXSL.PNG)
   
 # Conclusion
 I am happy to have discovered this language. Knowing other programming languages helped me to understand the basics of this one. I had always been intrigued by XML. When I got acquainted with XSL I was even more lost. Finally I was happy to have tried to understand this language and to have done a first project even though it was basic.
   
   
