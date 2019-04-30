const CLIENT_USER_STATUS = {
    ACTIVE: "ACTIVE",
    BLOCKED: "BLOCKED"
};

let isFormChanged = false;

const NAME_PATTERN = /^[A-Z][a-z]+$/;

$(function () {

    if (clientUserStatus === CLIENT_USER_STATUS.ACTIVE) {
        $('#client-user-status').css("color", "green");
    } else {
        $('#client-user-status').css("color", "red");
    }

    setAllEditFormsDisable();

    $('#edit-button').click(function () {
            setAllEditFormsEnable();
        }
    );

    $('#back-button').click(function () {
            location.reload();
        }
    );

    $('#edit-user-form').validate({
        rules: {
            login: {
                required: true,
                minlength: 3,
                maxlength: 30,
                lettersonly: true
            },
            first_name: {
                required: true,
                minlength: 3,
                lettersonly: true
            },
            last_name: {
                required: true,
                minlength: 3,
                lettersonly: true
            }
        }
    });
});

function setAllEditFormsDisable() {
    $('#login').attr('readonly', true)
        .attr('value', login)
        .css('border', 'none')
        .css('outline', 'none')
        .bind("change paste keyup", function () {
            isFormChanged = true;
            console.log("Form has been changed");
        });

    $('#first-name').attr('readonly', true)
        .attr('value', firstName)
        .css('border', 'none')
        .css('outline', 'none')
        .bind("change paste keyup", function () {
            isFormChanged = true;
            console.log("Form has been changed");
        });


    $('#last-name').attr('readonly', true)
        .attr('value', lastName)
        .css('border', 'none')
        .css('outline', 'none')
        .bind("change paste keyup", function () {
            isFormChanged = true;
            console.log("Form has been changed");
        });

    $('#submit-button').hide();
    $('#edit-button').show();
    $('#back-button').hide();
}

function setAllEditFormsEnable() {
    $('#login').attr('readonly', false)
        .css('border', '')
        .css('outline', '');
    $('#first-name').attr('readonly', false)
        .css('border', '')
        .css('outline', '');
    $('#last-name').attr('readonly', false)
        .css('border', '')
        .css('outline', '');

    $('#submit-button').show();
    $('#edit-button').hide();
    $('#back-button').show();
}

