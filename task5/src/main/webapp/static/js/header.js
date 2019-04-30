const USER_TYPE_GUEST = "GUEST";
const USER_TYPE_PLAIN = "CLIENT";
const USER_TYPE_ADMIN = "ADMIN";


console.log(userType);
console.log(userLogin);
console.log(contentPath);

$(function () {
    console.log("User type: " + userType);
    let topUserStatus = userLogin + " " + userType;

    $('#userLoginLabel').html(topUserStatus);

    if (userType === USER_TYPE_PLAIN || userType === USER_TYPE_ADMIN) {
        $('#a-sign-in-out')
            .html(logOutText)
            .attr("href", contentPath + "/log_out");

        $('#navigation-menu').show();

    } else {
        $('#a-sign-in-out')
            .html(signInText)
            .attr("href", contentPath + "/sign_in");

        $('#navigation-menu').hide();
    }

    console.log("aaa");
    if (userType === USER_TYPE_ADMIN) {
        $('#admin-block').show();
        // console.log("aaa");
    } else {
        $('#admin-block').hide();
        //  console.log("bbb");
    }

    $('#a-en').attr('href', location.pathname + '?lang=en_us');
    $('#a-ru').attr('href', location.pathname + '?lang=ru_ru');
});
