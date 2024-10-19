async function login() 
{
    const requestData = { 
        nameOrEmail: document.getElementById("nameOrEmail_login").value,
        password: document.getElementById("password_login").value
    }; 
    let response = await fetch( 'http://localhost:8080/Test/login',
        { 
            method:'POST',  
            headers : { 
            'Content-Type':'application/json'
        }, 
        body:JSON.stringify(requestData) 
    }

    ); 

    let responseData = await response.json();  
    console.log(responseData);
    if(responseData.userId<0) 
        document.getElementById("state_login").innerHTML=responseData.token; 
    else { 
        localStorage.setItem("userId", responseData.userId); 
        localStorage.setItem("token", responseData.token); 
        
        window.location.href="http://localhost:8080/Test/homePage";
    } 

} 
document.getElementById("LoginForm").addEventListener( 
    'submit', function(event) 
    {
        event.preventDefault(); 
        login();
    }
); 
console.log("Load login page");