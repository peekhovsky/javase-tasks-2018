const ERROR_CODES_MESSAGES_ENG = {
    400: "Bad request. ",
    401: "Access denied. ",
    404: "Page not found. ",
    500: "Internal server error"
};

$(function () {
    let message = "";
    if (ERROR_CODES_MESSAGES_ENG.hasOwnProperty(errorCode)) {
        message = ERROR_CODES_MESSAGES_ENG[errorCode];
    }
    $('#error-message').html("Error: " + errorCode + " " + message);
});
