function submitForm() {
    var email = document.getElementById("email").value;
    var password = document.getElementById("password").value;

    // Send data to the backend
    fetch('/auth/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ email: email, password: password})
    })
        .then(response => {
            if(response.ok){
                return response.json();
            } else {
                throw new Error('Error: '+ response.status)
            }
        })
        .then(data => {
            // Handle the response from the backend
            console.log(data);
        })
        .catch(error => {
            console.error('Error:', error);
        });
}