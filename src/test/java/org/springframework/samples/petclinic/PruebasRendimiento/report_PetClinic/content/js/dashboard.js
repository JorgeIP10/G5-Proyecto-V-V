/*
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
var showControllersOnly = false;
var seriesFilter = "";
var filtersOnlySampleSeries = true;

/*
 * Add header in statistics table to group metrics by category
 * format
 *
 */
function summaryTableHeader(header) {
    var newRow = header.insertRow(-1);
    newRow.className = "tablesorter-no-sort";
    var cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 1;
    cell.innerHTML = "Requests";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 3;
    cell.innerHTML = "Executions";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 7;
    cell.innerHTML = "Response Times (ms)";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 1;
    cell.innerHTML = "Throughput";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 2;
    cell.innerHTML = "Network (KB/sec)";
    newRow.appendChild(cell);
}

/*
 * Populates the table identified by id parameter with the specified data and
 * format
 *
 */
function createTable(table, info, formatter, defaultSorts, seriesIndex, headerCreator) {
    var tableRef = table[0];

    // Create header and populate it with data.titles array
    var header = tableRef.createTHead();

    // Call callback is available
    if(headerCreator) {
        headerCreator(header);
    }

    var newRow = header.insertRow(-1);
    for (var index = 0; index < info.titles.length; index++) {
        var cell = document.createElement('th');
        cell.innerHTML = info.titles[index];
        newRow.appendChild(cell);
    }

    var tBody;

    // Create overall body if defined
    if(info.overall){
        tBody = document.createElement('tbody');
        tBody.className = "tablesorter-no-sort";
        tableRef.appendChild(tBody);
        var newRow = tBody.insertRow(-1);
        var data = info.overall.data;
        for(var index=0;index < data.length; index++){
            var cell = newRow.insertCell(-1);
            cell.innerHTML = formatter ? formatter(index, data[index]): data[index];
        }
    }

    // Create regular body
    tBody = document.createElement('tbody');
    tableRef.appendChild(tBody);

    var regexp;
    if(seriesFilter) {
        regexp = new RegExp(seriesFilter, 'i');
    }
    // Populate body with data.items array
    for(var index=0; index < info.items.length; index++){
        var item = info.items[index];
        if((!regexp || filtersOnlySampleSeries && !info.supportsControllersDiscrimination || regexp.test(item.data[seriesIndex]))
                &&
                (!showControllersOnly || !info.supportsControllersDiscrimination || item.isController)){
            if(item.data.length > 0) {
                var newRow = tBody.insertRow(-1);
                for(var col=0; col < item.data.length; col++){
                    var cell = newRow.insertCell(-1);
                    cell.innerHTML = formatter ? formatter(col, item.data[col]) : item.data[col];
                }
            }
        }
    }

    // Add support of columns sort
    table.tablesorter({sortList : defaultSorts});
}

