const MAX_FREE_MB_PER_MONTH = 10000000;
const MAX_SPEED = 10000000;
const MAX_SPEED_OVER_TRAFFIC = 10000000;
const MAX_DAY_BILL = 10000000;

$(function () {
    $('#add-tariff-form')
        .validate({
            rules: {
                tariff: {
                    required: true,
                    minlength: 4,
                    maxlength: 29
                },
                description: {
                    required: true,
                },
                free_mb_per_month: {
                    number: true,
                    range: [0, MAX_FREE_MB_PER_MONTH],
                    required: true,
                },
                speed: {
                    number: true,
                    range: [0, MAX_SPEED],
                    required: true,
                },
                speed_over_traffic: {
                    number: true,
                    range: [0, MAX_SPEED_OVER_TRAFFIC],
                    required: true,
                },
                tariff_day_bill: {
                    number: true,
                    range: [0, MAX_DAY_BILL],
                    required: true,
                },
            }
        })
});