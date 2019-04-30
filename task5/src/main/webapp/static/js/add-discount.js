$(function () {
    $('#add-discount-form')
        .validate({
            rules: {
                start_date: {
                    required: true
                },
                finish_date: {
                    required: true
                },
                percent: {
                    required: true,
                    number: true,
                    range: [0.01, 100]
                }
            }
        })
});
