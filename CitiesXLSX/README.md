# Introduction

CitiesXLSX is a small demo using Excel spreadsheet and VBA Macros. It demostrates the following:
* Import an XML file into Exel
* Format the table
* Export execl spreadsheet to a generated XML file.


 # What I learned about Macros

   * EXCEL MACRO is a record and playback tool that simply records your Excel steps 
   * The macro will play it back as many times as you want.
   * VBA Macros save time as they automate repetitive tasks.

   * First I have to show the developer option with the following instruction (Only the first time)
   ** On the File tab, go to Options > Customize Ribbon.
   ** Under Customize the Ribbon and under Main Tabs, select the Developer check box.

   
   * It is possible to assign a macro to a button in excel
   * It is possible to record a macro and do some tasks before ending it
   * It is also possible to write all the code in Visual Basics
   
 # Images
 
   ### Before and After XML Import
   ![Cities World DB](https://github.com/camillebalima/XMLDemo/blob/main/img/SelectCity_FromWorldDatabase.PNG) ![XML Before formatting](https://github.com/camillebalima/XMLDemo/blob/main/img/CitiesXML_BeforeFormating.PNG)
 
   ### Before and after Formatting
   ![XML Before formatting](https://github.com/camillebalima/XMLDemo/blob/main/img/CitiesXML_BeforeFormating.PNG) ![XML Formatter](https://github.com/camillebalima/XMLDemo/blob/main/img/XML_Formatter.PNG)
   

   ### Before and After Exporting
   ![XML After formatting](https://github.com/camillebalima/XMLDemo/blob/main/img/XML_AfterFormatting.PNG) ![XSL Code](https://github.com/camillebalima/XMLDemo/blob/main/img/XSL_Code.PNG)
   
   ### VBA CODE
   ´´´
    Sub Import_XML()
    Dim xml_File_Path As String
    Dim wb As Workbook
    
    'Load XML Data into a New Workbook
    Application.DisplayAlerts = False
     xml_File_Path = "N:\POSTGRAD\PROJECTS\XSLT\XMLDemo\CitiesXML_Copy\cities.xml"
     Set wb = Workbooks.OpenXML(Filename:=xml_File_Path)

    'Copy Content from New workbook to current active Worksheet
     wb.Sheets(1).UsedRange.Copy ThisWorkbook.Sheets("Tabelle1").Range("A1")
     
    'Close New Workbook & Enable Alerts
     wb.Close False
     Application.DisplayAlerts = True
     End Sub
   ´´´
   
 # Conclusion
 This is my first experience with VBA Macros and excel. It was more diffictult for me to do this project but I am happy with what I have been able to do for a first projectL. I understand how important it is and I actually love the concept.
