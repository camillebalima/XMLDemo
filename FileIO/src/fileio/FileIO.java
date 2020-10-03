/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fileio;

import com.nbcc.business.CityService;
import com.nbcc.models.City;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

/**
 *
 * @author chris.cusack
 */
public class FileIO {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        p("World User Management");

        String menu = "1. Exit\n"
                + "2. Get Cities\n"
                + "3. Write Cities to CSV\n"
                + "4. Read Cities CSV file\n"
                + "5. Write Cities to XML\n"
                + "6. Read Cities XML file\n"
                + "7. Get a City with JDBC Statement\n"
                + "8. Delete a City with JDBC Prepared Statement\n"
                + "9. Create a City with JDBC Callable Statement\n"
                + "10. Update a City with JDBC Callable Statement\n"
                + "11. Swing form with Cities in JTable";

        int choice = getInt(menu);

        //Validate the menu choices
        while (choice > 11) {
            choice = getInt(menu);
        }

        while (choice > 1 && choice < 12) {

            CityService cityService = new CityService();

            switch (choice) {
                case 2:
                    p("View all Cities");
                    try {

                        List<City> cities = new ArrayList();
                        cities = cityService.getCities();

                        for (City c : cities) {
                            printCity(c);
                            p("~~~~~~");
                        }

                    } catch (Exception ex) {
                        p(ex.getMessage());
                    }
                    break;
                case 3:
                    exportCitiesCSV();
                    p("Write all cities to a CSV file");

                    break;
                case 4:
                    readCitiesCSV();
                    p("Read all cities from a CSV file");

                    break;
                case 5:
                    exportCitiesXML();
                    p("Write cities to an XML file");

                    break;
                case 6:
                    readCitiesXML();
                    p("Read from cities XML");

                    break;
                case 7:
                    p("Get a City with JDBC Statement");
                    City city = new City();
                    int id = getInt("Provide city id");
                    city = cityService.getCity(id);
                    
                    if (city != null){
                        printCity(city);
                    } else {
                        p("City not found");
                    }

                    break;
                case 8: 
                    p("Delete a City with JDBC Prepared Statement");
                    City deleteCity = new City();
                    int idDelete = getInt("Provide a city id");
                    
                    deleteCity = cityService.getCity(idDelete);
                    
                    if (deleteCity != null) {
                        p("Deleting City: " + deleteCity.getName());
                        
                        if (cityService.deleteCity(idDelete)) {
                            p("City was deleted");
                        } else {
                            p("No rows effected");
                        }
                    } else {
                        p("City not found");
                    }
                    
                    break;
                case 9:
                    p("Create a City with JDBC Callable Statement");
                        City newCity = new City();
                        newCity.setName(getString("City Name: "));
                        newCity.setCountryCode(getString("Country Code: "));
                        newCity.setPopulation(getInt("Population: "));
                        
                        newCity = cityService.createCity(newCity);
                        printCity(newCity);
                       
                    break;
                case 10:
                    p("Update a City with JDBC Callable Statement");
                        City updateCity = new City();
                        int idUpdate = getInt("Provide a city id");
                        updateCity.setName(getString("City Name: "));
                        updateCity.setCountryCode(getString("Country Code: "));
                        updateCity.setPopulation(getInt("Population: "));
                        
                        updateCity = cityService.UpdateCity(idUpdate,updateCity);
                        printCity(updateCity);

                    break;
                case 11:
                    CitiesForm citiesForm = new CitiesForm();
                    citiesForm.setVisible(true);
                    break;
            }

            choice = getInt(menu);
        }
    }

    /**
     * Connect to MySQL world query cities and create a CSV file to disk
     */
    public static void exportCitiesCSV() {
        try {
            String fileName = "cities.csv";

            //Asking the user to provide where to put the file
            String pathToWrite = getString("Provide an output path");

            Path citiesDir = Paths.get(pathToWrite);

            //Check if the directories exist
            if (Files.notExists(citiesDir)) {
                //Create 
                Files.createDirectories(citiesDir);
                p("The directory was created: " + citiesDir);
            } else {
                p("The directory exists: " + citiesDir);

                //List the fukes within
                DirectoryStream<Path> dirStream = Files.newDirectoryStream(citiesDir);

                for (Path p : dirStream) {
                    //Check for files only
                    if (Files.isRegularFile(p)) {
                        p(p.getFileName().toString());
                    }
                }
            }

            //Create our cities.csv file
            //Create a path to the file
            Path citiesFilePath = Paths.get(pathToWrite, fileName);
            File citiesFile = citiesFilePath.toFile();

            //This class queries the MySQL.world.city table
            CityService cityService = new CityService();
            List<City> cities = cityService.getCities();

            //Ready to create and write the file
            try (PrintWriter out = new PrintWriter(
                    new BufferedWriter(new FileWriter(citiesFile)))) {
                StringBuilder sb = new StringBuilder();

                int i = 0;

                for (City c : cities) {
                    sb.append(c.getId()).append(", ").append(c.getName()).append(", ").append(c.getCountryCode()).append(", ").append(c.getPopulation());

                    //Add a break line in every line except the last one
                    if (i++ != cities.size() - 1) {
                        sb.append("\n");
                    }
                }

                //Write out the contents of the string builder
                //All cities to csv file
                out.print(sb.toString());
            }

        } catch (Exception e) {
            p(e.getMessage());
        }
    }

    /**
     * Read a CSV File based off the request from the user
     */
    static void readCitiesCSV() {
        try {
            String csvFile = getString("Please provide the path to the csv file");
            String line = ""; //used in the buffered reader to gold each csv line'
            String csvSplitBy = ","; //Separator between values

            //Ready to read the file
            try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
                while ((line = br.readLine()) != null) {
                    //Split the string on csvSplitBy
                    String[] city = line.split(csvSplitBy);
                    p(
                            "City id: " + city[0]
                            + " Name: " + city[1]
                            + " Code: " + city[2]
                            + " Population: " + city[3]
                    );
                }
            }

        } catch (Exception e) {
            p(e.getMessage());
        }
    }

    private static void exportCitiesXML() {
        try {
            String fileName = "cities.xml";
            String pathToWrite = getString("Provide an output path");
            Path citiesDir = Paths.get(pathToWrite);

            //Check if the dir exists
            if (Files.notExists(citiesDir)) {
                Files.createDirectories(citiesDir);
            }

            CityService cityService = new CityService();
            List<City> cities = cityService.getCities();

            Path citiesFilePath = Paths.get(pathToWrite, fileName);
            File citiesFile = citiesFilePath.toFile();
            FileWriter fileWriter = new FileWriter(citiesFile);

            // our xml objects
            XMLOutputFactory oF = XMLOutputFactory.newInstance();
            XMLStreamWriter writer = oF.createXMLStreamWriter(fileWriter);

            //Ready to write the XML File
            writer.writeStartDocument("1.0"); //XML declaration
            writer.writeComment("Cities from the World MySql Database");
            writer.writeStartElement("Cities");

            //Create all the cities
            for (City c : cities) {
                writer.writeStartElement("City"); //Start City Element
                writer.writeAttribute("Id", Integer.toString(c.getId()));

                writer.writeStartElement("Name");
                writer.writeCharacters(c.getName());
                writer.writeEndElement();

                writer.writeStartElement("CountryCode");
                writer.writeCharacters(c.getCountryCode());
                writer.writeEndElement();

                writer.writeStartElement("Population");
                writer.writeCharacters(Integer.toString(c.getPopulation()));
                writer.writeEndElement();

                writer.writeEndElement();
            }

            writer.writeEndElement(); //End of root Products
            writer.writeEndDocument();
            writer.flush();
            writer.close();

        } catch (Exception e) {
            p(e.getMessage());
        }
    }

    /**
     * Read an xml doc
     */
    static void readCitiesXML() {
        try {
            String xmlFile = getString("Please provide a path to the xml file");
            XMLInputFactory iF = XMLInputFactory.newFactory();

            FileReader fileReader = new FileReader(xmlFile);
            XMLStreamReader reader = iF.createXMLStreamReader(fileReader);

            ArrayList<City> cities = new ArrayList();
            City c = null;

            //Iterate through the xml document elements 
            while (reader.hasNext()) {
                int eventType = reader.getEventType();

                switch (eventType) {
                    case XMLStreamConstants.START_ELEMENT:
                        String elementName = reader.getLocalName(); //gets element name

                        switch (elementName) {
                            case "City":
                                c = new City();
                                c.setId(Integer.parseInt(reader.getAttributeValue(0)));
                                break;
                            case "Name":
                                c.setName(reader.getElementText());
                                break;
                            case "CountryCode":
                                c.setCountryCode(reader.getElementText());
                                break;
                            case "Population":
                                c.setPopulation(Integer.parseInt(reader.getElementText()));
                                break;
                        }
                        break;
                    case XMLStreamConstants.END_ELEMENT:
                        elementName = reader.getLocalName();
                        if (elementName.equalsIgnoreCase("City")) {
                            cities.add(c);
                        }

                        break;
                }

                reader.next(); //Move to the next element
            }

            //Print the xml read we placed in the citites arraylist
            for (City city : cities) {
                printCity(city);
            }
        } catch (Exception e) {
            p(e.getMessage());
        }
    }

    /**
     * Print a City
     *
     * @param c
     */
    static void printCity(City c) {
        p("City: " + c.getName());
        p("Id: " + c.getId());
        p("Country COde: " + c.getCountryCode());
        p("Population: " + c.getPopulation());
        p("********************");
    }

    // <editor-fold defaultstate="collapsed" desc="helpers">
    /**
     * Method to get the next double value from a scanner
     *
     * @param prompt The prompt to inform the user of what to input to the
     * scanner
     * @return The valid number inputted by the user into the scanner
     */
    static double getDouble(String prompt) {
        Scanner sc = new Scanner(System.in);
        boolean isValid = false;
        double value = 0.0;

        while (!isValid) {
            p(prompt);
            if (sc.hasNextDouble()) {
                value = sc.nextDouble();
                isValid = true;
            } else {
                System.out.println("Please provide numbers only.");
            }

            sc.nextLine();
        }

        return value;
    }

    /**
     * Method to get the next integer value from a scanner
     *
     * @param prompt The prompt to inform the user of what to input to the
     * scanner
     * @return The valid whole number inputted by the user into the scanner
     */
    static int getInt(String prompt) {
        Scanner sc = new Scanner(System.in);
        boolean isValid = false;
        int value = 0;

        while (!isValid) {
            p(prompt);
            if (sc.hasNextInt()) {
                value = sc.nextInt();
                isValid = true;
            } else {
                System.out.println("Please provide whole numbers only.");
            }

            sc.nextLine();
        }

        return value;
    }

    /**
     * Get the next string input in a scanner
     *
     * @param prompt The prompt to inform the user of what to input to the
     * scanner
     * @return The valid string inputted by the user into the scanner
     */
    static String getString(String prompt) {
        Scanner sc = new Scanner(System.in);
        boolean isValid = false;
        String value = "";

        while (!isValid) {
            p(prompt);
            if (sc.hasNextLine()) {
                value = sc.nextLine();
                if (!value.equals("")) {
                    isValid = true;
                }
            } else {
                System.out.println("Please provide a valid response.");
            }
        }

        return value;
    }

    /**
     * Print to the console
     *
     * @param msg The message to prompt to the console
     */
    static void p(String msg) {
        System.out.println(msg);
    }

    // </editor-fold>
}
