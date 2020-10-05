# Introduction

CitiesXLSX is a small demo using Excel spreadsheet and VBA Macros. It demonstrates the following:
* Import an XML file into Excel
* Format the table
* Export excel spreadsheet to a generated XML file.


 # What I learned about Macros

   * EXCEL MACRO is a record and playback tool that simply records your Excel steps 
   * The macro will play it back as many times as you want.
   * VBA Macros save time as they automate repetitive tasks.

   * First, I have to show the developer option with the following instruction (Only the first time)
   * On the File tab, go to Options > Customize Ribbon.
   * Under Customize the Ribbon and under Main Tabs, select the Developer check box.

   
   * It is possible to assign a macro to a button in excel
   * It is possible to record a macro and do some tasks before ending it
   * It is also possible to write all the code in Visual Basics
   
 # Images
 
   ### Before and After XML Import
   ![Before Import](https://github.com/camillebalima/XMLDemo/blob/main/img/BeforeImport.PNG) ![After Import](https://github.com/camillebalima/XMLDemo/blob/main/img/AfterImport.PNG)
 
   ### Before and after Formatting
   ![XML Before formatting](https://github.com/camillebalima/XMLDemo/blob/main/img/BeforeFormatPNG.PNG) ![XML Formatter](https://github.com/camillebalima/XMLDemo/blob/main/img/AfterFormat.PNG)
   

   ### Before and After Exporting
   ![XML After formatting](https://github.com/camillebalima/XMLDemo/blob/main/img/BeforeExport.PNG) ![XSL Code](https://github.com/camillebalima/XMLDemo/blob/main/img/AfterExport_Message.PNG) ![XSL Code](https://github.com/camillebalima/XMLDemo/blob/main/img/AfterExport.PNG)
   
   ### XML File 
   ![XML After formatting](https://github.com/camillebalima/XMLDemo/blob/main/img/DisplayXML.PNG)
   ### VBA CODE
    
    Sub Import_XML()
    Dim xml_File_Path As String
    Dim wb As Workbook
    
    '//Load XML Data into a New Workbook
    Application.DisplayAlerts = False
     xml_File_Path = "N:\POSTGRAD\PROJECTS\XSLT\XMLDemo\CitiesXML_Copy\cities.xml"
     Set wb = Workbooks.OpenXML(Filename:=xml_File_Path)

    '//Copy Content from New workbook to current active Worksheet
     wb.Sheets(1).UsedRange.Copy ThisWorkbook.Sheets("Tabelle1").Range("A1")
     
    '//Close New Workbook & Enable Alerts
     wb.Close False
     Application.DisplayAlerts = True
     End Sub
    
     ---
     
     Sub FormatView()
     '
     ' FormatView Macro
     ' Delete the first row a nd change the rows Names
     ' Current Names : /City/@Id   /City/CountryCode /City/Name  /City/Population   /City/Population/#agg


    Rows("1:1").Select
    Selection.Delete Shift:=xlUp
    ActiveWindow.SmallScroll Down:=-27
    Range("A1").Select
    ActiveCell.FormulaR1C1 = "ID"
    Range("B1").Select
    ActiveCell.FormulaR1C1 = "Country Code"
    Range("C1").Select
    ActiveCell.FormulaR1C1 = "Name"
    Range("D1").Select
    Columns("D:D").ColumnWidth = 12.89
    Columns("D:D").ColumnWidth = 24.67
    Range("D1").Select
    ActiveCell.FormulaR1C1 = "Population"
    Columns("E:E").Select
    Selection.Delete Shift:=xlToLeft
    Columns("B:B").ColumnWidth = 14.22
    Columns("D:D").ColumnWidth = 13
    Columns("D:D").ColumnWidth = 10.11
    ActiveWindow.SmallScroll Down:=-15
    Columns("C:C").ColumnWidth = 11.78
    Columns("C:C").ColumnWidth = 18.11
    ActiveWindow.SmallScroll Down:=-27
    Columns("C:C").ColumnWidth = 21.22
    ActiveWindow.SmallScroll Down:=-33
    
    Range("A1").Select
    End Sub
    
    ---

    Sub Export_XML()
    Dim s As String
    Dim fPath As String
    Dim i As Long
    Dim dom As MSXML2.DOMDocument
    Dim cities As MSXML2.IXMLDOMNode
    Dim city As MSXML2.IXMLDOMNode
    Dim node As MSXML2.IXMLDOMNode
    Dim att As MSXML2.IXMLDOMAttribute

    Dim rowCount As Long
    rowCount = Cells(Rows.Count, "A").End(xlUp).Row

    'IMPORTANT:
    'This code requires setting a reference to the MSXML object library
    'In the VB editor under the Tools | References menu, select the
    '  checkbox for "Microsoft XML, v3.0" (or higher)
    
    On Error GoTo ErrHandler:
    
    
    'Create an XML Document
    Set dom = New MSXML2.DOMDocument
    dom.SetProperty "SelectionLanguage", "XPath"
    dom.SetProperty "SelectionNamespaces", "xmlns:xsl='http://www.w3.org/1999/XSL/Transform'"
    
    'Append an xml processing instruction
    dom.appendChild dom.createProcessingInstruction("xml", "version='1.0' encoding='UTF-8'")
    
    'Create a root node
    Set cities = dom.createNode(1, "Cities", "")
        
    'Book nodes
    For i = 2 To rowCount
    
    'Array to store the cell values for each row
        Dim values(4)
        values(0) = GetCellValue("A", CStr(i))
        values(1) = GetCellValue("B", CStr(i))
        values(2) = GetCellValue("C", CStr(i))
        values(3) = GetCellValue("D", CStr(i))
        
        
        'Create a city node with the Id attribute
        Set city = dom.createNode(1, "City", "")
        Set att = dom.createAttribute("Id")
        att.NodeValue = values(0)
        city.Attributes.setNamedItem att
        
        'Create the city name node
        Set cityName = dom.createNode(1, "Name", "")
        cityName.Text = values(2)
        
        'Create the country code node
        Set countryCode = dom.createNode(1, "CountryCode", "")
        countryCode.Text = values(1)
        
        'Create the population name node
        Set population = dom.createNode(1, "Population", "")
        population.Text = values(3)
        
        'Append the child nodes
        city.appendChild cityName
        city.appendChild countryCode
        city.appendChild population
        
        cities.appendChild city
        
    Next i
    
    'Append cities node to the document
    dom.appendChild cities
    
    'View the document
    Debug.Print dom.XML

    'Save the document
    fPath = Application.ActiveWorkbook.Path & "\" & "Cities_ExportedWithMacro.xml"
    dom.Save fPath
    
    'Inform that the XML is created
    MsgBox "XML file succesfuly saved to " & Application.ActiveWorkbook.Path

    
    My_Exit:
    Exit Sub
    
    ErrHandler:
    MsgBox Err.Description
    Resume My_Exit
    
    End Sub
    
    'Returns a cell range
    'Used in a loop
    
    ---
    
    Function GetCellValue(colRange As String, index As String) As String
        GetCellValue = Range(colRange & index).Value
    End Function

   
 # Conclusion
 This is my first experience with VBA Macros. It was more difficult for me to do this project, but I am happy with what I have been able to do for a first project. I understand how important it is and I actually love the concept.
