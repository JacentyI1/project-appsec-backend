// userAccount.js
document.addEventListener('DOMContentLoaded', function() {
    fetch('/users/account', {
        method: 'GET',
        credentials: 'include', // This is required to include the session cookie in the request
    })
        .then(response => response.json())
        .then(data => {
            // The response data is the UserAccountResponseDto object
            // You can use this data to update your UI
            document.getElementById('username').textContent = data.username;
            document.getElementById('fullName').textContent = data.fullName;
            document.getElementById('email').textContent = data.email;
            document.getElementById('createdAt').textContent = data.createdAt;
            document.getElementById('role').textContent = data.role;
            // document.getElementById('isActive').textContent = data.isActive;
        })
        .catch(error => console.error('Error:', error));
});