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
async function getBookByType(id)
{ 
    let response= await fetch("http://localhost:8080/Test/book-titles/get/byBookType/"+id)
    let responseData= await response.json();  
    
    return responseData;
}

async function getBookType(id) 
{ 
    let response = await fetch("http://localhost:8080/Test/book-types/details",{
        method:'GET',  
        headers:{
            'Content-Type':'text/plain'
        }, 
        body: id
    });
    let responseData= await response.json(); 
    return responseData;
}
async function displayListBookTitle(bookTitleList) {
     
      
    const bookTitleArea = document.getElementById("book_title_area"); 
    for(let i=0;i<bookTitleList.length;i++) 
    {
        const newDiv= document.createElement('div');

        const newTitleName = document.createElement('span'); 
        newTitleName.innerHTML= "Tên: "+bookTitleList[i].name; 
        
        const newAmountRemaining = document.createElement('span'); 
        newAmountRemaining.innerHTML = "Còn: " + bookTitleList[i].amountRemaining;

        const newBookTitleImage = document.createElement('img'); 
        newBookTitleImage.style.width="10vw"; 
        newBookTitleImage.style.height="10vh";
        const base64String = byteToBase64(bookTitleList[i].imageData);
        newBookTitleImage.src='data:image/jpeg;base64,'+base64String; 
        const br1 = document.createElement('br'); 
        const br2 = document.createElement('br');
        newDiv.appendChild(newBookTitleImage);  
        newDiv.appendChild(br1); 
        newDiv.appendChild(newTitleName); 
        newDiv.appendChild(br2); 
        newDiv.appendChild(newAmountRemaining); 
        newDiv.addEventListener("click", function(e) {
            localStorage.setItem("bookTitleId",bookTitleList[i].id);  
            window.location.href="http://localhost:8080/Test/bookTitleDetail";
            
        })  
        bookTitleArea.appendChild(newDiv); 

    }

}
function setUpForSearch() 
{
    const searchForm = document.getElementById("search_book_title");
    searchForm.addEventListener('submit', function(e) { 
        e.preventDefault();
        const searchInfo= document.getElementById("search").value;
        const searchBy = document.getElementById("search_type").value;  
        const bookTitleArea = document.getElementById("book_title_area");
        if(searchBy=="Tên sách") 
        {
            bookTitleArea.innterHTML=""; 
            const data= findByName(searchInfo);
            displayListBookTitle(data); 
            return;
        } 
        if(searchBy=="Thể loại") 
        {
            bookTitleArea.innterHTML=""; 
            const data= findByType(searchInfo);
            displayListBookTitle(data); 
            return;
        } 
        if(searchBy=="Tác giả") 
        {
            bookTitleArea.innterHTML=""; 
            const data= findByAuthor(searchInfo);
            displayListBookTitle(data); 
            return;
        }  
        

       
    })
} 
async function findByType(type) 
{ 
    const bookType = await fetch("http://localhost:8080/Test/book-types/findByName"+type); 
    const bookTypeData = await bookType.json();

    const bookTypeId = bookTypeData.id; 

    const listBookTitle = await fetch("http://localhost:8080/Test/book-titles/get/byBookType/"+bookTypeId); 
    const listBookTitleData = await listBookTitle.json(); 
    return listBookTitleData;
} 
async function findByAuthor(author) 
{
    const listBookTitle = await fetch("http://localhost:8080/Test/book-titles/get/byAuthor/"+author);  
    const listBookTitleData = await listBookTitle.json();
    return listBookTitleData;

} 
async function findByName(name)
{
    const bookTitle= await fetch("http://localhost:8080/Test/book-titles/get/byName/"+name); 
    const bookTitleData = await bookTitle.json(); 
    let res=[]; 
    res.push(bookTitleData); 
    return res;
}
async function mainFunction()
{ 
    const id= localStorage.getItem('bookTypeId');
    const bookTitleList = await getBookByType(id);
    displayListBookTitle(bookTitleList);
    setUpForSearch();

} 
mainFunction();