$(document).ready(function() {

    // Customize table sorter default options
    $.extend( $.tablesorter.defaults, {
        theme: 'blue',
        cssInfoBlock: "tablesorter-no-sort",
        widthFixed: true,
        widgets: ['zebra']
    });

    var data = {"OkPercent": 100.0, "KoPercent": 0.0};
    var dataset = [
        {
            "label" : "FAIL",
            "data" : data.KoPercent,
            "color" : "#FF6347"
        },
        {
            "label" : "PASS",
            "data" : data.OkPercent,
            "color" : "#9ACD32"
        }];
    $.plot($("#flot-requests-summary"), dataset, {
        series : {
            pie : {
                show : true,
                radius : 1,
                label : {
                    show : true,
                    radius : 3 / 4,
                    formatter : function(label, series) {
                        return '<div style="font-size:8pt;text-align:center;padding:2px;color:white;">'
                            + label
                            + '<br/>'
                            + Math.round10(series.percent, -2)
                            + '%</div>';
                    },
                    background : {
                        opacity : 0.5,
                        color : '#000'
                    }
                }
            }
        },
        legend : {
            show : true
        }
    });

    // Creates APDEX table
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [0.04138666666666667, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)", "F (Frustration threshold)", "Label"], "items": [{"data": [0.0867, 500, 1500, "CSS"], "isController": false}, {"data": [0.0127, 500, 1500, "Owner"], "isController": false}, {"data": [0.0126, 500, 1500, "POST Edit Owner"], "isController": false}, {"data": [0.0076, 500, 1500, "POST new visit-0"], "isController": false}, {"data": [5.0E-4, 500, 1500, "New Pet"], "isController": false}, {"data": [0.1153, 500, 1500, "POST new visit-1"], "isController": false}, {"data": [0.0836, 500, 1500, "Vets"], "isController": false}, {"data": [0.0913, 500, 1500, "Home page"], "isController": false}, {"data": [0.0847, 500, 1500, "JS"], "isController": false}, {"data": [0.0795, 500, 1500, "Find owner"], "isController": false}, {"data": [0.0224, 500, 1500, "Find owner with lastname=\"\""], "isController": false}, {"data": [0.0023, 500, 1500, "New visit"], "isController": false}, {"data": [0.0021, 500, 1500, "POST new visit"], "isController": false}, {"data": [6.0E-4, 500, 1500, "POST new pet"], "isController": false}, {"data": [0.0189, 500, 1500, "Edit Owner"], "isController": false}]}, function(index, item){
        switch(index){
            case 0:
                item = item.toFixed(3);
                break;
            case 1:
            case 2:
                item = formatDuration(item);
                break;
        }
        return item;
    }, [[0, 0]], 3);

    // Create statistics table
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 75000, 0, 0.0, 4807.559480000006, 1, 23491, 5096.5, 10671.900000000001, 12704.650000000005, 16370.820000000029, 106.3929389134302, 9190.16476769678, 19.91404292813228], "isController": false}, "titles": ["Label", "#Samples", "FAIL", "Error %", "Average", "Min", "Max", "Median", "90th pct", "95th pct", "99th pct", "Transactions/s", "Received", "Sent"], "items": [{"data": ["CSS", 5000, 0, 0.0, 2602.6949999999943, 1, 4934, 2688.5, 3768.0, 4102.9, 4494.99, 7.392807980684072, 2015.4931538901697, 1.0396136222836976], "isController": false}, {"data": ["Owner", 5000, 0, 0.0, 5577.854000000003, 201, 14714, 5431.5, 8288.7, 9240.75, 11061.99, 7.162944084625886, 1728.251110905475, 0.8743828228303083], "isController": false}, {"data": ["POST Edit Owner", 5000, 0, 0.0, 4191.534199999993, 34, 11942, 4091.5, 6109.0, 6715.95, 8027.99, 7.1573661465170115, 38.48482226828385, 2.0689261517275734], "isController": false}, {"data": ["POST new visit-0", 5000, 0, 0.0, 5536.8306, 54, 14892, 5276.0, 8208.300000000003, 9177.95, 11527.779999999995, 7.182102201314325, 2.1532279060581034, 1.8305944087334365], "isController": false}, {"data": ["New Pet", 5000, 0, 0.0, 8181.664800000011, 632, 21750, 7771.0, 12350.900000000005, 13617.849999999999, 16797.859999999997, 7.1489235150970964, 34.294815886623795, 0.9355036631084092], "isController": false}, {"data": ["POST new visit-1", 5000, 0, 0.0, 3375.622399999995, 146, 18743, 2994.0, 6361.900000000001, 7430.849999999999, 9677.97, 7.211635541247671, 1808.30998200111, 1.5634600489814288], "isController": false}, {"data": ["Vets", 5000, 0, 0.0, 2557.7516000000032, 2, 4936, 2633.0, 3655.0, 4077.8999999999996, 4476.98, 7.369815887259505, 29.551234407312037, 0.9068328142526344], "isController": false}, {"data": ["Home page", 5000, 0, 0.0, 2654.2042000000038, 1, 4890, 2733.0, 3897.9000000000005, 4213.95, 4503.959999999999, 7.41059121696729, 22.369274855122942, 0.8467179417823953], "isController": false}, {"data": ["JS", 5000, 0, 0.0, 2564.664999999997, 1, 4937, 2650.0, 3674.800000000001, 4053.0, 4524.99, 7.38076012972424, 584.2909562069979, 1.1964904116545154], "isController": false}, {"data": ["Find owner", 5000, 0, 0.0, 2564.5738000000015, 1, 4936, 2633.0, 3638.9000000000005, 4056.8999999999996, 4498.0, 7.359088179538203, 26.144885544101545, 0.9198860224422752], "isController": false}, {"data": ["Find owner with lastname=\"\"", 5000, 0, 0.0, 5555.507400000001, 33, 15940, 5476.0, 8422.0, 9318.649999999998, 11048.919999999998, 7.235460703489373, 36.21969879500637, 0.9397619859024283], "isController": false}, {"data": ["New visit", 5000, 0, 0.0, 4998.0039999999935, 642, 12887, 4861.0, 7037.900000000001, 7751.95, 9138.909999999998, 7.1669380547210055, 1179.2908183774266, 1.0008517010010778], "isController": false}, {"data": ["POST new visit", 5000, 0, 0.0, 8912.600999999997, 200, 23491, 8514.0, 13241.300000000003, 14930.9, 17887.89, 7.180596305439589, 1802.6797028934034, 3.3869414214134], "isController": false}, {"data": ["POST new pet", 5000, 0, 0.0, 8608.840600000018, 217, 23346, 8191.5, 12906.000000000005, 14325.699999999999, 17602.389999999985, 7.156392948949155, 35.48377797791823, 1.8659735521185785], "isController": false}, {"data": ["Edit Owner", 5000, 0, 0.0, 4231.043600000003, 63, 11398, 4140.5, 6193.0, 6927.799999999999, 8101.889999999998, 7.159282582610961, 37.653814669352116, 0.9088932966205322], "isController": false}]}, function(index, item){
        switch(index){
            // Errors pct
            case 3:
                item = item.toFixed(2) + '%';
                break;
            // Mean
            case 4:
            // Mean
            case 7:
            // Median
            case 8:
            // Percentile 1
            case 9:
            // Percentile 2
            case 10:
            // Percentile 3
            case 11:
            // Throughput
            case 12:
            // Kbytes/s
            case 13:
            // Sent Kbytes/s
                item = item.toFixed(2);
                break;
        }
        return item;
    }, [[0, 0]], 0, summaryTableHeader);

    // Create error table
    createTable($("#errorsTable"), {"supportsControllersDiscrimination": false, "titles": ["Type of error", "Number of errors", "% in errors", "% in all samples"], "items": []}, function(index, item){
        switch(index){
            case 2:
            case 3:
                item = item.toFixed(2) + '%';
                break;
        }
        return item;
    }, [[1, 1]]);

        // Create top5 errors by sampler
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 75000, 0, "", "", "", "", "", "", "", "", "", ""], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
