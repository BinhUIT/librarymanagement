async function changeInfo() 
{
    const requestData = { 
        fullName: document.getElementById("fullName_normalInfo").value,
        address: document.getElementById("address_normalInfo").value,
        phoneNumber: document.getElementById("phoneNumber_normalInfo").value
    };  
    let bearerToken= 'Bearer '; 
    if(localStorage.getItem("token")!=null) { 
        bearerToken = bearerToken+localStorage.getItem("token");
    } 
    console.log(bearerToken);
    let response= await fetch("http://localhost:8080/Test/update/normal", { 
        method:'PUT', 
        headers: {  
            'Content-Type':'application/json', 
            'Authorization': bearerToken
        }, 
        body:JSON.stringify(requestData)
    }); 
    let responseData = await response.text(); 
    document.getElementById("changeNormalInfo_state").innerHTML=responseData; 
    if(response.status=='OK') 
    { 
        fetchInfo();
    }
} 
async function fetchInfo() {
    const url='http://localhost:8080/Test/find/user/' +localStorage.getItem("userId");  
    let response = await fetch(url);  
    let responseData = await response.json(); 
    document.getElementById("fullName_normalInfo").value=responseData.fullname;  
    document.getElementById("address_normalInfo").value=responseData.address; 
    document.getElementById("phoneNumber_normalInfo").value=responseData.phoneNumber;
    console.log(responseData);

} 
fetchInfo(); 
document.getElementById("changeNormalInfo").addEventListener('submit', function(event) { 
    event.preventDefault(); 
    changeInfo(); 
})