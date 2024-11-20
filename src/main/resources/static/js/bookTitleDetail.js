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

async function getBookTitleById(id) 
{
    const response = await fetch("http://localhost:8080/Test/book-titles/details/"+id);
    const responseData = response.json();
    return responseData;
}

async function displayBookTitle(bookTitle) {
    const newDiv = document.createElement("div"); 
    const bookTitleDetail = document.getElementById("book_Title_Detail"); 

    const titleName = document.createElement('span');
    titleName.innerHTML= bookTitle.name;

    const titleType = document.createElement('span'); 
    titleType.innerHTML = bookTitle.type.name;

    const titleAmountRemaining = document.createElement('span'); 
    titleAmountRemaining.innerHTML=bookTitle.amountRemaining;

    const titleAuthor = document.createElement('span'); 
    titleAuthor.innerHTML= bookTitle.author; 

    const titleImage = document.createElement('img');
    titleImage.style.width="20vw";
    titleImage.style.height="20vh";
    const base64String = byteToBase64(bookTitle.imageData);
    titleImage.src='data:image/jpeg;base64,'+base64String; 

    const addToCartButton = document.createElement('button');
    addToCartButton.innerHTML= "Thêm vào giỏ sách"; 

    const amountButton = document.createElement('input'); 
    amountButton.type="number"; 
    amountButton.min="1"; 
    amountButton.max=bookTitle.amountRemaining;


    const br1= document.createElement('br');
    const br2=document.createElement('br'); 
    const br3=document.createElement('br'); 
    const br4 = document.createElement('br'); 
    const br5= document.createElement('br'); 
    const br6= document.createElement('br');

    newDiv.appendChild(titleImage); 
    newDiv.appendChild(br1);  
    newDiv.appendChild(titleName);
    newDiv.appendChild(br2);  
    newDiv.appendChild(titleType);
    newDiv.appendChild(br3);
    newDiv.appendChild(titleAmountRemaining);
    newDiv.appendChild(br4); 
    newDiv.appendChild(titleAuthor);  
    newDiv.appendChild(br5);
    newDiv.appendChild(amountButton); 
    newDiv.appendChild(br6); 
    newDiv.appendChild(addToCartButton); 
    
    bookTitleDetail.appendChild(newDiv);  

    addToCartButton.addEventListener('click',async function(e) {
        const token = localStorage.getItem("token"); 
        console.log(token);
        if(token==null) 
        {
            window.location.href="http://localhost:8080/Test/loginPage";
            return;
        }  
        const bearerToken = "Bearer "+token; 
        const requestObject={
            userId:localStorage.getItem("userId"),
            bookTitleId:bookTitle.id,
            amount:amountButton.value
        }
        const response =  await fetch(
            "http://localhost:8080/Test/reader/addToCart",{
                method:'POST',
                headers: {
                    'Content-Type':'application/json', 
                    'Authorization': bearerToken
                }, 
                body:JSON.stringify(requestObject)
            }
        ); 
        if(response.ok) {
            alert("Thêm sách vào giỏ thành công");
        } 
        else { 
            alert("Thêm sách vào giỏ thất bại");

        }

    })
    

}   


async function mainFunction() 
{ 
    const titleId = localStorage.getItem("bookTitleId"); 
    const bookTitle = await getBookTitleById(titleId); 
    console.log(titleId);   
    console.log(bookTitle); 
    
    displayBookTitle(bookTitle);

} 
mainFunction();