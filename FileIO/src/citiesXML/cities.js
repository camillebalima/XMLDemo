var filter = "all";

function sortByFilter(){

    switch (event.target.id){
        case "sortByCityCode":
            filter = "CityCode";
            break;
        case "sortByCityName":
            filter = "Name";
            break;
        case "sortByCityPopulation":
            filter = "Population";
    }

    var filterText = "<xsl:sort select='Population'/>";
    var elements = document.getElementsByClassName('rows');
    var element = elements[0];
    
    element.insertAdjacentHTML('beforebegin', filterText)
    

    document.getElementById("filterValue").innerHTML = filter;

    console.log(filter);
    console.log(event.target.id);
    console.log(document.getElementById("filterValue").innerHTML);
}
