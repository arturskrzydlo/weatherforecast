var cityName;
var tempCanvas = document.getElementById("tempChart").getContext("2d");
var pressureCanvas = document.getElementById("pressureChart").getContext("2d");
var humidityCanvas = document.getElementById("humidityChart").getContext("2d");

$(document).ready(function () {

    $("#selectCity").change(cityChange);

    $.ajax({
        type: "GET",
        dataType: "json",
        url: "http://localhost:8080/cities",
        success: function (cities) {

            $("#selectCity").empty();

            $("#selectCity").append('<option disabled selected value> -- select an option -- </option>');
            $.each(cities, function (index, value) {
                $("#selectCity").append('<option value="' + value + '">' + value + '</option>');
            });
        },
        error: function (error) {
            handleError(error.responseJSON);
        }
    })
});


function handleError(responeJSON) {


    if (responeJSON.subErrors == undefined) {
        $("#errorMessages").text(responeJSON.message);
    } else {
        var messages = "";
        $(responeJSON.subErrors).each(function (index, item) {
            messages = message + item.message + "\n";
        });

        $("#errorMessages").text(message);
    }

    $("#errorMessages").css("display", "block");

}

function clearError() {
    $("#errorMessages").text("");
    $("#errorMessages").css("display", "none");
}


function cityChange() {
    if (cityName !== this.value) {
        clearError();
        cityName = this.value;
        getWeatherForecast(cityName);
    }
}

function getWeatherForecast(cityName) {

    $.ajax({
        type: "GET",
        dataType: "json",
        url: "http://localhost:8080/weather/" + cityName,
        success: function (weatherForecast) {

            var dates = [];
            var temp = [];
            var pressure = [];
            var humidity = [];

            $(weatherForecast).each(function (index, item) {

                dates.push(item.dt);
                temp.push(item.temperature);
                pressure.push(item.pressure);
                humidity.push(item.humidity);

            })

            var tempChart = new Chart(tempCanvas, {
                type: 'line',
                data: {
                    labels: dates,
                    datasets: [
                        {
                            label: 'Temperature',
                            data: temp,
                            backgroundColor: "rgba(255, 139, 68, 0.2)"
                        }]
                },
                options: {
                    pan: {
                        enabled: true,
                        mode: 'xy'
                    },

                    zoom: {
                        enabled: true,
                        mode: 'xy',
                    }
                }
            });

            var pressureChart = new Chart(pressureCanvas, {
                type: 'line',
                data: {
                    labels: dates,
                    datasets: [{
                        label: 'Pressure',
                        data: pressure,
                        backgroundColor: "rgba(0, 125, 130, 0.2)"
                    }]
                },
                options: {
                    pan: {
                        enabled: true,
                        mode: 'xy'
                    },

                    zoom: {
                        enabled: true,
                        mode: 'xy',
                    }
                }
            });

            var humidityChart = new Chart(humidityCanvas, {
                type: 'line',
                data: {
                    labels: dates,
                    datasets: [{
                        label: 'Humidity',
                        data: humidity,
                        backgroundColor: "rgba(0, 29, 138, 0.2)"
                    }]
                },
                options: {
                    pan: {

                        enabled: true,
                        mode: 'xy'
                    },

                    zoom: {
                        enabled: true,
                        mode: 'xy',
                    }
                }
            });


        },
        error: function (error) {
            handleError(error.responseJSON);
        }
    })
}
