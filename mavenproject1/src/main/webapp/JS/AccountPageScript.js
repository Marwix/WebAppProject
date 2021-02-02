var saveCostumerData = () => {
    var saveButton = document.querySelector('.account-button');
        saveButton.addEventListener('click', (e) => {
            console.log(saveButton);
            alert('information saved');
        });

}

var savePasswordData = () => {


    var oldPassword = document.getElementById("old-password");
    var password = document.getElementById("new-password")
    var confirm_password = document.getElementById("repeat-password");

    function validatePassword(){
        if(password.value != confirm_password.value) {
          alert('Password doesnt match')
        } else {
          alert('Password matches');
        }
      }

    var saveButton = document.querySelector('.password-button');


        saveButton.addEventListener('click', (e) => {
            validatePassword();
        });
}

