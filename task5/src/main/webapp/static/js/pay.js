const MAX_PAY_MONEY_DOLLARS = 10000000;

$(function () {
    $('#pay-form').validate({
            rules: {
                money: {
                    required: true,
                    range: [0.01, MAX_PAY_MONEY_DOLLARS]
                }
            }
    });

    $('#back-button').click(
        function () {
            window.location = contentPath + "/user_info";
        }
    )
});