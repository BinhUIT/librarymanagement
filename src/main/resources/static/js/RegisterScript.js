async function register() 
{
    const requestData= { 
        fullName: document.getElementById("fullName_register").value, 
        address: document.getElementById("address_register").value,
        phoneNumber: document.getElementById("phoneNumber_register").value,
        email: document.getElementById("email_register").value,
        password: document.getElementById("password_register").value,
        repeatPassword: document.getElementById("repeatPassword_register").value
    }; 
    let response = await fetch(
        "http://localhost:8080/Test/register", { 
            method:'POST', 
            headers : { 
                'Content-Type':'application/json'
            }, 
            body: JSON.stringify(requestData)
        }
    );
    let responseData = await response.text(); 
    document.getElementById("state_Register").innerHTML=responseData;
}  
document.getElementById("registerForm").addEventListener(
    'submit', function(event) { 
        event.preventDefault(); 
        register();
    }
)