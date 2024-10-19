async function changeEmail() 
{
    const request = document.getElementById("newEmail").value ; 
    let bearerToken= 'Bearer '; 
    if(localStorage.getItem("token")!=null) { 
        bearerToken = bearerToken+localStorage.getItem("token");
    }
    let response= await fetch('http://localhost:8080/Test/update/email', { 
        method:'PUT', 
        headers: { 
            'Content-type': 'application/json', 
            'Authorization': bearerToken
        },
        body:request
    }) 
    let responseData = await response.text(); 
    document.getElementById('changeEmail_state').innerHTML= responseData; 
}  
document.getElementById("changeEmail").addEventListener('submit', function(event) {
    event.preventDefault();
    changeEmail();
})