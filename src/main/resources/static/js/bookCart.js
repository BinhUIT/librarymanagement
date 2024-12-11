import displayOnUi from "./borrowingCard";
const bearerToken = "Bearer "+localStorage.getItem("token"); 
const cartTable = document.getElementById("cart_Table");
async function fetchBookCart() 
{  
    
    let response = await fetch("http://localhost:8080/Test/reader/cart",{
        method:'GET',  
        headers:{
            'Content-Type':'application/json',
            'Authorization':bearerToken
        }
    });
    let responseData = await response.json(); 
    return responseData;
} 
function displayCart(cart) 
{
    const cartTable= document.getElementById("cart_Table_Body");

    if(cart==undefined||cart==null) 
    {
        return;
    } 
    for(let i=0;i<cart.length;i++)
    {
        const cartDetail = document.createElement('tr');   

        const cartDetailId = document.createElement('th'); 
        cartDetailId.innerHTML= cart[i].bookTitleImagePath.id;

        const cartDetailName = document.createElement('th'); 
        cartDetailName.innerHTML = cart[i].bookTitleImagePath.name; 

        const cartDetailAmount = document.createElement('th'); 
        const cartDetailAmountInput = document.createElement('input'); 
        cartDetailAmountInput.type="number"; 
        cartDetailAmountInput.min="1";
        cartDetailAmountInput.max=cart[i].bookTitleImagePath.amountRemaining;
        cartDetailAmountInput.value= cart[i].amount;
        cartDetailAmount.appendChild(cartDetailAmountInput);

        const saveButtonTh = document.createElement('th'); 
        const saveButton = document.createElement("button"); 
        saveButton.innerHTML="Lưu";  
        saveButtonTh.appendChild=saveButton;

        const deleteButtonTh = document.createElement('th'); 
        const deleteButton = document.createElement("Button");
        deleteButton.innerHTML="Xóa" ;
        deleteButtonTh.appendChild(deleteButton);

        saveButton.addEventListener("click", async function(e) {  
            const requestObject={
                cartDetailId:cart[i].id,
                userId:localStorage.getItem("userId"),
                amount:cartDetailAmountInput.value
            };
            const response = await fetch("http://localhost:8080/Test/reader/cart/updateItem",{
                method:'PUT', 
                headers:{
                    'Content-Type':'application/json', 
                    'Authorization':bearerToken
                },
                body:JSON.stringify(requestObject)
            }); 
            if(!response.ok)
            {
                alert("Thất bại, vui lòng thử lại");
                return;
            }
            alert("Cập nhật thành công");
            cart[i].amount=requestObject.amount;

        });
        deleteButton.addEventListener("click", async function(e) {
            const response =await fetch("http://localhost:8080/reader/deleteItemFromCart/"+i,{
                method:'DELETE', 
                headers:{
                    'Content-Type':'application/json',
                    'Authorization':bearerToken
                }
            });
            if(!response.ok){
                alert("Thất bại, vui lòng thử lại"); 
                return;
            }  
            cart.splice(i,1);
            cartTable.removeChild(cartTable.children[i+1]);
        });
        cartDetail.appendChild(cartDetailId);
        cartDetail.appendChild(cartDetailName); 
        cartDetail.appendChild(cartDetailAmount); 
        cartDetail.appendChild(saveButtonTh); 
        cartDetail.appendChild(deleteButtonTh);
        cartTable.appendChild(cartDetail);



    }
}  
async function setupBorrowingButton(cart) 
{ 
    const listRequest =[];
    for(let i=0;i<cart.length;i++) 
    {
        const newRequest = {
            bookTitleId:cart[i].bookTitleImagePath.id,
            amount:cart[i].amount
        };
        listRequest.push(newRequest);
    }
    
    const borrowingRequest={
        serviceRequest: {
            readerId:localStorage.getItem("userId"),
            implementDate:new Date(),
            serviceTypeId:1

        },
        listBook:listRequest
    };
    const response = await fetch("http://localhost:8080/Test/reader/borrow",{
        method:'POST',
        headers:{
            "Content-Type":'application/json',
            "Authorization":bearerToken
        },
        body:JSON.stringify(borrowingRequest)
    });
    if(!response.ok)
    {
        alert("Thất bại, vui lòng thử lại"); 
        return null;
    } 
    const responseData = await response.json();
    console.log(responseData.data); 
    return responseData.data;
}
function displayBorrowingCard(borrowingResponse) {
    const serviceInfo = borrowingResponse[0].service;

    const borrowingZone = document.getElementById("borrowing_card_zone");
    borrowingZone.innerHTML= displayOnUi(borrowingResponse);

}
async function mainFunction() 
{ 
    const cartData= await fetchBookCart();  
    let borrowingResponse = null;
    displayCart(cartData); 
    document.getElementById("borrow_Book").addEventListener("click", async function() {
        borrowingResponse= await setupBorrowingButton(cartData);
    });
    
}
mainFunction();