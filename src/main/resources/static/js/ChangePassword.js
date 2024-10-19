async function changePassword() 
{
    const request = { 
        oldPassword:document.getElementById("oldPassword").value , 
        newPassword:document.getElementById("newPassword").value, 
        repeatNewPassword:document.getElementById("repeatNewPassword").value 

    };  
    let bearerToken= 'Bearer '; 
    if(localStorage.getItem("token")!=null) { 
        bearerToken = bearerToken+localStorage.getItem("token");
    }
    let response = await fetch('http://localhost:8080/Test/update/password', { 
        method:'PUT',  
        headers: { 
            'Content-Type':'application/json', 
            'Authorization': bearerToken
        }, 
        body: JSON.stringify(request)
    }) 
    let responseData = await response.text(); 
    document.getElementById('changePassword_state').innerHTML=responseData;
} 
document.getElementById("changePassword").addEventListener('submit', 
    function(event) { 
        event.preventDefault(); 
        changePassword();
    }
)