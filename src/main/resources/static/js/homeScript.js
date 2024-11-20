async function fetchBookType() 
{  
    let response = await fetch("http://localhost:8080/Test/book-types"); 
    let responseData= await response.json(); 
    return responseData;
}  
 
function displayListBookType(listBookType)
{  
    let bookTypeZone= document.getElementById("Book-Types-Area"); 
    for(let i=0;i<listBookType.length;i++) 
    { 
        let newDiv = document.createElement("div"); 
        
        let newName = document.createElement("h3"); 
        newName.innerHTML=listBookType[i].name;  
        newDiv.appendChild(newName);

        let newImage = document.createElement("img"); 
        newImage.style.width="10vw"; 
        newImage.style.height="10vh";
        const base64String = byteToBase64(listBookType[i].imageData); 
        newImage.src='data:image/jpeg;base64,' + base64String; 
        newDiv.appendChild(newImage);  
        bookTypeZone.appendChild(newDiv); 
        newDiv.addEventListener('click',function(e) { 
            localStorage.setItem("bookTypeId",listBookType[i].id);
            window.location.href="http://localhost:8080/Test/bookTitle";
        })

    }
}
function byteToBase64(byteArray) 
{
    let binary ="";
    const length = byteArray.length;
    for(let i=0;i<length;i++) 
    {
        binary+=String.fromCharCode(byteArray[i]&0xFF);

    } 
    return window.btoa(binary);
}   
async function mainFunction() 
{ 
    const listBookType= await fetchBookType(); 
    displayListBookType(listBookType);

} 
mainFunction(); 




