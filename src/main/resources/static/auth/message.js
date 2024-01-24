document.addEventListener('DOMContentLoaded', function (){
    fetch('/messages')
        .then(response => response.json())
        .then(data => {
            data.forEach(message ={
            //     ...............
            })
        }).catch(error => console.error('Error fetching message display', error));
});