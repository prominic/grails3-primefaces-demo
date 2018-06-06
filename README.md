# Summary

Example project for [grails3-primefaces](https://github.com/prominic/grails3-primefaces) plugin.

## Dependencies

This plugin was written and tested with Grails 3.3.3 and Java 8u131.

## Running the Example Interface

Clone the [plugin project](https://github.com/prominic/grails3-primefaces).

Install the plugin:

    cd grails3-primefaces
    ./gradlew install
    
Run this application:

    cd grails3-primefaces-demo
    ./grailsw run-app    
    
Once the application finishes loading, check the interface at:  [http://localhost:8080/faces/anagraphic/home.xhtml](http://localhost:8080/faces/anagraphic/home.xhtml)


## Building an Example From a New Domain Class

### Generate the new domain class

Run this command:

    ./grailsw create-domain-class demo.Car

Populate the domain class at grails-app/domain/demo/Car.groovy:
```
package demo
 
class Car {
    String carID
    int year
    String brand
    String color
 
    static constraints = {
    }
}
```

### Generate the service and bean

The primefaces plugin adds a command to generate the managed bean and service for the domain class:

    ./grailsw run-command pf-generate-all demo.Car

The resulting files are:
* grails-app/services/demo/CarService.groovy
* src/main/groovy/demo/beans/CarLazyDataModel.groovy
* src/main/groovy/demo/beans/CarManagedBean.groovy

### Update application.yml

Add the new package "demo.beans" to the grails.plugins.primefaces.beans.packages list in grails-app/conf/application.yml
```
grails:
    plugins:
        primefaces:
                beans:
                   packages: com.company.demo.beans,demo.beans
```



Optionally, add this line to the bottom of grails-app/conf/logback.groovy to enable debugging output:
```
logger('demo', DEBUG)
```

### Add some test data

Add this code to grails-app/init/grails3primefacesapp/BootStrap.groovy
```
...
 
import demo.Car
import java.util.UUID
 
 
class BootStrap {
 
    def init = { servletContext ->

        ... init already has code for Anagraphic.  Add the code below at the end of the block ...
 
        // initialize Car data
        def colors = ['Black', 'White', 'Green', 'Red', 'Blue', 'Orange', 'Silver', 'Yellow', 'Brown', 'Maroon']
        def brands = ['BMW', 'Mercedes', 'Volvo', 'Audi', 'Renault', 'Fiat', 'Volkswagen', 'Honda', 'Jaguar', 'Ford']
        // enumerate all color and brand combinations
        def combinations = [colors, brands].combinations()
        combinations.each{ row ->
            String color = row[0]
            String brand = row[1]
            // initialize a car entry
            int newYear = (int) (Math.random() * 50 + 1960)
            String newID = UUID.randomUUID().toString().substring(0, 8);
            Car car = new Car (carID:newID, year:newYear, color:color, brand:brand)
            car.save(flush:true, failOnError:true)
        }
 
        println "Total Cars:  " + Car.count()
    }
 
    ...
}
```

This code will run on startup and generate some Car objects to populate the datatables.


### Create the XHTML file:

Create src/main/webapp/car/home.xhtml:
```
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:h="http://java.sun.com/jsf/html"
      xmlns:f="http://java.sun.com/jsf/core"
      xmlns:p="http://primefaces.org/ui"
      xmlns:pe="http://primefaces.org/ui/extensions" >
  
<h:head>
    <title>Car Demo</title>
</h:head>
<h:body>
  
    <h:form id="carForm">
  
        <p:dataTable var="car"
                     value="#{carMB.cars}"
                     selection="#{carMB.car}"
                     rowKey="#{car.carID}"
                     rows="10"
                     paginator="true"
                     rowsPerPageTemplate="10,20,50"
                     lazy="true"
                     selectionMode="single"
                     currentPageReportTemplate="#{message.i18n('primefaces.datatable.currentPageReportTemplate.label', '{startRecord}', '{endRecord}', '{totalRecords}')}"
                     paginatorTemplate="{CurrentPageReport}  {FirstPageLink} {PreviousPageLink} {PageLinks} {NextPageLink} {LastPageLink} {RowsPerPageDropdown}"
                     >
            <p:column headerText="Id">
                <h:outputText value="#{car.carID}" />
            </p:column>
  
            <p:column headerText="Year">
                <h:outputText value="#{car.year}" />
            </p:column>
  
            <p:column headerText="Brand">
                <h:outputText value="#{car.brand}" />
            </p:column>
  
            <p:column headerText="Color">
                <h:outputText value="#{car.color}" />
            </p:column>
        </p:dataTable>
    </h:form>
  
</h:body>
</html>
```

### Run the application

Launch the application with this command:

    ./grailsw run-app

Open the new interface in a browser: [http://localhost:8080/faces/car/home.xhtml](http://localhost:8080/faces/car/home.xhtml)

## Built With

* [Grails](http://grails.org/download.html)
* [Gradle](https://gradle.com/)

## Authors

* [mibesoft](https://github.com/mibesoft/primefaces) - Original code 
* [feather812002](https://github.com/feather812002) - Grails 3 Conversion
* [JoelProminic](https://github.com/JoelProminic) - Cleanup and Documentation

## License

Moonshine-IDE is licensed under the Apache License 2.0 - see the [LICENSE.md](https://github.com/prominic/grails3-primefaces-demo/blob/master/LICENSE.MD) file for details

## Acknowledgments

* This code was adapted from [this Grails 2.x example](https://github.com/mibesoft/grails-primefaces-demo)
